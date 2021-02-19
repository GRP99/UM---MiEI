import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class threadAnonGW implements Runnable {

    private Socket s;

    private String targetServer;
    private int porta;

    private int maxBytes;

    private ArrayList<String> peers;
    private int indice_ultimo;

    //cada thread ao ser iniciada vai ter o seu id
    private int identificador_thread;

    private PipedInputStream pipe_input;

    private Cipher cipher;
    private PublicKey chave_publica;
    private PrivateKey chave_privada;
    private HashMap<String,PublicKey> chaves_peers;

    private String meu_ip;

    private HashMap<String, Key> chaves_combinadas_com_peers;

    public threadAnonGW (Socket aa,String t,int p,ArrayList<String> parceiros,int id_th,PipedInputStream pis,PublicKey publicKey, PrivateKey privateKey, String ip, HashMap<String,PublicKey> chaves_peers, HashMap<String, Key> chaves_combinadas_com_peers){
        this.s=aa;
        this.targetServer=t;
        this.porta=p;
        this.maxBytes=32768;
        this.peers=parceiros;
        this.indice_ultimo=0;
        this.identificador_thread = id_th;
        this.pipe_input=pis;
        this.meu_ip=ip;
        this.chave_publica = publicKey;
        this.chave_privada = privateKey;
        this.chaves_peers = chaves_peers;
        this.chaves_combinadas_com_peers = chaves_combinadas_com_peers;
        try {
            this.cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    public synchronized String getProximoPeer(){
        String ret = peers.get(indice_ultimo);
        if(indice_ultimo+1 >= peers.size()){
            indice_ultimo=0;
        }
        else{
            indice_ultimo++;
        }
        return ret;
    }

    private byte[] prepara_pacote_a_enviar(byte[] bufferINcs, int numBytes1){

        int tamanho_pacote = numBytes1+48;
        byte[] pacote = new byte[tamanho_pacote];

        int index = 0;

        byte[] tipo_pacote = ByteConversion.leIntToByteArray(3);
        pacote[index++]=tipo_pacote[0];pacote[index++]=tipo_pacote[1];pacote[index++]=tipo_pacote[2];pacote[index++]=tipo_pacote[3];

        byte[] idthread = ByteConversion.leIntToByteArray(identificador_thread);
        pacote[index++]=idthread[0];pacote[index++]=idthread[1];pacote[index++]=idthread[2];pacote[index++]=idthread[3];

        String[] partes = meu_ip.split("[.]");//Endereco ip da maquina a mandar
        for(int i=0;i<partes.length;i++){
            byte[] num = ByteConversion.leIntToByteArray(Integer.parseInt(partes[i]));
            pacote[index++]=num[0];pacote[index++]=num[1];pacote[index++]=num[2];pacote[index++]=num[3];
        }

        partes = targetServer.split("[.]");
        for(int i=0;i<partes.length;i++){
            byte[] num = ByteConversion.leIntToByteArray(Integer.parseInt(partes[i]));
            pacote[index++]=num[0];pacote[index++]=num[1];pacote[index++]=num[2];pacote[index++]=num[3];
        }

        byte[] porta = ByteConversion.leIntToByteArray(this.porta);
        pacote[index++]=porta[0];pacote[index++]=porta[1];pacote[index++]=porta[2];pacote[index++]=porta[3];

        byte[] size_packet = ByteConversion.leIntToByteArray(tamanho_pacote);
        pacote[index++]=size_packet[0];pacote[index++]=size_packet[1];pacote[index++]=size_packet[2];pacote[index++]=size_packet[3];

        for(int i=0;i<numBytes1;i++){
            pacote[index++]=bufferINcs[i];
        }

        return pacote;

    }


    public void run() {
        try {

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            DatagramSocket socket_udp=new DatagramSocket();

            //enviar para todos os peers a sau chave publica

            String ip_proximo = getProximoPeer();
            SocketAddress sa = new InetSocketAddress(ip_proximo,6666);
            AtomicBoolean flag = new AtomicBoolean(false);

            Runnable leCliente_escreveServidor = () -> {
                try {
                    byte[] bufferINcs = new byte[maxBytes];

                    int numBytes1;
                    while ((numBytes1 = in.read(bufferINcs, 0, maxBytes)) > 0) {

                        Key chave_para_encriptar = chaves_combinadas_com_peers.get(ip_proximo);

                        byte[] num_op = ByteConversion.leIntToByteArray(2);

                        byte[] mine_ip = new byte[16];
                        String[] partes = meu_ip.split("[.]");
                        int index = 0;
                        for(int i=0;i<partes.length;i++){
                            byte[] num = ByteConversion.leIntToByteArray(Integer.parseInt(partes[i]));
                            mine_ip[index++]=num[0];mine_ip[index++]=num[1];
                            mine_ip[index++]=num[2];mine_ip[index++]=num[3];
                        }

                        byte[] dados_plus_header = prepara_pacote_a_enviar(bufferINcs,numBytes1);

                        cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.ENCRYPT_MODE,chave_para_encriptar);

                        byte[] so_enviar = concatenate(num_op,concatenate(mine_ip,cipher.doFinal(dados_plus_header)));

                        DatagramPacket pacote_a_enviar = new DatagramPacket(so_enviar,so_enviar.length,sa);
                        socket_udp.send(pacote_a_enviar);
                    }
                    if(!s.isClosed()) {
                        s.shutdownInput();
                        s.shutdownOutput();
                        s.close();
                        envia_pacote_do_adeus(ip_proximo);
                        pipe_input.close();
                        flag.set(true);
                    }

                }catch (Exception e){
                    try {
                        s.shutdownOutput();
                        s.shutdownInput();

                        s.close();
                        flag.set(true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            };
            Thread t1 = new Thread(leCliente_escreveServidor);
            t1.start();



            Runnable lePipe_escreveCliente = () -> {
                try {
                    byte[] bufferINcs = new byte[maxBytes];

                    int numBytes1;
                    while ((numBytes1 = pipe_input.read(bufferINcs, 0, maxBytes)) > 0) {

                        out.write(bufferINcs,0,numBytes1);
                        out.flush();

                    }
                    pipe_input.close();

                    if(!s.isClosed()) {
                        s.shutdownOutput();
                        s.shutdownInput();
                        s.close();
                        flag.set(true);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            };
            Thread t2 = new Thread(lePipe_escreveCliente);
            t2.start();

            t1.join();
            t2.join();

            if(!flag.get() && !s.isClosed()) {
                s.shutdownOutput();
                s.shutdownInput();

                s.close();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void envia_pacote_do_adeus(String ip_next_anongw){
        try {
            byte[] tipo_pacote = ByteConversion.leIntToByteArray(4);

            byte[] idthread = ByteConversion.leIntToByteArray(identificador_thread);

            byte[] ip1 = new byte[16];
            int index = 0;
            String[] partes = meu_ip.split("[.]");
            for(int i=0;i<partes.length;i++){
                byte[] num = ByteConversion.leIntToByteArray(Integer.parseInt(partes[i]));
                ip1[index++]=num[0];ip1[index++]=num[1];
                ip1[index++]=num[2];ip1[index++]=num[3];
            }

            byte[] ip2 = new byte[16];
            index = 0;
            partes = targetServer.split("[.]");
            for(int i=0;i<partes.length;i++){
                byte[] num = ByteConversion.leIntToByteArray(Integer.parseInt(partes[i]));
                ip2[index++]=num[0];ip2[index++]=num[1];
                ip2[index++]=num[2];ip2[index++]=num[3];
            }

            byte[] portaserver = ByteConversion.leIntToByteArray(porta);

            byte[] dados_prontos = concatenate(tipo_pacote, concatenate(idthread,concatenate(ip1,concatenate(ip2,portaserver))));

            DatagramSocket socket_udp = new DatagramSocket();

            SocketAddress sa = new InetSocketAddress(ip_next_anongw, 6666);

            DatagramPacket pacote_a_enviar = new DatagramPacket(dados_prontos, dados_prontos.length, sa);
            socket_udp.send(pacote_a_enviar);

            socket_udp.close();
        }catch (Exception e){}
    }

    public byte[] concatenate(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        byte[] c = (byte[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }
}

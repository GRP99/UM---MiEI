import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.lang.reflect.Array;
import java.net.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class threadCommunicationWithServer implements Runnable {

    private String ipServidor;
    private int portaServidor;

    private String ip_next_anongw;
    private int identificador_thread;

    private PipedInputStream input;

    private int maxBytes = 32768;

    private Cipher cipher;
    private PublicKey chave_publica_do_anonGW_para_one_vou_enviar;
    private PublicKey minha_chave_publica;
    private PrivateKey minha_chave_privada;

    private Key chave_para_encriptar;

    private String meu_ip;


    public threadCommunicationWithServer(String ip_servidor,int porta,String ip_next_anongw,int identificador_thread,PipedInputStream in,PublicKey chave_do_anongw_dest,PublicKey minha_chave_publica,PrivateKey minha_chave_privada,Key chave_para_encriptar,String meu_ip){
        this.ipServidor=ip_servidor;
        this.portaServidor=porta;
        this.ip_next_anongw=ip_next_anongw;
        this.input=in;
        this.identificador_thread=identificador_thread;
        this.chave_publica_do_anonGW_para_one_vou_enviar=chave_do_anongw_dest;
        this.minha_chave_publica=minha_chave_publica;
        this.minha_chave_privada=minha_chave_privada;
        this.chave_para_encriptar=chave_para_encriptar;
        this.meu_ip=meu_ip;
        try {
            this.cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private void envia_pacote_do_adeus(){
        try {
            byte[] tipo_pacote = ByteConversion.leIntToByteArray(5);

            byte[] idthread = ByteConversion.leIntToByteArray(identificador_thread);

            byte[] dados_prontos = concatenate(tipo_pacote, idthread);

            DatagramSocket socket_udp = new DatagramSocket();

            SocketAddress sa = new InetSocketAddress(ip_next_anongw, 6666);

            DatagramPacket pacote_a_enviar = new DatagramPacket(dados_prontos, dados_prontos.length, sa);
            socket_udp.send(pacote_a_enviar);

            socket_udp.close();
        }catch (Exception e){}
    }


    public void run() {
        try {
            Socket socket = new Socket(ipServidor,portaServidor);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            AtomicBoolean flag = new AtomicBoolean(false);

            Runnable lepipe_escreve_servidor = () -> {
                try {
                    byte[] bufferINps = new byte[maxBytes];
                    int numBytes1;
                    while ((numBytes1 = input.read(bufferINps, 0, maxBytes)) > 0) {
                        out.write(bufferINps,0,numBytes1);
                        out.flush();
                    }

                    input.close();
                    if(!socket.isClosed()) {
                        socket.shutdownOutput();
                        socket.shutdownInput();
                        flag.set(true);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            };
            Thread t1 = new Thread(lepipe_escreve_servidor);
            t1.start();

            Runnable leservidor_escreve_anongw = () -> {
                try {
                    byte[] bufferINcs = new byte[maxBytes];
                    int numBytes1;

                    DatagramSocket socket_udp=new DatagramSocket();

                    SocketAddress sa = new InetSocketAddress(ip_next_anongw,6666);

                    while ((numBytes1 = in.read(bufferINcs, 0, maxBytes)) > 0) {

                        byte[] tipo_pacote = ByteConversion.leIntToByteArray(2);

                        byte[] idthread = ByteConversion.leIntToByteArray(identificador_thread);

                        byte[] dados_mais_header_a_enviar = concatenate(tipo_pacote,concatenate(idthread, Arrays.copyOfRange(bufferINcs,0,numBytes1)));

                        byte[] mine_ip = new byte[16];
                        int index = 0;
                        String[] partes = meu_ip.split("[.]");
                        for(int i=0;i<partes.length;i++){
                            byte[] num = ByteConversion.leIntToByteArray(Integer.parseInt(partes[i]));
                             mine_ip[index++]=num[0];mine_ip[index++]=num[1];
                             mine_ip[index++]=num[2];mine_ip[index++]=num[3];
                        }

                        cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.ENCRYPT_MODE,chave_para_encriptar);

                        byte[] dados_encriptados = cipher.doFinal(dados_mais_header_a_enviar);

                        byte[] so_enviar = concatenate(tipo_pacote,concatenate(mine_ip,dados_encriptados));

                        DatagramPacket pacote_a_enviar = new DatagramPacket(so_enviar,so_enviar.length,sa);
                        socket_udp.send(pacote_a_enviar);

                    }

                    if(!flag.get() && !socket.isClosed()){
                        socket.shutdownInput();
                        socket.shutdownOutput();
                        socket.close();
                        this.envia_pacote_do_adeus();
                    }else{
                        if(!socket.isClosed())
                            socket.close();
                    }

                }catch (Exception e){
                    try{
                        if(!socket.isClosed()) {
                            socket.shutdownInput();
                            socket.shutdownOutput();
                            socket.close();

                            this.envia_pacote_do_adeus();
                        }
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            };
            Thread t2 = new Thread(leservidor_escreve_anongw);
            t2.start();

            t1.join();
            t2.join();

        }catch (Exception e){
            e.printStackTrace();
        }
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

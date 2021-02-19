import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class threadRecieveFromPeers implements Runnable {

    private DatagramSocket ds;
    private int maxBytes;

    private HashMap<Integer, PipedOutputStream> pipes_escrita_cliente;

    private ArrayList<String> peers;

    private Cipher cipher;
    private HashMap<String,PublicKey> chaves_peers;
    private PublicKey minha_chave_publica;
    private PrivateKey minha_chave_privada;

    private HashMap<String, PipedOutputStream> pipes_escrita_comms_servidor;

    private String meu_ip;

    private HashMap<String, Key> chaves_combinadas_com_peers;

    public threadRecieveFromPeers(HashMap<Integer, PipedOutputStream> pipesEscrita, PrivateKey p, ArrayList<String> parceiros, HashMap<String,PublicKey> chaves_peers,PublicKey publicKey, String meu_ip, HashMap<String, Key> chaves_combinadas_com_peers) {
        this.maxBytes = 32768;
        this.pipes_escrita_cliente = pipesEscrita;
        this.pipes_escrita_comms_servidor = new HashMap<>();
        this.chaves_peers = chaves_peers;
        this.minha_chave_privada = p;
        this.minha_chave_publica = publicKey;
        this.peers = parceiros;
        this.meu_ip = meu_ip;
        this.chaves_combinadas_com_peers = chaves_combinadas_com_peers;
        try {
            this.cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public void data(byte[] a) {
        if (a == null)
            return;

        int tipoPacote = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 0, 4));
        int idThread = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 4, 8));


        switch (tipoPacote) {
            case 0:
            case 1:
                //pr = new pacote_recebido(tipoPacote, idThread, ip_origem, null, -1, null);
                break;

            case 2:

                if(pipes_escrita_cliente.containsKey(idThread)){
                    try {
                        pipes_escrita_cliente.get(idThread).write(a,8,a.length-8);
                        pipes_escrita_cliente.get(idThread).flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 3:

                String ip_origem = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 8, 12)) + "." +
                        ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 12, 16)) + "." +
                        ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 16, 20)) + "." +
                        ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 20, 24));

                String ipServidor = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 24, 28)) + "." +
                        ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 28, 32)) + "." +
                        ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 32, 36)) + "." +
                        ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 36, 40));

                int portaServidor = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 40, 44));

                int tamanho_pacote = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(a, 44, 48));

                try{
                    if(!pipes_escrita_comms_servidor.containsKey(idThread+ipServidor+portaServidor+ip_origem)){
                        PipedOutputStream out = new PipedOutputStream();
                        PipedInputStream in = new PipedInputStream(out);
                        pipes_escrita_comms_servidor.put(idThread+ipServidor+portaServidor+ip_origem,out);

                        Thread serverComms = new Thread(new threadCommunicationWithServer(ipServidor,portaServidor,ip_origem,idThread,in,chaves_peers.get(ip_origem),minha_chave_publica,minha_chave_privada,chaves_combinadas_com_peers.get(ip_origem),meu_ip));
                        serverComms.start();

                        out.write(a,48,tamanho_pacote-48);
                        out.flush();
                    }
                    else{
                        pipes_escrita_comms_servidor.get(idThread+ipServidor+portaServidor+ip_origem).write(a,48,tamanho_pacote-48);
                        pipes_escrita_comms_servidor.get(idThread+ipServidor+portaServidor+ip_origem).flush();
                    }

                }catch (Exception e){}

                break;

            default:
                break;
        }

    }

    public byte[] security(byte[] pacote){
        if (pacote == null)
            return null;

        try {

            int tipoPacote = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 0, 4));

            switch (tipoPacote){

                case 0: //recebe a chave publica do peer ao qual acabou de enviar a sua chave

                    String ip_origem0 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 4, 8)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 8, 12)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 12, 16)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 16, 20));

                    int tamanho_pacote0 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 20, 24));

                    chaves_peers.put(ip_origem0,KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Arrays.copyOfRange(pacote,24,tamanho_pacote0+24))));

                    Key chave_a_enviar = KeyGenerator.getInstance("AES").generateKey();
                    chaves_combinadas_com_peers.put(ip_origem0,chave_a_enviar);

                    byte[] aux = chave_a_enviar.getEncoded();

                    byte[] tipo_pacote01 = ByteConversion.leIntToByteArray(3);

                    int index0 = 0;
                    String[] partes0 = meu_ip.split("[.]");//Endereco ip da maquina a mandar
                    byte[] ip = new byte[16];
                    for(int i=0;i<partes0.length;i++){
                        byte[] num0 = ByteConversion.leIntToByteArray(Integer.parseInt(partes0[i]));
                        ip[index0++]=num0[0];ip[index0++]=num0[1];
                        ip[index0++]=num0[2];ip[index0++]=num0[3];
                    }


                    cipher.init(Cipher.ENCRYPT_MODE,minha_chave_privada);
                    byte[] dados_prontinhos = cipher.doFinal(aux);

                    byte[] ty_next = concatenate(tipo_pacote01,concatenate(ip,dados_prontinhos));

                    DatagramSocket ds0 = new DatagramSocket();
                    DatagramPacket dp0 = new DatagramPacket(ty_next,ty_next.length,new InetSocketAddress(ip_origem0,6666));
                    ds0.send(dp0);
                    ds0.close();

                    break;

                case 1: //pacote com a chave publica de um peer, se receber uma chave entao manda a sua de volta

                    String ip_origem = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 4, 8)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 8, 12)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 12, 16)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 16, 20));

                    int tamanho_pacote = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 20, 24));

                    byte[] chave_publica = Arrays.copyOfRange(pacote,24,tamanho_pacote+24);
                    EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(chave_publica);
                    PublicKey newPublicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);

                    chaves_peers.put(ip_origem,newPublicKey);

                    boolean flag = false;
                    for(int i=0;i<peers.size();i++){
                        if(peers.get(i).equals(ip_origem)){
                            flag = true;
                        }
                    }
                    if(!flag){
                        peers.add(ip_origem);
                    }

                    byte[] bites = minha_chave_publica.getEncoded();
                    byte[] packet = new byte[bites.length + 24];

                    int index = 0;
                    byte[] tipo_pacote = ByteConversion.leIntToByteArray(0);
                    packet[index++]=tipo_pacote[0];packet[index++]=tipo_pacote[1];packet[index++]=tipo_pacote[2];packet[index++]=tipo_pacote[3];

                    String[] partes = meu_ip.split("[.]");//Endereco ip da maquina a mandar
                    for(int i=0;i<partes.length;i++){
                        byte[] num = ByteConversion.leIntToByteArray(Integer.parseInt(partes[i]));
                        packet[index++]=num[0];packet[index++]=num[1];packet[index++]=num[2];packet[index++]=num[3];
                    }

                    byte[] size_packet = ByteConversion.leIntToByteArray(bites.length);
                    packet[index++]=size_packet[0];packet[index++]=size_packet[1];packet[index++]=size_packet[2];packet[index++]=size_packet[3];

                    for (byte bite : bites) {
                        packet[index++] = bite;
                    }

                    DatagramSocket ds = new DatagramSocket();
                    DatagramPacket dp = new DatagramPacket(packet,index,new InetSocketAddress(ip_origem,6666));
                    ds.send(dp);
                    ds.close();

                    break;

                case 2: //pacote normal com os dados encriptados

                    String ip_origem2 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 4, 8)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 8, 12)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 12, 16)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 16, 20));

                    
                    cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE,this.chaves_combinadas_com_peers.get(ip_origem2));
                    return cipher.doFinal(Arrays.copyOfRange(pacote,20,pacote.length));

                case 3: //este pacote vai (vai ser enviado depois de um anongw receber um pacote 0) ter de ser desencriptado com a chave publica de quem o enviou
                    //dentro dele vai estar a chave combinada pelos dois

                    String ip_origem3 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 4, 8)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 8, 12)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 12, 16)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 16, 20));

                    int tamanho_pacote3 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 20, 24));

                    cipher.init(Cipher.DECRYPT_MODE,this.chaves_peers.get(ip_origem3));
                    byte[] chave = cipher.doFinal(Arrays.copyOfRange(pacote,20,pacote.length));

                    Key chave_combinada = new SecretKeySpec(chave,"AES");
                    chaves_combinadas_com_peers.put(ip_origem3,chave_combinada);


                    break;

                case 4: //pacote para terminar comunicacao com o servidor

                    int idThread4 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 4, 8));

                    String ip_origem4 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 8, 12)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 12, 16)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 16, 20)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 20, 24));

                    String ipServidor4 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 24, 28)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 28, 32)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 32, 36)) + "." +
                            ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 36, 40));

                    int portaServidor4 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 40, 44));

                    if(pipes_escrita_comms_servidor.containsKey(idThread4+ipServidor4+portaServidor4+ip_origem4)){
                        pipes_escrita_comms_servidor.get(idThread4+ipServidor4+portaServidor4+ip_origem4).close();
                        pipes_escrita_comms_servidor.remove(idThread4+ipServidor4+portaServidor4+ip_origem4);
                    }

                    break;
                case 5: //pacote para terminar comunicacao com o cliente

                    int idThread5 = ByteConversion.byteArrayToLeInt(Arrays.copyOfRange(pacote, 4, 8));

                    if(pipes_escrita_cliente.containsKey(idThread5)){
                        pipes_escrita_cliente.get(idThread5).close();
                        pipes_escrita_cliente.remove(idThread5);
                    }

                    break;


                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return  null;
    }

    public void run() {
        try {
            ds = new DatagramSocket(6666);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        byte[] bites = new byte[maxBytes];

        DatagramPacket dp_receber = null;

        while(true){

            dp_receber = new DatagramPacket(bites,maxBytes);

            try {
                ds.receive(dp_receber);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] dados = security(Arrays.copyOfRange(dp_receber.getData(),0,dp_receber.getLength()));
            if(dados!=null){
                data(dados);
            }

            bites = new byte[maxBytes];


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

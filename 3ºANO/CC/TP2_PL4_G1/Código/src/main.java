import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Array;
import java.net.*;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.*;

public class main {

    public static byte[] concatenate(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        byte[] c = (byte[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    public static void envia_chave_aos_peers(ArrayList<String> peers,String meu_ip,KeyPair keyPair){
        try {
            DatagramSocket socket_udp = new DatagramSocket();

            for (String p : peers) {

                byte[] tipo_pacote = ByteConversion.leIntToByteArray(1);

                int index = 0;
                byte[] mineip = new byte[16];
                String[] partes = meu_ip.split("[.]");//Endereco ip da maquina a mandar
                for (int i = 0; i < partes.length; i++) {
                    byte[] num = ByteConversion.leIntToByteArray(Integer.parseInt(partes[i]));
                    mineip[index++] = num[0];mineip[index++] = num[1];
                    mineip[index++] = num[2];mineip[index++] = num[3];
                }

                PublicKey publicKey = keyPair.getPublic();
                byte[] bites = publicKey.getEncoded();

                byte[] size_packet = ByteConversion.leIntToByteArray(bites.length);

                byte[] so_enviar = concatenate(tipo_pacote,concatenate(mineip,concatenate(size_packet,bites)));

                DatagramPacket pacote_a_enviar = new DatagramPacket(so_enviar, so_enviar.length, new InetSocketAddress(p, 6666));
                socket_udp.send(pacote_a_enviar);

            }
            socket_udp.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        ArrayList<String> peers = new ArrayList<>();
        if(args.length >= 6){
            String target_server=args[2];
            int porta_servidor=Integer.parseInt(args[4]);
            for(int i=6;i<args.length;i++){
                peers.add(args[i]);
            }

            int identificador = 0;

            String meu_ip = InetAddress.getLocalHost().getHostAddress();

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            HashMap<String,PublicKey> chaves_dos_peers = new HashMap<>();
            HashMap<String, Key> chaves_combinadas_com_peers = new HashMap<>();

            HashMap<Integer,PipedOutputStream> pipes_threads = new HashMap<>();
            Thread receiver_handler = new Thread(new threadRecieveFromPeers(pipes_threads,keyPair.getPrivate(),peers,chaves_dos_peers,keyPair.getPublic(),meu_ip,chaves_combinadas_com_peers));
            receiver_handler.start();

            envia_chave_aos_peers(peers,meu_ip,keyPair);

            ServerSocket serverSocket = new ServerSocket(porta_servidor);

            System.out.println("Estou a pronto a receber chamadas");

            while(true){
                Socket s = serverSocket.accept();

                //Acrescentar o novo pipe de escrita
                PipedOutputStream output = new PipedOutputStream();
                pipes_threads.put(identificador,output);

                //Pipe de leitura
                PipedInputStream in = new PipedInputStream(output);
                System.out.println("Aceitei conexao");
                Thread t = new Thread(new threadAnonGW(s,target_server,porta_servidor,peers,identificador,in,keyPair.getPublic(),keyPair.getPrivate(),meu_ip,chaves_dos_peers,chaves_combinadas_com_peers));
                t.start();
                identificador++;
            }

        }

    }
}

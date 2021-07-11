package Server;

import java.net.Socket;

public class RejectCall {

    private int socket;
    private String ipAdress;

    public RejectCall(String ip, int s) {
        this.socket = s;
        this.ipAdress = ip;
    }

    public void close() {
        try {
            Socket s = new Socket(this.ipAdress, this.socket);
            s.shutdownInput();
            s.shutdownOutput();
            s.close();
        } catch (Exception e) {
        }
    }
}

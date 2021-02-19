package Operations;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Send {

    private boolean errorReceivingFile;
    private String path;
    private Socket socket;

    public Send(Socket s, String p) {
        this.errorReceivingFile = false;
        this.path = p;
        this.socket = s;
    }

    public boolean getErrorReceivingFile() {
        return this.errorReceivingFile;
    }

    public void send() {
        try {
            DataOutputStream channel = new DataOutputStream(this.socket.getOutputStream());
            FileInputStream fil = new FileInputStream(this.path);

            byte[] bytes = new byte[65536];
            int reads;
            int count = 0;
            while ((reads = fil.read(bytes, 0, 65536)) > 0) {
                count = count + reads;
                channel.write(bytes, 0, reads);
            }
            fil.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.errorReceivingFile = true;
        }
        try {
            this.socket.shutdownOutput();
            this.socket.shutdownInput();
            this.socket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

package Operations;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive {

    private boolean errorReceivingFile;
    private String path;
    private Socket socket;

    public Receive(Socket s, String p) {
        this.errorReceivingFile = false;
        this.path = p;
        this.socket = s;
    }

    public boolean getErrorReceivingFile() {
        return this.errorReceivingFile;
    }

    public void receive() {
        try {
            DataInputStream channel = new DataInputStream(this.socket.getInputStream());
            File f = new File(this.path);
            FileOutputStream fil = new FileOutputStream(f, true);
            byte[] bytes = new byte[65536];
            int reads;
            int count = 0;
            while ((reads = channel.read(bytes, 0, 65536)) > 0) {
                count = count + reads;
                fil.write(bytes, 0, reads);
            }
            fil.close();
            if (count == 0) {
                this.errorReceivingFile = true;
                f.delete();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            try {
                File f = new File(path);
                if (f.delete()) {
                    f.delete();
                }
            } catch (Exception ea) {
                System.out.println(ea.getMessage());
            }
        }
        try {
            this.socket.shutdownInput();
            this.socket.shutdownOutput();
            this.socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

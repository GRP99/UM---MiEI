package Guiao6;

import java.net.*;
import java.io.*;

class Servidor {
    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public Servidor(int port) throws UnknownHostException, IOException {
        server = new ServerSocket(port);
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");
        while (true) {
            socket = server.accept();
            System.out.println("Client accepted");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            String line = "";
            while (line != null && !line.equals("quit")) {
                line = in.readLine();
                System.out.println("Recebi a mensagem: " + line);
                out.println(line);
                out.flush();
            }
            System.out.println("Closing connection");
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }
    }
}

public class Ex1 {
    public static void main(String[] args) {
        try {
            Servidor server = new Servidor(12345);
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}

package Guiao6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

class Worker implements Runnable {
    private Socket socket;
    public Worker(Socket s) {
        this.socket = s;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
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
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class ServidorSocket {
    private Socket socket = null;
    private ServerSocket server = null;

    public ServidorSocket(int port) throws UnknownHostException, IOException {
        server = new ServerSocket(port);
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");
        while (true) {
            this.socket = server.accept();
            System.out.println("Client accepted");
            Thread worker = new Thread(new Worker(socket));
            worker.start();
        }
    }
}

public class Ex3 {
    public static void main(String args[]) {
        try {
            ServidorSocket server = new ServidorSocket(12345);
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}

package Guiao6;

import java.net.*;
import java.io.*;

class Cliente {
    private Socket socket = null;
    private DataInputStream input = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public Cliente(String address, int port) throws UnknownHostException, IOException {
        socket = new Socket(address, port);
        System.out.println("Connected");
        input = new DataInputStream(System.in);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
        String line = "";
        while (!line.equals("quit")) {
            line = input.readLine();
            out.println(line);
            out.flush();
            System.out.println(in.readLine());
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }
}

public class Ex2 {
    public static void main(String args[]) {
        try {
            Cliente client = new Cliente("2.83.152.80", 12345);
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
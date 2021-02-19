package Guiao7;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

    public static void main(String args[]) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected");
            DataInputStream input = new DataInputStream(System.in);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String line = "";
            while ((line = input.readLine()) != null && !line.equals("quit")) {
                out.println(line);
                out.flush();
                if (line.startsWith("movimentos")) {
                    String message = in.readLine();
                    if (message.startsWith("err ")) {
                        System.out.println(message);
                    } else {
                        int nmovs = Integer.valueOf(message).intValue();
                        System.out.println("Numero de movimentos " + nmovs);
                        for (int i = 0; i < nmovs; i++) {
                            System.out.println(in.readLine());
                        }
                    }
                } else {
                    System.out.println(in.readLine());
                }
            }
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}

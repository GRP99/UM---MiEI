package Clients;

import Operations.ManagerThreads;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), 12345);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        WriterThread wt = new WriterThread(false, in, out);
        Thread writer = new Thread(wt);

        writer.start();

        ManagerThreads clientManager = new ManagerThreads();
        ClientCommands cc = new ClientCommands(input, out, wt, clientManager);

        while (true) {
            int r = cc.handle(input.readLine());
            if (r != 0) {
                break;
            }
        }

        writer.stop();
        input.close();
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
    }
}

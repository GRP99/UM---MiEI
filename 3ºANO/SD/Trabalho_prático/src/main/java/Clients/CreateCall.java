package Clients;

import Operations.Send;
import Operations.ManagerThreads;
import Operations.Receive;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateCall implements Runnable {

    private int port;
    private boolean receiveSend;
    public boolean ready;// if it's not already accepted or not
    private String path;
    private ServerSocket sSocket;
    private ManagerThreads manager;

    public CreateCall(ManagerThreads g, boolean f, String p) {
        this.manager = g;
        this.receiveSend = f;
        this.path = p;
        this.ready = false;
        try {
            this.sSocket = new ServerSocket(0);
            this.port = this.sSocket.getLocalPort();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getPort() {
        return this.port;
    }

    public boolean getReady() {
        return this.ready;
    }

    public void setPort(int p) {
        this.port = p;
    }

    public void setReady(boolean r) {
        this.ready = r;
    }

    public void run() {
        try {
            this.ready = true;
            Socket s = this.sSocket.accept();
            if (receiveSend) {
                (new Receive(s, this.path)).receive();
                this.manager.freeReceive();
            } else {
                (new Send(s, this.path)).send();
                this.manager.releaseShipping(-1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

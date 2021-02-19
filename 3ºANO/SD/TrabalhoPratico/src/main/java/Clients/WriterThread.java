package Clients;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class WriterThread implements Runnable {

    private boolean loggedIn;
    private boolean pathChange;
    private String pathClient;
    private BufferedReader in;
    private PrintWriter out;

    public WriterThread(boolean b, BufferedReader br, PrintWriter pw) {
        this.loggedIn = b;
        this.pathClient = null;
        this.in = br;
        this.out = pw;
    }

    public boolean getLoggedIn() {
        return this.loggedIn;
    }

    public boolean getPathChange() {
        return this.pathChange;
    }

    public String getPathClient() {
        return this.pathClient;
    }

    public void setLoggedIn(boolean li) {
        this.loggedIn = li;
    }

    public void setPathChange(boolean pc) {
        this.pathChange = pc;
    }

    public void setPathClient(String pc) {
        this.pathClient = pc;
    }

    public void run() {
        String[] line;
        while (true) {
            try {
                String str = in.readLine();
                line = str.split(" ");
                if (line.length == 1 && line[0].equals("isLoggedIn")) {
                    this.loggedIn = true;
                    this.out.println("getAddress");
                    this.out.flush();
                } else if (line.length == 1 && line[0].equals("isLoggedOut")) {
                    this.loggedIn = false;
                } else if (line.length == 1 && line[0].equals("pathClient")) {
                    this.pathClient = this.in.readLine();
                    this.pathChange = true;
                } else {
                    System.out.println(str);
                }
            } catch (Exception e) {
            }
        }
    }
}

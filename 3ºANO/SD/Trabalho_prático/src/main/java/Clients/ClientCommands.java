package Clients;

import Operations.ManagerThreads;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class ClientCommands {

    private WriterThread wt;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader input;
    private ManagerThreads clientManager;
    private String path;

    public ClientCommands(BufferedReader br, PrintWriter pw, WriterThread w, ManagerThreads mt) {
        this.wt = w;
        this.input = br;
        this.out = pw;
        this.clientManager = mt;
        this.path = w.getPathClient();
    }

    public int handle(String read) {
        int returnValue = 0;
        String[] str = read.split(" ");
        if (wt.getLoggedIn()) { // Commands available if user is logged in
            switch (str[0]) {
                case "upload":
                    if (str.length == 1) {
                        System.out.println("Please insert as follows path file|Music Name|Artist Name|Year|tag1,tag2,tag3,...");
                        try {
                            read = this.input.readLine();
                        } catch (IOException ie) {
                            System.out.println(ie.getMessage());
                        }
                        String[] parts = read.split("\\|");
                        if (parts.length > 4) {
                            try {
                                int year = Integer.parseInt(parts[3]);
                                this.out.println("upload");
                                this.out.flush();
                                CreateCall cc = new CreateCall(this.clientManager, false, parts[0]);
                                int port = cc.getPort();
                                StringBuilder sb = new StringBuilder();
                                sb.append(port).append("|");
                                for (int i = 1; i < parts.length; i++) {
                                    sb.append(parts[i]);
                                    if (i + 1 != parts.length) {
                                        sb.append("|");
                                    }
                                }
                                this.clientManager.requestShipping(-1);
                                Thread t1 = new Thread(cc);
                                t1.start();
                                while (!cc.getReady());
                                this.out.println(sb.toString());
                                this.out.flush();
                            } catch (NumberFormatException nfe) {
                                System.out.println("You did not enter an integer!!!");
                            } catch (Exception e) {
                                this.clientManager.freeReceive();
                                System.out.println("Could not create call " + e.getMessage());
                            }
                        } else {
                            System.out.println("You did not enter a valid command!!!");
                        }
                    } else {
                        System.out.println("You did not enter a valid command!!!");
                    }
                    break;
                case "download":
                    if (path == null) {
                        this.out.println("getAddress");
                        this.out.flush();
                        while (wt.getPathClient() == null);
                        this.path = this.wt.getPathClient();
                    }
                    if (str.length == 2 && this.path != null) {
                        int id = 0;
                        try {
                            id = Integer.parseInt(str[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Did not enter an integer!!!");
                            break;
                        }
                        if (id != 0) {
                            String pClient = this.path;
                            if (pClient != null) {
                                this.clientManager.requestReceive();
                                CreateCall ch = new CreateCall(this.clientManager, true, pClient + "songID" + id + "@" + (new Random()).nextInt());
                                int port = ch.getPort();
                                Thread t1 = new Thread(ch);
                                t1.start();
                                while (!ch.ready) {
                                    System.out.print(" ");
                                }
                                System.out.println("\n");
                                this.out.println("download " + id + " " + port);
                                this.out.flush();
                            }
                        } else {
                            System.out.println("An error occured while downloading!!!");
                        }
                    } else {
                        System.out.println("An error occured while downloading!!!");
                    }
                    break;
                case "change_path":
                    if (str.length > 1) {
                        this.wt.setPathChange(false);
                        this.out.println("change_path");
                        this.out.flush();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < str.length; i++) {
                            sb.append(str[i]);
                            if (i + 1 != str.length) {
                                sb.append(" ");
                            }
                        }
                        this.out.println("new_path|" + sb.toString());
                        this.out.flush();
                        this.out.println("getAddress");
                        this.out.flush();
                        while (wt.getPathChange() == false);
                        this.path = this.wt.getPathClient();
                    }
                    break;
                case "musics":
                    this.out.println(read);
                    this.out.flush();
                    break;
                case "change_email":
                    if (str.length == 2) {
                        this.out.println(read);
                        this.out.flush();
                    } else {
                        System.out.println("You have not entered a valid command, make sure you have not placed spaces in the new email!!! \n");
                    }
                    break;
                case "change_password":
                    if (str.length == 2) {
                        this.out.println(read);
                        this.out.flush();
                    } else {
                        System.out.println("You have not entered a valid command, make sure you have not placed spaces in the new password!!! \n");
                    }
                    break;
                case "current_path":
                    this.out.println("getAddress");
                    this.out.flush();
                    while (this.wt.getPathClient() == null);
                    this.path = this.wt.getPathClient();
                    System.out.println(this.path);
                    break;
                case "logout":
                    this.out.println(read);
                    this.out.flush();
                    while (this.wt.getLoggedIn());
                    break;
                default:
                    System.out.println("\n You have not entered a valid command!!! \n"
                            + "Available Commands:\n"
                            + "upload \n"
                            + "download -idMusic (Will be downloaded to your default path) \n"
                            + "current_path\n"
                            + "change_path -new_path\n"
                            + "musics -tag1 -tag2 .... (To see all the songs on the server just do not put any tags) \n"
                            + "change_email -new_email \n"
                            + "change_password -new_password \n"
                            + "logout (to get out) \n");
            }
        } else { // Commands available if user is not logged in
            switch (str[0]) {
                case "login":
                    if (str.length == 3) {
                        this.out.println(read);
                        this.out.flush();
                    } else {
                        System.out.println("You have not entered a valid command, make sure you have not placed spaces in the email or password!!! \n");
                    }
                    break;

                case "register":
                    if (str.length == 3) {
                        this.out.println(read);
                        this.out.flush();
                        try {
                            read = this.input.readLine();
                            this.out.println(read);
                            this.out.flush();
                        } catch (IOException ie) {
                        }
                    } else {
                        System.out.println("You have not entered a valid command, make sure you have not placed spaces in the email or password!!! \n");
                    }
                    break;
                case "quit":
                    returnValue = 1;
                    break;
                default:
                    System.out.println("\nYou have not entered a valid command!!!\n Available Commands: \n login -email -password \n register -email -password \n quit (finish program)");
            }
        }
        return returnValue;
    }
}

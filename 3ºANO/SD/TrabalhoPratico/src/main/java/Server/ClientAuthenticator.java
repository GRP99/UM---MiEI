package Server;

import Users.*;
import Operations.ManagerThreads;
import java.net.Socket;
import Musics.InterfMusicLibrary;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientAuthenticator implements Runnable {

    private InterfMusicLibrary library;
    private InterfUserCatalog catalog;
    private Socket s;
    private ManagerThreads manager;
    private int idClient;
    private String pathServer;

    public ClientAuthenticator(InterfMusicLibrary icm, InterfUserCatalog iuc, Socket so, ManagerThreads mt, int idC, String pathServer) {
        this.library = icm;
        this.catalog = iuc;
        this.s = so;
        this.manager = mt;
        this.idClient = idC;
        this.pathServer = pathServer;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            out = new PrintWriter(this.s.getOutputStream());
        } catch (IOException i) {
            System.out.println("An error occurred connecting to server:(");
            try {
                this.s.shutdownOutput();
                this.s.shutdownInput();
                this.s.close();
            } catch (IOException ie) {
                System.out.println(ie.getMessage());
            }
        }
        String read = "";
        while (read != null && !read.equals("quit") && !read.equals("logout")) {
            try {
                read = in.readLine();
            } catch (IOException ie) {
                out.println("Error reading message!");
                out.flush();
            }
            String[] part;
            if (read != null) {
                part = read.split(" ");
                String op = part[0];
                switch (op) {
                    case ("register"):
                        if (part.length == 3) {
                            try {
                                out.println("Please enter the path where downloads are made: ");
                                out.flush();
                                try {
                                    read = in.readLine();
                                } catch (IOException ie) {
                                    out.println("Error reading message! \n");
                                    out.flush();
                                }
                                this.catalog.newUser(part[1], part[2], read);
                                out.println("Registration successful!!! \n");
                                out.flush();
                                this.catalog.changeUserStatus(true, part[1]);
                                Thread.sleep(1000);
                                out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                                out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                                out.flush();
                                try {
                                    out.println("isLoggedIn");
                                    out.flush();
                                    this.manager.addClient(this.idClient, out);
                                    Thread init = new Thread(new ServerCommands(this.library, this.catalog, this.s, in, out, part[1], part[2], this.pathServer, this.catalog.getDefaultPath(part[1]), this.manager, this.idClient));
                                    init.start();
                                    init.join();
                                } catch (WrongEmail we) {
                                    out.println("Wrong Email!!!\n");
                                    out.flush();
                                }
                            } catch (ExistingEmail ee) {
                                out.println("Email already exists!!!\n");
                                out.flush();
                            } catch (InterruptedException ie) {
                            }
                        } else {
                            out.println("You did not enter a valid command, make sure you did not enter spaces in the email/password!!!\n");
                            out.flush();
                        }
                        break;
                    case ("login"):
                        if (part.length == 3) {
                            try {
                                if (this.catalog.login(part[1], part[2])) {
                                    out.println("Successfully Authenticated!!! \n");
                                    out.flush();
                                    out.println("isLoggedIn");
                                    out.flush();
                                    Thread.sleep(1000);
                                    out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                                    out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                                    out.flush();
                                    this.manager.addClient(this.idClient, out);
                                    Thread init = new Thread(new ServerCommands(this.library, this.catalog, this.s, in, out, part[1], part[2], this.pathServer, this.catalog.getDefaultPath(part[1]), this.manager, this.idClient));
                                    init.start();
                                    init.join();
                                }
                            } catch (WrongEmail we) {
                                out.println("Wrong Email!!! \n");
                                out.flush();
                            } catch (WrongPassword wp) {
                                out.println("Wrong Password!!! \n");
                                out.flush();
                            } catch (AlreadyLoggedIn ali) {
                                out.println("This user is already signed in!!! \n");
                                out.flush();
                            } catch (InterruptedException ignored) {
                            }
                        } else {
                            out.println("You did not enter a valid command, make sure you did not enter spaces in the email/password!!!\n");
                            out.flush();
                        }
                        break;
                    case ("quit"):
                        break;
                    case ("logout"):
                        break;
                    default:
                        out.println("\n You have not entered a valid command!!! \n Available Commands:: \n login -email -password \n register -email -password \n");
                        out.flush();
                }
            }
        }
        try {
            this.s.shutdownOutput();
            this.s.shutdownInput();
            this.s.close();
        } catch (IOException ie) {
            out.println("An error occurred while closing a socket!");
            out.flush();
        }
    }
}

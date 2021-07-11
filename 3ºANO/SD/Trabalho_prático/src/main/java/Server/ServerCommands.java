package Server;

import Users.*;
import Musics.*;
import Operations.ManagerThreads;

import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerCommands implements Runnable {

    private InterfMusicLibrary library;
    private InterfUserCatalog catalog;
    private Socket s;
    private BufferedReader in;
    private PrintWriter out;
    private String emailUser;
    private String passwordUser;
    private String pathUser;
    private String musicsPath;
    private ManagerThreads manager;
    private int idClient;

    public ServerCommands(InterfMusicLibrary iml, InterfUserCatalog iuc, Socket soc, BufferedReader br, PrintWriter pw, String email, String password, String path, String pathClient, ManagerThreads gt, int idC) {
        this.library = iml;
        this.catalog = iuc;
        this.s = soc;
        this.in = br;
        this.out = pw;
        this.emailUser = email;
        this.passwordUser = password;
        this.musicsPath = path;
        this.pathUser = pathClient;
        this.manager = gt;
        this.idClient = idC;
    }

    public void run() {
        String read = "";
        while (read != null && !read.equals("quit") && !read.equals("logout")) {
            try {
                read = this.in.readLine();
            } catch (IOException i) {
                this.out.println("Erro reading the message !!!");
                this.out.flush();
            }
            String[] str;
            if (read != null) {
                str = read.split(" ");
                String command = str[0];
                switch (command) {
                    case ("change_email"):
                        try {
                            this.catalog.changeEmail(this.emailUser, str[1]);
                            this.emailUser = str[1];
                            this.out.println("Email change successful!!! \n");
                            this.out.flush();
                        } catch (WrongEmail we) {
                            this.out.println("Wrong Email!!! \n");
                            this.out.flush();
                        } catch (ExistingEmail de) {
                            this.out.println("Email already exists!!! \n");
                            this.out.flush();
                        }
                        break;
                    case ("change_password"):
                        try {
                            this.catalog.changePassword(this.emailUser, this.passwordUser, str[1]);
                            this.out.println("Password change successful!!! \n");
                            this.out.flush();
                        } catch (WrongEmail we) {
                            this.out.println("Wrong Email!!! \n");
                            this.out.flush();
                        } catch (WrongPassword wp) {
                            this.out.println("Wrong Password!!! \n");
                            this.out.flush();
                        }
                        break;
                    case ("musics"):
                        if (str.length >= 2) {
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append("List of Musics with tags: ");
                                String[] s = new String[str.length - 1];
                                int index_parts = 1, index_tags = 0;
                                while (index_parts < str.length) {
                                    s[index_tags] = str[index_parts];
                                    if (index_parts + 1 != str.length) {
                                        sb.append(s[index_tags]).append(" | ");
                                    } else {
                                        sb.append(s[index_tags]);
                                    }
                                    index_tags++;
                                    index_parts++;
                                }
                                sb.append("\n");
                                List<String> musics = this.library.getMusics(s);
                                for (String m : musics) {
                                    sb.append(m).append("\n");
                                }
                                this.out.println(sb.toString());
                                this.out.flush();
                            } catch (NoMusics sm) {
                                this.out.println("There is no music with any of the tags provided!!! \n");
                                this.out.flush();
                            }
                        } else {
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append("List of Musics: ");
                                sb.append("\n");
                                List<String> musics = this.library.getMusics();
                                for (String m : musics) {
                                    sb.append(m).append("\n");
                                }
                                this.out.println(sb.toString());
                                this.out.flush();
                            } catch (NoMusics sm) {
                                this.out.println("There is no music in the system!!! \n");
                                this.out.flush();
                            }
                        }
                        break;
                    case ("change_path"):
                        try {
                            read = this.in.readLine();
                            String[] stri = read.split("\\|");
                            if (stri.length > 1 && stri[0].equals("new_path")) {
                                this.pathUser = stri[1];
                                this.out.println("Path change successful!!!");
                                this.out.flush();
                                this.out.println("pathClient");
                                this.out.flush();
                                this.out.println(this.pathUser);
                                this.out.flush();
                            }
                        } catch (IOException e) {
                            this.out.println("Error reading the message, please try again. \n");
                            this.out.flush();
                        }
                        break;
                    case ("upload"):
                        this.manager.requestReceive();
                        try {
                            String addrClient = this.s.getInetAddress().toString().split("/")[1];
                            String filetoCreate = this.musicsPath + "ficheiro@" + (new Random()).nextInt();
                            try {
                                read = this.in.readLine();
                            } catch (IOException i) {
                                this.out.println("Error reading the message, please try again. \n");
                                this.out.flush();
                                break;
                            }
                            String[] comm = read.split("\\|");
                            AnswerCall ch = new AnswerCall(this.manager, addrClient, Integer.parseInt(comm[0]), true, filetoCreate, this.library, 0, comm[1], comm[2], Integer.parseInt(comm[3]), comm[4].split(","), 0);
                            Thread t1 = new Thread(ch);
                            t1.start();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case ("download"):
                        int idMusic = 0;
                        String music = null;
                        String addrClient = this.s.getInetAddress().toString().split("/")[1];
                        int port = -1;
                        try {
                            idMusic = Integer.parseInt(str[1]);
                            port = Integer.parseInt(str[2]);
                            music = this.library.getPathFile(idMusic);
                        } catch (NonexistentMusic mi) {
                            this.out.println("The id you entered does not exist!!! \n");
                            this.out.flush();
                            (new RejectCall(addrClient, port)).close();
                        }
                        if (idMusic != 0 && music != null && port != -1) {
                            this.manager.requestShipping(idClient);
                            try {
                                AnswerCall ch = new AnswerCall(manager, addrClient, port, false, music, library, idMusic, "", "", 0, null, idClient);
                                Thread t1 = new Thread(ch);
                                t1.start();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case ("getAddress"):
                        try {
                            this.out.println("pathClient");
                            this.out.flush();
                            this.out.println(this.pathUser);
                            this.out.flush();
                        } catch (Exception e) {
                        }
                        break;
                    case ("logout"):
                        this.manager.removeCliente(this.idClient);
                        this.out.println("isLoggedOut");
                        this.out.flush();
                        break;
                    default:
                }
            }
        }
    }
}

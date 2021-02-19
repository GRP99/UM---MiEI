package Server;

import Operations.Receive;
import Operations.Send;
import Operations.ManagerThreads;

import java.net.Socket;
import Musics.InterfMusicLibrary;

public class AnswerCall implements Runnable {

    private int port;
    private int idMusic;
    private int year;
    private int idClient;
    private boolean receiveSend;
    private String address;
    private String title;
    private String artist;
    private String path;
    private String[] tags;
    private ManagerThreads manager;
    private InterfMusicLibrary library;

    public AnswerCall(ManagerThreads mt, String add, int por, boolean rs, String pat, InterfMusicLibrary iml, int idM, String tit, String ar, int yea, String[] t, int idC) {
        this.port = por;
        this.idMusic = idM;
        this.year = yea;
        this.idClient = idC;
        this.receiveSend = rs;
        this.address = add;
        this.title = tit;
        this.artist = ar;
        this.path = pat;
        this.tags = t;
        this.manager = mt;
        this.library = iml;
    }

    public void run() {
        try {
            Socket s = new Socket(this.address, this.port);
            if (this.receiveSend) {
                Receive re = new Receive(s, this.path);
                re.receive();
                this.manager.freeReceive();
                if (re.getErrorReceivingFile() != true) {
                    int id = this.library.upload(this.title, this.artist, this.tags, this.year, this.path);
                    this.manager.notifyClients(id, this.title, this.artist);
                }
            } else {
                Send se = new Send(s, this.path);
                se.send();
                this.manager.releaseShipping(idClient);
                if (se.getErrorReceivingFile() != true) {
                    this.library.download(idMusic);
                    this.manager.notifyClientDownload(this.idClient, this.idMusic);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

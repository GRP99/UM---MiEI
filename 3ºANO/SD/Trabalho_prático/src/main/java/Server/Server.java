package Server;

import Users.UserCatalog;
import Musics.MusicLibrary;
import Operations.ManagerThreads;

import java.net.ServerSocket;
import Musics.InterfMusicLibrary;
import Users.InterfUserCatalog;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(12345);
        InterfMusicLibrary library = new MusicLibrary();
        InterfUserCatalog catalog = new UserCatalog();
        ManagerThreads serverManager = new ManagerThreads();
        int counter = 0;
        while (true) {
            Thread t = new Thread(new ClientAuthenticator(library, catalog, ss.accept(), serverManager, counter++, "C:\\Users\\Gonçalo Pinto\\Downloads\\"));
            t.start();
        }
    }

}

package Musics;

public class NonexistentMusic extends Exception {

    public NonexistentMusic() {
        super();
    }

    public NonexistentMusic(String s) {
        super(s);
    }
}

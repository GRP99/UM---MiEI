package Musics;

import java.util.List;

public interface InterfMusicLibrary {

    public String generateMusicName(int id) throws NonexistentMusic;

    public String getPathFile(int id) throws NonexistentMusic;

    public int upload(String title, String artist, String[] tags, int year, String pathFile);

    public void download(int id_unico);

    public void removeMusic(int id) throws NonexistentMusic;

    public List<String> getMusics() throws NoMusics;

    public List<String> getMusics(String[] tags) throws NoMusics;

}

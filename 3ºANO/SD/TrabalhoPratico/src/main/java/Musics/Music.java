package Musics;

import RWLocks.*;

public class Music {

    private int id;
    private String title;
    private String artist;
    private String[] tags;
    private int year;
    private String path;
    private int downloads;
    private InterfRWLock lockMusic;

    public Music(int i, String t, String a, String[] e, int y, String p) {
        this.id = i;
        this.title = t;
        this.artist = a;
        this.tags = e;
        this.year = y;
        this.path = p;
        this.downloads = 0;
        this.lockMusic = new RWLock();
    }

    public void readLock() {
        this.lockMusic.readLock();
    }

    public void readUnlock() {
        this.lockMusic.readUnlock();
    }

    public void writeLock() {
        this.lockMusic.writeLock();
    }

    public void writeUnlock() {
        this.lockMusic.writeUnlock();
    }

    public void incrementDownloads() {
        this.downloads = this.downloads + 1;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int i) {
        this.id = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String a) {
        this.artist = a;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(String[] t) {
        this.tags = t;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int y) {
        this.year = y;
    }

    public String getPathFile() {
        return this.path;
    }

    public void setPathFile(String f) {
        this.path = f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Music::{").append("\n");;
        sb.append("ID: ").append(this.id).append(";\n");;
        sb.append("Title: ").append(this.title).append(";\n");
        sb.append("Artist: ").append(this.artist).append(";\n");
        sb.append("Year: ").append(this.year).append(";\n");
        sb.append("Number of Downloads: ").append(this.downloads).append(";\n");
        sb.append("Tags{ ");
        for (int i = 0; i < this.tags.length; i++) {
            if (i + 1 != this.tags.length) {
                sb.append(this.tags[i]).append(", ");
            } else {
                sb.append(this.tags[i]);
            }
        }
        sb.append(" }\n");
        sb.append(" }\n");
        return sb.toString();
    }
}

package Musics;

import RWLocks.InterfRWLock;
import RWLocks.RWLock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicLibrary implements InterfMusicLibrary {

    private int biggerID;
    private List<Integer> deletedIDs;
    private Map<Integer, Music> library;
    private InterfRWLock rwlock;

    public MusicLibrary() {
        this.biggerID = 1;
        this.deletedIDs = new ArrayList<>();
        this.library = new HashMap<>();
        this.rwlock = new RWLock();
    }

    public String generateMusicName(int id) throws NonexistentMusic {
        this.rwlock.readLock();
        if (!(this.library.containsKey(id))) {
            this.rwlock.readUnlock();
            throw new NonexistentMusic();
        }
        Music m = this.library.get(id);
        m.readLock();
        this.rwlock.readUnlock();
        String ret = m.getTitle().replaceAll("\\s", "_").toLowerCase() + "-" + m.getArtist().replaceAll("\\s", "_").toLowerCase() + "-" + m.getYear() + "@" + LocalDateTime.now();
        m.readUnlock();
        return ret;
    }

    public String getPathFile(int id) throws NonexistentMusic {
        this.rwlock.readLock();
        if (!(this.library.containsKey(id))) {
            this.rwlock.readUnlock();
            throw new NonexistentMusic();
        }
        Music m = this.library.get(id);
        m.readLock();
        rwlock.readUnlock();
        String pf = m.getPathFile();
        m.readUnlock();
        return pf;
    }

    public int upload(String title, String artist, String[] tags, int year, String pathFile) {
        rwlock.writeLock();
        int id;
        if (this.deletedIDs.size() != 0) {
            id = this.deletedIDs.get(0);
            this.deletedIDs.remove(0);
            Music m = new Music(id, title, artist, tags, year, pathFile);
            this.library.put(id, m);
        } else {
            id = this.biggerID;
            this.biggerID = this.biggerID + 1;
            Music m = new Music(id, title, artist, tags, year, pathFile);
            this.library.put(id, m);
        }
        this.rwlock.writeUnlock();
        return id;
    }

    public void download(int id) {
        this.rwlock.readLock();
        if (!(this.library.containsKey(id))) {
            this.rwlock.readUnlock();
        }
        Music m = library.get(id);
        m.readLock();
        this.rwlock.readUnlock();
        m.incrementDownloads();
        m.readUnlock();
    }

    public void removeMusic(int id) throws NonexistentMusic {
        this.rwlock.writeLock();
        if (!(this.library.containsKey(id))) {
            this.rwlock.writeUnlock();
            throw new NonexistentMusic();
        }
        Music m = this.library.get(id);
        this.library.remove(id, m);
        m = null;
        this.rwlock.writeUnlock();
    }

    public List<String> getMusics() throws NoMusics {
        this.rwlock.readLock();
        List<String> list = new ArrayList<>();
        for (Music m : this.library.values()) {
            m.readLock();
            list.add(m.toString());
            m.readUnlock();
        }
        if (list.size() == 0) {
            rwlock.readUnlock();
            throw new NoMusics();
        }
        rwlock.readUnlock();
        return list;
    }

    public List<String> getMusics(String[] tags) throws NoMusics {
        this.rwlock.readLock();
        List<String> list = new ArrayList<>();
        for (Music m : this.library.values()) {
            m.readLock();
            String[] h = m.getTags();
            boolean flag = false;
            for (int i = 0; i < tags.length && !flag; i++) {
                for (int j = 0; j < m.getTags().length && !flag; j++) {
                    if (tags[i].equals(h[j])) {
                        list.add(m.toString());
                        flag = true;
                    }
                }
            }
            m.readUnlock();
        }
        if (list.size() == 0) {
            this.rwlock.readUnlock();
            throw new NoMusics();
        }
        this.rwlock.readUnlock();
        return list;
    }
}

package RWLocks;

public interface InterfRWLock {

    public void readLock();

    public void readUnlock();

    public void writeLock();

    public void writeUnlock();
}

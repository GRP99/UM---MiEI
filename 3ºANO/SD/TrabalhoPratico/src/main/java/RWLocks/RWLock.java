package RWLocks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock implements InterfRWLock {

    private int readers;
    private int writers;
    private int writeRequests;
    private int readRequests;
    private int readersPriority;
    private int writersPriority;
    private int maxPriority = 3;
    private ReentrantLock rl = new ReentrantLock();
    private Condition waitReaders = this.rl.newCondition();
    private Condition waitWriters = this.rl.newCondition();

    public RWLock() {
        this.readers = 0;
        this.writers = 0;
        this.writeRequests = 0;
        this.readRequests = 0;
        this.readersPriority = 0;
        this.writersPriority = 0;
    }

    public void readLock() {
        this.rl.lock();
        try {
            this.readRequests++;
            while (this.writers > 0 || (this.writeRequests > 0 && this.readersPriority >= this.maxPriority)) {
                this.waitReaders.await();
            }
            this.readRequests--;
        } catch (InterruptedException ie) {
        }
        this.readers++;
        this.readersPriority++;
        if (this.readersPriority >= this.maxPriority) {
            this.writersPriority = 0;
        }
        this.rl.unlock();
    }

    public void readUnlock() {
        this.rl.lock();
        this.readers--;
        if (this.readers == 0) {
            this.waitWriters.signal();
        }
        this.rl.unlock();
    }

    public void writeLock() {
        this.rl.lock();
        this.writeRequests++;
        while (this.readers > 0 || this.writers > 0 || (this.readRequests > 0 && this.writersPriority >= this.maxPriority)) {
            try {
                this.waitWriters.await();
            } catch (InterruptedException ie) {
            }
        }
        this.writeRequests--;
        this.writers = 1;
        this.writersPriority++;
        if (this.writersPriority == this.maxPriority) {
            this.readersPriority = 0;
        }
        this.rl.unlock();
    }

    public void writeUnlock() {
        this.rl.lock();
        this.writers = 0;
        this.waitWriters.signal();
        this.waitReaders.signalAll();
        this.rl.unlock();
    }
}

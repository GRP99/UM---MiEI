package Guiao5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {
    ReentrantLock lock;
    Condition waitRead;
    Condition waitWrite;
    private boolean write;
    private int readers = 0;

    public RWLock() {
        this.readers = 0;
        this.write = false;
        this.lock = new ReentrantLock();
        this.waitRead = this.lock.newCondition();
        this.waitWrite = this.lock.newCondition();
    }

    public void readLock() {
        this.lock.lock();
        try {
            while (write) {
                this.waitRead.await();
            }
            this.readers++;
        } catch (InterruptedException e) {
        } finally {
            this.lock.unlock();
        }
    }

    public void readUnlock() {
        this.lock.lock();
        this.readers--;
        if (readers == 0) this.waitWrite.signal();
        this.lock.unlock();
    }

    public void writeLock() {
        this.lock.lock();
        try {
            while (write || readers > 0) {
                this.waitWrite.await();
            }
            this.write = true;
        } catch (InterruptedException e) {
        } finally {
            this.lock.unlock();
        }
    }

    public void writeUnlock() {
        this.lock.lock();
        write = false;
        //Ordem pelo qual faz 'signal' afecta a ordem de execução!
        this.waitWrite.signal(); 
        this.waitRead.signalAll();
        this.lock.unlock();
    }
}

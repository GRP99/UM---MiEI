package Guiao5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock_SolveStarvation {
    private int readers;
    private boolean write;
    ReentrantLock lock;
    Condition waitRead;
    Condition waitWrite;
    private int writerequests;

    public RWLock_SolveStarvation() {
        this.readers = 0;
        this.write = false;
        this.lock = new ReentrantLock();
        this.waitRead = this.lock.newCondition();
        this.waitWrite = this.lock.newCondition();
        this.writerequests = 0;
    }

    public void readLock() {
        this.lock.lock();
        try {
            while (write || writerequests>0) {
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
        readers--;
        if (readers == 0) this.waitWrite.signal();
        this.lock.unlock();
    }

    public void writeLock() {
        this.lock.lock();
        try {
            this.writerequests = this.writerequests + 1;
            while (write || readers > 0) {
                this.waitWrite.await();
            }
            this.writerequests = this.writerequests - 1;
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

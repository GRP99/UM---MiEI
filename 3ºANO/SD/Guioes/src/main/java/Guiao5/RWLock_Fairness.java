package Guiao5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock_Fairness {
    private int readers;
    private boolean write;
    ReentrantLock lock;
    Condition waitRead;
    Condition waitWrite;
    private int writerequests;
    private int limit = 3;
    private int readrequests;
    private int readersPriority; //contador de leitores consecutivos
    private int writersPriority; //contador de escritores consecutivos

    public RWLock_Fairness() {
        this.readers = 0;
        this.write = false;
        this.lock = new ReentrantLock();
        this.waitRead = this.lock.newCondition();
        this.waitWrite = this.lock.newCondition();
        this.writerequests = 0;
        this.readrequests = 0;
        this.readersPriority=0;
        this.writersPriority=0;
    }

    public void readLock() {
        this.lock.lock();
        try {
            this.readrequests = this.readrequests + 1;
            while (write || (writerequests>0 && readersPriority>=limit)) {
                this.waitRead.await();
            }
            this.readrequests = this.readrequests - 1;
            this.readers++;
            this.readersPriority = this.readersPriority + 1;
            if(readersPriority == limit){
                this.writersPriority = 0;
            }
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
            while (write || readers > 0 || (readrequests > 0 && writersPriority>=limit)) {
                this.waitWrite.await();
            }
            this.writerequests = this.writerequests - 1;
            this.write = true;
            this.writersPriority = this.writersPriority + 1;
            if(this.writersPriority==this.limit){
                this.readersPriority = 0;
            }
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

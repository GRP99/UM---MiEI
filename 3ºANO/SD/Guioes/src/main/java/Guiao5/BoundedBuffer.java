package Guiao5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    private int[] buff;
    private int size;
    private ReentrantLock lock;
    private Condition gets;
    private Condition puts;

    public BoundedBuffer(int size) {
        this.buff = new int[size];
        this.size = 0;
        this.lock = new ReentrantLock();
        this.gets = this.lock.newCondition();
        this.puts = this.lock.newCondition();
    }

    public void put(int v) {
        this.lock.lock();
        try {
            while (this.size == this.buff.length) {
                this.puts.await();
            }
            this.buff[this.size++] = v;
            this.gets.signal();
        } catch (InterruptedException e) {
        } finally {
            this.lock.unlock();
        }
    }

    public int get() {
        int v = 0;
        this.lock.lock();
        try {
            while (this.size == 0) {
                this.gets.await();
            }
            v = this.buff[--this.size];
            this.puts.signal();
        } catch (InterruptedException e) {
        } finally {
            this.lock.unlock();
        }
        return v;
    }
}

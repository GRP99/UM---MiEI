package Guiao4;

public class BoundedBuffer {

    private int[] buffer;
    private int poswrite;
    private int size;

    public BoundedBuffer(int sz) {
        this.buffer = new int[sz];
        this.size = 0;
    }

    public synchronized void put(int v) throws InterruptedException {
        while (this.size == this.buffer.length) {
            this.wait();
        }
        this.buffer[this.size++] = v;
        this.notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (this.size == 0) {
            this.wait();
        }
        int v = this.buffer[--this.size];
        this.notifyAll();
        return v;
    }

}

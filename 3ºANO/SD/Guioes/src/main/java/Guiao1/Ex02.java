package Guiao1;

import java.lang.Runnable;
import java.lang.Thread;

class Counter {

    public int count;

    public Counter() {
        this.count = 0;
    }

    public void increment() {
        this.count++;
    }

    public int getCounter() {
        return this.count;
    }
}

class Incrementor implements Runnable {

    private Counter c;
    private int i;

    public Incrementor(Counter nC, int nI) {
        this.c = nC;
        this.i = nI;
    }

    public void run() {
        for (int x = 0; x < this.i; x++) {
            //count.increment();
            this.c.count++;
        }
    }
}

public class Ex02 {

    public static void main(String args[]) throws Exception {
        int N = 10;
        int I = 100000;
        Counter ct = new Counter();
        Thread[] threadArray = new Thread[N];
        for (int i = 0; i < N; i++) {
            threadArray[i] = new Thread(new Incrementor(ct, I));
        }
        for (int i = 0; i < N; i++) {
            threadArray[i].start();
        }
        for (int i = 0; i < N; i++) {
            threadArray[i].join();
        }
        System.out.println(ct.getCounter());
    }
}

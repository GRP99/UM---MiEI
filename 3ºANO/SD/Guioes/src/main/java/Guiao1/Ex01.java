package Guiao1;

import java.lang.Runnable;
import java.lang.Thread;

class IncrementerRunnable implements Runnable {

    private int maxInc;

    public IncrementerRunnable(int i) {
        this.maxInc = i;
    }

    public void run() {
        for (int i = 0; i < this.maxInc; i++) {
            System.out.println(i);
        }
    }
}

public class Ex01 {

    public static void main(String args[]) throws Exception {
        int N = 10;
        int I = 10000;
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            IncrementerRunnable inc = new IncrementerRunnable(I);
            threads[i] = new Thread(inc);
        }
        for (int i = 0; i < N; i++) {
            threads[i].start();
        }
        for (int i = 0; i < N; i++) {
            threads[i].join();
        }
    }
}

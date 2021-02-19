package Guiao4;

public class Barreira {

    private int max;
    private int counter;
    private int etapa;

    public Barreira(int val) {
        this.etapa = 0;
        this.max = this.counter = val;
    }

    public synchronized void esperar() throws InterruptedException {
        this.counter--;
        int myetapa = this.etapa;
        if (this.counter <= 0) {
            this.counter = this.max;
            this.etapa++;
            System.out.println(Thread.currentThread().getName() + " notifying");
            this.notifyAll();
        }
        while (this.etapa == myetapa) {
            System.out.println(Thread.currentThread().getName() + " waiting");
            this.wait();
        }
    }
}

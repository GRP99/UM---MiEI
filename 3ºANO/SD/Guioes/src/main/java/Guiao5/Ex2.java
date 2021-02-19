package Guiao5;

class Producer implements Runnable {
    private Warehouse wh;
    public Producer(Warehouse w) {this.wh = w;}
    public void run() {
        this.wh.supply("Item1", 1);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        this.wh.supply("Item2", 1);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        this.wh.supply("Item3", 1);
    }
}

class Consumer implements Runnable {
    private Warehouse wh;
    public Consumer(Warehouse w) {this.wh = w;}
    public void run() {
        String[] it = {"Item1", "Item2", "Item3"};
        this.wh.consume(it);
    }
}

public class Ex2 {
    public static void main(String args[]) throws InterruptedException{
        Warehouse wh = new Warehouse();
        Thread t1 = new Thread(new Producer(wh));
        Thread t2 = new Thread(new Consumer(wh));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
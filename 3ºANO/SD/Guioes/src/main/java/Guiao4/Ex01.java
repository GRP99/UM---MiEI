package Guiao4;

import java.util.Random;

class Produtor implements Runnable {
    private BoundedBuffer buff;
    private int num;
    public Produtor(BoundedBuffer b, int n) {
        this.buff = b;
        this.num = n;
    }
    public void run() {
        Random r = new Random();
        while (this.num > 0){
            try {
                Thread.sleep(1000);
                int x = r.nextInt(10);
                this.buff.put(x);
                System.out.println("Coloquei o valor " + x);
                this.num = this.num - 1;
            } 
            catch (InterruptedException e) {}
        }
    }
}

class Consumidor implements Runnable {
    private BoundedBuffer buff;
    private int num;
    public Consumidor(BoundedBuffer buff, int n) {
        this.buff = buff;
        this.num = n;
    }
    public void run() {
        while (this.num > 0){
            try {
                Thread.sleep(500);
                int x = this.buff.get();
                System.out.println("Tirei o valor " + x);
                this.num = this.num - 1;
            } catch (InterruptedException e) {}
        }
    }
}

public class Ex01 {
    public static void main(String[] args) throws InterruptedException{
        BoundedBuffer buff = new BoundedBuffer(10);
        Thread t1 = new Thread(new Produtor(buff, 10));
        Thread t2 = new Thread(new Consumidor(buff, 10));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

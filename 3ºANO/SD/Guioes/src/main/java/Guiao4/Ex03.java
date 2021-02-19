package Guiao4;

import java.util.stream.IntStream;

public class Ex03 {
    public static void main(String[] args) throws InterruptedException{
        Barreira b = new Barreira(5);
        Runnable r = () -> {
            try {
                b.esperar();
            } catch (InterruptedException ignored) {
            }
            System.out.println(Thread.currentThread().getName() + " passed barrier");
        };
        IntStream.range(0, 10).forEach(i -> {
            new Thread(r).start();
        });
    }
}

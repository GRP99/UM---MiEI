package Guiao3;

public class Ex01 {

    public static void main(String[] args) throws InterruptedException {
        Banco b = new Banco();
        System.out.println("Criar Conta " + b.criarConta(10));
        System.out.println("Criar Conta " + b.criarConta(10));
        Cliente1 c1 = new Cliente1(b);
        Cliente2 c2 = new Cliente2(b);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

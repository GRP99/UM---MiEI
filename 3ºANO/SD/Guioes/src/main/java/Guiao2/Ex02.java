package Guiao2;

import java.lang.Runnable;
import java.lang.Thread;

class Banco {
    private double contas[];
    public Banco(int n) {
        this.contas = new double[n];
        for (int i = 0; i < n; i++) {
            this.contas[i] = 0;
        }
    }
    public double consultar(int conta) {
        return this.contas[conta];
    }
    public synchronized void depositar(int conta, double valor) {
        this.contas[conta] = this.contas[conta] + valor;
    }
    public synchronized void levantar(int conta, double valor) {
        this.contas[conta] = this.contas[conta] - valor;
    }
}

class Cliente1 implements Runnable {
    private Banco bank;
    public Cliente1(Banco b) {
        this.bank = b;
    }
    public void run() {
        for (int i = 0; i < 100000; i++) {
            bank.depositar(0, 5);
        }
    }
}

class Cliente2 implements Runnable {
    private Banco bank;
    public Cliente2(Banco b) {
        this.bank = b;
    }
    public void run() {
        for (int i = 0; i < 100000; i++) {
            bank.levantar(0, 5);
        }
    }
}

public class Ex02 {
    public static void main(String args[]) throws Exception {
        int numContas = 10;
        Banco b = new Banco(numContas);
        Thread t1 = new Thread(new Cliente1(b));
        Thread t2 = new Thread(new Cliente2(b));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("O valor da conta 0 é " + b.consultar(0));
    }
}

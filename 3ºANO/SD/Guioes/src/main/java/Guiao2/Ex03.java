package Guiao2;

import java.lang.Runnable;
import java.lang.Thread;

class Banc {
    private double contas[];
    public Banc(int n) {
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
    public synchronized void transferir(int contaOrigem, int contaDestino, double valor) {
        levantar(contaOrigem, valor);
        depositar(contaDestino, valor);
    }
}

class Cli1 implements Runnable {
    private Banc bank;
    public Cli1(Banc b) {
        this.bank = b;
    }
    public void run() {
        bank.transferir(0, 1, 1000);
    }
}

class Cli2 implements Runnable {
    private Banc bank;
    public Cli2(Banc b) {
        this.bank = b;
    }
    public void run() {
        System.out.println("O valor final da conta 0 é " + bank.consultar(0));
        System.out.println("O valor final da conta 1 é " + bank.consultar(1));
        bank.levantar(1, 1000);
    }
}

public class Ex03 {
    public static void main(String args[]) throws Exception {
        int numContas = 10;
        Banc b = new Banc(numContas);
        b.depositar(0, 1000);
        Thread t1 = new Thread(new Cli1(b));
        Thread t2 = new Thread(new Cli2(b));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("O valor final da conta 1 é " + b.consultar(1));
    }
}

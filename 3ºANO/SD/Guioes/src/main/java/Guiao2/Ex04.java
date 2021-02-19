package Guiao2;

import java.lang.Runnable;
import java.lang.Thread;

class Conta {
    private double valor;
    public Conta(){
        this.valor = 0;
    }
    void depositar(double val) {
        this.valor = this.valor + val;
    }
    void levantar(double val) {
        this.valor = this.valor - val;
    } 
    double consultar() {
        return this.valor;
    }
}

class Bank {
    private Conta contasArray[];
    public Bank(int n) {
        this.contasArray = new Conta[n];
        for (int i = 0; i < n; i++) {
            this.contasArray[i] = new Conta();
        }
    }
    public double consultar(int conta) {
        return this.contasArray[conta].consultar();
    }
    public void depositar(int conta, double valor) {
        synchronized (this.contasArray[conta]) {
            this.contasArray[conta].depositar(valor);
        }
    }
    public void levantar(int conta, double valor) {
        synchronized (this.contasArray[conta]) {
            this.contasArray[conta].levantar(valor);
        }
    }
    public void transferir(int contaOrigem, int contaDestino, double valor) throws InterruptedException {
        int conta_menor_id = Math.min(contaOrigem, contaDestino);
        int conta_maior_id = Math.max(contaOrigem, contaDestino);
        synchronized (this.contasArray[conta_menor_id]) {
            synchronized (this.contasArray[conta_maior_id]) {
                this.contasArray[contaOrigem].levantar(valor);
                this.contasArray[contaDestino].depositar(valor);
            }
        }
    }
}

class C1 implements Runnable {
    private Bank b;
    public C1(Bank ba) {
        this.b = ba;
    }
    public void run() {
        try {
            b.transferir(0, 1, 1000);
        } catch (InterruptedException e) {
        }
    }
}

class C2 implements Runnable {
    private Bank b;
    public C2(Bank ba) {
        this.b = ba;
    }
    public void run() {
        //System.out.println("O valor final da conta 1 é " + b.consultar(1));
        try {
            b.transferir(1, 0, 1000);
        } catch (InterruptedException e) {
        }
    }
}

public class Ex04 {
    public static void main(String args[]) throws Exception {
        int numContas = 10;
        Bank b = new Bank(numContas);
        Thread t1 = new Thread(new C1(b));
        t1.setName("Cliente1");
        Thread t2 = new Thread(new C2(b));
        t2.setName("Cliente2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("O valor final da conta 0 é " + b.consultar(0));
        System.out.println("O valor final da conta 1 é " + b.consultar(1));
    }
}

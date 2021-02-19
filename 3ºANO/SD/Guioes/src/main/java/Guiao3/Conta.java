package Guiao3;

import java.util.concurrent.locks.ReentrantLock;

public class Conta {
    private double saldo;
    private ReentrantLock lockConta;

    public Conta(double saldo) {
        this.saldo = saldo;
        this.lockConta = new ReentrantLock();
    }

    public void levantar(double val){
        this.saldo = this.saldo - val;
    }

    public void depositar(double val) {
        this.saldo = this.saldo + val;
    }

    public double consultar() {
        return this.saldo;
    }

    public void lock() {
        this.lockConta.lock();
    }

    public void unlock() {
        this.lockConta.unlock();
    }
}

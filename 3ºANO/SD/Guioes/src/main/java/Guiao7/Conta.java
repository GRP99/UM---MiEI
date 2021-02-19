package Guiao7;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Conta {

    private double saldo;
    private ReentrantLock lockConta;
    private ArrayList<Movimento> movimentos;

    public Conta(double saldo) {
        this.saldo = saldo;
        this.lockConta = new ReentrantLock();
        this.movimentos = new ArrayList<>();
    }

    public void levantar(double val, String descritivo) {
        this.saldo = this.saldo - val;
        Movimento m = new Movimento (Thread.currentThread().getId(),descritivo,val,this.saldo,"Levantamento");
        this.movimentos.add(m);
    }

    public void depositar(double val, String descritivo) {
        this.saldo = this.saldo + val;
        Movimento m = new Movimento (Thread.currentThread().getId(),descritivo,val,this.saldo,"Depósito");
        this.movimentos.add(m);
    }

    public double consultar() {
        return this.saldo;
    }

    public void addMovimento(Movimento m) {
        this.movimentos.add(m);
    }

    public ArrayList<Movimento> movimentos() {
        return new ArrayList<>(this.movimentos);
    }

    public void lock() {
        this.lockConta.lock();
    }

    public void unlock() {
        this.lockConta.unlock();
    }
}

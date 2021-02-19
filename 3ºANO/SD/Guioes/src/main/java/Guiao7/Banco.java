package Guiao7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {

    private int lastId;
    private ReentrantLock lockBanco;
    private HashMap<Integer, Conta> contas;

    public Banco() {
        this.lastId = -1;
        this.lockBanco = new ReentrantLock();
        this.contas = new HashMap<>();
    }

    public int criarConta(double saldo) {
        this.lockBanco.lock();
        this.lastId = this.lastId + 1;
        int id = lastId;
        Conta c = new Conta(saldo);
        this.contas.put(id, c);
        this.lockBanco.unlock();
        return id;
    }

    public double fecharConta(int id) throws ContaInvalida {
        this.lockBanco.lock();
        if (this.contas.containsKey(id)) {
            Conta c = this.contas.get(id);
            c.lock();
            double saldo = c.consultar();
            this.contas.remove(id);
            c.unlock();
            this.lockBanco.unlock();
            return saldo;
        } else {
            this.lockBanco.unlock();
            throw new ContaInvalida(id);
        }
    }

    public void transferir(int c_origem, int c_destino, double valor, String descritivo) throws ContaInvalida, SaldoInsuficiente {
        int min = Math.min(c_origem, c_destino);
        int max = Math.max(c_origem, c_destino);
        this.lockBanco.lock();
        Conta c1 = this.contas.get(min);
        Conta c2 = this.contas.get(max);
        if (c1 == null) {
            this.lockBanco.unlock();
            throw new ContaInvalida(min);
        }
        if (c2 == null) {
            this.lockBanco.unlock();
            throw new ContaInvalida(max);
        }
        c1.lock();
        c2.lock();
        try {
            c1.levantar(valor, descritivo);
            c2.depositar(valor, descritivo);

        } finally {
            c1.unlock();
            c2.unlock();
            this.lockBanco.unlock();
        }
    }

    public double consultarTotal(int[] contasInput) {
        double saldoTotal = 0;
        ArrayList<Integer> contasLocked = new ArrayList(contasInput.length);
        this.lockBanco.lock();
        for (int i = 0; i < contasInput.length; i++) {
            int id = contasInput[i];
            if (contas.containsKey(id)) {
                this.contas.get(id).lock();
                contasLocked.add(id);
            }
        }
        this.lockBanco.unlock();
        for (int id : contasLocked) {
            saldoTotal = contas.get(id).consultar() + saldoTotal;
            contas.get(id).unlock();
        }
        return saldoTotal;
    }

    public double consultar(int id) throws ContaInvalida {
        this.lockBanco.lock();
        if (this.contas.containsKey(id)) {
            Conta c = this.contas.get(id);
            c.lock();
            double saldo = c.consultar();
            c.unlock();
            this.lockBanco.unlock();
            return saldo;
        } else {
            this.lockBanco.unlock();
            throw new ContaInvalida(id);
        }
    }

    public void levantar(int id, double valor, String descritivo) throws ContaInvalida, SaldoInsuficiente {
        this.lockBanco.lock();
        if (this.contas.containsKey(id)) {
            Conta c = this.contas.get(id);
            c.lock();
            if (c.consultar() > valor) {
                c.levantar(valor, descritivo);
                this.contas.put(id, c);
                c.unlock();
                this.lockBanco.unlock();
            } else {
                c.unlock();
                throw new SaldoInsuficiente(valor);
            }
        } else {
            this.lockBanco.unlock();
            throw new ContaInvalida(id);
        }
    }

    public void depositar(int id, double valor, String descritivo) throws ContaInvalida {
        this.lockBanco.lock();
        if (this.contas.containsKey(id)) {
            Conta c = this.contas.get(id);
            c.lock();
            c.depositar(valor, descritivo);
            this.contas.put(id, c);
            c.unlock();
            this.lockBanco.unlock();
        } else {
            this.lockBanco.unlock();
            throw new ContaInvalida(id);
        }
    }

    public ArrayList<Movimento> movimentos(int id) throws ContaInvalida {
        ArrayList<Movimento> list = new ArrayList<>();
        this.lockBanco.lock();
        if (this.contas.containsKey(id)) {
            this.contas.get(id).lock();
            list = this.contas.get(id).movimentos();
            this.contas.get(id).unlock();
            this.lockBanco.unlock();
        } else {
            this.lockBanco.unlock();
            throw new ContaInvalida(id);
        }
        return list;
    }
}

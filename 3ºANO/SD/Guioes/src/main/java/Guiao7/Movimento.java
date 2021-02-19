package Guiao7;

public class Movimento {

    private long id;
    private String descricao;
    private double valor;
    private double saldo;
    private String tipo;

    public Movimento(long id_operacao, String descritivo, double val, double sal, String tipo) {
        this.id = id_operacao;
        this.descricao = descritivo;
        this.valor = val;
        this.saldo = sal;
        this.tipo = tipo;
    }

    public String toString() {
        return "ID: " + this.id + " da operação " + this.tipo + " com a descrição " + this.descricao + ", no valor de " + this.valor + ". Ficou com " + this.saldo + ".";
    }
}

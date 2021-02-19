package Guiao3;

public class ContaInvalida extends Exception {

    public ContaInvalida(int id) {
        super("Conta Invalida: " + id);
    }
}

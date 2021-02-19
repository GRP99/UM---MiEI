package Guiao7;

public class SaldoInsuficiente extends Exception {

    public SaldoInsuficiente(double val) {
        super("Saldo Insuficiente:" + val);
    }
}

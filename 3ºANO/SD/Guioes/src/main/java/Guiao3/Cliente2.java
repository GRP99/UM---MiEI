package Guiao3;

public class Cliente2 implements Runnable {

    private Banco banco;

    public Cliente2(Banco b) {
        this.banco = b;
    }

    public void run() {
        try {
            this.banco.transferir(0, 1, 5);
            System.out.println("Fechar Conta " + this.banco.fecharConta(1));
            System.out.println("Consultar " + this.banco.consultar(0));
        } catch (ContaInvalida ci) {
            System.out.println(ci);
        } catch (SaldoInsuficiente si) {
            System.out.println(si);
        }
    }

}

package Guiao3;

public class Cliente1 implements Runnable {

    private Banco banco;

    public Cliente1(Banco b) {
        this.banco = b;
    }

    public void run() {
        try {
            System.out.println("Criar Conta " + this.banco.criarConta(0));
            this.banco.transferir(0, 2, 5);
            int[] contas = {0, 1, 2};
            System.out.println("Consultar Total " + this.banco.consultarTotal(contas));
        } catch (ContaInvalida ci) {
            System.out.println(ci);
        } catch (SaldoInsuficiente si) {
            System.out.println(si);
        }
    }
}

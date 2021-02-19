import java.util.Scanner;
public class ExeII_3{
    public static String criaDescricaoConta (String nome, double saldo){
        return "Nome: " + nome + ",saldo: " + saldo;
    }
    public static void main(String[] args) {
        String nome,cDc;
        double saldo;
        Scanner ler = new Scanner(System.in);
        System.out.print("Indique o seu nome: ");
        nome = ler.nextLine();
        System.out.print("Indique o seu saldo: ");
        saldo = ler.nextDouble();
        cDc=criaDescricaoConta(nome,saldo);
        System.out.println(cDc);
    }
}
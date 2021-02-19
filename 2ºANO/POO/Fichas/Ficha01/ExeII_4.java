import java.util.Scanner;
public class ExeII_4{
    public static double eurosParalibras (double valor, double taxaConversao){
        return (valor*taxaConversao);
    }
    public static void main(String[] args) {
        double val,taxa,lib;
        Scanner ler = new Scanner(System.in);
        System.out.print("Indique o valor em Euros: ");
        val = ler.nextDouble();
        System.out.print("Indique a taxa de conversao: ");
        taxa = ler.nextDouble();
        lib=eurosParalibras(val,taxa);
        System.out.println("O seu valor em libras é " +lib);
    }
}
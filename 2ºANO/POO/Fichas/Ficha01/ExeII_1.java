import java.util.Scanner;
public class ExeII_1 {
    public static double  celsiusParaFarenheit(double graus){
       return ((graus * 1.8) +32);
    }
    /** Início do programa */
    public static void main(String[] args) {
        double x,f;
        Scanner ler = new Scanner(System.in);
        System.out.print("Indique a temperatura em Graus Celsius: ");
        x = ler.nextInt();
        ler.close();
        f = celsiusParaFarenheit(x);
        System.out.println("A sua temperatura em Farenheit é: "+f);
        ler.close();
    }
}

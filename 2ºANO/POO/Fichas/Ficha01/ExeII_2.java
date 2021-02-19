import java.util.Scanner;
public class ExeII_2{
    public static int maximoNumeros(int a, int b){
        if (a>b) return a;
        else return b;
    }
    public static void main(String[] args) {
        int x,y,max;
        Scanner ler = new Scanner(System.in);
        System.out.print("Indique dois números: ");
        x = ler.nextInt();
        y = ler.nextInt();
        ler.close();
        max = maximoNumeros(x,y);
        System.out.println("O máximo dos dois números é: "+max);
        ler.close();
    }
}

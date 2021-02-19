import java.util.Scanner;
public class ExeII_6{
    public static long factorial (int num){
        int i;
        long fact=1;
        for(i=1;i<=num;i++){
            fact=fact*i;
        }
        return fact;
    }
    public static void main(String[] args) {
        int val;
        long fact;
        Scanner ler = new Scanner(System.in);
        System.out.print("Indique um número: ");
        val = ler.nextInt();
        fact = factorial(val);
        System.out.println("O factorial de " + val + " é " + fact);
    }
}

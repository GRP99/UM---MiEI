import java.util.Scanner;
public class ExeII_5{
    public static String ordemDecrescente (int x, int y){
        if (x>y) return (+ x + ">" + y + "\n") ; else return (+ y + ">" + x + "\n"); 
    }
    public static void main(String[] args) {
        int x, y;
        Scanner ler = new Scanner(System.in);
        System.out.print("Indique dois numeros inteiros: ");
        x = ler.nextInt();
        y = ler.nextInt();
        String ordem = ordemDecrescente(x,y);
        System.out.println(ordem + "média dos seus numeros: " +((x+y)/2));
    }
}
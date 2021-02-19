import java.time.LocalDateTime;
public class ExeII_7{
    public static long tempoGasto (){
        int i;
        long fact=1;
        for (i=1;i<=5000;i++){
            fact=fact*i;
        }
        return fact;
    }
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long fact = tempoGasto();
        long end = System.currentTimeMillis();
        System.out.println("O factorial de 5000 demorou " + (end - start) + "ms");
    }
}
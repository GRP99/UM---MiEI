import java.util.Scanner;
public class Ex1{
    public static int minimo (int [] lista){
        int min = lista[0];
        for (int i=1; i < lista.length; i++) {
            if (lista[i] < min) min = lista[i];
        }
        return min;
    }
    public static int [] arrayEntre (int [] lista, int i1, int i2){
        int [] l2 = new int [i2-i1+1];
        int j = 0;
        for (int i = i1; i <= i2; i++){
            l2[j] = lista[i];
            j++;
        }
        return l2;
    }
    public static int [] comuns (int []l1, int []l2, int m){
        int flag = 0;
        int x = 0;
        int [] c = new int [m];
        for (int i = 0; i < l1.length; i++){
            for (int j = 0; j < l2.length; j++){
                if (l1[i]==l2[j]){
                    for(int y=0; y < c.length && flag != 1;y++){
                        if (l1[j] == c[y]) flag = 1;                
                    }
                    if (flag == 0){
                        c[x] = l1[j];
                        x++;
                    }
                    else flag=0;
                }
            }
        }
        return c;
    }
    
    public static void main (String [] args){
        Scanner sc = new Scanner(System.in);
        /*
        int min = minimo(lista);
        System.out.print("O minimo da lista : " + min);
        
        System.out.print("Indique um índice:");
        int i1 = sc.nextInt();
        System.out.print("Indique um outro índice:");
        int i2 = sc.nextInt();
        int [] lista2 = arrayEntre(lista,i1,i2);
        for (int j=0; j<lista2.length; j++){
            System.out.print("\n" + lista2[j]);
        }
        */
        int min;
        int valor1=0;
        int valor2=0;
        System.out.print("Quantos inteiros quer introduzir para o array1?");
        int num1 = sc.nextInt();
        System.out.print("Quantos inteiros quer introduzir para o array2?");
        int num2 = sc.nextInt();
        int [] lista1 = new int [num1];
        int [] lista2 = new int [num2];
        for(int i = 0; i < num1; i++) {
            System.out.print("Introduza um valor para o array1: ");
            valor1 = sc.nextInt();
            lista1[i] = valor1;
        }
        for(int j = 0; j < num2; j++) {
            System.out.print("Introduza um valor para o array2: ");
            valor2 = sc.nextInt();
            lista2[j] = valor2;
        }
        sc.close();
        if (lista1.length > lista2.length){
            min = lista2.length;
        }
        else{
            min = lista1.length;
        }
        int [] sol = comuns(lista1,lista2,min);
        System.out.print("Elementos comuns: ");
        for (int z=0; z<sol.length; z++){
            System.out.print("\n" + sol[z]);
        }
    }
}

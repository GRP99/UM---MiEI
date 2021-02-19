import java.util.Scanner;
public class Ex5{
    public static String [] semrepeticoes(String [] palavras){
       String novaspalavras1 [] = new String [palavras.length];
       int x=0;
       for(int i = 0; i < palavras.length; i++){
           for (int j = 1; j < palavras.length; j++){
               if (!(palavras[i]==palavras[j])) novaspalavras1[x] = palavras[i];x++;
           }
       }
       String novaspalavras2 [] = new String [x];
       for (int y=0; y < x; y++){
           novaspalavras2[y] = novaspalavras1[y];
       }
       return novaspalavras2;
    }
    public static String maiorString(String [] palavras){
        int max = 0;
        String maior = " ";
        for(int i=0; i<palavras.length; i++){
            if (palavras[i].length() > max){
                max = palavras[i].length();
                maior = palavras[i];
            }
        }
        return maior;
    }
    public static String [] repetidas(String [] palavras){
        String [] repetidas1 = new String [palavras.length];
        int j=0;
        int x=0;
        for (int i=0; i<repetidas1.length; i++){
            if (!(palavras[i] == palavras[j])) j++;
            else repetidas1[x] = palavras[i];x++;
        }
        String [] repetidas2 = new String [palavras.length];
        for(int y=0; y<x; y++){
            repetidas2[y]=repetidas1[y];
        }
        return repetidas2;
    }
    public static int procura (String palavra, String [] palavras){
        int x=0;
        for(int i=0; i<palavras.length; i++){
            if (palavra.equals(palavras[i])) x++;
        }
        return x;
    }
    
    public static void main (String [] args){
        Scanner sc = new Scanner(System.in);
        String [] palavras = {"Olá", "Mundo", "Olá", "Java", "POO"};
        /*
        System.out.print("Introduza o tamanho do array:");
        int tam = sc.nextInt();
        String [] mypalavras = new String [tam]; 
        for(int i=0; i<tam; i++){
            System.out.print("Introduza uma palavra: ");
            String p = sc.next();
            mypalavras[i] = p;
        }
        sc.close();
        System.out.print("As suas palavras são: ");
        for(int j=0; j<tam; j++){
            System.out.print("\n" + mypalavras[j]);
        }
        
        String [] palavrasExistentes = semrepeticoes(palavras);
        for(int i =0; i < palavrasExistentes.length; i++){
            System.out.print(palavrasExistentes[i]);
        }
        
        String maior = maiorString(palavras);
        System.out.print("A String maior é " + maior + ".");
        
        String [] palavrasrepetidas = repetidas(palavras);
        System.out.print("As palavras que aparecem mais de uma vez são: \n");
        for(int i=0; i<palavrasrepetidas.length; i++){
            System.out.print(palavrasrepetidas[i] + "\n");
        }
        */
        System.out.print("Introduza uma palavra:");
        String palavra = sc.next();
        sc.close();
        int ocorrencias = procura(palavra, palavras);
        System.out.print("A sua palavras ocorre no array criado " + ocorrencias + "vez.");
    }
}
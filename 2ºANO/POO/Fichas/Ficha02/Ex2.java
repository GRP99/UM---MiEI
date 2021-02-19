import java.util.Scanner;
public class Ex2{
    public static int [][] atualizaNota (int aluno,int uc, int novaNota, int [][] notasTurma){
        notasTurma [aluno-1] [uc-1] = novaNota;
        return notasTurma;         
    }
    public static int somaNotas (int uc, int [][] notasTurma){
        int soma = 0;
        uc --; 
        for (int x = 0; x < 5; x++){
            soma = notasTurma[x][uc] + soma;
        }
        return soma;
    }
    public static float mediaAluno (int aluno,int [][] notasTurma){
        int soma = 0;
        aluno--;
        for (int i = 0; i < 5; i++){
            soma = notasTurma[aluno][i] + soma;
        }
        float media = soma/5;
        return media;
    }
    public static float mediaUC (int uc,int [][] notasTurma){
        int soma = 0;
        uc--;
        for (int i = 0; i < 5; i++){
            soma = notasTurma[i][uc] + soma;
        }
        float media = soma/5;
        return media;
    }
    public static int notaMaisalta (int [][] notasTurma){
        int maximo = 0;
        for (int i = 0; i < 5; i ++){
            for (int j = 0; j < 5; j++){
                if (maximo < notasTurma[i][j]) maximo = notasTurma[i][j];
            }
        }
        return maximo;
    }
    public static int notaMaisbaixa (int [][] notasTurma){
        int minimo = notasTurma[0][0];
        for (int i = 0; i < 5; i ++){
            for (int j = 0; j < 5; j++){
                if (minimo > notasTurma[i][j]) minimo = notasTurma[i][j];
            }
        }
        return minimo;
    }
    public static int [] acima (int valor, int [][] notasTurma){
        int [] valores = new int [25];
        int x = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if (notasTurma[i][j] > valor){
                    valores[x] = notasTurma[i][j];
                    x++;
                }
            }
        }
        int [] valorescertos = new int [x];
        for (int g = 0; g < x; g++){
            valorescertos[g] = valores[g];
        }
        return valorescertos;
    }
    public static String geraString (int [][] notasTurma){
        String notas = " ";
        for(int i=0; i<5; i++){
            for (int j=0; j<5;j++){
                notas = notas + Integer.toString(notasTurma[i][j]) + " ; " ;
            }
        }
        return notas;
    }
    public static int mediaUCalta(int [][] notasTurma){
        int max = 0;
        float maxmedia = 0;
        float media = 0;
        for(int x=0; x<5; x++){
            media = mediaUC((x+1),notasTurma);
            if (media > maxmedia){
                max = x;
                maxmedia = media;
            }
        }
        return max;
    }
    
    
    
    public static void main (String [] args){
       Scanner sc = new Scanner(System.in);
        
       int [][]notasTurma = { {13, 13, 15, 14, 13}, {17, 16, 19, 18, 15}, {10, 10, 11, 10, 10}, {11, 13, 15, 17, 19}, {9, 10, 13, 5, 12}};
        
       /*
        int [][] notasTurma = new int [5][5];
        for (int i=0; i < 5; i++){
             System.out.print("Aluno nº" + (i+1) + ":\n");
             for (int j=0; j<5;j++){
                 System.out.print("Introduza uma nota entre 0 e 20: ");
                 int nota = sc.nextInt(); 
             }
         }
         
         System.out.print("Introduza o aluno(1-5), a unidade curricular(1-5) e a nota que pretende atualiza(0-20), por esta ordem:");
         int aluno = sc.nextInt();
         int uc = sc.nextInt();
         int novaNota = sc.nextInt();
         sc.close();
         int [][] notasTurmasAtualizadas = atualizaNota(aluno,uc,novaNota,notasTurma);
         
         System.out.print("Introduza uma Unidade Curricular: ");
         int uc = sc.nextInt();
         sc.close();
         int soma = somaNotas(uc,notasTurma);
         System.out.print("A soma das notas dessa Unidade Curricular é " + soma);
         
         System.out.print("Introduza um aluno(1-5): ");
         int a = sc.nextInt();
         sc.close();
         float media = mediaAluno(a,notasTurma);
         System.out.print("A média do aluno " + a + " é " + media);
         
         System.out.print("Introduza uma Unidade Curricular(1-5): ");
         int uc = sc.nextInt();
         sc.close();
         float media = mediaUC(uc,notasTurma);
         System.out.print("A média da Unidade Curricular " + uc + " é " + media);
         
         int max = notaMaisalta(notasTurma);
         System.out.print("A nota mais alta foi " + max);
         
         int min = notaMaisbaixa(notasTurma);
         System.out.print("A nota mais baixa foi " + min);
         
         for (int x=0; x < 5; x++){
             System.out.print("Aluno nº" + (x+1) + ": \n");
             for (int y=0; y < 5; y++){
                 System.out.print("Notas : " + notasTurma[x][y] + "  ");
                 if (y==4) System.out.print("\n");
             }
         } 
         
         System.out.print("Introduza um valor(1-20): ");
         int val = sc.nextInt();
         int [] acimaVal = acima(val,notasTurma);
         for(int i=0; i < acimaVal.length; i++){
             System.out.print("Notas acima do seu valor : " + acimaVal[i] + "\n" );
         }
         
         String notas = geraString(notasTurma);
         System.out.print("A string com todas as notas dos alunos é " + notas);
       */
        int mediaUCmaisAlta = mediaUCalta(notasTurma);
        System.out.print("A UC com a média mais alta é a UC nº" + (mediaUCmaisAlta+1));
    }
}
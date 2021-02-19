/*********************************************************************************/
/** DISCLAIMER: Este código foi criado e alterado durante as aulas práticas      */
/** de POO. Representa uma solução em construção, com base na matéria leccionada */ 
/** até ao momento da sua elaboração, e resulta da discussão e experimentação    */
/** durante as aulas. Como tal, não deverá ser visto como uma solução canónica,  */
/** ou mesmo acabada. É disponibilizado para auxiliar o processo de estudo.      */
/** Os alunos são encorajados a testar adequadamente o código fornecido e a      */
/** procurar soluções alternativas, à medida que forem adquirindo mais           */
/** conhecimentos de POO.                                                        */
/*********************************************************************************/


 


/**
 * Write a description of class TesteComparators here.
 *
 * @author MaterialPOO
 * @version 2018/2019
 */

import java.util.TreeSet;
import java.util.Comparator;

public class TesteComparators {
    
  public static void main(String[] args) {
    
    Circulo c1 = new Circulo(3,5,4);
    Circulo c2 = new Circulo(6,7,2);
    Circulo c3 = new Circulo(0,0,1);
    Circulo c4 = new Circulo(10,2,5.5);
      
    TreeSet<Circulo> circs = new TreeSet<>();
    
    circs.add(c1.clone());
    circs.add(c2.clone());
    circs.add(c3.clone());
    circs.add(c4.clone());
    
    System.out.println("Ordem Natural");
    System.out.println(circs);
   
    TreeSet<Circulo> circs1 = new TreeSet<>(new ComparatorCirculov1());
    
    circs1.add(c1.clone());
    circs1.add(c2.clone());
    circs1.add(c3.clone());
    circs1.add(c4.clone());
    
    System.out.println("Ordem Comparator por raio");
    System.out.println(circs1);
    
    
    
    TreeSet<Circulo> oCircs = new TreeSet<>(
                     new Comparator<Circulo>() {
                         public int compare(Circulo a, Circulo b) {
                             if (a.getY() == b.getY())
                               return 0;
                             else  
                               return (int) (a.getY() - b.getY());
                         }     
                     
                        });
    oCircs.add(c1.clone());
    oCircs.add(c2.clone());
    oCircs.add(c3.clone());
    oCircs.add(c4.clone());

    System.out.println("Comparator classe anónima");
    System.out.println(oCircs);
    
    
    TreeSet<Circulo> nCircs = new TreeSet<>((x,y) -> 
                              {if (x.getRaio() == y.getRaio())
                                  return 0;
                               else return (int) (y.getRaio()-x.getRaio());
                              });
                                                        
                                                        
    nCircs.add(c1.clone());
    nCircs.add(c2.clone());
    nCircs.add(c3.clone());
    nCircs.add(c4.clone());
    
    System.out.println("Comparator lambda");
    System.out.println(nCircs);
   
  }
}

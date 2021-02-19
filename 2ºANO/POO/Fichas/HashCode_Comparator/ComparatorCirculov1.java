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
 * Esta classe é a implementação de um comparador de Ponto.
 * É necessário ter um comparador de Ponto, por forma a que seja possível
 * parmetrizarmos os TreeSet e TreeMap por esta forma de comparação.
 * 
 * 
 * As estruturas de dados ordenadas devem ser parametrizadas, na sua 
 * criação, por um objecto do tipo PontoComparator.
 * 
 * @author MaterialPOO
 * @version 20180314
 */

import java.util.Comparator;
import java.io.Serializable;

public class ComparatorCirculov1 implements Comparator<Circulo>, Serializable {
  

  /** 
   * Implementação do método compare de Circulo.
   * 
   * Nota: a implementação utilizada é apenas uma das várias
   * formas que poderiamos ter de comparar Circulo.
   * Um circulo é maior do que outro se o raio for maior!!
   * 
   */
  public int compare(Circulo c1, Circulo c2) {
    if (c1.getRaio() == c2.getRaio())
      return 0;
    else 
      return (int) (c1.getRaio() - c2.getRaio());
  }
}








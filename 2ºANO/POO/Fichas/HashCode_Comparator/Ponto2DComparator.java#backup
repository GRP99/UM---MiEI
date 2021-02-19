
/**
 * Esta classe é a implementação de um comparador de Ponto2D.
 * É necessário ter um comparador de Ponto2D, de forma a que seja possível
 * colecções (TreeSet, TreeMap) ordenadas de Ponto2D. Sem a existência de 
 * um comparador não seria
 * possível determinar a ordem pela qual os objectos são guardados.
 * 
 * 
 * As estruturas de dados ordenadas devem ser parametrizadas, na sua 
 * criação, por um objecto do tipo Ponto2DComparator.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Comparator;
import java.io.Serializable;

public class Ponto2DComparator implements Comparator<Ponto2D>, Serializable {
  

  /** 
   * Implementação do método compare de Ponto2D.
   * 
   * Nota: a implementação utilizada é apenas uma das várias
   * formas que poderiamos ter de comparar Ponto2D.
   * 
   */
  public int compare(Ponto2D p1, Ponto2D p2) {
    if (p1.getX() < p2.getX()) return -1;
    if( p1.getX() > p2.getX() ) return 1;
    if( p1.getY() < p2.getY() ) return -1;
    if( p1.getY() > p2.getY() ) return 1; else return 0;
  }
}












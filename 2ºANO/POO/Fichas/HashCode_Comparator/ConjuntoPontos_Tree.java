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
 * Classe que representa um conjunto de Ponto.
 * Internamente os diversos pontos s�o mantidos num TreeSet<Ponto>. 
 * (note-se a semelhan�a do c�digo desta classe com o da classe
 * ConjuntoPontos_Hash).
 * 
 * Os pontos s�o ordenados na estrutura de dados atrav�s do m�todo
 * compare(Ponto,Ponto) que foi definido em Ponto2DComparator.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.TreeSet;
import java.util.Collection;
import java.util.Iterator;

public class ConjuntoPontos_Tree {


  private TreeSet<Ponto> pontos;
  private String nomeConj;
  
  public ConjuntoPontos_Tree() {
    this.pontos = new TreeSet<Ponto>(new ComparatorPontov1());
    this.nomeConj = "";
  }
  
  public ConjuntoPontos_Tree(String nome) {
    this.pontos = new TreeSet<Ponto>(new ComparatorPontov1());
    this.nomeConj = nome;
  }
  
  public ConjuntoPontos_Tree(Collection<Ponto> novosPontos) {
    this.pontos = new TreeSet<Ponto>(new ComparatorPontov1());
    this.nomeConj = "";
    for (Ponto p: novosPontos)
      pontos.add(p.clone());
  }
  
  
  public ConjuntoPontos_Tree(ConjuntoPontos_Tree umConj) {
    this.pontos = umConj.getPontos();
    this.nomeConj = umConj.getNomeConj();
  }  
  
  
  /**
   * Devolve o valor do nome do conjunto de pontos
   */
  public String getNomeConj() {
    return this.nomeConj;
  }

  /**
   * Devolve um conjunto de Ponto. Para respeitar o princ�pio do encapsulamento
   * os objectos contidos s�o clonados.
   */

  public TreeSet<Ponto> getPontos() {
    TreeSet<Ponto> novo = new TreeSet<>(new ComparatorPontov1());
    for(Ponto p: this.pontos)
       novo.add(p.clone());
    return novo;
  }
  
  //outros m�todos
  
   /**
   * Insere um novo Ponto no conjunto.
   * Esta classe guarda o ponto no HashSet.
   */
  
  public void inserePonto(Ponto p) {
    this.pontos.add(p.clone());
  }  
  
  
  /**
   * Devolve o n�mero de pontos que est�o guardados.
   * 
   */
  
   public int numPontos() {
     return this.pontos.size();
   }
   
   /**
    * Verifica se um determinado Ponto existe neste conjunto.
    * 
    */
  
    public boolean existePonto(Ponto p) {
      return this.pontos.contains(p);
    }  
    
    /**
     * Conta o n�mero de pontos at� encontrar um ponto com 
     * coordenada X igual ao par�metro.
     * 
     * M�todo com utiliza��o de Iterator, para percorrer o 
     * TreeSet at� encontrar um ponto com X = valor passado como
     * par�metro.
     */
     
     public int numPontosAntesCoord(double coordX) {
         int numPontos = 0;
         boolean stop = false;
         Iterator<Ponto> i = this.pontos.iterator();
         while(i.hasNext() && !stop) {
            if ((i.next().getX()) != coordX)
              numPontos++;
            else
              stop = true;
         }
         
         return numPontos;
     }   
             
     /**
      * Altera a coordenada X de todos os pontos 
      * que est�o no conjunto.
      * 
      */
     
     public void alteraTodosPontos(int incremento) {
       for(Ponto p: this.pontos) 
         p.deslocamento(incremento,0); // incremento 0 para a coord Y
     }    
    
    
    
  // toString, equals e clone
  /**
   * Vers�o simplista, e n�o muito eficiente, do m�todo toString.
   * Deveria ser reescrito com base num StringBuffer.
   */
  
  public String toString() {
    return "ConjuntoPontos: "+ this.nomeConj + "Elementos: " + this.pontos.toString();
  }  
  
  
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    else {
      ConjuntoPontos_Tree conj = (ConjuntoPontos_Tree) o;
      return this.nomeConj.equals(conj.getNomeConj()) && 
             this.pontos.equals(conj.getPontos());
    }
  }  
  
  
  public ConjuntoPontos_Tree clone() {
    return new ConjuntoPontos_Tree(this);
  }
    
}

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
 * Internamente os diversos pontos s�o mantidos num HashSet<Ponto>
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.HashSet;
import java.util.Collection;
import java.util.Set;
import java.util.Iterator;

public class ConjuntoPontos_Hash {

  private HashSet<Ponto> pontos;
  private String nomeConj;
  
  public ConjuntoPontos_Hash() {
    this.pontos = new HashSet<Ponto>();
    this.nomeConj = "";
  }
  
  public ConjuntoPontos_Hash(String nome) {
    this.pontos = new HashSet<Ponto>();
    this.nomeConj = nome;
  }
  
  public ConjuntoPontos_Hash(Collection<Ponto> novosPontos) {
    this.pontos = new HashSet<Ponto>();
    this.nomeConj = "";
    for (Ponto p: novosPontos)
      this.pontos.add(p.clone());
      
    //this.pontos.addAll(novosPontos); --> deixo de ter encapsulamento garantido.  
  }
  
  
  public ConjuntoPontos_Hash(ConjuntoPontos_Hash umConj) {
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

  public HashSet<Ponto> getPontos() {
    HashSet<Ponto> novo = new HashSet<Ponto>();
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
     * HashSet at� encontrar um ponto com X = valor passado como
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
      ConjuntoPontos_Hash conj = (ConjuntoPontos_Hash) o;
      return this.nomeConj.equals(conj.getNomeConj()) && 
             this.pontos.equals(conj.getPontos());
    }
  }  
  
  
  public ConjuntoPontos_Hash clone() {
    return new ConjuntoPontos_Hash(this);
  }
  
}

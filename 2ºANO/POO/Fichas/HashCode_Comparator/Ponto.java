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


package HashCode_Comparator;


/**
 * Classe que implementa um Ponto num plano2D.
 * As coordenadas do Ponto são inteiras.
 *
 * @author  MaterialPOO
 * @version 20180212
 */
public class Ponto {
    
  //variáveis de instância
  private int x;
  private int y;
  
  /**
   * Construtores da classe Ponto.
   * Declaração dos construtores por omissão (vazio),
   * parametrizado e de cópia.
   */
  
  /**
   * Construtor por omissão de Ponto.
   */
  public Ponto() {
    this.x = 0;
    this.y = 0;
  }
  
  /**
   * Construtor parametrizado de Ponto.
   * Aceita como parâmetros os valores para cada coordenada.
   */
  public Ponto(int cx, int cy) {
    this.x = cx;
    this.y = cy;
  }
  
  
  
  /**
   * Construtor de cópia de Ponto.
   * Aceita como parâmetro outro Ponto e utiliza os métodos
   * de acesso aos valores das variáveis de instância.
   */
  public Ponto(Ponto umPonto) {
    this.x = umPonto.getX();
    //this.x = umPonto.x;
    this.y = umPonto.getY();
  }
  
  /**
   * métodos de instância
   */
  
  /**
   * Devolve o valor da coordenada em x.
   * 
   * @return valor da coordenada x.
   */
  public int getX() {
    return this.x;
  }
  
  /**
   * Devolve o valor da coordenada em y.
   * 
   * @return valor da coordenada y.
   */
  public int getY() {
    return this.y;
  }
  
  /**
   * Actualiza o valor da coordenada em x.
   * 
   * @param novoX novo valor da coordenada em X
   */
  public void setX(int novoX) {
    this.x = novoX;
  }
  
  /**
   * Actualiza o valor da coordenada em y.
   * 
   * @param novoY novo valor da coordenada em Y
   */
  public void setY(int novoY) {
    this.y = novoY;
  }
  
  /**
   * Método que desloca um ponto somando um delta às coordenadas
   * em x e y.
   * 
   * @param deltaX valor de deslocamento do x
   * @param deltaY valor de deslocamento do y
   */
  public void deslocamento(int deltaX, int deltaY) {
    this.x += deltaX;
    this.y += deltaY;
  }
  
  /**
   * Método que soma as componentes do Ponto passado como parâmetro.
   * @param umPonto ponto que é somado ao ponto receptor da mensagem.
   */
  public void somaPonto(Ponto umPonto) {
    this.x += umPonto.getX();
    this.y += umPonto.getY();
  }
  
  /**
   * Método que move o Ponto para novas coordenadas.
   * @param novoX novo valor de x.
   * @param novoY novo valor de y.
   */
  public void movePonto(int cx, int cy) {
    this.x = cx;  // ou setX(cx)
    this.y = cy;  // ou this.setY(cy)
  }
  
  /**
   * Método que determina se o ponto está no quadrante positivo de x e y
   * @return booleano que é verdadeiro se x>0 e y>0
   */
  public boolean ePositivo() {
    return (this.x > 0 && this.y > 0);
  }
  
  /**
   * Método que determina a distância de um Ponto a outro.
   * @param umPonto ponto ao qual se quer determinar a distância
   * @return double com o valor da distância
   */
  public double distancia(Ponto umPonto) {
      
    return Math.sqrt(Math.pow(this.x - umPonto.getX(), 2) +
                     Math.pow(this.y - umPonto.getY(), 2));
  }
  
  
  /**
   * Método que determina se dois pontos são iguais.
   * @return booleano que é verdadeiro se os valores das duas 
   * coordenadas forem iguais
   */
  public boolean iguais(Ponto umPonto) {
    return (this.x == umPonto.getX() && this.y == umPonto.getY());
  }   
  
  
  /**
   * Método que determina se o módulo das duas coordenadas é o mesmo.
   * @return true, se as coordenadas em x e y 
   * forem iguais em valor absoluto.
   */
  private boolean xIgualAy() {
    return (Math.abs(this.x) == Math.abs(this.y));
  }
  
  /**
   * Método que devolve a representação em String do Ponto.
   * @return String com as coordenadas x e y
   */
  public String toString() {
    return "Cx = " + this.x + " -  Cy = " + this.y;
  }
  
  
  /**
   * Método que faz uma cópia do objecto receptor da mensagem.
   * Para tal invoca o construtor de cópia.
   * 
   * @return objecto clone do objecto que recebe a mensagem.
   */
  
  public Ponto clone() {
    return new Ponto(this);    
  }    
  
  /**
    * Implementação do método hashcode.
    * Este método é necessário sempre que for preciso criar estruturas baseadas em hash.
    * Dessa forma cada uma das instâncias de Ponto será capaz de calcular o seu valor de hash.
    * 
    * Note-se que sempre que sempre que a comparação, com equals, de dois objectos dê true, 
    * então os seus valores de hashcode devem ser o mesmo.
    * 
    * Isto é, se  (o1.equals(o2)) == true, então o1.hashcode() == o2.hashcode()
    * 
    * 
    */
    public int hashCode() {
      return (int)(this.x*7 + this.y*11);
    }   
   
    
   /**
    *todo que implementa a ordem natural.
    */ 
   
   public int compareTo(Ponto p) {
    if (this.x < p.getX()) return 1;
    if( this.x > p.getX() ) return -1;
    if( this.y < p.getY() ) return 1;
    if( this.y > p.getY() ) return -1; else return 0;
  } 
    
  
  
  
}
/**
 * Classe que implementa um Ponto num plano2D.
 * As coordenadas do Ponto são inteiras.
 */
public class Ponto {
  private int x;
  private int y;

  public Ponto() {
    this.x = 0;
    this.y = 0;
  }
  
  public Ponto(int cx, int cy) {
    this.x = cx;
    this.y = cy;
  }
  
  public Ponto(Ponto umPonto) {
    this.x = umPonto.getX();
    this.y = umPonto.getY();
  }
 
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public void setX(int novoX) {
    this.x = novoX;
  }

  public void setY(int novoY) {
    this.y = novoY;
  }
  

  public void deslocamento(int deltaX, int deltaY) {
    this.x += deltaX;
    this.y += deltaY;
  }
  
  public void somaPonto(Ponto umPonto) {
    this.x += umPonto.getX();
    this.y += umPonto.getY();
  }
  
  public void movePonto(int cx, int cy) {
    this.x = cx;  
    this.y = cy;
  }
  
  public boolean ePositivo() {
    return (this.x > 0 && this.y > 0);
  }
  
  public double distancia(Ponto umPonto) {
     return Math.sqrt(Math.pow(this.x - umPonto.getX(), 2) + Math.pow(this.y - umPonto.getY(), 2));
  }
  
  public boolean iguais(Ponto umPonto) {
     return (this.x == umPonto.getX() && this.y == umPonto.getY());
  }   
  
  private boolean xIgualAy() {
    return (Math.abs(this.x) == Math.abs(this.y));
  }
  
  public String toString() {
      return "(" + this.x + "," + this.y + ")";
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if ((o == null) || (this.getClass() != o.getClass()))
      return false;
    Ponto p = (Ponto) o;
    return (this.x == p.getX() && this.y == p.getY());
      
  }
  
  public Ponto clone() {
    return new Ponto(this);    
  }    
}
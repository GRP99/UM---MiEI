/**
   * Classe que implementa um Triangulo 
*/
public class Triangulo{
        // VARIÁVEIS DE INSTÂNCIA
    private Ponto p1;
    private Ponto p2;
    private Ponto p3;
            
    /**
     * CONSTRUTORES DA CLASSE TRIANGULO
     * Declaração dos construtores por omissão (vazio),parametrizado e de cópia. 
     */
   
    /**
     * Construtor por omissão de Triangulo
     */
    public Triangulo(){
        this.p1 = null;
        this.p2 = null;
        this.p3 = null;
    }    
    /**
     * Construtor parametrizado de Triangulo
     * Aceita como parâmetros os valores para cada coordenada
     */
    public Triangulo(Ponto c1, Ponto c2, Ponto c3){
        this.p1 = c1;
        this.p2 = c2;
        this.p3 = c3;
    }    
    /**
     * Construtor de cópia de Triangulo
     * Aceita como parâmetro outro Triangulo e utiliza os métodos de acesso aos valores das variáveis de instância. 
     */
    public Triangulo (Triangulo outro){
        this.p1 = outro.getP1();
        this.p2 = outro.getP2();
        this.p3 = outro.getP3();
    }
    
    
    /**
     * MÉTODOS DE INSTÂNCIA
     */
    
    /**
     * Devolve o valor da ponto 1
     * @return valor do ponto 1
     */
    public Ponto getP1(){
        return this.p1;
    }
    
    /**
     * Devolve o valor do ponto 2
     * @return valor do ponto 2
     */
    public Ponto getP2(){
        return this.p2;
    }
    
    /**
     * Devolve o valor do ponto 3
     * @return valor do ponto 3
     */
    public Ponto getP3(){
        return this.p3;
    }
    
    /**
     * Altera o valor da ponto 1
     * @param n1 novo valor do ponto2.
     */
    public void setP1(Ponto n1){
        this.p1 = n1;
    }
    
    /**
     * Altera o valor da ponto 2
     * @param n2 novo valor do ponto2.
     */
    public void setP2(Ponto n2){
        this.p2 = n2;
    }
    
    /**
     * Altera o valor do ponto 3
     * @param n3 novo valor do ponto3. 
     */
    public void setP3(Ponto n3){
        this.p3 = n3;
    }
    
    /**
     * (B)
     */
    public double calculaAreaTriangulo(){
        int matrix [][] = new int [3][3];
        double det=0;
        double diagPrin1, diagPrin2, diagPrin3, diagSec1, diagSec2, diagSec3;
        matrix[0][0] = this.p1.getX();
        matrix[0][1] = this.p1.getY();
        matrix[0][2]= 1;
        matrix[1][0] = this.p2.getX();
        matrix[1][1] = this.p2.getY();
        matrix[1][2]= 1;
        matrix[2][0] = this.p3.getX();
        matrix[2][1] = this.p3.getY();
        matrix[2][2]= 1;
        diagPrin1 = matrix[0][0] * matrix[1][1] * matrix[2][2];
        diagPrin2 = matrix[0][1] * matrix[1][2] * matrix[2][0];
        diagPrin3 = matrix[0][2] * matrix[1][0] * matrix[2][1];
        diagSec1 = matrix[2][0] * matrix[1][1] * matrix[0][2];
        diagSec2 = matrix[2][1] * matrix[1][2] * matrix[0][0];
        diagSec3 = matrix[2][2] * matrix[1][0] * matrix[0][1];
        det = -(diagSec1 + diagSec2 + diagSec3) + (diagPrin1 + diagPrin2 + diagPrin3);
        return Math.abs(det)/2;
    }
    
    /**
     * (C)
     */
    public double calculaPerimetroTriangulo(){
        return this.p1.distancia(this.p2) + this.p2.distancia(this.p3) + this.p3.distancia(this.p1);
    }
    
    /**
     * (D)
     */
    public double alturaTriangulo(){
        int p1y = this.p1.getY();
        int p2y = this.p2.getY();
        int p3y = this.p3.getY();
        int min = 0;
        int max = 0;
        if ((p1y > p2y) && (p1y > p3y)) max=p1y;
        else if ((p2y > p1y) && (p2y > p3y)) max = p2y;
             else max = p3y;
        if ((p1y < p2y) && (p1y < p3y)) min=p1y;
        else if ((p2y < p1y) && (p2y < p3y)) min = p2y;
             else max = p3y;
        return (max-min);
    }
    /**
     * Método que devolve a representação em String do Triangulo.
     * @return String com pontos 1, 2 e 3
     */
    public String toString(){
        return "Ponto1= " + p1 + "    Ponto2= " + p2 + "    Ponto3= " + p3;
    }
    
    /**
     *  Método que determina se dois triangulos são iguais.
     *  @return booleano que é verdadeiro se os valores dos três pontos forem iguais 
     */
    public boolean equals (Object o){
           if (this == o) return true;
           if (o == null || o.getClass() != this.getClass()) return false;
           Triangulo c = (Triangulo) o;
           return (this.p1.equals(c.getP1()) && this.p2.equals(getP2()) && this.p3.equals(c.getP3()));
    }
    
    /**
     *  Método que faz uma cópia do objecto receptor da mensagem para tal invoca o construtor de cópia
     *  @return objecto clone do objecto que recebe a mensagem. 
     */
    public Triangulo clone (){
        return new Triangulo(this);
    }
}

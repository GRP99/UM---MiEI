/**
   * Classe que implementa um Circulo 
*/
public class Circulo{
        // VARIÁVEIS DE INSTÂNCIA
    private double x;
    private double y;
    private double raio;
            
    /**
     * CONSTRUTORES DA CLASSE CIRCULO
     * Declaração dos construtores por omissão (vazio),parametrizado e de cópia. 
     */
   
    /**
     * Construtor por omissão de Circulo
     */
    public Circulo(){
        this.x=0;
        this.y=0;
        this.raio=0;
    }    
    /**
     * Construtor parametrizado de Circulo
     * Aceita como parâmetros os valores para cada coordenada e o seu raio
     */
    public Circulo(double cx, double cy, double raio){
        this.x = cx;
        this.y = cy;
        this.raio=raio;
    }    
    /**
     * Construtor de cópia de Circulo
     * Aceita como parâmetro outro Circulo e utiliza os métodos de acesso aos valores das variáveis de instância. 
     */
    public Circulo (Circulo outro){
        this.x = outro.getX();
        this.y = outro.getY();
        this.raio = outro.getRaio();
    }
    
    
    /**
     * MÉTODOS DE INSTÂNCIA
     */
    
    /**
     * Devolve o valor da variável x
     * @return valor de x
     */
    public double getX(){
        return this.x;
    }
    
    /**
     * Devolve o valor da variável y
     * @return valor de y
     */
    public double getY(){
        return this.y;
    }
    
    /**
     * Devolve o valor da variável raio
     * @return valor do raio
     */
    public double getRaio(){
        return this.raio;
    }
    
    /**
     * Altera o valor da variável x
     * @param cx novo valor do x.
     */
    public void setX(double cx){
        this.x = cx;
    }
    
    /**
     * Altera o valor da variável y
     * @param cy novo valor do y.
     */
    public void setY(double cy){
        this.y = cy;
    }
    
    /**
     * Altera o valor da variável raio
     * @param newRaio novo valor do raio. 
     */
    public void setRaio(double newRaio){
        this.raio = newRaio;
    }
    
    /**
     * Altera o posicionamento do circulo
     *  @param cx novo valor de x
     *  @param cy novo valor de y. 
     */
    public void alteraCentro(double cx, double cy){
        this.x=cx;
        this.y=cy;
    }
    
    /**
     * Calcula a área do circulo
     * @return double com o valor da área
     */
    public double calculaArea(){
        return Math.PI * Math.pow(raio,2);
    }
    
    /**
     * Calcula o perímetro do circulo
     * @return double com o valor do perímetro
     */
    public double calculaPerimetro(){
        return Math.PI * 2 * raio;
    }
    
    /**
     * Método que devolve a representação em String do Circulo.
     * @return String com as variáveis x, y e raio
     */
    public String toString(){
        return "X= " + x + "    Y= " + y + "    Raio= " + raio;
    }
    
    /**
     *  Método que determina se dois circulos são iguais.
     *  @return booleano que é verdadeiro se os valores das três variáveis forem iguais 
     */
    public boolean equals (Object o){
           if (this == o) return true;
           if (o == null || o.getClass() != this.getClass()) return false;
           Circulo c = (Circulo) o;
           return this.x==c.getX() && this.y==getY() && this.raio==c.getRaio();
    }
    
    /**
     *  Método que faz uma cópia do objecto receptor da mensagem para tal invoca o construtor de cópia
     *  @return objecto clone do objecto que recebe a mensagem. 
     */
    public Circulo clone (){
        return new Circulo(this);
    }
}
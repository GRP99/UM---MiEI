import java.io.Serializable;
/**
 * Classe que implementa as Coordenadas num plano2D.
 * As coordenadas são doubles.
 */
public class Coordenadas implements Serializable{
    private double x;
    private double y;

    /**
     * CONSTRUTORES DA CLASSE Coordenadas
     * Declaração dos construtores por omissão (vazio),parametrizado e de cópia. 
     */
    /**
     * Construtor por omissão de Coordenadas
     */
    public Coordenadas(){
        this.x = 0;
        this.y = 0;
    }

    /**
     * Construtor parametrizado de Coordenadas
     * Aceita como parâmetros os valores para cada coordenada
     */
    public Coordenadas(double cx, double cy){
        this.x = cx;
        this.y = cy;
    }

    /**
     * Construtor de cópia de Coordenadas
     * Aceita como parâmetro outro Coordenadas e utiliza os métodos de acesso aos valores das variáveis de instância. 
     */
    public Coordenadas(Coordenadas c){
        this.x=c.getX();
        this.x=c.getY();
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
     * Altera o valor da variável x
     * @param cx novo valor do x.
     */
    public void setX(double cx){
        this.x=cx;
    }

    /**
     * Altera o valor da variável y
     * @param cy novo valor do y.
     */
    public void setY(double cy){
        this.y=cy;
    }

    /**
     * Move um ponto para uma dada coordenada
     * @param cx novo valor do x.
     * @param cy novo valor do y.
     */
    public void movePonto(double cx,double cy){
        this.x=cx;
        this.y=cy;
    }

    /**
     * Calcula a distancia entre duas coordenadadas
     * @param local coordenada fornecida
     * @return a distancia
     */
    public double distancia(Coordenadas local) {
        return Math.sqrt(Math.pow(this.x - local.getX(), 2) + Math.pow(this.y - local.getY(), 2));
    }

    /**
     *  Método que determina se duas coordenadas são iguais.
     *  @return booleano que é verdadeiro se os valores das duas variáveis forem iguais 
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass()))return false;
        Coordenadas c = (Coordenadas) o;
        return (this.x == c.getX() && this.y == c.getY());
    }

    /**
     * Método que devolve a representação em String de Coordenadas.
     * @return String com as variáveis x e y
     */
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    /**
     *  Método que faz uma cópia do objecto receptor da mensagem para tal invoca o construtor de cópia
     *  @return objecto clone do objecto que recebe a mensagem. 
     */
    public Coordenadas clone(){
        return new Coordenadas (this);
    }
}
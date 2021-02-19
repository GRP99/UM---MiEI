import java.io.*;

public abstract class Hotel implements Comparable<Hotel>, Serializable {
    private String codigo;
    private String nome;
    private String localidade;
    private double precoBaseQuarto;
    private int numeroQuartos;
    private int estrelas;

    public Hotel() {
        this.codigo = "n/a";
        this.nome = "n/a";
        this.localidade = "n/a";
        this.precoBaseQuarto = 0;
        this.numeroQuartos = 0;
        this.estrelas = 1;
    }

    public Hotel(Hotel c) {
        this.codigo = c.getCodigo();
        this.nome = c.getNome();
        this.localidade = c.getLocalidade();
        this.precoBaseQuarto = c.getPrecoBaseQuarto();
        this.numeroQuartos = c.getNumeroQuartos();
        this.estrelas = c.getEstrelas();
    }

    public Hotel(String codigo, String nome, String localidade, double precoBaseQuarto, int numQuartos, int estrelas) {
        this.codigo = codigo;
        this.nome = nome;
        this.localidade = localidade;
        this.precoBaseQuarto = precoBaseQuarto;
        this.numeroQuartos = numQuartos;
        this.estrelas = estrelas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumeroQuartos(int nquartos){
        this.numeroQuartos = nquartos;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public double getPrecoBaseQuarto() {
        return precoBaseQuarto;
    }

    public void setPrecoBaseQuarto(double precoBaseQuarto) {
        this.precoBaseQuarto = precoBaseQuarto;
    }

    public int getNumeroQuartos() { 
        return this.numeroQuartos; 
    }

    public int getEstrelas() { 
        return this.estrelas; 
    }

    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Hotel o = (Hotel) obj;
        return o.getCodigo().equals(this.codigo) && o.getNome().equals(this.nome) && 
        o.getLocalidade().equals(this.localidade) && o.getPrecoBaseQuarto() == this.precoBaseQuarto 
        && o.getEstrelas() == this.estrelas;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.codigo).append(",");
        sb.append("Hotel ").append(nome).append(",");
        sb.append(this.localidade).append(",");
        sb.append(this.precoBaseQuarto).append(",");
        sb.append(this.numeroQuartos).append(",");
        sb.append(this.estrelas).append(",");
        return sb.toString();
    }
    
    public abstract double precoNoite();
    
    public abstract Hotel clone();

    public int hashCode() {
        return codigo.hashCode();
    }

    public int compareTo(Hotel h) {
        return h.getNome().compareTo(this.nome);
    }
}
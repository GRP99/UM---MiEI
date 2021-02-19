import java.util.Arrays;
import java.util.Objects;
import java.io.Serializable;

public class Produto implements Serializable{
    private String codigo;

    public Produto(){
        this.codigo = "";
    }

    public Produto(String c){
        this.codigo = c;
    }

    public Produto(Produto p){
        this.codigo = p.getCodigo();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo Produto= ").append(codigo);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (this.getClass() != obj.getClass()) {return false;}
        Produto prod = (Produto) obj;
        return this.codigo.equals(prod.getCodigo());
    }

    public Produto clone() throws CloneNotSupportedException {
        return new Produto(this);
    }

    public int compareTo(Produto p) {
        return this.codigo.compareTo(p.getCodigo());
    }

    public int hashCode() {
        return Arrays.hashCode( new Object[] {codigo} );
    }
}

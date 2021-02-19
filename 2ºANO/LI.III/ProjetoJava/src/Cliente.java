import java.util.Arrays;
import java.util.Objects;
import java.io.Serializable;

public class Cliente implements Serializable{
    private String codigo;

    public Cliente() {
        codigo = "";
    }

    public Cliente(String c){
        this.codigo = c;
    }

    public Cliente(Cliente c){
        this.codigo = c.getCodigo();
    }

    public String getCodigo(){
        return this.codigo;
    }

    public void setCodigo(String c){
        this.codigo = c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo Cliente= ").append(codigo);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (this.getClass() != obj.getClass()) {return false;}
        Cliente cli = (Cliente) obj;
        return this.codigo.equals(cli.getCodigo());
    }

    public Cliente clone() throws CloneNotSupportedException {
        return new Cliente(this);
    }

    public int compareTo(Cliente c) {
        return this.codigo.compareTo(c.getCodigo());
    }

    public int hashCode() {
        return Arrays.hashCode( new Object[] {codigo} );
    }
}

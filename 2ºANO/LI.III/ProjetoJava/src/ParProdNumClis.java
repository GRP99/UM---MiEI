import java.util.Arrays;
import java.io.Serializable;

public class ParProdNumClis implements Serializable{
    private String codigo;
    private int total=0;

    public ParProdNumClis() {
        this.codigo = "";
        this.total = 0;
    }

    public ParProdNumClis(ParProdNumClis pmc) {
        this.codigo = pmc.getCodigo();
        this.total = pmc.getTotal();
    }

    public String getCodigo() {
        return codigo;
    }

    public int getTotal() {
        return total;
    }

    public void setCodigo (String c) {
        this.codigo = c;
    }

    public void setTotal (int t) {
        this.total = t;
    }

    public ParProdNumClis clone() throws CloneNotSupportedException  {
        return new ParProdNumClis(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((!(this.getClass().getSimpleName().equals(o.getClass().getSimpleName()))) || o == null) return false;
        else {
            ParProdNumClis pmc = (ParProdNumClis) o;
            return (this.total == pmc.getTotal() && codigo.equals(pmc.getCodigo())) ;
        }
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[] {total,codigo});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();     
        sb.append("Código : ").append(codigo).append("\n");
        sb.append("Número Total: ").append(total).append("\n");
        return sb.toString();
    }
}

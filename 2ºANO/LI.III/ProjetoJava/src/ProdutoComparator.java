import java.io.Serializable;
import java.util.Comparator;

public class ProdutoComparator implements Comparator<Produto>, Serializable{
    public int compare(Produto p1, Produto p2) {
        return p1.getCodigo().compareTo(p2.getCodigo());
    }
}
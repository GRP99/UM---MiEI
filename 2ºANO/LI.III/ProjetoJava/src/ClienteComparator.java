import java.util.Comparator;
import java.io.Serializable;

public class ClienteComparator implements Comparator<Cliente>, Serializable{
    public int compare(Cliente c1, Cliente c2) {
        return c1.getCodigo().compareTo(c2.getCodigo());
    }
}

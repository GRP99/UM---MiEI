import java.util.Comparator;
import java.io.Serializable;

public class CompParesProdClientes implements Comparator<ParProdNumClis>,Serializable{
    public int compare(ParProdNumClis o1, ParProdNumClis o2) {
        int tot1 = o1.getTotal();
        int tot2 = o2.getTotal();
        if(tot1==0) return 0;
        if(tot1<tot2) return 1;
        if(tot1>tot2) return -1;
        if(tot2==tot1) return o1.getCodigo().compareTo(o2.getCodigo());
        return 0;
    }
}

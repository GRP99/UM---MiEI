import java.util.Comparator;
import java.io.Serializable;

public class VeiculosComparator implements Comparator<Veiculos>{
    public int compare(Veiculos a1 ,Veiculos a2){
        if(a1.equals(a2)) return 0;
        if(a1.getMatricula().compareTo(a2.getMatricula())!= 0) return a1.getMatricula().compareTo(a2.getMatricula());
        return 1;
    }
}

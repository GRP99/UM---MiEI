import java.util.Comparator;
import java.io.Serializable;

public class AluguerComparator implements Comparator<Aluguer>,Serializable{
    public int compare(Aluguer a1 ,Aluguer a2){
        if(a1.equals(a2)) return 0;
        if(a1.getData().compareTo(a2.getData())!= 0) return a1.getData().compareTo(a2.getData());
        return 1;
    }
}

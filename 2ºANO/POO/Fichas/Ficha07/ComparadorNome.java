import java.util.Comparator;

public class ComparadorNome implements Comparator<Hotel>{
    public int compare(Hotel h1, Hotel h2) {
        return h1.getNome().compareTo(h2.getNome());
    }
}
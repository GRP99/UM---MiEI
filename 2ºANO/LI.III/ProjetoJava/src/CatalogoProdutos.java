import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.Objects;
import java.io.Serializable;

public class CatalogoProdutos implements Serializable{
    private Map<Character,TreeSet<Produto>> cp; /*Letra, Todos os produtos começados por essa letra sem repeticoes*/

    public CatalogoProdutos(){
        this.cp = new HashMap<>();
        for (Character c = 'A'; c <= 'Z'; c++) {
            this.cp.put(c,new TreeSet<>(new ProdutoComparator()));
        }
    }

    public CatalogoProdutos(CatalogoProdutos ctcp) throws CloneNotSupportedException {
        this.cp = ctcp.getCatalogoP();
    }

    public Map<Character,TreeSet<Produto>> getCatalogoP() throws CloneNotSupportedException {
        Map<Character, TreeSet<Produto>> hm = new HashMap<>();
        Character counter = 'A';
        TreeSet<Produto> aux;
        for (TreeSet<Produto> c : this.cp.values()) {
            aux = new TreeSet<>();
            for (Produto pro : c) {
                aux.add(pro.clone());
            }
            hm.put(counter, aux);
            counter++;
        }
        return hm;
    }

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (this.getClass() != obj.getClass()) {return false;}
        final CatalogoProdutos other = (CatalogoProdutos) obj;
        if (!Objects.equals(this.cp, other.cp)) {return false;}
        return true;
    }

    public CatalogoProdutos clone() throws CloneNotSupportedException {
        return new CatalogoProdutos(this);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[] {cp});
    }

    public void adicionaProduto(Produto pro) throws CloneNotSupportedException {
        String cod = pro.getCodigo();
        Character c = Character.toUpperCase(cod.charAt(0));
        this.cp.get(c).add(pro.clone());
    }

    public boolean existeProduto(Produto pro) {
        String cod = pro.getCodigo();
        Character c = Character.toUpperCase(cod.charAt(0));
        return this.cp.get(c).contains(pro);
    }

    public int total(){
        int r=0;
        for (Character c = 'A'; c <= 'Z'; c++) {
            r += cp.get(c).size();
        }
        return r;
    }
}

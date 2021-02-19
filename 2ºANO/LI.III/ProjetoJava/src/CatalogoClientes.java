import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.Objects;
import java.io.Serializable;

public class CatalogoClientes implements Serializable{
    private Map<Character,TreeSet<Cliente>> cc; /*Letra, Todos os clientes começados por essa letra sem repeticoes*/

    public CatalogoClientes(){
        this.cc = new HashMap<>();
        for (Character c = 'A'; c <= 'Z'; c++) {
            this.cc.put(c,new TreeSet<>(new ClienteComparator()));
        }
    }

    public CatalogoClientes(CatalogoClientes ctcl) throws CloneNotSupportedException {
        this.cc = ctcl.getCatalogoC();
    }

    public Map<Character,TreeSet<Cliente>> getCatalogoC() throws CloneNotSupportedException {
        Map<Character, TreeSet<Cliente>> hm = new HashMap<>();
        Character counter = 'A';
        TreeSet<Cliente> aux;
        for (TreeSet<Cliente> c : this.cc.values()) {
            aux = new TreeSet<>();
            for (Cliente cli : c) {
                aux.add(cli.clone());
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
        final CatalogoClientes other = (CatalogoClientes) obj;
        if (!Objects.equals(this.cc, other.cc)) {return false;}
        return true;
    }

    public CatalogoClientes clone() throws CloneNotSupportedException {
        return new CatalogoClientes(this);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[] {cc});
    }

    public void adicionaCliente(Cliente cli) throws CloneNotSupportedException {
        String cod = cli.getCodigo();
        Character c = Character.toUpperCase(cod.charAt(0));
        this.cc.get(c).add(cli.clone());
    }

    public boolean existeCliente(Cliente cli) {
        String cod = cli.getCodigo();
        Character c = Character.toUpperCase(cod.charAt(0));
        return this.cc.get(c).contains(cli);
    }

    public int total(){
        int r=0;
        for (Character c = 'A'; c <= 'Z'; c++) {
            r += cc.get(c).size();
        }
        return r;
    }
}
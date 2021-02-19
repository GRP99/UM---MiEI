import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class GestaoEncomendas {
    private Map<Integer,Encomenda> encomendas;

    public GestaoEncomendas() {    
        this.encomendas = new HashMap<>();
    }    

    public GestaoEncomendas(Map<Integer,Encomenda> encs) {
        this.encomendas = encs.values().stream().collect(Collectors.toMap((e) -> e.getNEnc(),(e) -> e.clone()));
    }

    public GestaoEncomendas(GestaoEncomendas ge) {
        this.encomendas = ge.getEncomendas();    
    }

    public Map<Integer,Encomenda> getEncomendas() {
        return this.encomendas.values().stream().collect(Collectors.toMap((e) -> e.getNEnc(),(e) -> e.clone())); 
    }

    public Set<Integer> todosCodigosEnc() {
        return this.encomendas.keySet();    
    }

    public void addEncomenda(Encomenda enc) {
        this.encomendas.put(enc.getNEnc(), enc.clone());    
    }

    public Encomenda getEncomenda(Integer codEnc) {
        return (this.encomendas.get(codEnc)).clone();
    }

    public void removeEncomenda(Integer codEnc) {
        this.encomendas.remove(codEnc);    
    }

    public Integer encomendaComMaisProdutos() {
        TreeSet<Encomenda> aux = new TreeSet<>((e1,e2) -> (e1.numProdutos()) - (e2.numProdutos()));
        for (Encomenda e: this.encomendas.values()){
            aux.add(e);
        }
        return (aux.last()).getNEnc();
    }

    public Set<Integer> encomendasComProduto(String codProd) {
        return this.encomendas.values().stream().filter(e -> e.existeNaEncomenda(codProd)).map(Encomenda::getNEnc).collect(Collectors.toCollection(TreeSet::new));       
    }

    public Set<Integer> encomendasAposData(LocalDate d) {
        return this.encomendas.values().stream().filter(e -> e.getData().isAfter(d)).map(Encomenda::getNEnc).collect(Collectors.toSet());  
    }

    public Set<Encomenda> encomendasValorDecrescente() { 
        TreeSet<Encomenda> aux = new TreeSet<Encomenda>((e1,e2) -> (int)(e2.calculaValorTotal() - e1.calculaValorTotal()));
        for(Encomenda e: this.encomendas.values())
            aux.add(e.clone());
        return aux;        
    }

    public Map<String,List<Integer>> encomendasDeProduto() {
        Map<String,List<Integer>> aux = new HashMap<>();
        for (Encomenda e: this.encomendas.values()) {
            List<String> lprods = e.getListaProdutos();
            for (String codProd: lprods) {
                if (!aux.containsKey(codProd)){
                    aux.put(codProd, new ArrayList<Integer>());
                }
                aux.get(codProd).add(e.getNEnc());
            }   
        }
        return aux;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Encomenda e: this.encomendas.values())
            sb.append(e.toString() + "\n");
        return sb.toString(); 
    }

    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if ((o == null) || (this.getClass() != o.getClass()))
            return false;
        GestaoEncomendas ge = (GestaoEncomendas) o;
        return this.encomendas.equals(ge.getEncomendas());
    }    

    public GestaoEncomendas clone() {
        return new GestaoEncomendas(this); 
    }
}

import java.util.*;
import static java.util.stream.Collectors.*;

public class Parque{
    private String nome;
    private Map <String,Lugar> lugares;

    public Parque(){
        this.nome = " ";
        this.lugares = new TreeMap<String,Lugar>();
    }

    public Parque(Parque p){
        this.nome = p.getNome();
        this.lugares = p.getParque();
    }

    public Parque (Map<String,Lugar> p, String n){
        this.nome = n;
        this.lugares = p.entrySet().stream().collect(toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public String getNome(){
        return this.nome;
    }

    public Map<String,Lugar> getParque(){
        return this.lugares;
    }

    public List<String> getMatriculas(){
        List<String> lmat = new ArrayList<>(this.lugares.size());
        for(String l: lugares.keySet()){
            lmat.add(l);
        }
        return lmat;
    }

    public void regista(Lugar l){
        String mat = l.getMatricula();
        Lugar oc = l.clone();
        this.lugares.put(mat,oc);
    }

    public void remove(Lugar l){
        String mat = l.getMatricula();
        this.lugares.remove(mat,l);
    }

    public void alteraTempo(int temp,String mat){
        int t;
        Lugar n;
        String m;
        for(Lugar l: lugares.values()){
            if(l.getMatricula().equals(mat)){
                n = l.clone();
                t = temp;
                m = mat;
                n.setMinutos(t);
                lugares.replace(mat,l,n);
            }
        }
    }

    public boolean existe(String mat){
        return lugares.containsKey(mat);
    }

    public int qtd (int minutos){
        return this.lugares.values().stream().mapToInt(Lugar::getMinutos).sum();
    }

    public List<String> getMatriculasTempo(int x){
        return this.lugares.values().stream().filter(l->l.getMinutos() > x && l.getPermanente()).map(Lugar::getMatricula).collect(toList());
    }

    public Map<String,Lugar> copiaLugares(){
        return this.lugares.values().stream().collect(toMap((l)->l.getMatricula(),(l)->l.clone()));
    }

    public Lugar devolve(String mat){
        Lugar d = null;
        for(Lugar l: lugares.values()){
            if(l.getMatricula().equals(mat)){
                d = l.clone();
            }
        }
        return d;
    }

    public String toString (){
        StringBuffer sb = new StringBuffer();
        sb.append("Parque " + this.nome);
        for(Lugar l: this.lugares.values()){
            sb.append(l.toString());
        }
        return sb.toString();
    }

    public boolean equals (Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Parque p = (Parque) o;
        return ((this.getNome().equals(p.getNome())) && (this.lugares.equals(p.getParque())));
    }

    public Parque clone(){
        return new Parque(this);
    }
}

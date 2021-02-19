import java.io.*;
import java.util.TreeMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RedeClientes implements Serializable{
    private Map<String,Cliente> rede;

    public RedeClientes(){
        rede = new TreeMap<String,Cliente>();
    }
    
    public RedeClientes(Map<String,Cliente> m){
        this.rede = new TreeMap<String,Cliente>();
        setRede(m);
    }
    
    public RedeClientes(RedeClientes rc){
        this.rede = rc.getRede();
    }

    public Map<String,Cliente> getRede(){
        return this.rede.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void setRede(Map<String,Cliente> rc){
        this.rede = rc.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void addCliente(Cliente c) throws AtoresException{
        if(rede.containsKey(c.getEmail())) throw new AtoresException(c.getEmail());
        rede.put(c.getEmail(),c.clone());
    }

    public void removeCliente (Cliente c) throws AtoresException{
        if(!rede.containsKey(c.getEmail())) throw new AtoresException(c.getEmail());
        rede.remove(c.getEmail());
    }

    public int login(String email,String password) throws AtoresException{
        if(!rede.containsKey(email)) throw new AtoresException(email);
        if(rede.get(email).getPass().equals(password)) return 1;
        else return 0;
    }

    public Cliente getCliente(String c){
        return rede.get(c).clone();
    }

    public void trocaCliente(Cliente c){
        rede.put(c.getEmail(),c.clone());
    }
    
    public boolean contains(String c){
        return rede.containsKey(c);
    }
    
    public RedeClientes clone(){
        return new RedeClientes(this);
    }
    
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        RedeClientes rc = (RedeClientes) obj;
        return rc.getRede().equals(rede);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Cliente c: this.rede.values()){
            sb.append(c.toString());
        }
        return sb.toString();
    }

    public void guardaEstado(String nomeFicheiro) throws FileNotFoundException,IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this); 
        oos.flush();
        oos.close();
    }

    public static RedeClientes carregaEstado(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        RedeClientes rc = (RedeClientes) ois.readObject();
        ois.close();
        return rc;
    }
}

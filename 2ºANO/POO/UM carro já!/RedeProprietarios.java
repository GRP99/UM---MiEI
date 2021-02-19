import java.io.*;
import java.util.TreeMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RedeProprietarios implements Serializable{
    private Map<String,Proprietario> rede;

    public RedeProprietarios(){
        rede = new TreeMap<String,Proprietario>();
    }

    public RedeProprietarios(Map<String,Proprietario> m){
        this.rede = new TreeMap<String,Proprietario>();
        setRede(m);
    }

    public RedeProprietarios(RedeProprietarios rp){
        this.rede = rp.getRede();
    }

    public Map<String,Proprietario> getRede(){
        return this.rede.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void setRede(Map<String,Proprietario> rp){
        this.rede = rp.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void addProprietario(Proprietario p) throws AtoresException{
        if(rede.containsKey(p.getEmail())) throw new AtoresException(p.getEmail());
        rede.put(p.getEmail(),p.clone());
    }

    public void removeProprietario (Proprietario p) throws AtoresException {
        if(!rede.containsKey(p.getEmail())) throw new AtoresException(p.getEmail());
        rede.remove(p.getEmail());
    }

    public int login(String email,String password) throws AtoresException{
        if(!rede.containsKey(email)) throw new AtoresException(email);
        if(rede.get(email).getPass().equals(password)) return 1;
        else return 0;
    }

    public Proprietario getProprietario(String p){
        return rede.get(p).clone();
    }

    public void trocaProprietario(Proprietario p){
        rede.put(p.getEmail(),p.clone());
    }

    public boolean contains(String c){
        return rede.containsKey(c);
    }

    public RedeProprietarios clone(){
        return new RedeProprietarios(this);
    }

    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        RedeProprietarios rp = (RedeProprietarios) obj;
        return rp.getRede().equals(rede);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Proprietario p: this.rede.values()){
            sb.append(p.toString());
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

    public static RedeProprietarios carregaEstado(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        RedeProprietarios rp = (RedeProprietarios) ois.readObject();
        ois.close();
        return rp;
    }
}

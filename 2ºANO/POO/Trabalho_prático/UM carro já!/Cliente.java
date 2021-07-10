import java.io.Serializable;
import java.time.LocalDate;
import java.util.TreeSet;
import java.util.Set;
import java.util.Iterator;
import java.text.SimpleDateFormat;

public class Cliente implements Serializable{
    private String email;
    private String nome;
    private String password;
    private String morada;
    private LocalDate dataNascimento;
    private Coordenadas local;
    private Coordenadas destino;
    private TreeSet<Aluguer> historico;

    public Cliente(){
        this.email = " ";
        this.nome = " ";
        this.password = " ";
        this.morada = " ";
        this.dataNascimento = LocalDate.now();
        this.local = new Coordenadas(0,0);
        this.destino = new Coordenadas(0,0);
        this.historico = new TreeSet<Aluguer>(new AluguerComparator());
    }

    public Cliente(String e, String n, String p, String m, LocalDate d, Coordenadas l){
        this.email = e;
        this.nome = n;
        this.password = p;
        this.morada = m;
        this.dataNascimento = d;
        this.local = new Coordenadas(l);
        this.destino = new Coordenadas(l);
        this.historico = new TreeSet<Aluguer>(new AluguerComparator());
    }

    public Cliente(Cliente c){
        this.email = c.getEmail();
        this.nome = c.getNome();
        this.password = c.getPass();
        this.morada = c.getMorada();
        this.dataNascimento = c.getData();
        this.local = c.getLocal();
        this.destino = c.getDestino();
        this.historico = c.getHistorico();
    }

    public String getEmail(){return this.email;}

    public String getNome(){return this.nome;}

    public String getPass(){return this.password;}

    public String getMorada(){return this.morada;}

    public LocalDate getData(){ return this.dataNascimento;}

    public Coordenadas getLocal(){return this.local;}

    public Coordenadas getDestino(){return this.destino;}

    public TreeSet<Aluguer> getHistorico(){
        TreeSet<Aluguer> h = new TreeSet<Aluguer>(new AluguerComparator());
        Iterator<Aluguer> it = this.historico.iterator();
        Aluguer a;
        while(it.hasNext()){
            a = it.next();
            h.add(a.clone());
        }
        return h;
    }

    public void setEmail(String email){this.email = email;}

    public void setNome(String nome){this.nome = nome;}

    public void setPass(String pass){this.password = pass;}

    public void setMorada(String morada){this.morada = morada;}

    public void setData(LocalDate data){this.dataNascimento = data;}

    public void setLocal(Coordenadas l){this.local = l;}

    public void setDestino(Coordenadas dest){this.destino = dest;}

    public void setHistorico(TreeSet<Aluguer> hist){
        TreeSet<Aluguer> h = new TreeSet<Aluguer>(new AluguerComparator());
        Iterator<Aluguer> it = hist.iterator();
        Aluguer a;
        while(it.hasNext()){
            a = it.next();
            h.add(a.clone());
        }
        this.historico = h;
    }
    
    public int hashCode() {
        return email.hashCode();
    }
    
    public boolean equals(Object o){
        if(o==this) return true;
        if((o==null)||(this.getClass()!=o.getClass()))return false;
        Cliente a = (Cliente) o;
        return (this.email.equals(a.getEmail()));
    }

    public String toString(){  
        StringBuilder s = new StringBuilder(); 
        s.append("Email: ");
        s.append(this.email);
        s.append(System.lineSeparator());
        s.append("Nome: ");
        s.append(this.nome);
        s.append(System.lineSeparator());
        s.append("Morada: ");
        s.append(this.morada);
        s.append(System.lineSeparator());
        s.append("Data de nascimento: ");
        s.append(this.dataNascimento);
        s.append(System.lineSeparator());
        s.append("Local: ");
        s.append(local);
        s.append(System.lineSeparator());
        return s.toString();
    }

    public Cliente clone(){return new Cliente(this);}

    public void addAluguer(Aluguer a){
        this.historico.add(a.clone());
    }
}

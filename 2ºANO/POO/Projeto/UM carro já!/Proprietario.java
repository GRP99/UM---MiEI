import java.io.Serializable;
import java.time.LocalDate;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class Proprietario implements Serializable{
    private String email;
    private String nome;
    private String password;
    private String morada;
    private LocalDate dataNascimento;
    private int classificacao;
    private Map<String,Veiculos> listaVeiculos;
    private Map<String,Cliente> listapedidos;
    private TreeSet<Aluguer> historico;
    
    public Proprietario(){
        this.email = " ";
        this.nome = " ";
        this.password = " ";
        this.morada = " ";
        this.dataNascimento = LocalDate.now();
        this.classificacao = 0;
        this.listaVeiculos = new TreeMap<String,Veiculos>();
        this.listapedidos = new HashMap<String,Cliente>();
        this.historico = new TreeSet<Aluguer>(new AluguerComparator());
    }
    
    public Proprietario(String email, String nome, String pass, String morada, LocalDate data, int clas){
        this.email = email;
        this.nome = nome;
        this.password = pass;
        this.morada = morada;
        this.dataNascimento = data;
        this.classificacao = clas;
        this.listaVeiculos = new TreeMap<String,Veiculos>();
        this.listapedidos = new HashMap<String,Cliente>();
        this.historico = new TreeSet<Aluguer>(new AluguerComparator());
    }

    public Proprietario(Proprietario p){
        this.email = p.getEmail();
        this.nome = p.getNome();
        this.password = p.getPass();
        this.morada = p.getMorada();
        this.dataNascimento = p.getData();
        this.classificacao = p.getClas();
        this.listaVeiculos = p.getVeiculos();
        this.listapedidos = p.getPedidos();
        this.historico = p.getHistorico();
    }

    public String getEmail(){return this.email;}

    public String getNome(){return this.nome;}

    public String getPass(){return this.password;}

    public String getMorada(){return this.morada;}

    public LocalDate getData(){ return this.dataNascimento;}

    public int getClas(){return this.classificacao;}

    public Map<String,Veiculos> getVeiculos(){
        return this.listaVeiculos.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public Map<String,Cliente> getPedidos(){
        return this.listapedidos.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public TreeSet<Aluguer> getHistorico(){
        TreeSet<Aluguer> novo = new TreeSet<>(new AluguerComparator());
        for(Aluguer a: this.historico){
            novo.add(a.clone());
        }
        return novo;
    }

    public void setEmail(String email){this.email = email;}

    public void setNome(String nome){this.nome = nome;}

    public void setPass(String pass){this.password = pass;}

    public void setMorada(String morada){this.morada = morada;}

    public void setData(LocalDate data){this.dataNascimento = data;}

    public void setClas(int clas){this.classificacao = clas;}

    public void setVeiculos(Map<String,Veiculos> v){
        this.listaVeiculos = v.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void setPedidos(Map<String,Cliente> p){
        this.listapedidos = p.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void setHistorico(TreeSet<Aluguer> h){
        TreeSet<Aluguer> novo = new TreeSet<>(new AluguerComparator());
        for(Aluguer a: h){
            novo.add(a.clone());
        }
        this.historico =  novo;
    }
    
    public int hashCode() {
        return email.hashCode();
    }

    public boolean equals(Object o){
        if(o==this) return true;
        if((o==null)||(this.getClass()!=o.getClass()))return false;
        Proprietario a = (Proprietario) o;
        return (this.email.equals(a.getEmail()));
    }

    public Proprietario clone(){return new Proprietario(this);}

    public String toString(){  
        StringBuilder s = new StringBuilder(); 
        s.append("Email: ");
        s.append(email);
        s.append(System.lineSeparator());
        s.append("Nome: ");
        s.append(nome);
        s.append(System.lineSeparator());
        s.append("Morada: ");
        s.append(morada);
        s.append(System.lineSeparator());
        s.append("Data de nascimento: ");
        s.append(dataNascimento);
        s.append(System.lineSeparator());
        s.append("Classificacao: ");
        s.append(classificacao);
        s.append(System.lineSeparator());
        return s.toString();
    }

    public void addVeiculo (Veiculos v) throws VeiculoException{
        if(this.listaVeiculos.containsKey(v.getMatricula())) throw new VeiculoException();
        this.listaVeiculos.put(v.getMatricula(),v.clone());
    }

    public void remVeiculo(Veiculos v){
        this.listaVeiculos.remove(v.getMatricula(),v);
    }
    
    public Veiculos getVeiculo(String mat){
        return this.listaVeiculos.get(mat).clone();
    }
    
    public void trocaVeiculo(Veiculos v){
        this.listaVeiculos.put(v.getMatricula(),v.clone());
    }
    
    public boolean existeVeiculo(String mat){
        return this.listaVeiculos.containsKey(mat);
    }
    
    public int quantosVeiculos(){
        return this.listaVeiculos.size();
    }
    
    public void addPedido(Cliente c, Veiculos v){
        this.listapedidos.put(v.getMatricula(),c.clone());
    }

    public void remPedido(Cliente c,Veiculos v){
        this.listapedidos.remove(v.getMatricula(),c);
    }
    
    public Cliente getPedido(String mat){
        return this.listapedidos.get(mat).clone();
    }
    
    public int quantosPedidos(){
        return this.listapedidos.size();
    }

    public void addAluguer(Aluguer a){
        this.historico.add(a.clone());
    }
    
    public void removeAluguer(Aluguer a){
        this.historico.remove(a.clone());
    }

    public void classifica(int c){
        if (this.classificacao == 0) this.classificacao = c;
        else this.classificacao = (int)((this.classificacao + c)/2);
    }
}

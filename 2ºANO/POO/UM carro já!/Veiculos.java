import java.util.Iterator;
import java.io.Serializable;
import java.util.TreeSet;
import java.util.Comparator;

public abstract class Veiculos implements Serializable{
    private String descricao;
    private String matricula;
    private boolean estado;
    private int velocidade;
    private double preco;
    private double facturado;
    private int classificacao;
    private Coordenadas local;
    private TreeSet<Aluguer> historico;

    public Veiculos(){
        this.descricao = "";
        this.matricula = "";
        this.estado = false;
        this.velocidade = 0;
        this.preco = 0.0;      
        this.facturado = 0.0;
        this.classificacao = 0; 
        this.local = new Coordenadas(0,0);
        this.historico = new TreeSet<Aluguer>(new AluguerComparator());
    }

    public Veiculos(String desc, boolean est,String mat,int vel, double pre, int clas, Coordenadas l){
        this.descricao = desc;
        this.matricula = mat;
        this.estado = est;
        this.velocidade = vel;
        this.preco = pre;
        this.facturado = 0.0;
        this.classificacao = clas;
        this.local = new Coordenadas(l);
        this.historico = new TreeSet<Aluguer>(new AluguerComparator());
    }

    public Veiculos(Veiculos v){
        this.descricao = v.getDescricao();
        this.matricula = v.getMatricula();
        this.estado = v.getEstado();
        this.velocidade = v.getVelocidade();
        this.preco = v.getPreco();
        this.facturado = v.getFacturado();
        this.classificacao = v.getClas();
        this.local = v.getLocal();
        this.historico = v.getHistorico();
    }

    public String getDescricao(){return this.descricao;}

    public String getMatricula(){return this.matricula;}

    public boolean getEstado(){return this.estado;}

    public int getVelocidade(){return this.velocidade;}

    public double getPreco(){return this.preco;}

    public double getFacturado(){return this.facturado;}

    public int getClas(){return this.classificacao;}

    public Coordenadas getLocal(){return this.local;}

    public TreeSet<Aluguer> getHistorico(){
        TreeSet<Aluguer> novo = new TreeSet<>(new AluguerComparator());
        for(Aluguer a: this.historico){
            novo.add(a.clone());
        }
        return novo;
    }

    public void setDescricao(String desc){this.descricao = desc;}
    
    public void setMatricula(String mat){this.matricula = mat;}

    public void setEstado(boolean estado){this.estado = estado;}
    
    public void setVelocidade(int vel){this.velocidade = vel;}

    public void setPreco(double pre){this.preco = pre;}
    
    public void setFacturado(double f){this.facturado = f;}

    public void setClas(int clas){this.classificacao = clas;}
    
    public void setLocal(Coordenadas loc){this.local = loc;}

    public void setHistorico(TreeSet<Aluguer> t){
        TreeSet<Aluguer> novo = new TreeSet<>(new AluguerComparator());
        for(Aluguer a: t){
            novo.add(a.clone());
        }
        this.historico = novo;
    }

    public int hashCode() {
        return matricula.hashCode();
    }

    public boolean equals(Object o){
        if(o==this) return true;
        if((o==null)||(this.getClass()!=o.getClass()))return false;
        Veiculos a = (Veiculos) o;
        return (this.matricula.equals(a.getMatricula()));
    }

    public String toString(){  
        StringBuilder s = new StringBuilder();
        s.append(descricao);
        s.append(System.lineSeparator());
        s.append("Estado: ");
        if (estado) s.append("Livre");
        else s.append("Ocupado");
        s.append(System.lineSeparator());
        s.append("Matricula: ");
        s.append(matricula);
        s.append(System.lineSeparator());
        s.append("Velocidade media por km: ");
        s.append(velocidade);
        s.append(System.lineSeparator());
        s.append("Preco base por km: ");
        s.append(preco);
        s.append(System.lineSeparator());
        s.append("Total facturado até ao momento: ");
        s.append(facturado);
        s.append(System.lineSeparator());
        s.append("Classificacao do carro: ");
        s.append(classificacao);
        s.append(System.lineSeparator());
        s.append("Local: ");
        s.append(local);
        s.append(System.lineSeparator());
        return s.toString();
    }

    public void addAluguer(Aluguer a){
        this.historico.add(a.clone());
    }

    public void removeAluguer(Aluguer a){
        this.historico.remove(a.clone());
    }

    public void addFacturacao(double p){
        this.facturado += p;
    }

    public void classifica(int c){
        if (this.classificacao == 0) this.classificacao = c;
        else this.classificacao = (int)((this.classificacao + c)/2); 
    }

    public abstract void abastece(int a);
    
    public abstract int getLimite();
    
    public abstract void setLimite(int l);
    
    public abstract double getConsumo();
    
    public abstract void setConsumo(double c);
   
    public abstract Veiculos clone();
}   

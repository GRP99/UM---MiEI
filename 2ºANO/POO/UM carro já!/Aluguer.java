import java.io.Serializable;
import java.time.LocalDate;
import java.text.SimpleDateFormat;

public class Aluguer implements Serializable{
    private String user;
    private Veiculos veiculo;
    private LocalDate data;
    private Coordenadas ini;
    private Coordenadas fim;
    private double distancia;
    private double preco;
    private int tempo;
    private boolean flag;

    public Aluguer(){
        this.user = " ";
        this.veiculo = null;
        this.data = LocalDate.now();
        this.ini = new Coordenadas(0,0);
        this.fim = new Coordenadas(0,0);
        this.distancia = 0;
        this.preco = 0;
        this.tempo = 0;
        this.flag = false;
    }

    public Aluguer(String cliente,Veiculos veiculo,LocalDate d,Coordenadas i, Coordenadas f, double dist, double pre, int tem){
        this.user = cliente;
        this.veiculo = veiculo;
        this.data = d;
        this.ini = new Coordenadas(i);
        this.fim = new Coordenadas(f);
        this.distancia = dist;
        this.preco = pre;
        this.tempo = tem;
        this.flag = false;
    }

    public Aluguer(Aluguer a){
        this.user = a.getCliente();
        this.veiculo = a.getVeiculo();
        this.data = a.getData();
        this.ini = a.getInic();
        this.fim = a.getFim();
        this.distancia = a.getDist();
        this.preco = a.getPreco();
        this.tempo = a.getTempo();
        this.flag = a.getFlag();
    }

    public boolean getFlag(){return this.flag;}

    public String getCliente(){return this.user;}

    public Veiculos getVeiculo(){return this.veiculo;}

    public LocalDate getData(){return this.data;}

    public Coordenadas getInic(){return this.ini;}

    public Coordenadas getFim(){return this.fim;}

    public double getDist(){return this.distancia;}

    public double getPreco(){return this.preco;}

    public int getTempo(){return this.tempo;}

    public void setFlag(boolean f){this.flag = f;}

    public void setCliente(String cli){this.user = cli;}

    public void setVeiculo(Veiculos vei){this.veiculo = vei;}

    public void setData(LocalDate dat){this.data = dat;}

    public void setInic(Coordenadas i){this.ini = i;}

    public void setFim(Coordenadas f){this.fim = f;}

    public void setDist(double dist){this.distancia = dist;}

    public void setPreco(double prec){this.preco = prec;}

    public void setTempo(int temp){this.tempo = temp;}

    public Aluguer clone(){return new Aluguer(this);}

    public boolean equals(Object o){
        if(o==this) return true;
        if((o==null)||(this.getClass()!=o.getClass()))return false;
        Aluguer a = (Aluguer) o;
        return (this.user.equals(a.getCliente()) && this.veiculo.equals(a.getVeiculo()) && this.data.isEqual(a.getData()) && this.ini.equals(a.getInic()) && this.fim.equals(a.getFim()));
    }

    public String toString(){
        StringBuilder s=new StringBuilder();
        s.append("User: ");
        s.append(this.user);
        s.append(System.lineSeparator());
        s.append("Data em que da viagem foi realizada: ");;
        s.append(this.data);
        s.append(System.lineSeparator());
        s.append("Veiculo utilizado: ");
        s.append(this.veiculo.getMatricula() + " -> " + this.veiculo.getDescricao());
        s.append(System.lineSeparator());
        s.append("Coordenadas iniciais: ");
        s.append(this.ini);
        s.append(System.lineSeparator());
        s.append("Coordenadas finais: ");
        s.append(this.fim);
        s.append(System.lineSeparator());
        s.append("Distancia percorrida: ");
        s.append(this.distancia);
        s.append(System.lineSeparator());
        s.append("Preco pago: ");
        s.append(this.preco);
        s.append(System.lineSeparator());
        s.append("Tempo da viagem: ");
        s.append(this.preco);
        s.append(System.lineSeparator());
        return s.toString();
    }
} 
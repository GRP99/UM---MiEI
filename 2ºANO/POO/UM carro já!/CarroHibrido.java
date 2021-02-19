import java.io.Serializable;

public class CarroHibrido extends Veiculos implements Serializable{
    private int capacidade;
    private double consumo;
   
    public CarroHibrido(){
        super();
        this.capacidade = 0;
        this.consumo = 0.0;
    }

    public CarroHibrido(String desc, boolean est, String mat, int vel, double pre, double con, int clas, int aut, Coordenadas l){
        super(desc,est,mat,vel,pre,clas,l);
        this.capacidade = aut;
        this.consumo = con;
    }

    public CarroHibrido(CarroHibrido ch){
        super(ch);
        this.capacidade = ch.getLimite();
        this.consumo = ch.getConsumo();
    }
    
    public int getLimite(){
        return this.capacidade;
    } 

    public void setLimite(int l){
        this.capacidade = l;
    }

    public double getConsumo(){
        return this.consumo;
    }

    public void setConsumo(double c){
        this.consumo = c;
    }
    
    public boolean equals(Object o){
        if(o==this) return true;
        if((o==null)||(this.getClass()!=o.getClass()))return false;
        CarroHibrido a = (CarroHibrido) o;
        return (this.getMatricula().equals(a.getMatricula()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CarroHibrido,");
        sb.append(System.lineSeparator());
        sb.append(super.toString());
        sb.append("Capacidade atual: ");
        sb.append(capacidade);
        sb.append(System.lineSeparator());
        sb.append("Consumo por km percorrido: ");
        sb.append(consumo);
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    public CarroHibrido clone(){
        return new CarroHibrido(this);
    }
    
    public void abastece(int a){
        if (this.capacidade == 0) this.capacidade = a;
        else this.capacidade = this.capacidade + a;
    }
}

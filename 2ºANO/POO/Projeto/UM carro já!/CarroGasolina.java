import java.io.Serializable;

public class CarroGasolina extends Veiculos implements Serializable{
    private int autonomia;
    private double consumo;    

    public CarroGasolina(){
        super();
        this.autonomia = 0;
        this.consumo = 0.0;
    }

    public CarroGasolina(String desc, boolean est,String mat,int vel, double pre, double con, int clas, int aut, Coordenadas l){
        super(desc,est,mat,vel,pre,clas,l);
        this.autonomia = aut;
        this.consumo = con;
    }

    public CarroGasolina(CarroGasolina cg){
        super(cg);
        this.autonomia = cg.getLimite();
        this.consumo = cg.getConsumo();
    }

    public int getLimite(){
        return this.autonomia;
    } 

    public void setLimite(int l){
        this.autonomia = l;
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
        CarroGasolina a = (CarroGasolina) o;
        return (this.getMatricula().equals(a.getMatricula()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CarroGasolina,");
        sb.append(System.lineSeparator());
        sb.append(super.toString());
        sb.append("Autonomia: ");
        sb.append(autonomia);
        sb.append(System.lineSeparator());
        sb.append("Consumo por km percorrido: ");
        sb.append(consumo);
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    public CarroGasolina clone(){
        return new CarroGasolina(this);
    }

    public void abastece(int a){
        if (this.autonomia == 0) this.autonomia = a;
        else this.autonomia = this.autonomia + a;
    }
}

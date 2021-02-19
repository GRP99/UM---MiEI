import java.io.Serializable;

public class CarroElectrico extends Veiculos implements Serializable{
    private int bateria;
    private double gasto;
    
    public CarroElectrico(){
        super();
        this.bateria = 0;
        this.gasto = 0.0;
    }

    public CarroElectrico(String desc, boolean est, String mat, int vel, double pre, double con, int clas, int aut, Coordenadas l){
        super(desc,est,mat,vel,pre,clas,l);
        this.bateria = aut;
        this.gasto = con;
    }

    public CarroElectrico(CarroElectrico ce){
        super(ce);
        this.bateria = ce.getLimite();
        this.gasto = ce.getConsumo();
    }
    
    public int getLimite(){
        return this.bateria;
    } 

    public void setLimite(int l){
        this.bateria = l;
    }

    public double getConsumo(){
        return this.gasto;
    }

    public void setConsumo(double c){
        this.gasto = c;
    }

    public boolean equals(Object o){
        if(o==this) return true;
        if((o==null)||(this.getClass()!=o.getClass()))return false;
        CarroElectrico a = (CarroElectrico) o;
        return (this.getMatricula().equals(a.getMatricula()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CarroElectrico,");
        sb.append(System.lineSeparator());
        sb.append(super.toString());
        sb.append("Estado atual da bateria: ");
        sb.append(bateria);
        sb.append(System.lineSeparator());
        sb.append("Consumo por km percorrido: ");
        sb.append(gasto);
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    public CarroElectrico clone(){
        return new CarroElectrico(this);
    }
    
    public void abastece(int a){
        if (this.bateria == 0) this.bateria = a;
        else this.bateria = this.bateria + a;
    }
}

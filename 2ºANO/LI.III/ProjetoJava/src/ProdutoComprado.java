import java.io.Serializable;
import java.util.*;

public class ProdutoComprado implements Serializable{
    private int[] comprasN; /*Total de unidades vendidas num determinado mes em modo normal*/
    private int[] comprasP; /*Total de unidades vendidas num determinado mes em modo promo*/
    private int unidadesVendidas; /*Total de unidades vendidas*/
    private double totalPago; /*Total de facturado global*/

    public ProdutoComprado(){
        this.comprasN = new int [12];
        this.comprasP = new int [12];
        this.unidadesVendidas = 0;
        this.totalPago = 0.0;
    }

    public ProdutoComprado(int [] cn, int []cp, int uv, double tp){
        this.comprasN = cn.clone();
        this.comprasP = cp.clone();
        this.unidadesVendidas = uv;
        this.totalPago = tp;
    }

    public ProdutoComprado(ProdutoComprado pc){
        this.comprasN = pc.getComprasN();
        this.comprasP = pc.getComprasP();
        this.unidadesVendidas = pc.getUnidadesVendidas();
        this.totalPago = pc.getTotalPago();
    }

    public int[] getComprasN(){return comprasN.clone();}

    public int[] getComprasP(){return comprasP.clone();}

    public int getUnidadesVendidas(){return unidadesVendidas;}

    public double getTotalPago(){return totalPago;}

    public void setUnidadesVendidas(int uv){unidadesVendidas = uv;}

    public void setTotalPago(double tp){totalPago = tp;}

    public ProdutoComprado clone() throws CloneNotSupportedException{
        return new ProdutoComprado(this);
    }

    public boolean equals (Object obj){
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final ProdutoComprado other = (ProdutoComprado) obj;
        if (!Arrays.equals(this.comprasN, other.comprasN)) {return false;}
        if (!Arrays.equals(this.comprasP, other.comprasP)) {return false;}
        if (this.unidadesVendidas != other.unidadesVendidas){return false;}
        if (Double.doubleToLongBits(this.totalPago) != Double.doubleToLongBits(other.totalPago)){return false;}
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode( new Object[] {comprasN,comprasP,unidadesVendidas,totalPago});
    }

    public void adicionaProdutoComprado(double preco, int quant, int m, String t){
        unidadesVendidas = unidadesVendidas + quant;
        totalPago = totalPago + (preco * quant);
        if (t.equals("N")) comprasN[m-1] = comprasN[m-1] + 1;
        else comprasP[m-1] = comprasP[m-1] + 1;
    }
    
    public int totalComprasMes(int mes){
        return (comprasN[mes-1] + comprasP[mes-1]);
    }
}

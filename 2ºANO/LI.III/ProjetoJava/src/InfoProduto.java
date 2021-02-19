import java.util.Map;
import java.util.TreeMap;
import java.util.Objects;
import java.util.Arrays;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoProduto implements Serializable{
    private int unidadesVendidas; /*Unidades vendidas globais*/
    private int[] comprasM; /*Unidades vendidas separadas por mes*/
    private double[] totalFM; /*Total facturado separado por mes*/

    public InfoProduto(){
        this.unidadesVendidas = 0;
        this.comprasM = new int[12];
        this.totalFM = new double[12];
    }

    public InfoProduto(int uv, int[]cm , double[] tfm){
        this.unidadesVendidas = uv;
        this.comprasM = cm.clone();
        this.totalFM = tfm.clone();
    }

    public InfoProduto(InfoProduto ip) throws CloneNotSupportedException{
        this.unidadesVendidas = ip.getUnidadesVendidas();
        this.comprasM = ip.getComprasM();
        this.totalFM = ip.getTotalFM();
    }

    public int getUnidadesVendidas(){return this.unidadesVendidas;}

    public int[] getComprasM(){return this.comprasM.clone();}

    public double[] getTotalFM(){return this.totalFM.clone();}

    public void setUnidadesVendidas(int uv){this.unidadesVendidas = uv;}

    public InfoProduto clone() throws CloneNotSupportedException{
        return new InfoProduto(this);
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final InfoProduto other = (InfoProduto) obj;
        if (this.unidadesVendidas != other.unidadesVendidas) return false;
        if (!Arrays.equals(this.comprasM, other.comprasM)) return false;
        if (!Arrays.equals(this.totalFM, other.totalFM)) return false;
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode( new Object[] {unidadesVendidas,comprasM,totalFM});
    }

    public void adicionaIP(double preco, int quantidade, int mes) {
        unidadesVendidas = unidadesVendidas + 1;
        comprasM[mes-1] = comprasM[mes-1] + quantidade;
        totalFM[mes-1] = totalFM[mes-1] + (preco * quantidade);
    }
    
    public int getComprasMes(int m){
        return comprasM[m-1];
    }
    
    public double getFacturadoMes(int m) {
        return totalFM[m-1];
    }
}

import java.util.Map;
import java.util.TreeMap;
import java.util.Objects;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;

public class InfoCliente implements Serializable{
    private Map<Produto,ProdutoComprado> compras; /*Codigo do produto que o cliente comprou, informacao sobre as vendas produto */
    private int[] totalgasto; /*Total gasto mensal por um cliente*/
    private int[] comprasMesN; /*Total de compras em modo normal por mes*/
    private int[] comprasMesP; /*Total de compras em modo promo por mes*/

    public InfoCliente(){
        this.compras = new TreeMap<>(new ProdutoComparator());
        this.totalgasto = new int[12];
        this.comprasMesN = new int[12];
        this.comprasMesP = new int[12];
    }

    public InfoCliente(TreeMap<Produto,ProdutoComprado> c, int[] tg, int[]cn , int[] cp){
        this.compras = c;
        this.totalgasto = tg.clone();
        this.comprasMesN = cn.clone();
        this.comprasMesP = cp.clone();
    }

    public InfoCliente(InfoCliente ic) throws CloneNotSupportedException{
        this.compras = ic.getCompras();
        this.totalgasto = ic.getTotalGasto();
        this.comprasMesN = ic.getCMN();
        this.comprasMesP = ic.getCMP();
    }

    public TreeMap<Produto,ProdutoComprado> getCompras() throws CloneNotSupportedException {
        TreeMap<Produto,ProdutoComprado> res = new TreeMap<>();
        compras.forEach((k,v) ->  {
                try {
                    res.put(k.clone(), v.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(InfoCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        return res;
    }

    public int[] getTotalGasto(){return totalgasto.clone();}

    public int[] getCMN(){return comprasMesN;}

    public int[] getCMP(){return  comprasMesP;}

    public void setCompras(TreeMap<Produto,ProdutoComprado> c) {
        this.compras = new TreeMap<>(new ProdutoComparator());
        c.forEach((k,v) ->  {
                try {
                    this.compras.put(k.clone(), v.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
    }

    public InfoCliente clone() throws CloneNotSupportedException{
        return new InfoCliente(this);
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final InfoCliente other = (InfoCliente) obj;
        if (!Objects.equals(this.compras, other.compras)) return false;
        if (!Arrays.equals(this.totalgasto, other.totalgasto)) return false;
        if (!Arrays.equals(this.comprasMesN, other.comprasMesN)) return false;
        if (!Arrays.equals(this.comprasMesP, other.comprasMesP)) return false;
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode( new Object[] {compras,totalgasto,comprasMesN,comprasMesP});
    }

    public void adicionaIC(Venda v) throws CloneNotSupportedException{
        Produto p = v.getProduto();
        String tipo = v.getTipo();
        int mes = v.getMes();
        double preco = v.getPreco();
        int quantidade = v.getQuantidade();
        if (tipo.equals("N")) comprasMesN[mes-1] = comprasMesN[mes-1] + 1;
        else comprasMesP[mes-1] = comprasMesP[mes-1] + 1;
        totalgasto[mes-1] += preco*quantidade;
        if (!(compras.containsKey(p))){
            this.compras.put(v.getProduto().clone(),new ProdutoComprado());
        }
        ProdutoComprado pc = new ProdutoComprado(this.compras.get(p));
        pc.adicionaProdutoComprado(preco,quantidade,mes,tipo);
        this.compras.put(v.getProduto(),pc.clone());
    }

    public int comprasMN(int m){
        return comprasMesN[m-1];
    }

    public int comprasMP(int m){
        return comprasMesP[m-1];
    }

    public int totalprodutosM(int mes) {
        int r = 0;
        for(ProdutoComprado pc : compras.values()){
            if(pc.totalComprasMes(mes)!=0) r++;
        }
        return r;
    }

    public int totalGM(int m){
        return totalgasto[m-1];
    }

    public int calculaDistintos(int mes, Produto p) {
        int r=0;
        if(compras.containsKey(p)){
            ProdutoComprado pc = compras.get(p);
            if(pc.totalComprasMes(mes)!=0) r++;
        }
        return r;
    }

    public Set<ParProdNumClis> getCodProd(){
        Set<ParProdNumClis> cod = new TreeSet<>(new CompParesProdClientes());
        compras.forEach((k,v) ->  {
                ParProdNumClis pmc = new ParProdNumClis();
                pmc.setTotal(v.getUnidadesVendidas());
                pmc.setCodigo(k.getCodigo());
                try {      
                    cod.add(pmc.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(InfoCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        return cod;
    }

    public boolean existeProduto(Produto p){
        return this.compras.containsKey(p);
    }

    public int quantosPDistintos(){
        int tam = (int) this.compras.size();
        return tam;
    }

    public int gastoProduto(Produto p){
        int gasto = (int) this.compras.get(p).getTotalPago();
        return gasto;
    }
}

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Objects;

public class Facturacao implements Serializable{
    private Map<Produto,Integer> fact; /*Codigo, unidades vendidas*/
    private int[][] tVendas; /*Total de unidades vendidas mensais, por tipo N ou P*/
    private double[][] tFF1; /*Total facturado mensal na Filial 1, por tipo N ou P*/
    private double[][] tFF2; /*Total facturado mensal na Filial 2, por tipo N ou P*/
    private double[][] tFF3; /*Total facturado mensal na Filial 3, por tipo N ou P*/
    private double tFacturado; /*Total Facturado global*/

    public Facturacao (){
        this.fact = new TreeMap<>(new ProdutoComparator());
        this.tVendas = new int[12][2];
        this.tFF1 = new double [12][2];
        this.tFF2 = new double [12][2];
        this.tFF3 = new double [12][2];
        this.tFacturado = 0.0;
    }

    public Facturacao(Map<Produto,Integer> f, int[][] tv, double[][] tf1, double[][] tf2, double[][] tf3, double tf) throws CloneNotSupportedException{
        this.tVendas = tv.clone();
        this.tFF1 = tf1.clone();
        this.tFF2 = tf2.clone();
        this.tFF3 = tf3.clone();
        this.tFacturado = tf;
        this.fact = new TreeMap<>();
        f.forEach((Produto k, Integer v) ->  {
                try {
                    this.fact.put(k.clone(), v);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Facturacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
    }

    public Facturacao(Facturacao f){
        this.fact = f.getFacturacao();
        this.tVendas = f.getTVendas();
        this.tFF1 = f.getTFF1();
        this.tFF2 = f.getTFF2();
        this.tFF3 = f.getTFF3();
        this.tFacturado = f.getTFacturado();
    }

    public Map<Produto, Integer> getFacturacao() {
        TreeMap<Produto,Integer> aux = new TreeMap<>();
        fact.forEach((k,v) ->  {
                try {
                    aux.put(k.clone(), v);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Facturacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        return aux;
    }

    public int[][] getTVendas() {return tVendas.clone();}

    private double[][] getTFF1() {return  this.tFF1.clone();}

    private double[][] getTFF2() {return  this.tFF2.clone();}

    private double[][] getTFF3() {return  this.tFF3.clone();}

    public double getTFacturado(){return tFacturado;}

    public void setFacturacao(Map<Produto, Integer> facturacao){
        this.fact = new TreeMap<>(new ProdutoComparator());
        facturacao.forEach((k,v) ->  {
                try {
                    this.fact.put(k.clone(), v);
                }catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Facturacao.class.getName()).log(Level.SEVERE, null, ex);
                }});
    }

    public void setTVendas(int [][] totalVendas){this.tVendas = totalVendas.clone();}

    public void setTotalFaturado(double totalFaturado) {this.tFacturado = totalFaturado;}

    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final Facturacao other = (Facturacao) obj;
        if (!Objects.equals(this.fact, other.fact)) {return false;}
        if (!Arrays.deepEquals(this.tVendas, other.tVendas)){return false;}
        if (!Arrays.deepEquals(this.tFF1, other.tFF1)) {return false;}
        if (!Arrays.deepEquals(this.tFF2, other.tFF2)) {return false;}
        if (!Arrays.deepEquals(this.tFF3, other.tFF1)) {return false;}
        if (Double.doubleToLongBits(this.tFacturado) != Double.doubleToLongBits(other.tFacturado)){return false;}
        return true;
    }

    public Facturacao clone() throws CloneNotSupportedException{
        return new Facturacao(this);
    }

    public int hashCode() {
        return Arrays.hashCode( new Object[] {fact,tVendas,tFF1,tFF2,tFF3,tFacturado} );
    }

    public void adicionaProduto(Produto prod) throws CloneNotSupportedException{
        this.fact.put(prod.clone(),0);
    }
    
    public void adicionaFaturacao(Venda v) throws CloneNotSupportedException {
        String tipo;
        int mes,quantidade,filial;
        double preco;
        quantidade = v.getQuantidade();
        tipo = v.getTipo();
        mes = v.getMes();
        filial = v.getFilial();
        preco = v.getPreco();
        this.tFacturado =  this.tFacturado + (preco*quantidade);
        if(tipo.equals("N")){
            if (filial == 1) tFF1[mes-1][0] += preco*quantidade;
            else if (filial == 2) tFF2[mes-1][0] += preco*quantidade;
            else tFF3[mes-1][0] += preco*quantidade;
            tVendas[mes-1][0] = tVendas[mes-1][0] + 1;
        }
        else{  
            if(filial==1) tFF1[mes-1][1] += preco*quantidade;
            else if (filial == 2) tFF2[mes-1][1] += preco*quantidade;
            else tFF3[mes-1][1] += preco*quantidade;
            tVendas[mes-1][1] = tVendas[mes-1][1] + 1;
        }
        int q = this.fact.get(v.getProduto());
        q = q + quantidade;
        this.fact.put(v.getProduto(),q);    
    }
    
    public int totalProdutoNaoComprados(){
        int  r=0;
        for(Integer p: fact.values()){
            if (p==0) r = r + 1;
        }
        return r;
    }

    public int vendasMensais(int mes){
        int vendas = tVendas[mes][0]+tVendas[mes][1];
        return vendas;
    }

    public double totalFacturadoMensalF1(int mes){
        double fat = tFF1[mes][0]+tFF1[mes][1];
        return fat;
    }

    public double totalFacturadoMensalF2(int mes){
        double fat = tFF2[mes][0]+tFF2[mes][1];
        return fat;
    }

    public double totalFacturadoMensalF3(int mes){
        double fat = tFF3[mes][0]+tFF3[mes][1];
        return fat;
    }
    
    public double totalFacturadoMensal(int mes){
        double fat = tFF1[mes][0]+tFF1[mes][1] + tFF2[mes][0]+tFF2[mes][1] + tFF3[mes][0]+tFF3[mes][1];
        return fat;
    }

    public ArrayList<ParProdNumClis> listaXProd (int valor) throws CloneNotSupportedException {
        TreeSet<ParProdNumClis> cod = new TreeSet<>(new CompParesProdClientes());
        ArrayList<ParProdNumClis> prodF = new ArrayList<>();
        ArrayList<ParProdNumClis> prodAux = new ArrayList<>();
        fact.forEach((k,v) -> {
                if(v!=0){
                    ParProdNumClis pmc = new ParProdNumClis();
                    pmc.setCodigo(k.getCodigo());
                    pmc.setTotal(v);
                    try {
                        cod.add(pmc.clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Facturacao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        cod.forEach((v) ->  {  
                try {
                    prodAux.add(v.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Facturacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        for(int i=0; i<valor && i<prodAux.size(); i++){
            prodF.add(prodAux.get(i).clone());
        }
        return prodF;
    }    
}

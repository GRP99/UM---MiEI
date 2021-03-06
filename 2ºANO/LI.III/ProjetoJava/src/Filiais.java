import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Filiais  implements Serializable{
    private Map<Cliente,InfoCliente> comprasCliente; /*Codigo do cliente, Informacao das suas compras*/
    private Map<Produto,InfoProduto> comprasProduto; /*Codigo do produto, Informacao das suas vendas*/
    private Map<Cliente,Double> compradoresF1; /*Codigo do cliente, quanto gastou na filial1 */
    private Map<Cliente,Double> compradoresF2; /*Codigo do cliente, quanto gastou na filial2 */
    private Map<Cliente,Double> compradoresF3; /*Codigo do cliente, quanto gastou na filial3 */

    public Filiais (){
        this.comprasCliente = new HashMap<>();
        this.comprasProduto = new HashMap<>();
        this.compradoresF1 = new HashMap<>();
        this.compradoresF2 = new HashMap<>();
        this.compradoresF3 = new HashMap<>();
    }

    public Filiais (Map<Cliente,InfoCliente> cc, Map<Produto,InfoProduto> cp, Map<Cliente,Double> cf1, Map<Cliente,Double> cf2, Map<Cliente,Double> cf3){
        this.comprasCliente = cc;
        this.comprasProduto = cp;
        this.compradoresF1 = cf1;
        this.compradoresF2 = cf2;
        this.compradoresF3 = cf3;
    }

    public Filiais (Filiais f){
        this.comprasCliente = f.getCC();
        this.comprasProduto = f.getCP();
        this.compradoresF1 = f.getCF1();
        this.compradoresF2 = f.getCF2();
        this.compradoresF3 = f.getCF3();
    }

    public Map<Cliente,InfoCliente> getCC() {
        Map<Cliente,InfoCliente> hm = new HashMap<>();
        this.comprasCliente.forEach((k,v) -> { 
                try {
                    hm.put(k.clone(), v.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        return hm;
    }

    public Map<Produto,InfoProduto> getCP() {
        Map<Produto,InfoProduto> hm = new HashMap<>();
        this.comprasProduto.forEach((k,v) -> { 
                try {
                    hm.put(k.clone(), v.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        return hm;
    }

    public HashMap<Cliente,Double> getCF1() {
        HashMap<Cliente,Double> hm = new HashMap<>();
        this.compradoresF1.forEach((k,v) -> { 
                try {
                    hm.put(k.clone(), v);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais .class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        return hm;
    }

    public HashMap<Cliente,Double> getCF2() {
        HashMap<Cliente,Double> hm = new HashMap<>();
        this.compradoresF2.forEach((k,v) -> { 
                try {
                    hm.put(k.clone(), v);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais .class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        return hm;
    }

    public Map<Cliente,Double> getCF3() {
        Map<Cliente,Double> hm = new HashMap<>();
        this.compradoresF3.forEach((k,v) -> { 
                try {
                    hm.put(k.clone(), v);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        return hm;
    }

    public void setCC(Map<Cliente,InfoCliente> cc) {
        this.comprasCliente = new HashMap<>();
        comprasCliente.forEach((k,v) -> {
                try {
                    this.comprasCliente.put(k.clone(), v.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais .class.getName()).log(Level.SEVERE, null, ex);
                }
            });
    }

    public void setCP(Map<Produto,InfoProduto> cc) {
        this.comprasProduto = new HashMap<>();
        comprasProduto.forEach((k,v) -> {
                try {
                    this.comprasProduto.put(k.clone(), v.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
    }

    public void setCF1(Map<Cliente,Double> cc) {
        this.compradoresF1 = new HashMap<>();
        compradoresF1.forEach((k,v) -> {
                try {
                    this.compradoresF1.put(k.clone(), v);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
    }

    public void setCF2(Map<Cliente,Double> cc) {
        this.compradoresF2 = new HashMap<>();
        compradoresF2.forEach((k,v) -> {
                try {
                    this.compradoresF2.put(k.clone(), v);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
    }

    public void setCF3(Map<Cliente,Double> cc) {
        this.compradoresF3 = new HashMap<>();
        compradoresF3.forEach((k,v) -> {
                try {
                    this.compradoresF3.put(k.clone(), v);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
    }

    public Filiais clone() throws CloneNotSupportedException {
        return new Filiais(this);
    }

    public boolean equals(Object o) {
        if (o == this) {return true;}
        if ((o == null) || (this.getClass() != o.getClass())) {return false;}
        final Filiais other = (Filiais) o;
        return this.comprasCliente.keySet().containsAll(other.comprasCliente.keySet())
        && this.comprasCliente.values().containsAll(other.comprasCliente.values())
        && this.comprasProduto.keySet().containsAll(other.comprasProduto.keySet())
        && this.comprasProduto.values().containsAll(other.comprasProduto.values());
    }

    public int hashCode() {
        return Arrays.hashCode( new Object[] {comprasCliente,comprasProduto,compradoresF1,compradoresF2,compradoresF3} );
    }

    public void adicionaCliente(Cliente cli) throws CloneNotSupportedException{
        InfoCliente ic = new InfoCliente();
        this.comprasCliente.put(cli.clone(), ic);
    }

    public void adicionaProduto(Produto pro) throws CloneNotSupportedException{
        InfoProduto ip = new InfoProduto();
        this.comprasProduto.put(pro.clone(), ip);
    }

    public void adicionaCompra(Venda v) throws CloneNotSupportedException{
        double x;
        double preco = v.getPreco();
        int quantidade = v.getQuantidade();
        int mes = v.getMes();
        int filial = v.getFilial();
        Produto p = v.getProduto();
        Cliente c = v.getCliente();
        if (filial == 1){
            if(this.compradoresF1.containsKey(c)){
                x = this.compradoresF1.get(c);
                x = x + v.getPreco() * v.getQuantidade();
                this.compradoresF1.put(c.clone(),x);
            }
            else{
                x = v.getPreco() * v.getQuantidade();
                this.compradoresF1.put(c.clone(),x);
            }
        }
        else{
            if (filial == 2){
                if(this.compradoresF2.containsKey(c)){
                    x = this.compradoresF2.get(c);
                    x = x + v.getPreco() * v.getQuantidade();
                    this.compradoresF2.put(c.clone(),x);
                }
                else{
                    x = v.getPreco() * v.getQuantidade();
                    this.compradoresF2.put(c.clone(),x);
                }
            }
            else{
                if(this.compradoresF3.containsKey(c)){
                    x = this.compradoresF3.get(c);
                    x = x + v.getPreco() * v.getQuantidade();
                    this.compradoresF3.put(c.clone(),x);
                }
                else{
                    x = v.getPreco() * v.getQuantidade();
                    this.compradoresF3.put(c.clone(),x);
                }
            }
        }
        InfoCliente ic = new InfoCliente();
        ic = this.comprasCliente.get(c);
        ic.adicionaIC(v);
        this.comprasCliente.put(c,ic);
        InfoProduto ip = new InfoProduto();
        ip = this.comprasProduto.get(p);
        ip.adicionaIP(preco,quantidade,mes);
        this.comprasProduto.put(p,ip);
    }

    public int getTCCompram(){
        return comprasCliente.size();
    }
    
    public int getTPDiferentes(){
        int r=0;
        for(InfoProduto ip : comprasProduto.values()){
            if (ip.getUnidadesVendidas() !=0) r++;
        }
        return r;
    }

    public int getCompradoresM(int m){
        int res = 0;
        for (InfoCliente ic : this.comprasCliente.values()){
            if ((ic.comprasMN(m+1) + ic.comprasMP(m+1)) != 0) res=res+1;
        }
        return res;
    }

    public List<String> getProdutosNaoComprados (){
        List<String> naocomprados = new ArrayList<>();
        comprasProduto.forEach((k,v) -> {
                if (v.getUnidadesVendidas()==0) naocomprados.add(k.getCodigo());
            });
        return naocomprados;
    }

    public int getCliM(int mes){
        int tot=0;
        for(InfoCliente ic: this.comprasCliente.values()){
            if ((ic.comprasMN(mes) != 0) || (ic.comprasMP(mes) != 0)) tot=tot+1;
        }
        return tot;
    }

    public int[] compras(Cliente cli) throws ClienteInexistente, CloneNotSupportedException{
        int mes,cc;
        int[] c = new int[12];
        if (!(this.comprasCliente.containsKey(cli))) throw new ClienteInexistente(cli.getCodigo());
        else{
            InfoCliente ic = this.comprasCliente.get(cli);
            for (mes=0; mes<12; mes++){
                cc = ic.comprasMN(mes+1) + ic.comprasMP(mes+1);
                c[mes] = cc;
            }
        }
        return c.clone();
    }

    public int[] produtosdistintos(Cliente cli) throws ClienteInexistente, CloneNotSupportedException{
        int mes;
        int[] pd = new int[12];
        if (!(this.comprasCliente.containsKey(cli))) throw new ClienteInexistente(cli.getCodigo());
        else{
            InfoCliente ic = this.comprasCliente.get(cli);
            for (mes=0; mes<12; mes++){
                pd[mes] = ic.totalprodutosM(mes+1);
            }
        }
        return pd.clone();
    }

    public int[] gastoutotal(Cliente cli) throws ClienteInexistente, CloneNotSupportedException{
        int mes;
        int[] gt = new int[12];
        if (!(this.comprasCliente.containsKey(cli))) throw new ClienteInexistente(cli.getCodigo());
        else{
            InfoCliente ic = this.comprasCliente.get(cli);
            for (mes=0; mes<12; mes++){
                gt[mes] = ic.totalGM(mes+1);
            }
        }
        return gt.clone();
    }

    public int[] comprado(Produto pro) throws ProdutoInexistente, CloneNotSupportedException{
        int mes;
        int[] nc = new int[12];
        if (!(this.comprasProduto.containsKey(pro))) throw new ProdutoInexistente(pro.getCodigo());
        else{
            InfoProduto ip = this.comprasProduto.get(pro);
            for (mes=0; mes<12; mes++){
                nc[mes] = ip.getComprasMes(mes+1);
            }
        }
        return nc.clone();
    }

    public int[] clientesdiferentes(Produto pro) throws ProdutoInexistente, CloneNotSupportedException{
        int mes;
        int[] cf = new int[12];
        if (!(this.comprasProduto.containsKey(pro))) throw new ProdutoInexistente(pro.getCodigo());
        else{
            for(InfoCliente ic : comprasCliente.values()){
                for(mes=1;mes<=12;mes++){
                    if(ic.calculaDistintos(mes,pro)!=0) cf[mes-1]++;
                }
            }
        }
        return cf.clone();
    }

    public double[] totalfacturado(Produto pro) throws ProdutoInexistente, CloneNotSupportedException{
        int mes;
        double[] tf = new double[12];
        if (!(this.comprasProduto.containsKey(pro))) throw new ProdutoInexistente(pro.getCodigo());
        else{
            InfoProduto ip = this.comprasProduto.get(pro);
            for (mes=0; mes<12; mes++){
                tf[mes] = ip.getFacturadoMes(mes+1);
            }
        }
        return tf.clone();
    }

    public Set<ParProdNumClis> listaProdutos(Cliente c) throws ClienteInexistente{
        if (!(this.comprasCliente.containsKey(c))) throw new ClienteInexistente(c.getCodigo());
        else {
            Set<ParProdNumClis> pmc;
            InfoCliente ic = comprasCliente.get(c);
            pmc = ic.getCodProd();
            return pmc;
        } 
    }

    public int quantCompradores(String pro) throws CloneNotSupportedException{
        int r=0;
        InfoCliente ic;
        Produto p = new Produto(pro);
        for(Cliente cli: comprasCliente.keySet()){
            ic=comprasCliente.get(cli);
            if(ic.existeProduto(p.clone())) r++;
        }
        return r;
    }

    public Set<ParProdNumClis> maiscomprasF1(){
        Set<ParProdNumClis> c = new TreeSet<>(new CompParesProdClientes());
        compradoresF1.forEach((Cliente k,Double v) ->  {
                ParProdNumClis pmc = new ParProdNumClis();
                pmc.setCodigo(k.getCodigo());
                pmc.setTotal(v.intValue());
                try {
                    c.add(pmc.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais .class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        return c;
    }

    public TreeSet<ParProdNumClis> maiscomprasF2(){
        TreeSet<ParProdNumClis> c = new TreeSet<>(new CompParesProdClientes());
        compradoresF2.forEach((Cliente k,Double v) ->  {
                ParProdNumClis pmc = new ParProdNumClis();
                pmc.setCodigo(k.getCodigo());
                pmc.setTotal(v.intValue());
                try {
                    c.add(pmc.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        return c;
    }

    public Set<ParProdNumClis> maiscomprasF3(){
        Set<ParProdNumClis> c = new TreeSet<>(new CompParesProdClientes());
        compradoresF3.forEach((Cliente k,Double v) ->  {
                ParProdNumClis pmc = new ParProdNumClis();
                pmc.setCodigo(k.getCodigo());
                pmc.setTotal(v.intValue());
                try {
                    c.add(pmc.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        return c;
    }

    public List<ParProdNumClis> compradoresDif (int valor) throws CloneNotSupportedException{
        Set<ParProdNumClis> c = new TreeSet<>(new CompParesProdClientes());
        List<ParProdNumClis> lista = new ArrayList<>();
        List<ParProdNumClis> aux = new ArrayList<>();
        comprasCliente.forEach((k,v) -> {
                InfoCliente ic = v;    
                ParProdNumClis pmc = new ParProdNumClis();
                pmc.setCodigo(k.getCodigo());
                pmc.setTotal(ic.quantosPDistintos());
                try {
                    c.add(pmc.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        c.forEach((v) -> {  
                try {
                    aux.add(v.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        for(int i=0; i<valor && i<aux.size();i++){
            lista.add(aux.get(i).clone());
        }
        return lista;
    }

    public List<ParProdNumClis> listaCli(Produto codigo, int valor) throws ProdutoInexistente,CloneNotSupportedException{
        if (!(this.comprasProduto.containsKey(codigo))) throw new ProdutoInexistente(codigo.getCodigo());
        else{
            Set<ParProdNumClis> listaordenada = new TreeSet<>(new CompParesProdClientes());
            List<ParProdNumClis> lista = new ArrayList<>();
            List<ParProdNumClis> aux = new ArrayList<>();
            comprasCliente.forEach((k,v) ->  {
                    InfoCliente ic = v;
                    try {
                        if(ic.existeProduto(codigo.clone())) {
                            ParProdNumClis pmc = new ParProdNumClis();
                            pmc.setCodigo(k.getCodigo());
                            pmc.setTotal(ic.gastoProduto(codigo.clone()));
                            try {
                                listaordenada.add(pmc.clone());
                            } catch (CloneNotSupportedException ex) {
                                Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                            }            
                        }
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            listaordenada.forEach( (v) ->  {  
                    try {
                        aux.add(v.clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Filiais.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            for(int i=0; i<valor && i<aux.size(); i++){
                lista.add(aux.get(i).clone());
            }
            return lista;
        }
    }
}

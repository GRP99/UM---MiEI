import java.io.Serializable;

    public class GestVendas implements InterfGestVendas, Serializable{ 
    private CatalogoClientes clientes;
    private CatalogoProdutos produtos;
    private Facturacao facturacao;
    private Filiais filial;
    private Estatistica estatistica;
    
    public GestVendas(){
        clientes = new CatalogoClientes();
        produtos = new CatalogoProdutos();
        facturacao = new Facturacao();
        filial = new Filiais();
        estatistica = new Estatistica();
    }

    public GestVendas(CatalogoClientes c, CatalogoProdutos p, Facturacao f, Filiais g, Estatistica e) throws CloneNotSupportedException{
        this.clientes = c;
        this.produtos = p;
        this.facturacao = f;
        this.filial = g;
        this.estatistica = e;
    }

    public GestVendas(GestVendas sm) {
        clientes = sm.getClientes();
        produtos = sm.getProdutos();
        facturacao = sm.getFacturacao();
        filial = sm.getFilial();
        estatistica = sm.getEstatistica();
    }

    public CatalogoClientes getClientes() { return clientes; }

    public CatalogoProdutos getProdutos() {return produtos;}

    public Facturacao getFacturacao() {return facturacao;}

    public Filiais getFilial() {return filial;}

    public Estatistica getEstatistica() {return estatistica;}

    public void setClientes(CatalogoClientes c) {this.clientes = c;}

    public void setProdutos(CatalogoProdutos p) {this.produtos = p;}

    public void setFacturacao(Facturacao f) {this.facturacao = f;}

    public void setFilial(Filiais f) {this.filial = f;}

    public void setEstatistica(Estatistica e) {this.estatistica = e;}

    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final GestVendas other = (GestVendas) obj;
        return this.clientes.equals(other.getClientes())
        && this.produtos.equals(other.getProdutos())
        && this.facturacao.equals(other.getFacturacao())
        && this.filial.equals(other.getFilial())
        && this.estatistica.equals(other.getEstatistica());
    }
    
    public GestVendas clone() throws CloneNotSupportedException {
        return new GestVendas(this);
    }
}

import java.io.IOException;

interface InterfGereVendasModel{
    
    public CatalogoClientes getClientes();
    
    public CatalogoProdutos getProdutos();
    
    public Facturacao getFacturacao();
    
    public Filiais getFilial();
    
    public Estatistica getEstatistica();
    
    public void createData() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente , ProdutoInexistente;
    
    public void guardaEstado(String put) throws CloneNotSupportedException;
    
    public void carregaEstado(String put) throws IOException, ClassNotFoundException;
}

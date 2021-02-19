import java.util.List;
import java.io.IOException;

interface InterfGereVendasView{
    
    public int menuInicial() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente , ProdutoInexistente;

    public int menuEstatistico() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente, ProdutoInexistente;

    public List<Object> menuInteractivo() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente, ProdutoInexistente;
    
    public List<Object> menuObjectos() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente, ProdutoInexistente;
}

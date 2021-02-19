import java.io.IOException;

interface InterfGereVendasController{

    public InterfGereVendasModel getModel();

    public InterfGereVendasView getView();

    public void setView(InterfGereVendasView gvv);

    public void setModel(InterfGereVendasModel gvm);

    public void startController() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente , ProdutoInexistente;
}

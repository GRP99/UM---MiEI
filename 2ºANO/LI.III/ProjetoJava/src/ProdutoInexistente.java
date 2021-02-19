public class ProdutoInexistente extends Exception{

    public ProdutoInexistente(){
        super();
    }

    public ProdutoInexistente(String cod){
        super(cod);
    }
}

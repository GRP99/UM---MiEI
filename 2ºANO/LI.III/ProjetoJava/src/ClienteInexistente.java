public class ClienteInexistente extends Exception{

    public ClienteInexistente(){
        super();
    }

    public ClienteInexistente(String cod){
        super(cod);
    }
}

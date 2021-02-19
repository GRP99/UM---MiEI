import java.io.Serializable;

public class VeiculoException extends Exception implements Serializable{
    public VeiculoException(){
        super();
    }
    public VeiculoException(String s){
        super(s);
    }
}
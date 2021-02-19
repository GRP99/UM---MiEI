import java.io.Serializable;

public class AtoresException extends Exception implements Serializable{
    public AtoresException(){
        super();
    }
    
    public AtoresException(String s){
        super(s);
    }
}

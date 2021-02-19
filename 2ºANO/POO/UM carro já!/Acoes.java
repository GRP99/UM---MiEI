    import java.time.LocalDate;
import java.io.Serializable;
import java.io.IOException;

public class Acoes implements Serializable{
    private static RedeClientes redeC;
    private static RedeProprietarios redeP;

    public String loginCliente(String email, String password){
        int aux = 0;
        redeC = SISTEMA.getRedeC();
        try{
            aux = redeC.login(email,password);
            if (aux == 1) return "Sucesso";
            else return "Email ou palavra-passe inválidos";
        }
        catch(AtoresException e){
            return "Cliente inexistente";
        }
    }
    
    public String loginProprietario(String email, String password){
        int aux = 0;
        redeP = SISTEMA.getRedeP();
        try{
            aux = redeP.login(email,password);
            if (aux == 1){
                Proprietario p = new Proprietario(redeP.getProprietario(email));
                int nv = p.quantosVeiculos();
                int np = p.quantosPedidos();
                String str = (Integer.toString(nv) + "," + Integer.toString(np));
                return str;
            }
            else return "Email ou palavra-passe inválidos!!!";
        }
        catch(AtoresException e){
            return ("Cliente inexistente!!!");
        }
    }
    
    public String criaCliente (String email, String nome, String pass, String morada, LocalDate dn, Coordenadas l){
        redeC = SISTEMA.getRedeC();
        Cliente c = new Cliente (email,nome,pass,morada,dn,l);
        try{
            redeC.addCliente(c.clone());
            SISTEMA.setRedeC(redeC);
            String r = "Cliente criado com sucesso";
            return r;
        }
        catch(AtoresException e){
            String r = "Cliente já existe";
            return r;
        }
    }
    
    public String criaProprietario(String email, String nome, String pass, String morada, LocalDate dn, int clas){
        redeP = SISTEMA.getRedeP();
        Proprietario p = new Proprietario (email,nome,pass,morada,dn,clas);
        try{
            redeP.addProprietario(p.clone());
            SISTEMA.setRedeP(redeP);
            String r = "Proprietário criado com sucesso";
            return r;
        }
        catch(AtoresException e){
            String r = "Proprietário já existe";
            return r;
        }
    }
    
    public String grava(){
        try{
            redeC = SISTEMA.getRedeC();
            redeP = SISTEMA.getRedeP();
            redeC.guardaEstado("estadoC.obj");
            redeP.guardaEstado("estadoP.obj");
            return ("Até Breve");
        }
        catch(IOException e){
            return("Ops! Não consegui gravar os dados!");
        }
    }
}

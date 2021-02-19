    import java.io.*;

public class SISTEMA{
    private static RedeClientes redeC ;
    private static RedeProprietarios redeP;

    public static void main(String[] args){
        try {
            redeC = RedeClientes.carregaEstado("estadoC.obj");
            redeP = RedeProprietarios.carregaEstado("estadoP.obj");
        }
        catch (FileNotFoundException e) {
            System.out.println("Parece que é a primeira utilização...");
            redeC = new RedeClientes();
            redeP = new RedeProprietarios();
            try {
                Carregar.leitura();
                System.out.println("Dados carregados.");
            }
            catch (IOException x) {System.out.println("Não foi possível ler o ficheiro BAK.");}
        }
        catch (IOException e) {
            System.out.println("Ops! Erro de leitura!");     
            redeC = new RedeClientes();
            redeP = new RedeProprietarios();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Ops! Formato de ficheiro de dados errado!");
            redeC = new RedeClientes();
            redeP = new RedeProprietarios();
        }
        Menu.menuInicial();
    }

    public static RedeClientes getRedeC() {return redeC;}

    public static void setRedeC(RedeClientes rc){redeC = rc;}

    public static RedeProprietarios getRedeP() {return redeP;}

    public static void setRedeP(RedeProprietarios rp){redeP = rp;}
}
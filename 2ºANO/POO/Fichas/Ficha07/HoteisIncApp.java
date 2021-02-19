import java.io.*;
import java.util.Scanner;

public class HoteisIncApp {
    private HoteisInc logNegocio;
    private Menu menuPrincipal, menuHoteis;

    public static void main(String[] args) {
        new HoteisIncApp().run();
    }

    private HoteisIncApp() {
        String[] opcoes = {"Adicionar Hotel","Consultar Hotel"};
        String[] opcoesHoteis = {"Criar Hotel Standard","Criar Hotel Discount","Criar Hotel Premium"};
        this.menuPrincipal = new Menu(opcoes);        
        this.menuHoteis = new Menu(opcoesHoteis);
        try {
            this.logNegocio = HoteisInc.carregaEstado("estado.obj");
        }
        catch (FileNotFoundException e) {
            System.out.println("Parece que é a primeira utilização...");  
            this.logNegocio = new HoteisInc();
        }
        catch (IOException e) {
            System.out.println("Ops! Erro de leitura!");     
            this.logNegocio = new HoteisInc();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Ops! Formato de ficheiro de dados errado!");
            this.logNegocio = new HoteisInc();
        }
    }

    private void run() {
        System.out.println(this.logNegocio.toString());
        do {
            menuPrincipal.executa();
            switch (menuPrincipal.getOpcao()) {
                case 1: System.out.println("Escolheu adicionar");
                break;
                case 2: //trataConsultarHotel();
            }
        } while (menuPrincipal.getOpcao()!=0);
        try {
            this.logNegocio.guardaEstado("estado.obj");
        }
        catch (IOException e) {
            System.out.println("Ops! Não consegui gravar os dados!");
        }
        System.out.println("Até breve!...");     
    }

    public void trataConsultarHotel() {
        Scanner scin = new Scanner(System.in);
        System.out.println("Código a consultar: ");
        String cod = scin.nextLine();
        try {
            System.out.println(this.logNegocio.getHotel(cod).toString());
        }
        catch (HotelInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }
}


import java.time.LocalDate;
import java.io.Serializable;

public class Menu implements Serializable{

    public static void menuInicial(){
        int opcao;
        boolean f = true;
        Acoes ac = new Acoes();
        while(f){
            System.out.print('\u000C');
            System.out.println("################## UM CARRO JÁ ############");
            System.out.println("# 1. Login de Cliente.                    #");
            System.out.println("# 2. Login de Proprietário.               #");
            System.out.println("# 3. Criar conta como Cliente.            #");
            System.out.println("# 4. Criar conta como Proprietário.       #");
            System.out.println("# 0. SAIR.                                #");
            System.out.println("###########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    String res = ac.grava();
                    System.out.println(res);
                    System.exit(0);
                    break;
                case 1:
                    loginC();
                    f = false;
                    break;
                case 2:
                    loginP();
                    f = false;
                    break;
                case 3:
                    criaC();
                    f = false;
                    break;
                case 4:
                    criaP();
                    f = false;
                    break;
                default:
                    break;
            }
        }
    }

    public static void loginC(){
        int aux=0;
        MenuCliente mc = new MenuCliente();
        Acoes ac = new Acoes();
        System.out.print('\u000C');
        System.out.println("Insira o email: ");
        String email = Input.lerString();
        System.out.println("Insira a password: ");
        String pass = Input.lerString();
        String res = ac.loginCliente(email,pass);
        if (res.equals("Sucesso")) mc.menu(email);
        else{
            System.out.println(res);
            sair();
            menuInicial();
        }
    }

    public static void loginP(){
        int aux=0;
        Acoes ac = new Acoes();
        System.out.print('\u000C');
        System.out.println("Insira o email:");
        String email = Input.lerString();
        System.out.println("Insira a password:");
        String pass = Input.lerString();
        String res = ac.loginProprietario(email,pass);
        if (!(res.endsWith("!!!"))){
            String[] partes = res.split(",");
            int result1 = Integer.parseInt(partes[0]);
            int result2 = Integer.parseInt(partes[1]);
            MenuProprietario mp = new MenuProprietario(result1,result2);
            mp.menu(email);
        }
        else{
            System.out.println(res);
            sair();
            menuInicial();
        }
    }

    public static void criaC(){
        int dia,mes,ano;
        double x,y;
        Acoes ac = new Acoes();
        System.out.print('\u000C');
        System.out.println("Insira o seu email:");
        String email = Input.lerString();
        System.out.println("Insira a sua password:");
        String pass = Input.lerString();
        System.out.println("Insira o seu nome:");
        String nome = Input.lerString();
        System.out.println("Insira a sua morada:");
        String morada = Input.lerString();;
        System.out.println("Introduza a sua data de nascimento no formato dia,mes,ano separado por 'Enters':");
        while(true){
            dia = Input.lerInt();
            mes = Input.lerInt();
            ano = Input.lerInt();
            if (dia>=1 && dia <=31 && mes >=1 && mes <=12) break;
        }
        LocalDate dn = LocalDate.of(ano,mes,dia);
        System.out.println("Introduza a sua localização atual no espaco 2D em x e em y separado por 'Enters':");
        x = Input.lerDouble();
        y = Input.lerDouble();
        Coordenadas l = new Coordenadas(x,y);
        String val = ac.criaCliente(email,nome,pass,morada,dn,l);
        System.out.println(val);
        sair();
        menuInicial();
    }

    public static void criaP(){
        int dia,mes,ano;
        System.out.print('\u000C');
        Acoes ac = new Acoes();
        System.out.println("Insira o seu email:");
        String email = Input.lerString();
        System.out.println("Insira a sua password:");
        String pass = Input.lerString();
        System.out.println("Insira o seu nome:");
        String nome = Input.lerString();
        System.out.println("Insira a sua morada:");
        String morada = Input.lerString();
        System.out.println("Introduza a sua data de nascimento no formato dia,mes,ano separado por 'Enters':");
        while(true){
            dia = Input.lerInt();
            mes = Input.lerInt();
            ano = Input.lerInt();
            if (dia>=1 && dia <=31 && mes >=1 && mes <=12) break;
        }
        LocalDate dn = LocalDate.of(ano,mes,dia);
        String val = ac.criaProprietario(email,nome,pass,morada,dn,0);
        System.out.println(val);
        sair();
        menuInicial();
    }

    private static void sair(){
        System.out.println("Prima Enter para sair");
        String x = Input.lerString();
        while(x.endsWith("\n")){
            x = Input.lerString();
        }
    }
}

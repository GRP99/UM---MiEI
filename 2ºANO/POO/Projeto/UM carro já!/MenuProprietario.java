import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MenuProprietario implements Serializable{
    private int numeroVeiculos;
    private int numeroPedidos;
    
    public MenuProprietario(){
        this.numeroVeiculos = 0;
        this.numeroPedidos = 0;
    }
    
    public MenuProprietario(int nv, int np){
        this.numeroVeiculos = nv;
        this.numeroPedidos = np;
    }
    
    public MenuProprietario(MenuProprietario mp){
        this.numeroVeiculos = mp.getNV();
        this.numeroPedidos = mp.getNP();
    }
    
    public int getNV(){return this.numeroVeiculos;}
    
    public void setNV(int nv){this.numeroVeiculos = nv;}
    
    public int getNP(){return this.numeroPedidos;}
    
    public void getNP(int np){this.numeroPedidos = np;}

    public void menu(String p){
        int opcao,valor;
        boolean r = true;
        while(r){
            System.out.print('\u000C');
            System.out.println("###########################################");
            System.out.println("# 1. Alterar dados                        #");
            System.out.println("# 2. Registar Carros                      #");
            System.out.println("# 3. Ver carros                           #");
            System.out.println("# 4. Sinalizar veiculos                   #");
            System.out.println("# 5. Abastacer veiculos                   #");
            System.out.println("# 6. Alterar preço                        #");
            System.out.println("# 7. Ver total facturado                  #");
            System.out.println("# 8. Lista de Pedidos                     #");
            System.out.println("# 9. Ver viagens                         #");
            System.out.println("# 10. Ver 10 clientes que mais utilizam   #");
            System.out.println("# 0. SAIR                                 #");
            System.out.println("###########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    Menu.menuInicial();
                    break;
                case 1:
                    altera(p);
                    r = false;
                    menu(p);
                    break;
                case 2:
                    regista(p);
                    r = false;
                    menu(p);
                    break;
                case 3:
                    ver(p);
                    r = false;
                    menu(p);
                    break;
                case 4:
                    sinalizar(p);
                    r = false;
                    menu(p);
                    break;
                case 5:
                    abastecer(p);
                    r = false;
                    menu(p);
                    break;
                case 6:
                    alteraPreco(p);
                    r = false;
                    menu(p);
                    break;
                case 7:
                    verFacturado(p);
                    r = false;
                    menu(p);
                    break;
                case 8:
                    pedidos(p);
                    r = false;
                    menu(p);
                    break;
                case 9:
                    viagens(p);
                    r = false;
                    menu(p);
                    break;
                case 10:
                    top10(p);
                    r = false;
                    menu(p);
                    break;
                default:
                    break;
            }
        }
    }

    public void altera(String p){
        int opcao,valor;
        boolean r = true;
        String res = " ";
        AcoesProprietario ap = new AcoesProprietario();
        while(r){
            System.out.print('\u000C');
            System.out.println("##########################################");
            System.out.println("# 1. Alterar Password                    #");
            System.out.println("# 2. Alterar nome                        #");
            System.out.println("# 3. Alterar morada                      #");
            System.out.println("# 4. Alterar data de nascimento          #");
            System.out.println("# 5. Ver perfil                          #");
            System.out.println("# 0. SAIR                                #");
            System.out.println("##########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    menu(p);
                    break;
                case 1:
                    System.out.println("Insira a sua nova password:");
                    String pass = Input.lerString();
                    res = ap.editPass(p,pass);
                    System.out.println(res);
                    sair();
                    altera(p);
                    r=false;
                    break;
                case 2:
                    System.out.println("Insira o seu novo nome:");
                    String nome = Input.lerString();
                    res = ap.editNome(p,nome);
                    System.out.println(res);
                    sair();
                    altera(p);
                    r=false;
                    break;
                case 3:
                    System.out.println("Insira a sua nova morada");
                    String morada = Input.lerString();
                    res = ap.editMorada(p,morada);
                    System.out.println(res);
                    sair();
                    altera(p);
                    r=false;
                    break;
                case 4:
                    int dia,mes,ano;
                    System.out.println("Introduza a sua nova data de nascimento no formato dia,mes,ano separado por 'Enters':");
                    while(true){
                        dia = Input.lerInt();
                        mes = Input.lerInt();
                        ano = Input.lerInt();
                        if (dia>=1 && dia <=31 && mes >=1 && mes <=12) break;
                    }
                    LocalDate dn = LocalDate.of(ano,mes,dia);
                    res = ap.editNascimento(p,dn);
                    System.out.println(res);
                    sair();
                    altera(p);
                    r=false;
                    break;
                case 5:
                    String dados = ap.verperfil(p);
                    System.out.println(dados);
                    sair();
                    r=false;
                    break;
                default:
                    break;
            }
        }
    }

    public void regista(String p){
        int opcao;
        boolean f = true;
        while(f){
            System.out.print('\u000C');
            System.out.println("##########################################");
            System.out.println("# 1. Registar carro a gasolina           #");
            System.out.println("# 2. Registar carro electrico            #");
            System.out.println("# 3. Registar carro hibrido              #");
            System.out.println("# 0. SAIR                                #");
            System.out.println("##########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    menu(p);
                    break;
                case 1:
                    veiculoGasolina(p);
                    regista(p);
                    f = false;
                    break;
                case 2:
                    veiculoElectrico(p);
                    regista(p);
                    f = false;
                    break;
                case 3:
                    veiculoHibrido(p);
                    regista(p);
                    f = false;
                    break;
                    default:
                    break;
            }
        }
    }

    private void veiculoGasolina(String p){
        AcoesProprietario ap = new AcoesProprietario();
        System.out.println();
        System.out.println("Descricao do carro:");
        String desc = Input.lerString();
        System.out.println("Insira a matricula:");
        String mat = Input.lerString();
        System.out.println("Insira a velocidade media por km/h:");
        int vel = Input.lerInt();
        System.out.println("Insira o preço base por km:");
        double pre = Input.lerDouble();
        System.out.println("Insira consumo por km percorrido:");
        int cons = Input.lerInt();
        System.out.println("Insira autonomia:");
        int aut = Input.lerInt();
        System.out.println("Introduza a localização atual no espaco 2D em x e em y separado por 'Enters':");
        double x = Input.lerDouble();
        double y = Input.lerDouble();
        Coordenadas lv = new Coordenadas(x,y);
        String val = ap.criaGasolina(p,desc,mat,vel,pre,cons,aut,lv);
        if (val.equals("Veiculo registado.")) this.numeroVeiculos = this.numeroVeiculos + 1;
        System.out.println(val);
        sair();
    }

    private void veiculoElectrico(String p){
        AcoesProprietario ap = new AcoesProprietario();
        System.out.println();
        System.out.println("Descricao do carro:");
        String desc = Input.lerString();
        System.out.println("Insira a matricula:");
        String mat = Input.lerString();
        System.out.println("Insira a velocidade media por km/h:");
        int vel = Input.lerInt();
        System.out.println("Insira o preço base por km:");
        double pre = Input.lerDouble();
        System.out.println("Insira consumo por km percorrido:");
        int cons = Input.lerInt();
        System.out.println("Insira autonomia:");
        int aut = Input.lerInt();
        System.out.println("Introduza a localização atual no espaco 2D em x e em y separado por 'Enters':");
        double x = Input.lerDouble();
        double y = Input.lerDouble();
        Coordenadas lv = new Coordenadas(x,y);
        String val = ap.criaElectrico(p,desc,mat,vel,pre,cons,aut,lv);
        if (val.equals("Veiculo registado.")) this.numeroVeiculos = this.numeroVeiculos + 1;
        System.out.println(val);
        sair();
    }

    private void veiculoHibrido(String p){
        AcoesProprietario ap = new AcoesProprietario();
        System.out.println();
        System.out.println("Descricao do carro:");
        String desc = Input.lerString();
        System.out.println("Insira a matricula:");
        String mat = Input.lerString();
        System.out.println("Insira a velocidade media por km/h:");
        int vel = Input.lerInt();
        System.out.println("Insira o preço base por km:");
        double pre = Input.lerDouble();
        System.out.println("Insira consumo por km percorrido:");
        int cons = Input.lerInt();
        System.out.println("Insira autonomia:");
        int aut = Input.lerInt();
        System.out.println("Introduza a localização atual no espaco 2D em x e em y separado por 'Enters':");
        double x = Input.lerDouble();
        double y = Input.lerDouble();
        Coordenadas lv = new Coordenadas(x,y);
        String val = ap.criaHibrido(p,desc,mat,vel,pre,cons,aut,lv);
        if (val.equals("Veiculo registado.")) this.numeroVeiculos = this.numeroVeiculos + 1;
        System.out.println(val);
        sair();
    }

    public void ver(String p){
        int flag=1;
        AcoesProprietario ap = new AcoesProprietario();
        System.out.print('\u000C');
        if(this.numeroVeiculos==0){
            System.out.println("Nao existem veiculos registados");
            sair();
        }
        else{
            Map<String,Veiculos> lista = new TreeMap<String,Veiculos>();
            lista = ap.giveVeiculos(p);
            for(Veiculos v: lista.values()){
                if (flag==0){
                    sair();
                    menu(p);
                }
                else flag = verVeiculos(p,v);
            }
            if (flag==0) menu(p);
            else{
                System.out.println("Não existem mais veiculos criados");
                sair();
            }
        }
    }

    public int verVeiculos(String p, Veiculos v){
        int op;
        AcoesProprietario ap = new AcoesProprietario();
        int opcao=0,valor;
        boolean r = true;
        while(r){
            System.out.println("##########################################");
            System.out.println(v.toString());
            System.out.println("##########################################");
            System.out.println("# 1. Vender Veiculo                      #");
            System.out.println("# 2. Ver proximo veiculo                 #");
            System.out.println("# 0. SAIR                                #");
            System.out.println("##########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch(opcao){
                case 0:
                    r = false;
                    break;
                case 1:
                    String val = ap.apagarVeiculo(p,v);
                    System.out.println(val);
                    sair();
                    r = false;
                    break;
                case 2:
                    System.out.print('\u000C');
                    r = false;
                    break;
                default:
                    break;
            }
        }
        return opcao;
    }

    public void sinalizar(String p){
        int opcao,valor;
        boolean r = true;
        String mat;
        String res = " ";
        AcoesProprietario ap = new AcoesProprietario();
        System.out.print('\u000C');
        if (this.numeroVeiculos == 0){
            System.out.println("Nao tem veiculos associados.");
            sair();
        }
        else{
            while(r){
                System.out.println("##########################################");
                System.out.println("# 1. Sinalizar como indisponivel         #");
                System.out.println("# 2. Sinalizar como disponivel           #");
                System.out.println("# 0. SAIR                                #");
                System.out.println("##########################################");
                System.out.print(">>>");
                opcao = Input.lerInt();
                switch(opcao){
                    case 0:
                        menu(p);
                        r = false;
                        break;
                    case 1:
                        System.out.println("Qual o veiculo que pretende marcar, insira a matricula?");
                        mat = Input.lerString();
                        res = ap.marcaIndis(p,mat);
                        System.out.println(res);
                        sair();
                        sinalizar(p);
                        r=false;
                        break;
                    case 2:
                        System.out.println("Qual o veiculo que pretende marcar, insira a matricula?");
                        mat = Input.lerString();
                        res = ap.marcaDisp(p,mat);
                        System.out.println(res);
                        sair();
                        sinalizar(p);
                        r=false;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void abastecer(String p){
        int opcao,valor;
        int aut;
        boolean r = true;
        String mat;
        String res = " ";
        AcoesProprietario ap = new AcoesProprietario();
        System.out.print('\u000C');
        if (this.numeroVeiculos == 0){
            System.out.println("Nao tem veiculos associados.");
            sair();
        }
        else{
            while(r){
                System.out.println("##########################################");
                System.out.println("# 1. Abastecer veiculo a Gasolina        #");
                System.out.println("# 2. Abastecer veiculo a Electrico       #");
                System.out.println("# 3. Abastecer veiculo a Hibrido         #");
                System.out.println("# 0. SAIR                                #");
                System.out.println("##########################################");
                System.out.print(">>>");
                opcao = Input.lerInt();
                switch(opcao){
                    case 0:
                        menu(p);
                        r = false;
                        break;
                    case 1:
                        System.out.println("Qual o veiculo que pretende abastecer, insira a matricula?");
                        mat = Input.lerString();
                        System.out.println("Insira quanto pretende abastacer:");
                        aut = Input.lerInt();
                        if (aut >= 1000 && aut <= 0){
                            System.out.println("Seja realista.");
                            sair();
                            sinalizar(p);
                        }
                        res = ap.abasteceG(p,mat,aut);
                        System.out.println(res);
                        sair();
                        sinalizar(p);
                        r=false;
                        break;
                    case 2:
                        System.out.println("Qual o veiculo que pretende abastecer, insira a matricula?");
                        mat = Input.lerString();
                        System.out.println("Insira quanto pretende abastacer:");
                        aut = Input.lerInt();
                        if (aut >= 1000 && aut <= 0){
                            System.out.println("Seja realista.");
                            sair();
                            sinalizar(p);
                        }
                        res = ap.abasteceE(p,mat,aut);
                        System.out.println(res);
                        sair();
                        sinalizar(p);
                        r=false;
                        break;
                    case 3:
                        System.out.println("Qual o veiculo que pretende abastecer, insira a matricula?");
                        mat = Input.lerString();
                        System.out.println("Insira quanto pretende abastacer:");
                        aut = Input.lerInt();
                        if (aut >= 1000 && aut <= 0){
                            System.out.println("Seja realista.");
                            sair();
                            sinalizar(p);
                        }
                        res = ap.abasteceH(p,mat,aut);
                        System.out.println(res);
                        sair();
                        sinalizar(p);
                        r=false;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void alteraPreco(String p){
        String res = " ";
        System.out.print('\u000C');
        AcoesProprietario ap = new AcoesProprietario();
        if (this.numeroVeiculos == 0){
            System.out.println("Nao tem veiculos associados.");
            sair();
        }
        else{
            System.out.println("Insira a matricula do veiculo que pretende alterar o preço:");
            String mat = Input.lerString();
            System.out.println("Insira o novo preco: ");
            double novo = Input.lerDouble();
            res = ap.alteraP(p,mat,novo);
            System.out.println(res);
            sair();
        }
    }

    public void verFacturado(String p){
        int dia,mes,ano;
        int dia1,mes1,ano1;
        double val = 0;
        System.out.print('\u000C');
        AcoesProprietario ap = new AcoesProprietario();
        if (this.numeroVeiculos == 0){
            System.out.println("Nao tem veiculos associados.");
            sair();
        }
        else{
            System.out.println("Insira a matricula do veiculo que pretende ver quanto já facturou:");
            String mat = Input.lerString();
            System.out.println("Introduza a data inicial no formato dia,mes,ano separado por 'Enters':");
            while(true){
                dia = Input.lerInt();
                mes = Input.lerInt();
                ano = Input.lerInt();
                if (dia>=1 && dia <=31 && mes >=1 && mes <=12) break;
            }
            LocalDate di = LocalDate.of(ano,mes,dia);
            System.out.println("Introduza a data final no formato dia,mes,ano separado por 'Enters':");
            while(true){
                dia1 = Input.lerInt();
                mes1 = Input.lerInt();
                ano1 = Input.lerInt();
                if (dia>=1 && dia <=31 && mes >=1 && mes <=12) break;
            }
            LocalDate df = LocalDate.of(ano1,mes1,dia1);
            val = ap.verFact(p,mat,di,df);
            System.out.println ("Total facturado pelo veiculo " + mat + " é " + val);
            sair();
        }
    }

    public void pedidos(String p){
        int flag = 1;
        AcoesProprietario ap = new AcoesProprietario();
        System.out.print('\u000C');
        if(this.numeroPedidos == 0){
            System.out.println("Nenhum veiculo requisitado.");
            sair();
        }
        else{
            Map<String,Cliente> lista = new HashMap<String,Cliente>();
            lista = ap.givePedidos(p);
            for(String aux: lista.keySet()){
                if (flag==0){
                    sair();
                    menu(p);
                }
                else flag = gerirPedidos(p,aux);
            }
            if (flag==0) menu(p);
            else{
                System.out.println("Não existem mais pedidos");
                sair();
            }
        }
    }

    private int gerirPedidos(String p, String mat){
        int opcao=0, valor, f=0;
        boolean r = true;
        String dec = " ";
        AcoesProprietario ap = new AcoesProprietario();        
        while(r){
            System.out.println("##########################################");
            String res = ap.giveInfo(p,mat);
            System.out.println(res);
            System.out.println("##########################################");
            System.out.println("# 1. Aceitar pedido                      #");
            System.out.println("# 2. Rejeitar pedido                     #");
            System.out.println("# 0. SAIR                                #");
            System.out.println("##########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch(opcao){
                case 0:
                r = false;
                break;
                case 1:
                dec = ap.aceitarPedido(p,mat);
                System.out.println(dec);
                this.numeroPedidos = this.numeroPedidos - 1;
                r = false;
                break;
                case 2:
                ap.rejeitarPedido(p,mat);
                System.out.println(dec);
                this.numeroPedidos = this.numeroPedidos - 1;
                r = false;
                break;
                default:
                break;
            }
        }
        return opcao;
    }
    
    public void viagens(String p){
        int dia,mes,ano;
        int dia1,mes1,ano1;
        int tam = 0,aux=0;
        System.out.print('\u000C');
        ArrayList<Aluguer> lista = new ArrayList<Aluguer>();
        AcoesProprietario ap = new AcoesProprietario(); 
        System.out.println("Introduza a data inicial no formato dia,mes,ano separado por 'Enters':");
        while(true){
            dia = Input.lerInt();
            mes = Input.lerInt();
            ano = Input.lerInt();
            if (dia>=1 && dia <=31 && mes >=1 && mes <=12) break;
        }
        LocalDate di = LocalDate.of(ano,mes,dia);
        System.out.println("Introduza a data final no formato dia,mes,ano separado por 'Enters':");
        while(true){
            dia1 = Input.lerInt();
            mes1 = Input.lerInt();
            ano1 = Input.lerInt();
            if (dia>=1 && dia <=31 && mes >=1 && mes <=12) break;
        }
        LocalDate df = LocalDate.of(ano1,mes1,dia1);
        System.out.println("Introduza a matricula do veiculo que pretende ver as viagens:");
        String mat = Input.lerString();
        lista = ap.verviagens(p,di,df,mat);
        if (lista.size() == 0){
            System.out.println("Não existem alugueres entre essas datas ou a matricula está incorrecta.");
            sair();
        }
        else{
            tam = lista.size();
            printViagens(lista,aux,aux,tam);
        }
        menu(p);
    }

    private void printViagens(ArrayList<Aluguer> lista, int cont, int ind, int tam){
        int o,i;
        System.out.print('\u000C');
        System.out.println("##############################################");
        for (i=0; i<13 && ind < tam; i++){
            System.out.println(lista.get(ind));
            cont++;
            ind++;
        }
        System.out.println("##############################################");
        System.out.println("#  -> TOTAL:  " + tam + "                    #");
        System.out.println("##############################################");
        System.out.println("# 1. Continuar.                              #");
        System.out.println("# 2. Retroceder.                             #");
        System.out.println("# 0. Sair.                                   #");
        System.out.println("##############################################");
        System.out.println(">>>");
        o = Input.lerInt();
        if(o > 0){
            if(o == 1 && cont != tam) printViagens(lista, cont, ind++,tam);
            if(o == 1 && cont == tam){
                System.out.println("Impossível continuar.\n");
                printViagens(lista, cont-i, ind-i,tam);
            }
            if(o == 2 && (cont-13) > 0) printViagens(lista, cont-26, ind-26,tam);
            if(o == 2 && (cont-13) <= 0){
                System.out.println("Impossível retroceder.\n");
                printViagens(lista, 0, 0,tam);
            }
        }
        else if (o == 0) sair();
        else{
            System.out.println("Comando inválido\n");
            printViagens(lista, cont-i, ind-i,tam);
        }   
    }

    public void top10(String p){
        int i=0;
        AcoesProprietario ap = new AcoesProprietario();  
        ArrayList<Cliente> top = new ArrayList<Cliente>();
        top = ap.giveTop(p);
        System.out.print('\u000C');
        if (top.size() == 0){
            System.out.println("Nao existem clientes registados.");
            sair();
        }
        else{
            while(i < 10){
                System.out.println(top.get(i).toString());
                i++;
            }
            sair();
        }
    }

    private void sair(){
        System.out.println("Prima Enter para sair");
        String x = Input.lerString();
        while(x.endsWith("\n")){
            x = Input.lerString();
        }
    }
}

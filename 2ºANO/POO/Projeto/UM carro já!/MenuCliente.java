import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class MenuCliente implements Serializable{
    private boolean pedido;

    public void menu(String c){
        int opcao,valor;
        boolean r = true;
        while(r){
            System.out.print('\u000C');
            System.out.println("###########################################");
            System.out.println("# 1. Alterar dados                        #");
            System.out.println("# 2. CARRO JA                             #");
            System.out.println("# 3. Classificar viagem                   #");
            System.out.println("# 4. Ver viagens                          #");
            System.out.println("# 0. SAIR                                 #");
            System.out.println("###########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    Menu.menuInicial();
                    break;
                case 1:
                    altera(c);
                    r = false;
                    menu(c);
                    break;
                case 2:
                    alugar(c);
                    menu(c);
                    r = false;
                    break;
                case 3:
                    classificar(c);
                    menu(c);
                    r = false;
                    break;
                case 4:
                    viagens(c);
                    menu(c);
                    r = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void altera(String c){
        int opcao,valor;
        boolean r = true;
        String res = " ";
        AcoesCliente ac = new AcoesCliente();
        while(r){
            System.out.print('\u000C');
            System.out.println("##########################################");
            System.out.println("# 1. Alterar Password                    #");
            System.out.println("# 2. Alterar nome                        #");
            System.out.println("# 3. Alterar morada                      #");
            System.out.println("# 4. Alterar data de nascimento          #");
            System.out.println("# 5. Alterar localizacao atual           #");
            System.out.println("# 6. Ver perfil                          #");
            System.out.println("# 0. SAIR                                #");
            System.out.println("##########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    menu(c);
                    break;
                case 1:
                    System.out.println("Insira a sua nova password:");
                    String pass = Input.lerString();
                    res = ac.editPass(c,pass);
                    System.out.println(res);
                    sair();
                    altera(c);
                    r=false;
                    break;
                case 2:
                    System.out.println("Insira o seu novo nome:");
                    String nome = Input.lerString();
                    res = ac.editNome(c,nome);
                    System.out.println(res);
                    sair();
                    altera(c);
                    r=false;
                    break;
                case 3:
                    System.out.println("Insira a sua nova morada");
                    String morada = Input.lerString();
                    res = ac.editMorada(c,morada);
                    System.out.println(res);
                    sair();
                    altera(c);
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
                    res = ac.editNascimento(c,dn);
                    System.out.println(res);
                    sair();
                    altera(c);
                    r=false;
                    break;
                case 5:
                    double x,y;
                    System.out.println("Introduza a sua localização atual no espaco 2D em x e em y separado por 'Enters':");
                    x = Input.lerDouble();
                    y = Input.lerDouble();
                    Coordenadas l = new Coordenadas(x,y);
                    res = ac.editLocal(c,l);
                    System.out.println(res);
                    sair();
                    altera(c);
                    r=false;
                    break;
                case 6:
                    String dados = ac.verperfil(c);
                    System.out.println(dados);
                    sair();
                    r=false;
                    break;
                default:
                    break;
            }
        }
    }

    public void alugar(String c){
        String val = " ";
        double x,y;
        Coordenadas dest;
        String tipo = " ";
        int opcao,valor;
        boolean r = true;
        boolean flag = true;
        AcoesCliente ac = new AcoesCliente();
        while(r){
            System.out.print('\u000C');
            System.out.println("###########################################");
            System.out.println("# 1. Mais próximo                         #");
            System.out.println("# 2. Mais barato                          #");
            System.out.println("# 3. Mais barato dentro de uma distância  #");
            System.out.println("# 4. Especifico                           #");
            System.out.println("# 5. Autonomia desejada                   #");
            System.out.println("# 0. SAIR                                 #");
            System.out.println("###########################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao){
                case 0:
                    Menu.menuInicial();
                    break;
                case 1:
                    System.out.println("Qual é o tipo de veiculo que pretende alugar de momento existe Gasolina, Hibrido ou Electrico?");
                    while(flag){
                          tipo = Input.lerString();
                          if (tipo.equals("Gasolina") || tipo.equals("Hibrido") || tipo.equals("Electrico")) flag = false;
                    }
                    System.out.println("Introduza o destino para onde pretende ir no espaco 2D em x e em y separado por 'Enters':");
                    x = Input.lerDouble();
                    y = Input.lerDouble();
                    dest = new Coordenadas(x,y);
                    val = ac.maisproximo(c,dest,tipo);
                    System.out.println(val);
                    sair();
                    alugar(c);
                    r=false;
                    break;
                case 2:
                    System.out.println("Qual é o tipo de veiculo que pretende alugar de momento existe Gasolina, Hibrido ou Electrico?");
                    while(flag){
                          tipo = Input.lerString();
                          if (tipo.equals("Gasolina") || tipo.equals("Hibrido") || tipo.equals("Electrico")) flag = false;
                    }
                    System.out.println("Introduza o destino para onde pretende ir no espaco 2D em x e em y separado por 'Enters':");
                    x = Input.lerDouble();
                    y = Input.lerDouble();
                    dest = new Coordenadas(x,y);
                    val = ac.maisbarato(c,dest,tipo);
                    System.out.println(val);
                    sair();
                    alugar(c);
                    r=false;
                    break;
                case 3:
                    System.out.println("Qual é o tipo de veiculo que pretende alugar de momento existe Gasolina, Hibrido ou Electrico?");
                    while(flag){
                          tipo = Input.lerString();
                          if (tipo.equals("Gasolina") || tipo.equals("Hibrido") || tipo.equals("Electrico")) flag = false;
                    }
                    System.out.println("Introduza o destino para onde pretende ir no espaco 2D em x e em y separado por 'Enters':");
                    x = Input.lerDouble();
                    y = Input.lerDouble();
                    dest = new Coordenadas(x,y);
                    System.out.println("Qual é a distância maxima que esta disposto a percorrer?");
                    double maxdis = Input.lerDouble();
                    if (maxdis >= 0){
                        val = ac.maisbarato2(c,dest,maxdis,tipo);
                        System.out.println(val);
                        sair();
                    }
                    else{
                        System.out.println("Seja realista.");
                        sair();
                    }
                    alugar(c);
                    r=false;
                    break;
                case 4:
                    System.out.println("Qual é o tipo de veiculo que pretende alugar de momento existe Gasolina, Hibrido ou Electrico?");
                    while(flag){
                          tipo = Input.lerString();
                          if (tipo.equals("Gasolina") || tipo.equals("Hibrido") || tipo.equals("Electrico")) flag = false;
                    }
                    System.out.println("Introduza o destino para onde pretende ir no espaco 2D em x e em y separado por 'Enters':");
                    x = Input.lerDouble();
                    y = Input.lerDouble();
                    dest = new Coordenadas(x,y);
                    System.out.println("Qual é a matricula do carro que pretende alugar?");
                    String mat = Input.lerString();
                    val = ac.especifico(c,dest,mat,tipo);
                    System.out.println(val);
                    sair();
                    alugar(c);
                    r=false;
                    break;
                case 5:
                    System.out.println("Qual é o tipo de veiculo que pretende alugar de momento existe Gasolina, Hibrido ou Electrico?");
                    while(flag){
                          tipo = Input.lerString();
                          if (tipo.equals("Gasolina") || tipo.equals("Hibrido") || tipo.equals("Electrico")) flag = false;
                    }
                    System.out.println("Introduza o destino para onde pretende ir no espaco 2D em x e em y separado por 'Enters':");
                    x = Input.lerDouble();
                    y = Input.lerDouble();
                    dest = new Coordenadas(x,y);
                    System.out.println("Qual é a autonomia que pretende ter no veiculo?");
                    int aut = Input.lerInt();
                    if (aut > 0 && aut <= 1000){
                        val = ac.autonomia(c,dest,aut,tipo);
                        System.out.println(val);
                        sair();
                    }
                    else{
                        System.out.println("Seja realista.");
                        sair();
                    }   
                    alugar(c);
                    r=false;
                    break;
                default:
                    break;
            }
        }
    }

    public void classificar(String c){
        String val = " ";
        int cp,cv;
        int dia=0,mes=0,ano=0;
        AcoesCliente ac = new AcoesCliente();
        System.out.print('\u000C');
        System.out.println("A matricula do veiculo que pretende classificar:");
        String mat = Input.lerString();
        System.out.println("Introduza a data da viagem no formato dia,mes,ano separado por 'Enters':");
        while(true){
            dia = Input.lerInt();
            mes = Input.lerInt();
            ano = Input.lerInt();
            if (dia>=1 && dia <=31 && mes >=1 && mes <=12) break;
        }
        LocalDate d = LocalDate.of(ano,mes,dia);
        System.out.println("Introduza as coordenadas iniciais da viagem pretendida separado por 'Enters'.");
        double x1 = Input.lerDouble();
        double y1 = Input.lerDouble();
        Coordenadas i = new Coordenadas(x1,y1);
        System.out.println("Introduza as coordenadas finais da viagem pretendida separado por 'Enters'.");
        double x2 = Input.lerDouble();
        double y2 = Input.lerDouble();
        Coordenadas f = new Coordenadas(x2,y2);
        while(true){
            System.out.println("De 0 a 100 quanto avalia o veiculo: ");
            cv = Input.lerInt();
            if (cv>=0 && cv <= 100) break;
        }
        while(true){
            System.out.println("De 0 a 100 quanto avalia o proprietário: ");
            cp = Input.lerInt();
            if (cp>=0 && cp <= 100) break;
        }
        val = ac.classifica(c,mat,d,i,f,cv,cp);
        System.out.println(val);
        sair();
        menu(c);
    }

    public void viagens(String c){
        int dia=0,mes=0,ano=0;
        int dia1=0,mes1=0,ano1=0;
        int tam = 0,aux=0;
        System.out.print('\u000C');
        AcoesCliente ac = new AcoesCliente();
        ArrayList<Aluguer> lista = new ArrayList<Aluguer>();
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
        lista = ac.verviagens(c,di,df);
        if (lista.size() == 0){
            System.out.println("Não existem alugueres entre essas datas");
            sair();
        }
        else{
            tam = lista.size();
            printViagens(lista,aux,aux,tam);
        }
        menu(c);
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

    private void sair(){
        System.out.println("Prima Enter para sair");
        String x = Input.lerString();
        while(x.endsWith("\n")){
            x = Input.lerString();
        }
    }
}

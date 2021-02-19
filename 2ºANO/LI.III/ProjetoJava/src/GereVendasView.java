import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.IOException;

public class GereVendasView implements InterfGereVendasView, Serializable{

    public int menuInicial() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente , ProdutoInexistente{
        int opcao = 0;
        boolean f = true;
        while(f){
            System.out.print('\u000C');
            System.out.println("################## GestVendasAppMVC ############");
            System.out.println("# 1. Ler novos ficheiros.                      #");
            System.out.println("# 2. Consultas Estatisticas.                   #");
            System.out.println("# 3. Consultas Interactivas.                   #");
            System.out.println("# 4. Ficheiro de dados                         #");
            System.out.println("# 0. Sair                                      #");
            System.out.println("################################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    System.out.println("Até Breve...");
                    f = false;
                    break;
                case 1:
                    f = false;
                    break;
                case 2:
                    f = false;
                    break;
                case 3:
                    f = false;
                    break;
                case 4:
                    f=false;
                    break;
                default:
                    break;
            }
        }
        return opcao;
    }  

    public int menuEstatistico() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente, ProdutoInexistente{
        int opcao = 0;
        boolean r = true;
        while(r){
            System.out.print('\u000C');
            System.out.println("####################### GestVendasAppMVC #################################");
            System.out.println("#  1. Dados estatisticos acerca do ultimo ficheiro lido de vendas lido   #");
            System.out.println("#  0. Sair                                                               #");
            System.out.println("##########################################################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    r = false;
                    break;
                case 1:
                    r = false;
                    break;
                default:
                    break;
            }
        }
        return opcao;
    }

    public List<Object> menuInteractivo() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente, ProdutoInexistente{
        int opcao;
        int valor;
        boolean r = true;
        List<Object> rand = new ArrayList<>();
        String cliente;
        String produto;
        while(r){
            System.out.print('\u000C');
            System.out.println("################################################################################## GestVendasAppMVC ################################################################################################################");
            System.out.println("# 1.Lista ordenada alfabeticamente com os códigos dos produtos nunca comprados e o seu respetivo total.                                                                                                            #");
            System.out.println("# 2.Dado um mês válido, determinar o número total global de vendas realizadas e o número total de clientes distintos que as fizeram.                                                                               #");
            System.out.println("# 3.Dado um código de cliente, determinar, para cada mês, quantas compras fez, quantos produtos distintos comprou e quanto gastou no total.                                                                        #");
            System.out.println("# 4.Dado o código de um produto existente, determinar, mês a mês, quantas vezes foi comprado, por quantos clientes diferentes e o total facturado.                                                                 #");
            System.out.println("# 5.Dado o código de um cliente determinar a lista de códigos de produtos que mais comprou (e quantos), ordenada por ordem decrescente de quantidade e, para quantidades iguais, por ordem alfabética dos códigos. #");
            System.out.println("# 6.Determinar o conjunto dos X produtos mais vendidos em todo o ano (em número de unidades vendidas) indicando o número total de distintos clientes que o compraram.                                              #");
            System.out.println("# 7.Determinar, para cada filial, a lista dos três maiores compradores em termos de dinheiro facturado.                                                                                                            #");
            System.out.println("# 8.Determinar os códigos dos X clientes que compraram mais produtos diferentes, indicando quantos, sendo o critério de ordenação a ordem decrescente do número de produtos.                                       #");
            System.out.println("# 9.Dado o código de um produto que deve existir, determinar o conjunto dos X clientes que mais o compraram e, para cada um, qual o valor gasto.                                                                   #");
            System.out.println("# 10.Determinar mês a mês, e para cada mês filial a filial, a facturação total com cada produto.                                                                                                                   #");
            System.out.println("# 0. SAIR                                                                                                                                                                                                          #");
            System.out.println("####################################################################################################################################################################################################################");
            System.out.print(">>>");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    rand.add(0,0);
                    r = false;
                    break;
                case 1:
                    rand.add(0,1);
                    r = false;
                    break;
                case 2:
                    System.out.println("Insira um mês (1-12)");
                    int mes = Input.lerInt();
                    if (mes < 1 && mes>12){
                        System.out.println("Insira um mês válido!");
                        sair();
                        menuInteractivo();
                    }
                    rand.add(0,2);
                    rand.add(1,mes);
                    r = false;
                    break;
                case 3:
                    System.out.println("Insira um código de cliente (ex: G1999)");
                    cliente = Input.lerString();
                    rand.add(0,3);
                    rand.add(1,cliente);
                    r = false;
                    break;
                case 4:
                    System.out.println("Insira um código de um produto existente (ex: GR1999)");
                    produto = Input.lerString();
                    rand.add(0,4);
                    rand.add(1,produto);
                    r = false;
                    break;
                case 5:
                    System.out.println("Insira um código de cliente (ex: G1999)");
                    cliente = Input.lerString();
                    rand.add(0,5);
                    rand.add(1,cliente);
                    r = false;
                    break;
                case 6:
                    System.out.println("Insira um valor");
                    valor= Input.lerInt();
                    rand.add(0,6);
                    rand.add(1,valor);
                    r = false;
                    break;
                case 7:
                    rand.add(0,7);
                    r = false;
                    break;
                case 8:
                    System.out.println("Insira um valor");
                    valor= Input.lerInt();
                    rand.add(0,8);
                    rand.add(1,valor);
                    r=false;
                    break;
                case 9:
                    System.out.println("Insira um código de um produto existente (ex: GR1999)");
                    produto = Input.lerString();
                    System.out.println("Insira um valor");
                    valor = Input.lerInt();
                    rand.add(0,9);
                    rand.add(1,produto);
                    rand.add(2,valor);
                    r=false;
                    break;
                case 10:
                    System.out.println("Em construção...");
                    rand.add(0,10);
                    r=false;
                    break;
                default:
                    break;
            }
        }
        return rand;
    }

    public List<Object> menuObjectos() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente, ProdutoInexistente {
        int opcao;
        boolean r = true;
        String put;
        List<Object> rand = new ArrayList<>();
        while(r){
            System.out.print('\u000C');
            System.out.println("####################### GestVendasAppMVC ################################");
            System.out.println("# 1. Guardar  Ficheiro objecto                                          #");
            System.out.println("# 2. Carregar Ficheiro objecto                                          #");
            System.out.println("# 0. Sair                                                               #");
            System.out.println("#########################################################################");
            System.out.print(">");
            opcao = Input.lerInt();
            switch (opcao) {
                case 0:
                    rand.add(0,0);
                    r = false;
                    break;
                case 1:
                    System.out.println("Insira o nome do ficheiro a gravar (Prima Enter para gestVendas.dat):");
                    put = Input.lerString();
                    if (put.endsWith("\n")) put = "gestVendas.dat";
                    rand.add(0,1);
                    rand.add(1,put);
                    r = false;
                    break;
                case 2:
                    System.out.println("Insira o nome do ficheiro a carregar(Prima Enter para gestVendas.dat):");
                    put = Input.lerString();
                    if (put.endsWith("\n")) put = "gestVendas.dat";
                    rand.add(0,2);
                    rand.add(1,put);
                    r = false;
                    break;    
                default:
                    break;
            }
        }
        return rand;
    }

    private void sair(){
        System.out.println("Prima Enter para sair.");
        String x = Input.lerString();
        while(x.endsWith("\n")){
            x = Input.lerString();
        }
    }
}

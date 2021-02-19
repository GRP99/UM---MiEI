import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Queries{

    public void queryEstatistica(InterfGereVendasModel model){
        Estatistica estat = model.getEstatistica();
        System.out.println(estat.toString());
        sair();
    }

    public void query1(InterfGereVendasModel model){
        int tamanho, aux=0;
        Crono.start();
        Filiais fi = model.getFilial();
        List<String> naocomprados = fi.getProdutosNaoComprados();
        tamanho = naocomprados.size();
        Collections.sort(naocomprados);
        Crono.stop();
        System.out.println("Tempo: " + Crono.print());
        sairT();
        if (tamanho == 0){
            System.out.println("Todos os produtos foram comprados.");
            sair();
        }
        else printQ1(naocomprados,aux,aux,tamanho);
    }

    public void query2(InterfGereVendasModel model, int mes){
        Crono.start();
        Filiais fi = model.getFilial();
        Facturacao fact = model.getFacturacao();
        int clientes = fi.getCliM(mes);
        int vendas = fact.vendasMensais(mes);
        Crono.stop();
        System.out.println("Tempo: " + Crono.print());
        sairT();
        System.out.println("Número total de vendas realizadas no mês " + mes + " foi de " + vendas);
        System.out.println("Número total de clientes distintos que as fizeram foram " + clientes);
        sair();
    }

    public void query3(InterfGereVendasModel model, String codigoC) throws ClienteInexistente, CloneNotSupportedException{
        int i=0;
        CatalogoClientes cc = model.getClientes();
        Filiais fi = model.getFilial();
        Cliente cli = new Cliente(codigoC);
        if (cc.existeCliente(cli.clone())){
            Crono.start();
            int [] c = new int [12];
            int [] pd = new int [12];
            int [] gt = new int [12];
            c = fi.compras(cli.clone());
            pd = fi.produtosdistintos(cli.clone());
            gt = fi.gastoutotal(cli.clone());
            Crono.stop();
            System.out.println("Tempo: " + Crono.print());
            sairT();
            System.out.println("Número de Compras:");
            for(i=0;i<12;i++){
                System.out.print("| Mês" + (i+1) + "->" + c[i] + " |") ;
            }
            System.out.println("\n");
            System.out.println("Produtos Distintos:");
            for(i=0;i<12;i++){
                System.out.print("| Mês" + (i+1) + "->" + pd[i] + " |") ;
            }
            System.out.println("\n");
            System.out.println("Gastou:");
            for(i=0;i<12;i++){
                System.out.print("| Mês" + (i+1) + "->" + gt[i] + " |") ;
            }
            System.out.println("\n");
            sair();
        }
        else{
            System.out.println("Cliente não existe.");
        }
    }

    public void query4(InterfGereVendasModel model, String codigoP) throws ProdutoInexistente, CloneNotSupportedException{
        int i=0;
        CatalogoProdutos cp = model.getProdutos();
        Filiais fi = model.getFilial();
        Produto pro = new Produto(codigoP);
        if (cp.existeProduto(pro)){
            Crono.start();
            int [] nc = new int [12];
            int [] cf = new int [12];
            double [] tf = new double [12];
            nc = fi.comprado(pro.clone());
            cf = fi.clientesdiferentes(pro.clone());
            tf = fi.totalfacturado(pro.clone());
            Crono.stop();
            System.out.println("Tempo: " + Crono.print());
            sairT();
            System.out.println("\n");
            System.out.println("Número de vezes comprado:");
            for(i=0;i<12;i++){
                System.out.print("| Mês" + (i+1) + "->" + nc[i] + " |") ;
            }
            System.out.println("\n");
            System.out.println("Clientes diferentes:");
            for(i=0;i<12;i++){
                System.out.print("| Mês" + (i+1) + "->" + cf[i] + " |") ;
            }
            System.out.println("\n");
            System.out.println("Total facturado:");
            for(i=0;i<12;i++){
                System.out.print("| Mês" + (i+1) + "->" + tf[i] + " |") ;
            }
            System.out.println("\n");
            sair();
        }
        else{
            System.out.println("Produto não existe.");
        }
    }

    public void query5(InterfGereVendasModel model, String codigoC) throws ClienteInexistente, CloneNotSupportedException{
        int aux=0,tamanho=0;
        CatalogoClientes cc = model.getClientes();
        Filiais fi = model.getFilial();
        Cliente cli = new Cliente(codigoC);
        if (cc.existeCliente(cli)){
            Crono.start();
            Set<ParProdNumClis> treepmc = fi.listaProdutos(cli.clone());
            Crono.stop();
            System.out.println("Tempo: " + Crono.print());
            sairT();
            List<ParProdNumClis> array = new ArrayList<>();
            for(ParProdNumClis pmc : treepmc){
                array.add(pmc.clone());
            } 
            tamanho = array.size();
            printQ5(array,aux,aux,tamanho);
        }
        else{
            System.out.println("Cliente não existe.");
        }
    }

    public void query6(InterfGereVendasModel model, int valor) throws CloneNotSupportedException{
        int tam, aux=0;
        List<Integer> comp = new ArrayList<>();
        Crono.start();
        Filiais fi = model.getFilial();
        Facturacao fact = model.getFacturacao();
        List<ParProdNumClis> lista = fact.listaXProd(valor);
        lista.forEach((v) ->  {
                try {
                    comp.add(fi.quantCompradores(v.getCodigo()));
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        Crono.stop();
        System.out.println("Tempo: " + Crono.print() );
        sairT();
        tam = lista.size();
        printQ6(lista,comp,aux,aux,tam);    
    }

    public void query7(InterfGereVendasModel model){
        int x = 0;
        Crono.start();
        Filiais fi = model.getFilial();
        Set<ParProdNumClis> p1 = fi.maiscomprasF1();
        Set<ParProdNumClis> p2 = fi.maiscomprasF2();
        Set<ParProdNumClis> p3 = fi.maiscomprasF3();
        Crono.stop();
        System.out.println("Tempo: " + Crono.print());
        sairT();
        System.out.println("Três Maiores Compradores da Filial 1: ");
        Iterator<ParProdNumClis> iterator1 = p1.iterator();
        while (iterator1.hasNext() && x<3) {
            ParProdNumClis pmc = iterator1.next();
            System.out.println("Cliente : " + pmc.getCodigo() + " -> Valor Gasto: " + pmc.getTotal());pmc.toString();
            x++;
        }
        x=0;
        System.out.print("\n");
        System.out.println("Três Maiores Compradores da Filial 2: ");
        Iterator<ParProdNumClis> iterator2 = p2.iterator();
        while (iterator2.hasNext() && x<3) {
            ParProdNumClis pmc = iterator2.next();
            System.out.println("Cliente : " + pmc.getCodigo() + " -> Valor Gasto: " + pmc.getTotal());
            x++;
        }
        x=0;
        System.out.print("\n");
        System.out.println("Três Maiores Compradores da Filial 3: ");
        Iterator<ParProdNumClis> iterator3 = p3.iterator();
        while (iterator3.hasNext() && x<3) {
            ParProdNumClis pmc = iterator3.next();
            System.out.println("Cliente : " + pmc.getCodigo() + " -> Valor Gasto: " + pmc.getTotal());
            x++;
        }
        sair();
    }

    public void query8(InterfGereVendasModel model, int valor) throws CloneNotSupportedException{
        int x=0, tam=0;
        Crono.start();
        Filiais fi = model.getFilial();
        List<ParProdNumClis> pmc = new ArrayList<>();
        pmc = fi.compradoresDif(x);
        Crono.stop();
        System.out.println("Tempo: " + Crono.print());
        sairT();
        tam = pmc.size();
        printQ8(pmc,x,x,tam);
    }

    public void query9(InterfGereVendasModel model, String codigo, int valor) throws ProdutoInexistente, CloneNotSupportedException{
        int tam = 0, x = 0;
        CatalogoProdutos cp = model.getProdutos();
        Filiais fi = model.getFilial();
        Produto pro = new Produto(codigo);
        if (cp.existeProduto(pro)){
            Crono.start();
            List<ParProdNumClis> lista = fi.listaCli(pro.clone(),valor);
            Crono.stop();
            System.out.println("Tempo: " + Crono.print());
            sairT();
            tam = lista.size();
            printQ9(lista,x,x,tam);
        }
        else System.out.println("Produto não existe.");
    }

    private void printQ1(List<String> nc, int cont, int ind, int tam){
        int o,i;
        System.out.print('\u000C');
        System.out.println("####################### GestVendasAppMVC #######################");
        for (i=0; i<13 && ind < tam; i++){
            System.out.println("Produto: " + nc.get(ind));
            cont++;
            ind++;
        }
        System.out.println("################################################################");
        System.out.println("# TOTAL:  " + tam + "                                          #");
        System.out.println("################################################################");
        System.out.println("# 1. Continuar.                                                #");
        System.out.println("# 2. Retroceder.                                               #");
        System.out.println("# 0. Sair.                                                     #");
        System.out.println("################################################################");
        System.out.println(">>>");
        o = Input.lerInt();
        if(o > 0){
            if(o == 1 && cont != tam) printQ1(nc, cont, ind++,tam);
            if(o == 1 && cont == tam){
                System.out.println("Impossível continuar.\n");
                printQ1(nc, cont-i, ind-i,tam);
            }
            if(o == 2 && (cont-13) > 0) printQ1(nc, cont-26, ind-26,tam);
            if(o == 2 && (cont-13) <= 0){
                System.out.println("Impossível retroceder.\n");
                printQ1(nc, 0, 0,tam);
            }
        }
        else if (o == 0) sair();
        else{
            System.out.println("Comando inválido\n");
            printQ1(nc, cont-i, ind-i,tam);
        }   
    }

    private void printQ5(List<ParProdNumClis> array, int cont, int ind, int tam){
        int o,i;
        System.out.print('\u000C');
        System.out.println("####################### GestVendasAppMVC #######################");
        for (i=0; i<13 && ind < tam; i++){
            System.out.println("Produto: " + array.get(ind).getCodigo() + "\n" + "Quantidade: "+ array.get(ind).getTotal() + "\n");
            cont++;
            ind++;
        }
        System.out.println("################################################################");
        System.out.println("# TOTAL:  " + tam + "                                          #");
        System.out.println("################################################################");
        System.out.println("# 1. Continuar.                                                #");
        System.out.println("# 2. Retroceder.                                               #");
        System.out.println("# 0. Sair.                                                     #");
        System.out.println("################################################################");
        System.out.println(">>>");
        o = Input.lerInt();
        if(o > 0){
            if(o == 1 && cont != tam) printQ5(array, cont, ind++,tam);
            if(o == 1 && cont == tam){
                System.out.println("Impossível continuar.\n");
                printQ5(array, cont-i, ind-i,tam);
            }
            if(o == 2 && (cont-13) > 0) printQ5(array, cont-26, ind-26,tam);
            if(o == 2 && (cont-13) <= 0){
                System.out.println("Impossível retroceder.\n");
                printQ5(array, 0, 0,tam);
            }
        }
        else if (o == 0) sair();
        else{
            System.out.println("Comando inválido\n");
            printQ5(array, cont-i, ind-i,tam);
        }   
    }

    private void printQ6(List<ParProdNumClis> lista, List<Integer> comp, int cont, int ind, int tam){
        int o,i;
        System.out.print('\u000C');
        System.out.println("####################### GestVendasAppMVC #######################");
        for (i=0; i<13 && ind < tam; i++){
            System.out.println(lista.get(ind).toString() + "Clientes Distintos: " + comp.get(ind) + "\n");
            cont++;
            ind++;
        }
        System.out.println("################################################################");
        System.out.println("# TOTAL:  " + tam + "                                          #");
        System.out.println("################################################################");
        System.out.println("# 1. Continuar.                                                #");
        System.out.println("# 2. Retroceder.                                               #");
        System.out.println("# 0. Sair.                                                     #");
        System.out.println("################################################################");
        System.out.println(">>>");
        o = Input.lerInt();
        if(o > 0){
            if(o == 1 && cont != tam) printQ6(lista,comp,cont,ind++,tam);
            if(o == 1 && cont == tam){
                System.out.println("Impossível continuar.\n");
                printQ6(lista,comp,cont-i,ind-i,tam);
            }
            if(o == 2 && (cont-13) > 0) printQ6(lista,comp,cont-26,ind-26,tam);
            if(o == 2 && (cont-13) <= 0){
                System.out.println("Impossível retroceder.\n");
                printQ6(lista,comp,0,0,tam);
            }
        }
        else if (o == 0) sair();
        else{
            System.out.println("Comando inválido\n");
            printQ6(lista,comp,cont-i,ind-i,tam);
        }
    }

    private void printQ8(List<ParProdNumClis> lista, int cont, int ind, int tam){
        int o,i;
        System.out.print('\u000C');
        System.out.println("####################### GestVendasAppMVC #######################");
        for (i=0; i<13 && ind < tam; i++){
            System.out.println("Cliente: " + lista.get(ind).getCodigo() + " -> Número de Produtos Comprados: "+ lista.get(ind).getTotal());
            cont++;
            ind++;
        }
        System.out.println("################################################################");
        System.out.println("# TOTAL:  " + tam + "                                          #");
        System.out.println("################################################################");
        System.out.println("# 1. Continuar.                                                #");
        System.out.println("# 2. Retroceder.                                               #");
        System.out.println("# 0. Sair.                                                     #");
        System.out.println("################################################################");
        System.out.println(">>>");
        o = Input.lerInt();
        if(o > 0){
            if(o == 1 && cont != tam) printQ8(lista,cont,ind++,tam);
            if(o == 1 && cont == tam){
                System.out.println("Impossível continuar.\n");
                printQ8(lista,cont-i,ind-i,tam);
            }
            if(o == 2 && (cont-13) > 0) printQ8(lista,cont-26,ind-26,tam);
            if(o == 2 && (cont-13) <= 0){
                System.out.println("Impossível retroceder.\n");
                printQ8(lista,0,0,tam);
            }
        }
        else if (o == 0) sair();
        else{
            System.out.println("Comando inválido\n");
            printQ8(lista,cont-i,ind-i,tam);
        }
    }

    private void printQ9(List<ParProdNumClis> lista, int cont, int ind, int tam){
        int o,i;
        System.out.print('\u000C');
        System.out.println("####################### GestVendasAppMVC #######################");
        for (i=0; i<13 && ind < tam; i++){
            System.out.println("Cliente: " + lista.get(ind).getCodigo() + " -> Valor gasto: "+ lista.get(ind).getTotal());
            cont++;
            ind++;
        }
        System.out.println("################################################################");
        System.out.println("# TOTAL:  " + tam + "                                          #");
        System.out.println("################################################################");
        System.out.println("# 1. Continuar.                                                #");
        System.out.println("# 2. Retroceder.                                               #");
        System.out.println("# 0. Sair.                                                     #");
        System.out.println("################################################################");
        System.out.println(">>>");
        o = Input.lerInt();
        if(o > 0){
            if(o == 1 && cont != tam) printQ9(lista,cont,ind++,tam);
            if(o == 1 && cont == tam){
                System.out.println("Impossível continuar.\n");
                printQ9(lista,cont-i,ind-i,tam);
            }
            if(o == 2 && (cont-13) > 0) printQ9(lista,cont-26,ind-26,tam);
            if(o == 2 && (cont-13) <= 0){
                System.out.println("Impossível retroceder.\n");
                printQ9(lista,0,0,tam);
            }
        }
        else if (o == 0) sair();
        else{
            System.out.println("Comando inválido\n");
            printQ9(lista,cont-i,ind-i,tam);
        }
    }

    private void sairT(){
        System.out.println("Prima Enter para apresentar resultados");
        String c=Input.lerString();
        while(c.endsWith("\n")){
            c = Input.lerString();
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

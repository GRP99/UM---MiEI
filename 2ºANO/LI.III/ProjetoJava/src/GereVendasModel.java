import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import static java.lang.System.out;

public class GereVendasModel implements InterfGereVendasModel,Serializable{
    private CatalogoClientes clientes;
    private CatalogoProdutos produtos;
    private Facturacao facturacao;
    private Filiais filial;
    private Estatistica estatistica;

    public GereVendasModel(){
        clientes = new CatalogoClientes();
        produtos = new CatalogoProdutos();
        facturacao = new Facturacao();
        filial = new Filiais();
        estatistica = new Estatistica();
    }

    public GereVendasModel(CatalogoClientes c, CatalogoProdutos p, Facturacao f, Filiais g, Estatistica e) throws CloneNotSupportedException{
        this.clientes = c;
        this.produtos = p;
        this.facturacao = f;
        this.filial = g;
        this.estatistica = e;
    }

    public GereVendasModel(GereVendasModel sm) {
        clientes = sm.getClientes();
        produtos = sm.getProdutos();
        facturacao = sm.getFacturacao();
        filial = sm.getFilial();
        estatistica = sm.getEstatistica();
    }

    public CatalogoClientes getClientes() { return clientes; }

    public CatalogoProdutos getProdutos() {return produtos;}

    public Facturacao getFacturacao() {return facturacao;}

    public Filiais getFilial() {return filial;}

    public Estatistica getEstatistica() {return estatistica;}

    public void setClientes(CatalogoClientes c) {this.clientes = c;}

    public void setProdutos(CatalogoProdutos p) {this.produtos = p;}

    public void setFacturacao(Facturacao f) {this.facturacao = f;}

    public void setFilial(Filiais f) {this.filial = f;}

    public void setEstatistica(Estatistica e) {this.estatistica = e;}

    public void createData() throws CloneNotSupportedException, IOException, ClassNotFoundException, ClienteInexistente , ProdutoInexistente{
        int r;
        Scanner input = new Scanner(System.in);;
        System.out.print('\u000C');
        System.out.println("################## GestVendasAppMVC #######################");
        System.out.println("Ficheiro para clientes? (não se esqueça da terminação .txt)");
        String fc = input.nextLine();;
        System.out.println("Ficheiro para produtos? (não se esqueça da terminação .txt)");
        String fp = input.nextLine();
        System.out.println("Ficheiro para vendas? (não se esqueça da terminação .txt)");
        String fv = input.nextLine();
        Crono.start();
        lerClientes(fc);
        lerProdutos(fp);
        lerVendas(fv);
        Crono.stop();
        System.out.println("Tempo: " + Crono.print() + ".");
        System.out.println("Leitura dos ficheiros efectuada com sucesso.");
        sair();
    }

    private ArrayList<String> readFileBuffered(String fich) {
        ArrayList<String> linhas = new ArrayList<>();
        BufferedReader inStream ;
        String linha ;
        try {
            inStream = new BufferedReader(new FileReader(fich));
            while ((linha = inStream.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            out.println(e.getMessage());
            return null;
        }
        return linhas;
    }

    public void lerClientes(String fc) throws CloneNotSupportedException{
        ArrayList<String> codigos;
        codigos = readFileBuffered(fc);
        for(String cod: codigos){
            Cliente cli = new Cliente(cod);
            clientes.adicionaCliente(cli.clone());
            filial.adicionaCliente(cli.clone());
        }
    }

    public void lerProdutos(String fp) throws CloneNotSupportedException{
        ArrayList<String> codigos;
        codigos = readFileBuffered(fp);
        for(String cod: codigos){
            Produto pro = new Produto(cod);
            produtos.adicionaProduto(pro.clone());
            filial.adicionaProduto(pro.clone());
            facturacao.adicionaProduto(pro.clone());
        }
    }

    public void lerVendas(String fv) throws IOException, CloneNotSupportedException{
        Venda v;
        int precozero = 0; int errados=0; int total=0;
        ArrayList<String> codigos;
        codigos = readFileBuffered(fv);
        for(String c : codigos){
            String[] partes = c.split(" ");
            Produto pro = new Produto(partes[0]);
            double preco = Double.parseDouble(partes[1]);
            int quantidade = Integer.parseInt(partes[2]);
            String tipo = partes[3];
            Cliente cli = new Cliente(partes[4]);
            int mes = Integer.parseInt(partes[5]);
            int filial = Integer.parseInt(partes[6]);
            if (testeVenda(pro,preco,quantidade,cli)){
                if(preco == (0.0)){
                    precozero = precozero + 1;
                }
                v = new Venda(cli.clone(),pro.clone(),tipo,filial,quantidade,mes,preco);
                insereVenda(v.clone());
            }
            else errados++;
        }
        int tot = codigos.size();
        insereEstatistica(fv,precozero,errados,tot);
    }

    private boolean testeVenda(Produto pro, double preco, int quantidade, Cliente cli) throws CloneNotSupportedException {
        Boolean r = false;
        if (clientes.existeCliente(cli.clone())){
            if(produtos.existeProduto(pro.clone())){
                if ((preco>=0.0) && (quantidade>=0)) r=true;
            }
        }
        return r;
    }

    private void insereVenda(Venda v) throws CloneNotSupportedException {
        facturacao.adicionaFaturacao(v.clone());
        filial.adicionaCompra(v.clone());        
    }

    private void insereEstatistica(String fV, int precozero, int e, int t){
        int i = 0;
        int tClientes = clientes.total();
        int tProduto = produtos.total();
        int cc = filial.getTCCompram();
        int pdistintosC = filial.getTPDiferentes();
        int tpnc = facturacao.totalProdutoNaoComprados();
        estatistica.setFicheiro(fV);
        estatistica.setTotal(t);
        estatistica.setTRegistosErrados(e);
        estatistica.setTProdutos(tProduto);
        estatistica.setTProdutosDiferentes(pdistintosC);
        estatistica.setTProdutosNComprados(tpnc);
        estatistica.setTClientes(tClientes);
        estatistica.setTClientesCompraram(cc);
        estatistica.setClientesNCompraram(tClientes-cc);
        estatistica.setTCompras0(precozero);
        estatistica.setFacturacaoT(facturacao.getTFacturado());
        for(i=0; i<12; i++){
            estatistica.setTComprasM(i,facturacao.vendasMensais(i));
            estatistica.setTFacturacaoF1(i,facturacao.totalFacturadoMensalF1(i));
            estatistica.setTFacturacaoF2(i,facturacao.totalFacturadoMensalF2(i));
            estatistica.setTFacturacaoF3(i,facturacao.totalFacturadoMensalF3(i));
            estatistica.setTFacturacaoM(i,facturacao.totalFacturadoMensal(i));
            estatistica.setTClientesDM(i,filial.getCompradoresM(i));
        }
    }

    private void guarda(String ficheiro) throws FileNotFoundException,IOException,CloneNotSupportedException{
        InterfGestVendas gv = new GestVendas(this.clientes,this.produtos,this.facturacao,this.filial,this.estatistica);
        FileOutputStream fos = new FileOutputStream(ficheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(gv); 
        oos.flush();
        oos.close();
    }

    public void guardaEstado(String ficheiro) throws CloneNotSupportedException{
        try {
            Crono.start();
            guarda(ficheiro);
            Crono.stop();
            System.out.println("Tempo: " + Crono.print() + ".");
            System.out.println("Dados gravados com sucesso!");
            sair();
        }
        catch (IOException e) {
            System.out.println("Ops! Não consegui gravar os dados!");
        }
    }

    private InterfGestVendas carrega(String ficheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(ficheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        InterfGestVendas h = (InterfGestVendas) ois.readObject();
        ois.close();
        return h;
    }

    public void carregaEstado(String ficheiro) throws IOException, ClassNotFoundException{
        InterfGestVendas gv = new GestVendas();
        try {
            Crono.start();
            gv = carrega(ficheiro);
            this.clientes = gv.getClientes();
            this.produtos = gv.getProdutos();
            this.facturacao = gv.getFacturacao();
            this.filial = gv.getFilial();
            this.estatistica = gv.getEstatistica();
            Crono.stop();
            System.out.println("Tempo: " + Crono.print() + ".");
            System.out.println("Dados carregados com sucesso!");
            sair();
        }
        catch (FileNotFoundException e) {
            System.out.println("Não consegui encontrar o ficheiro...");
            this.clientes = new CatalogoClientes();
            this.produtos = new CatalogoProdutos();
            this.facturacao = new Facturacao();
            this.filial = new Filiais();
            this.estatistica = new Estatistica();
        }
        catch (IOException e) {
            System.out.println("Ops! Erro de leitura!");
            this.clientes = new CatalogoClientes();
            this.produtos = new CatalogoProdutos();
            this.facturacao = new Facturacao();
            this.filial = new Filiais();
            this.estatistica = new Estatistica();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Ops! Formato de ficheiro de dados errado!");
            this.clientes = new CatalogoClientes();
            this.produtos = new CatalogoProdutos();
            this.facturacao = new Facturacao();
            this.filial = new Filiais();
            this.estatistica = new Estatistica();
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
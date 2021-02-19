import java.io.Serializable;
import java.util.Objects;
import java.util.Arrays;

public class Estatistica implements Serializable {
    private String ultimoficheiroVL; /* De vendas lido*/
    private int total;
    private int totalRegistosErrados;
    private int totalProdutos;
    private int totalProdutosDiferentes;
    private int totalProdutosNComprados;
    private int totalClientes;
    private int totalClientesCompraram;
    private int totalClientesNCompraram;
    private int totalCompras0;
    private double facturacaoTotal;
    private int[] totalComprasMes;
    private double[] totalFacturacaoF1;
    private double[] totalFacturacaoF2;
    private double[] totalFacturacaoF3;
    private double[] totalFacturacaoMensal;
    private int[] totalClientesDistintosMes;

    public Estatistica() {
        this.ultimoficheiroVL = "";
        this.total = 0;
        this.totalRegistosErrados = 0;
        this.totalProdutos = 0;
        this.totalProdutosDiferentes = 0;
        this.totalProdutosNComprados = 0;
        this.totalClientes = 0;
        this.totalClientesCompraram = 0;
        this.totalClientesNCompraram = 0;
        this.totalCompras0 = 0;
        this.facturacaoTotal = 0.0;
        this.totalComprasMes = new int[12];
        this.totalFacturacaoF1 = new double[12];
        this.totalFacturacaoF2 = new double[12];
        this.totalFacturacaoF3 = new double[12];
        this.totalFacturacaoMensal = new double[12];
        this.totalClientesDistintosMes = new int[12];
    }

    public Estatistica(String ficheiro, int tot, int registosErrados, int produtos, int produtosDiferentes, int naoComprados, int clientes, int clientesCompradores, int clientesNaoCompradores, int comprasZero, double facturacao, int[] comprasMes, double[] facturacaoF1, double[] facturacaoF2, double[] facturacaoF3, double[] facturacaoMensal, int[] clientesDistintos) {
        this.ultimoficheiroVL = ficheiro;
        this.total = tot;
        this.totalRegistosErrados = registosErrados;
        this.totalProdutos = produtos;
        this.totalProdutosDiferentes = produtosDiferentes;
        this.totalProdutosNComprados = naoComprados;
        this.totalClientes = clientes;
        this.totalClientesCompraram = clientesCompradores;
        this.totalClientesNCompraram = clientesNaoCompradores;
        this.totalCompras0 = comprasZero;
        this.facturacaoTotal = facturacao;
        this.totalComprasMes = comprasMes.clone();
        this.totalFacturacaoF1 = facturacaoF1.clone();
        this.totalFacturacaoF2 = facturacaoF2.clone();
        this.totalFacturacaoF3 = facturacaoF3.clone();
        this.totalFacturacaoMensal = facturacaoMensal.clone();
        this.totalClientesDistintosMes = clientesDistintos.clone();
    }

    public Estatistica(Estatistica e) {
        this.ultimoficheiroVL = e.getFicheiro();
        this.total = e.getTotal();
        this.totalRegistosErrados = e.getTRegistosErrados();
        this.totalProdutos = e.getTProdutos();
        this.totalProdutosDiferentes = e.getTProdutosDiferentes();
        this.totalProdutosNComprados = e.getTProdutosNComprados();
        this.totalClientes = e.getTClientes();
        this.totalClientesCompraram = e.getTClientesCompraram();
        this.totalClientesNCompraram = e.getClientesNCompraram();
        this.totalCompras0 = e.getTCompras0();
        this.facturacaoTotal = e.getFacturacaoT();
        this.totalComprasMes = e.getTComprasM();
        this.totalFacturacaoF1 = e.getTFacturacaoF1();
        this.totalFacturacaoF2 = e.getTFacturacaoF2();
        this.totalFacturacaoF3 = e.getTFacturacaoF3();
        this.totalFacturacaoMensal = e.getTFacturacaoM();
        this.totalClientesDistintosMes = e.getTClientesDM();
    }

    public String getFicheiro() {return this.ultimoficheiroVL;}

    public int getTotal(){return this.total;}

    public int getTRegistosErrados() {return this.totalRegistosErrados;}

    public int getTProdutos() {return this.totalProdutos;}

    public int getTProdutosDiferentes() {return this.totalProdutosDiferentes;}

    public int getTProdutosNComprados() {return this.totalProdutosNComprados;}

    public int getTClientes() {return this.totalClientes;}

    public int getTClientesCompraram() {return this.totalClientesCompraram;}

    public int getClientesNCompraram() {return this.totalClientesNCompraram;}

    public int getTCompras0() {return this.totalCompras0;}

    public double getFacturacaoT() {return this.facturacaoTotal;}

    public int[] getTComprasM() {return this.totalComprasMes.clone();}

    public double[] getTFacturacaoF1() {return this.totalFacturacaoF1.clone();}

    public double[] getTFacturacaoF2() {return this.totalFacturacaoF2.clone();}

    public double[] getTFacturacaoF3() {return this.totalFacturacaoF3.clone();}

    public double[] getTFacturacaoM() {return this.totalFacturacaoMensal.clone();}

    public int[] getTClientesDM() {return this.totalClientesDistintosMes.clone();}

    public void setFicheiro(String f) {this.ultimoficheiroVL = f;}

    public void setTotal(int t){this.total = t;}

    public void setTRegistosErrados(int r) {this.totalRegistosErrados = r;}

    public void setTProdutos(int p) {this.totalProdutos = p;}

    public void setTProdutosDiferentes(int p) {this.totalProdutosDiferentes = p;}

    public void setTProdutosNComprados(int p) {this.totalProdutosNComprados = p;}

    public void setTClientes(int c) {this.totalClientes = c;}

    public void setTClientesCompraram(int c) {this.totalClientesCompraram = c;}

    public void setClientesNCompraram(int c) {this.totalClientesNCompraram = c;}

    public void setTCompras0(int c) {this.totalCompras0 = 0;}

    public void setFacturacaoT(double f) {this.facturacaoTotal = f;}

    public void setTComprasM(int m, int c) {this.totalComprasMes[m] = c;}

    public void setTFacturacaoF1(int m, double f) {this.totalFacturacaoF1[m] = f;}

    public void setTFacturacaoF2(int m, double f) {this.totalFacturacaoF2[m] = f;}

    public void setTFacturacaoF3(int m, double f) {this.totalFacturacaoF3[m] = f;}

    public void setTFacturacaoM(int m, double f) {this.totalFacturacaoMensal[m] = f;}

    public void setTClientesDM(int m, int c) {this.totalClientesDistintosMes[m] = c;}

    public Estatistica clone() throws CloneNotSupportedException {
        return new Estatistica(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Último ficheiro de vendas lido: ");
        sb.append(this.ultimoficheiroVL);
        sb.append("\n");
        sb.append("Total de linhas lidas: ");
        sb.append(this.total);
        sb.append("\n");
        sb.append("Número total de registos de venda errados: ");
        sb.append(this.totalRegistosErrados);
        sb.append("\n");
        sb.append("Número total de produtos: ");
        sb.append(this.totalProdutos);
        sb.append("\n");
        sb.append("Total de diferentes produtos comprados: ");
        sb.append(this.totalProdutosDiferentes);
        sb.append("\n");
        sb.append("Total de produtos não comprados: ");
        sb.append(this.totalProdutosNComprados);
        sb.append("\n");
        sb.append("Número total de clientes: ");
        sb.append(this.totalClientes);
        sb.append("\n");
        sb.append("Total de clientes dos que realizaram compras: ");
        sb.append(this.totalClientesCompraram);
        sb.append("\n");
        sb.append("Total de clientes que nada compraram: ");
        sb.append(this.totalClientesNCompraram);
        sb.append("\n");
        sb.append("Total de compras de valor igual a 0.0: ");
        sb.append(this.totalCompras0);
        sb.append("\n");
        sb.append("Facturação global: ");
        sb.append(this.facturacaoTotal);
        sb.append("\n");
        sb.append("Número total de compras por mês: ");
        for (int i = 0; i < 12; i++) {
            sb.append(this.getTComprasM()[i]).append(" ");
        }
        sb.append("\n");
        sb.append("Facturação total por mês pela filial1: ");
        for (int i = 0; i < 12; i++) {
            sb.append(this.getTFacturacaoF1()[i]).append(" ");
        }
        sb.append("\n");
        sb.append("Facturação total por mês pela filial2: ");
        for (int i = 0; i < 12; i++) {
            sb.append(this.getTFacturacaoF2()[i]).append(" ");
        }
        sb.append("\n");
        sb.append("Facturação total por mês pela filial3: ");
        for (int i = 0; i < 12; i++) {
            sb.append(this.getTFacturacaoF3()[i]).append(" ");
        }
        sb.append("\n");
        sb.append("Valor total global: ");
        for (int i = 0; i < 12; i++) {
            sb.append(this.getTFacturacaoM()[i]).append(" ");
        }
        sb.append("\n");
        sb.append("Número de distintos clientes que compraram em cada mês: ");
        for (int i = 0; i < 12; i++) {
            sb.append(this.getTClientesDM()[i]).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }

    public boolean equals(Object obj){
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final Estatistica other = (Estatistica) obj;
        if (!Objects.equals(this.ultimoficheiroVL, other.ultimoficheiroVL)) {return false;}
        if(this.total != other.total){return false;}
        if (this.totalRegistosErrados != other.totalRegistosErrados){return false;}
        if (this.totalProdutos != other.totalProdutos){return false;}
        if (this.totalProdutosDiferentes != other.totalProdutosDiferentes){return false;}
        if (this.totalProdutosNComprados != other.totalProdutosNComprados){return false;}
        if (this.totalClientes != other.totalClientes){return false;}
        if (this.totalClientesCompraram != other.totalClientesCompraram){return false;}
        if (this.totalClientesNCompraram != other.totalClientesNCompraram){return false;}
        if (this.totalCompras0 != other.totalCompras0){return false;}
        if (Double.doubleToLongBits(this.facturacaoTotal) != Double.doubleToLongBits(other.facturacaoTotal)) {return false;}
        if (!Arrays.equals(this.totalComprasMes, other.totalComprasMes)) {return false;}
        if (!Arrays.equals(this.totalFacturacaoF1, other.totalFacturacaoF1)) {return false;}
        if (!Arrays.equals(this.totalFacturacaoF2, other.totalFacturacaoF2)) {return false;}
        if (!Arrays.equals(this.totalFacturacaoF3, other.totalFacturacaoF3)) {return false;}
        if (!Arrays.equals(this.totalFacturacaoMensal, other.totalFacturacaoMensal)) {return false;}
        if (!Arrays.equals(this.totalClientesDistintosMes, other.totalClientesDistintosMes)) {return false;}
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{ultimoficheiroVL, total, totalRegistosErrados, totalProdutos, totalProdutosDiferentes, totalProdutosNComprados, totalClientes, totalClientesCompraram, totalClientesNCompraram, totalCompras0, facturacaoTotal, totalComprasMes, totalFacturacaoF1, totalFacturacaoF2, totalFacturacaoF3, totalFacturacaoMensal, totalClientesDistintosMes});
    }    
}

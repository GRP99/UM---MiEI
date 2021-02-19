public class Venda{
    private Cliente cliente;
    private Produto produto;
    private String  tipo;
    private int filial;
    private int quantidade;
    private int mes;
    private double preco;

    public Venda(){
        this.cliente = new Cliente();
        this.produto = new Produto();
        this.tipo = "";
        this.filial = 0;
        this.quantidade = 0;
        this.mes = 0;
        this.preco = 0.0;
    }

    public Venda(Cliente c, Produto pr, String t, int f, int q, int m, double p){
        this.cliente = c;
        this.produto = pr;
        this.tipo = t;
        this.filial = f;
        this.quantidade = q;
        this.mes = m;
        this.preco = p;
    }

    public Venda(Venda v){
        this.cliente = v.getCliente();
        this.produto = v.getProduto();
        this.tipo = v.getTipo();
        this.filial = v.getFilial();
        this.quantidade = v.getQuantidade();
        this.mes = v.getMes();
        this.preco = v.getPreco();
    }

    public Cliente getCliente(){return cliente;}

    public Produto getProduto(){return produto;}

    public String getTipo(){return tipo;}

    public int getFilial(){return filial;}

    public int getQuantidade(){return quantidade;}

    public int getMes(){return mes;}

    public double getPreco(){return preco;}

    public void setCliente(Cliente c){this.cliente = c;}

    public void setProduto(Produto p){this.produto = p;}

    public void setTipo(String t){this.tipo = t;}

    public void setFilial(int f){this.filial = f;}

    public void setQuantidade(int q){this.quantidade = q;}

    public void setMes(int m){this.mes = m;}

    public void setPreco(double p){this.preco = p;}

    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append("VENDA: ");
        st.append("cliente=").append(cliente);
        st.append(", produto=").append(produto);
        st.append(", preço=").append(preco);
        st.append(", quantidade=").append(quantidade);
        st.append(", mes=").append(mes);
        st.append(", tipo=").append(tipo);
        st.append(", filial=").append(filial);
        return st.toString();    
    }

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        Venda venda = (Venda) obj;
        return this.cliente.equals(venda.getCliente()) && this.produto.equals(venda.getProduto()) && (this.tipo.equals(venda.getTipo()) && (this.preco == venda.getPreco()) && (this.quantidade == venda.getQuantidade()) && (this.mes == venda.getMes()) && (this.filial == venda.getFilial()));
    }

    public Venda clone() throws CloneNotSupportedException{
        return new Venda(this);
    }
}

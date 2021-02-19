import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.Iterator;

public class Encomenda {
    private String nomeCliente;
    private int nif;
    private String morada;
    private int nEnc;
    private LocalDate data;
    private LinhaEncomenda[] linhas;
    private int used;
    private int capacity;

    public Encomenda(String nomeCliente, int nif, String morada, int nEnc, LocalDate data, LinhaEncomenda[] linhasEnc) {
        this.nomeCliente = nomeCliente;
        this.nif = nif;
        this.morada = morada;
        this.nEnc = nEnc;
        this.data = data;
        this.linhas = linhasEnc.clone();
        this.used = linhasEnc.length;
        this.capacity = linhasEnc.length;
    }

    public Encomenda() {
        this.used = 0;
        this.capacity = 10;
        this.linhas = new LinhaEncomenda[capacity];
        this.nomeCliente = "n/a";
        this.nif = 0;
        this.morada = "n/a";
        this.nEnc = 0;
        this.data = LocalDate.now();
    }

    public Encomenda(Encomenda enc) {
        this.linhas = enc.getLinhas();
        this.used = this.capacity = linhas.length;
        this.nomeCliente = enc.getNomeCliente();
        this.nif = enc.getNif();
        this.morada = enc.getMorada();
        this.nEnc = enc.getNEnc();
        this.data = enc.getData();
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public int getNif() {
        return this.nif;
    }

    public String getMorada() {
        return this.morada;
    }

    public int getNEnc() {
        return this.nEnc;
    }    

    public LocalDate getData() {
        return this.data;
    }

    public LinhaEncomenda[] getLinhas() {
        return this.linhas.clone();        
    }
    
    public void setEncomendas(LinhaEncomenda[] linhasEnc) {
        int i = 0;
        this.linhas = new LinhaEncomenda[linhasEnc.length];
        for(LinhaEncomenda le : linhasEnc) {
            this.linhas[i] = le.clone();
            i++;
        }
        this.used = i;
        this.capacity = linhasEnc.length;
    }

    public Encomenda clone() {
        return new Encomenda(this);
    }

    public boolean equals(Object o) {
        if(o==this) return true;
        if(o==null || o.getClass() != this.getClass()) return false;
        Encomenda e = (Encomenda)o;
        return nomeCliente.equals(e.getNomeCliente()) && nif == e.getNif() &&
        morada.equals(e.getMorada()) && nEnc == e.getNEnc() && 
        data.equals(e.getData()) && this.linhas.equals(e.getLinhas()); 
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Encomenda : [");
        sb.append("Número Encomenda: ").append(this.nEnc);
        sb.append("NomeCliente: ").append(this.nomeCliente);
        sb.append("NIF: ").append(this.nif);
        sb.append("Morada: ").append(this.morada);
        sb.append("Data Enc: ").append(this.data);
        sb.append(this.linhas.toString());
        return sb.toString();
    }

    public double calculaValorTotal() {
        double r = 0;
        for(LinhaEncomenda le : this.linhas) {
            r += le.calculaValorLinhaEnc();
        }
        return r;
    }

    public double calculaValorDesconto_E() {
        double r = 0;
        for(LinhaEncomenda le : this.linhas) {
            r += le.calculaValorDesconto();
        }
        return r;
    }

    public int numeroTotalProdutos() {
        int r = 0;
        for(LinhaEncomenda le : this.linhas) {
            r += le.getQuantidade();
        }
        return r;
    }

    public boolean existeProdutoEncomenda(String codProd) {
        int i=0;
        boolean existe = false;
        while(!existe && i<this.linhas.length) {
            if(this.linhas[i].getReferencia().equals(codProd)) existe = true;
            i++;
        }
        return existe;
    }

    public void adicionaLinha(LinhaEncomenda linha) {
        int i = 0;
        if (this.used == this.capacity){
            LinhaEncomenda[] copia = new LinhaEncomenda[this.capacity*2];
            while(i < capacity){
                copia[i] = this.linhas[i];
                i++;
            }
            this.linhas = copia.clone();
            this.linhas[i+1] = linha.clone();
            this.used++;
        }
        else{
            this.linhas[used] = linha.clone();
        }
    }

    public void removeProduto(String codProd){
    }
}

import java.io.*;

public class HotelPremium extends Hotel implements CartaoPontos, Serializable {
    private double taxa;

    public HotelPremium() {
        super();
        taxa = 1;
    }

    public HotelPremium(HotelPremium c) {
        super(c);
        this.taxa = c.getTaxa();
    }

    public HotelPremium(String codigo, String nome, String localidade, double precoBaseQuarto, double taxa, int nquartos, int estrelas) {
        super(codigo, nome, localidade, precoBaseQuarto , nquartos, estrelas);
        this.taxa = taxa;
    }

    public double precoNoite() {
        return getPrecoBaseQuarto()*taxa;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public HotelPremium clone() {
        return new HotelPremium(this);
    }

    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        HotelPremium o = (HotelPremium) obj;
        return super.equals(o) && o.getTaxa() == taxa;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Premium,");
        sb.append(super.toString());
        sb.append(this.taxa);
        return sb.toString();
    }

    private int pontos;
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    public int getPontos() {
        return this.pontos;
    }
}
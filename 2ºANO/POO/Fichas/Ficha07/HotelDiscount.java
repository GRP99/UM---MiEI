import java.io.*;

public class HotelDiscount extends Hotel implements Serializable {
    private double ocupacao;

    public HotelDiscount() {
        super();
        this.ocupacao = 0;
    }

    public HotelDiscount(HotelDiscount hotel) {
        super(hotel);
        this.ocupacao = hotel.getOcupacao();
    }

    public HotelDiscount(String codigo, String nome, String localidade, double precoBaseQuarto, int numQuartos, int estrelas, double ocupacao) {
        super(codigo, nome, localidade, precoBaseQuarto, numQuartos, estrelas);
        this.ocupacao = ocupacao;
    }

    public void setOcupacao(double ocupacao) {
        this.ocupacao = ocupacao;
    }

    public double getOcupacao() {
        return this.ocupacao;
    }

    public double precoNoite() {
        return getPrecoBaseQuarto() * 0.5 + getPrecoBaseQuarto() * 0.4 * ocupacao;
    }

    public HotelDiscount clone() {
        return new HotelDiscount(this);
    }

    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        HotelDiscount o = (HotelDiscount) obj;
        return super.equals(o) && o.getOcupacao() == this.ocupacao;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Discount,");
        sb.append(super.toString());
        sb.append(this.ocupacao);
        return sb.toString();
    }
}

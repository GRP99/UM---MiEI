import java.io.*;

public class HotelStandard extends Hotel implements CartaoPontos, Serializable {
    private boolean epocaAlta;

    public HotelStandard() {
        super();
        this.epocaAlta = false;
    }

    public HotelStandard(HotelStandard c) {
        super(c);
        this.epocaAlta = c.getEpocaAlta();
    }

    public HotelStandard(String codigo, String nome, String localidade, double precoBaseQuarto, boolean epocaAlta, int nquartos, int estrelas) {
        super(codigo, nome, localidade, precoBaseQuarto, nquartos, estrelas);
        this.epocaAlta = epocaAlta;
    }

    public double precoNoite() {
        return getPrecoBaseQuarto() + (this.epocaAlta?20:0);
    }

    public boolean getEpocaAlta() {
        return epocaAlta;
    }

    public void setEpocaAlta(boolean epocaAlta) {
        this.epocaAlta = epocaAlta;
    }

    public HotelStandard clone() {
        return new HotelStandard(this);
    }

    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        HotelStandard o = (HotelStandard) obj;
        return super.equals(o) && o.getEpocaAlta() == epocaAlta;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Standard,");
        sb.append(super.toString());
        sb.append(this.epocaAlta);
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
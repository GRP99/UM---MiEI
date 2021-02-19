import java.lang.String;
import java.util.Objects;

public class Estatisticas {
    private Double maiortemperaturaPrimaveraVerao;
    private String datemaiortemperaturaPrimaveraVerao;
    private Double menortemperaturaPrimaveraVerao;
    private String datemenortemperaturaPrimaveraVerao;
    private Double maiortemperaturaInvernoOutono;
    private String datemaiortemperaturaInvernoOutono;
    private Double menortemperaturaInvernoOutono;
    private String datemenortemperaturaInvernoOutono;;
    private Double maiordiferencaPrimaveraVerao;
    private String datemaiordiferencaPrimaveraVerao;
    private Double menordiferencaPrimaveraVerao;
    private String datemenordiferencaPrimaveraVerao;
    private Double maiordiferencaInvernoOutono;
    private String datemaiordiferencaInvernoOutono;
    private Double menordiferencaInvernoOutono;
    private String datemenordiferencaInvernoOutono;
    private Integer maiorhumidadePrimaveraVerao;
    private String datemaiorhumidadePrimaveraVerao;
    private Integer menorhumidadePrimaveraVerao;
    private String datemenorhumidadePrimaveraVerao;
    private Integer maiorhumidadeInvernoOutono;
    private String datemaiorhumidadeInvernoOutono;
    private Integer menorhumidadeInvernoOutono;
    private String datemenorhumidadeInvernoOutono;

    public Estatisticas(Double maiortemperaturaPrimaveraVerao, Double menortemperaturaPrimaveraVerao, Double maiortemperaturaInvernoOutono, Double menortemperaturaInvernoOutono, Double maiordiferencaPrimaveraVerao, Double menordiferencaPrimaveraVerao, Double maiordiferencaInvernoOutono, Double menordiferencaInvernoOutono, Integer maiorhumidadePrimaveraVerao, Integer menorhumidadePrimaveraVerao, Integer maiorhumidadeInvernoOutono, Integer menorhumidadeInvernoOutono) {
        this.maiortemperaturaPrimaveraVerao = maiortemperaturaPrimaveraVerao;
        this.menortemperaturaPrimaveraVerao = menortemperaturaPrimaveraVerao;
        this.maiortemperaturaInvernoOutono = maiortemperaturaInvernoOutono;
        this.menortemperaturaInvernoOutono = menortemperaturaInvernoOutono;
        this.maiordiferencaPrimaveraVerao = maiordiferencaPrimaveraVerao;
        this.menordiferencaPrimaveraVerao = menordiferencaPrimaveraVerao;
        this.maiordiferencaInvernoOutono = maiordiferencaInvernoOutono;
        this.menordiferencaInvernoOutono = menordiferencaInvernoOutono;
        this.maiorhumidadePrimaveraVerao = maiorhumidadePrimaveraVerao;
        this.menorhumidadePrimaveraVerao = menorhumidadePrimaveraVerao;
        this.maiorhumidadeInvernoOutono = maiorhumidadeInvernoOutono;
        this.menorhumidadeInvernoOutono = menorhumidadeInvernoOutono;
    }

    public Double getMaiortemperaturaPrimaveraVerao() {
        return maiortemperaturaPrimaveraVerao;
    }

    public void setMaiortemperaturaPrimaveraVerao(Double maiortemperaturaPrimaveraVerao) {
        this.maiortemperaturaPrimaveraVerao = maiortemperaturaPrimaveraVerao;
    }

    public Double getMenortemperaturaPrimaveraVerao() {
        return menortemperaturaPrimaveraVerao;
    }

    public void setMenortemperaturaPrimaveraVerao(Double menortemperaturaPrimaveraVerao) {
        this.menortemperaturaPrimaveraVerao = menortemperaturaPrimaveraVerao;
    }

    public Double getMaiortemperaturaInvernoOutono() {
        return maiortemperaturaInvernoOutono;
    }

    public void setMaiortemperaturaInvernoOutono(Double maiortemperaturaInvernoOutono) {
        this.maiortemperaturaInvernoOutono = maiortemperaturaInvernoOutono;
    }

    public Double getMenortemperaturaInvernoOutono() {
        return menortemperaturaInvernoOutono;
    }

    public void setMenortemperaturaInvernoOutono(Double menortemperaturaInvernoOutono) {
        this.menortemperaturaInvernoOutono = menortemperaturaInvernoOutono;
    }

    public Double getMaiordiferencaPrimaveraVerao() {
        return maiordiferencaPrimaveraVerao;
    }

    public void setMaiordiferencaPrimaveraVerao(Double maiordiferencaPrimaveraVerao) {
        this.maiordiferencaPrimaveraVerao = maiordiferencaPrimaveraVerao;
    }

    public Double getMenordiferencaPrimaveraVerao() {
        return menordiferencaPrimaveraVerao;
    }

    public void setMenordiferencaPrimaveraVerao(Double menordiferencaPrimaveraVerao) {
        this.menordiferencaPrimaveraVerao = menordiferencaPrimaveraVerao;
    }

    public Double getMaiordiferencaInvernoOutono() {
        return maiordiferencaInvernoOutono;
    }

    public void setMaiordiferencaInvernoOutono(Double maiordiferencaInvernoOutono) {
        this.maiordiferencaInvernoOutono = maiordiferencaInvernoOutono;
    }

    public Double getMenordiferencaInvernoOutono() {
        return menordiferencaInvernoOutono;
    }

    public void setMenordiferencaInvernoOutono(Double menordiferencaInvernoOutono) {
        this.menordiferencaInvernoOutono = menordiferencaInvernoOutono;
    }

    public Integer getMaiorhumidadePrimaveraVerao() {
        return maiorhumidadePrimaveraVerao;
    }

    public void setMaiorhumidadePrimaveraVerao(Integer maiorhumidadePrimaveraVerao) {
        this.maiorhumidadePrimaveraVerao = maiorhumidadePrimaveraVerao;
    }

    public Integer getMenorhumidadePrimaveraVerao() {
        return menorhumidadePrimaveraVerao;
    }

    public void setMenorhumidadePrimaveraVerao(Integer menorhumidadePrimaveraVerao) {
        this.menorhumidadePrimaveraVerao = menorhumidadePrimaveraVerao;
    }

    public Integer getMaiorhumidadeInvernoOutono() {
        return maiorhumidadeInvernoOutono;
    }

    public void setMaiorhumidadeInvernoOutono(Integer maiorhumidadeInvernoOutono) {
        this.maiorhumidadeInvernoOutono = maiorhumidadeInvernoOutono;
    }

    public Integer getMenorhumidadeInvernoOutono() {
        return menorhumidadeInvernoOutono;
    }

    public void setMenorhumidadeInvernoOutono(Integer menorhumidadeInvernoOutono) {
        this.menorhumidadeInvernoOutono = menorhumidadeInvernoOutono;
    }

    public String getDatemaiortemperaturaPrimaveraVerao() {
        return datemaiortemperaturaPrimaveraVerao;
    }

    public void setDatemaiortemperaturaPrimaveraVerao(String datemaiortemperaturaPrimaveraVerao) {
        this.datemaiortemperaturaPrimaveraVerao = datemaiortemperaturaPrimaveraVerao;
    }

    public String getDatemenortemperaturaPrimaveraVerao() {
        return datemenortemperaturaPrimaveraVerao;
    }

    public void setDatemenortemperaturaPrimaveraVerao(String datemenortemperaturaPrimaveraVerao) {
        this.datemenortemperaturaPrimaveraVerao = datemenortemperaturaPrimaveraVerao;
    }

    public String getDatemaiortemperaturaInvernoOutono() {
        return datemaiortemperaturaInvernoOutono;
    }

    public void setDatemaiortemperaturaInvernoOutono(String datemaiortemperaturaInvernoOutono) {
        this.datemaiortemperaturaInvernoOutono = datemaiortemperaturaInvernoOutono;
    }

    public String getDatemenortemperaturaInvernoOutono() {
        return datemenortemperaturaInvernoOutono;
    }

    public void setDatemenortemperaturaInvernoOutono(String datemenortemperaturaInvernoOutono) {
        this.datemenortemperaturaInvernoOutono = datemenortemperaturaInvernoOutono;
    }

    public String getDatemaiordiferencaPrimaveraVerao() {
        return datemaiordiferencaPrimaveraVerao;
    }

    public void setDatemaiordiferencaPrimaveraVerao(String datemaiordiferencaPrimaveraVerao) {
        this.datemaiordiferencaPrimaveraVerao = datemaiordiferencaPrimaveraVerao;
    }

    public String getDatemenordiferencaPrimaveraVerao() {
        return datemenordiferencaPrimaveraVerao;
    }

    public void setDatemenordiferencaPrimaveraVerao(String datemenordiferencaPrimaveraVerao) {
        this.datemenordiferencaPrimaveraVerao = datemenordiferencaPrimaveraVerao;
    }

    public String getDatemaiordiferencaInvernoOutono() {
        return datemaiordiferencaInvernoOutono;
    }

    public void setDatemaiordiferencaInvernoOutono(String datemaiordiferencaInvernoOutono) {
        this.datemaiordiferencaInvernoOutono = datemaiordiferencaInvernoOutono;
    }

    public String getDatemenordiferencaInvernoOutono() {
        return datemenordiferencaInvernoOutono;
    }

    public void setDatemenordiferencaInvernoOutono(String datemenordiferencaInvernoOutono) {
        this.datemenordiferencaInvernoOutono = datemenordiferencaInvernoOutono;
    }

    public String getDatemaiorhumidadePrimaveraVerao() {
        return datemaiorhumidadePrimaveraVerao;
    }

    public void setDatemaiorhumidadePrimaveraVerao(String datemaiorhumidadePrimaveraVerao) {
        this.datemaiorhumidadePrimaveraVerao = datemaiorhumidadePrimaveraVerao;
    }

    public String getDatemenorhumidadePrimaveraVerao() {
        return datemenorhumidadePrimaveraVerao;
    }

    public void setDatemenorhumidadePrimaveraVerao(String datemenorhumidadePrimaveraVerao) {
        this.datemenorhumidadePrimaveraVerao = datemenorhumidadePrimaveraVerao;
    }

    public String getDatemaiorhumidadeInvernoOutono() {
        return datemaiorhumidadeInvernoOutono;
    }

    public void setDatemaiorhumidadeInvernoOutono(String datemaiorhumidadeInvernoOutono) {
        this.datemaiorhumidadeInvernoOutono = datemaiorhumidadeInvernoOutono;
    }

    public String getDatemenorhumidadeInvernoOutono() {
        return datemenorhumidadeInvernoOutono;
    }

    public void setDatemenorhumidadeInvernoOutono(String datemenorhumidadeInvernoOutono) {
        this.datemenorhumidadeInvernoOutono = datemenorhumidadeInvernoOutono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estatisticas that = (Estatisticas) o;
        return Objects.equals(maiortemperaturaPrimaveraVerao, that.maiortemperaturaPrimaveraVerao) && Objects.equals(datemaiortemperaturaPrimaveraVerao, that.datemaiortemperaturaPrimaveraVerao) && Objects.equals(menortemperaturaPrimaveraVerao, that.menortemperaturaPrimaveraVerao) && Objects.equals(datemenortemperaturaPrimaveraVerao, that.datemenortemperaturaPrimaveraVerao) && Objects.equals(maiortemperaturaInvernoOutono, that.maiortemperaturaInvernoOutono) && Objects.equals(datemaiortemperaturaInvernoOutono, that.datemaiortemperaturaInvernoOutono) && Objects.equals(menortemperaturaInvernoOutono, that.menortemperaturaInvernoOutono) && Objects.equals(datemenortemperaturaInvernoOutono, that.datemenortemperaturaInvernoOutono) && Objects.equals(maiordiferencaPrimaveraVerao, that.maiordiferencaPrimaveraVerao) && Objects.equals(datemaiordiferencaPrimaveraVerao, that.datemaiordiferencaPrimaveraVerao) && Objects.equals(menordiferencaPrimaveraVerao, that.menordiferencaPrimaveraVerao) && Objects.equals(datemenordiferencaPrimaveraVerao, that.datemenordiferencaPrimaveraVerao) && Objects.equals(maiordiferencaInvernoOutono, that.maiordiferencaInvernoOutono) && Objects.equals(datemaiordiferencaInvernoOutono, that.datemaiordiferencaInvernoOutono) && Objects.equals(menordiferencaInvernoOutono, that.menordiferencaInvernoOutono) && Objects.equals(datemenordiferencaInvernoOutono, that.datemenordiferencaInvernoOutono) && Objects.equals(maiorhumidadePrimaveraVerao, that.maiorhumidadePrimaveraVerao) && Objects.equals(datemaiorhumidadePrimaveraVerao, that.datemaiorhumidadePrimaveraVerao) && Objects.equals(menorhumidadePrimaveraVerao, that.menorhumidadePrimaveraVerao) && Objects.equals(datemenorhumidadePrimaveraVerao, that.datemenorhumidadePrimaveraVerao) && Objects.equals(maiorhumidadeInvernoOutono, that.maiorhumidadeInvernoOutono) && Objects.equals(datemaiorhumidadeInvernoOutono, that.datemaiorhumidadeInvernoOutono) && Objects.equals(menorhumidadeInvernoOutono, that.menorhumidadeInvernoOutono) && Objects.equals(menorhumidadeInvernoOutono, that.menorhumidadeInvernoOutono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maiortemperaturaPrimaveraVerao, datemaiortemperaturaPrimaveraVerao, menortemperaturaPrimaveraVerao, datemenortemperaturaPrimaveraVerao, maiortemperaturaInvernoOutono, datemaiortemperaturaInvernoOutono, menortemperaturaInvernoOutono, datemenortemperaturaInvernoOutono, maiordiferencaPrimaveraVerao, datemaiordiferencaPrimaveraVerao, menordiferencaPrimaveraVerao, datemenordiferencaPrimaveraVerao, maiordiferencaInvernoOutono, datemaiordiferencaInvernoOutono, menordiferencaInvernoOutono, datemenordiferencaInvernoOutono, maiorhumidadePrimaveraVerao, datemaiorhumidadePrimaveraVerao, menorhumidadePrimaveraVerao, datemenorhumidadePrimaveraVerao, maiorhumidadeInvernoOutono, datemaiorhumidadeInvernoOutono, menorhumidadeInvernoOutono, menorhumidadeInvernoOutono);
    }

    @Override
    public String toString() {
        return "<ul>" +
                "<li> <u> maior temperatura Primavera+Verao </u> = " + maiortemperaturaPrimaveraVerao  + " : " + datemaiortemperaturaPrimaveraVerao + "</li>\n" +
                "<li> <u> menor temperatura Primavera+Verao </u> = " + menortemperaturaPrimaveraVerao + " : " + datemenortemperaturaPrimaveraVerao + "</li>\n" +
                "<li> <u> maior temperatura Inverno+Outono </u> = " + maiortemperaturaInvernoOutono + " : " + datemaiortemperaturaInvernoOutono + "</li>\n" +
                "<li> <u> menor temperatura Inverno+Outono </u> = " + menortemperaturaInvernoOutono + " : " + datemenortemperaturaInvernoOutono  + "</li>\n" +
                "<li> <u> maior diferenca Primavera+Verao </u> = " + maiordiferencaPrimaveraVerao + " : " + datemaiordiferencaPrimaveraVerao + "</li>\n" +
                "<li> <u> menor diferenca Primavera+Verao </u> = " + menordiferencaPrimaveraVerao + " : " + datemenordiferencaPrimaveraVerao + "</li>\n" +
                "<li> <u> maior diferenca Inverno+Outono </u> = " + maiordiferencaInvernoOutono + " : " + datemaiordiferencaInvernoOutono + "</li>\n" +
                "<li> <u> menor diferenca Inverno+Outono </u> = " + menordiferencaInvernoOutono + " : " + datemenordiferencaInvernoOutono + "</li>\n" +
                "<li> <u> maior humidade Primavera+Verao </u> = " + maiorhumidadePrimaveraVerao + " : " + datemenorhumidadePrimaveraVerao + "</li>\n" +
                "<li> <u> menor humidade Primavera+Verao </u> = " + menorhumidadePrimaveraVerao + " : " + datemenorhumidadePrimaveraVerao + "</li>\n" +
                "<li> <u> maior humidade Inverno+Outono </u> = " + maiorhumidadeInvernoOutono + " : " + datemaiorhumidadeInvernoOutono + "</li>\n" +
                "<li> <u> menor humidade Inverno+Outono </u> = " + menorhumidadeInvernoOutono + " : " + datemenorhumidadeInvernoOutono + "</li>\n" +
                "</ul>";
    }
}

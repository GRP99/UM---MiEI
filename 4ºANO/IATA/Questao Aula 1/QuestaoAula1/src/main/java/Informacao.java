import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Informacao {
    private Date date;
    private String city_name;
    private double latitude;
    private double longitude;
    private double temperature;
    private int humidity;

    public Informacao(Date date, String city_name, double latitude, double longitude, double temperature, int humidity) {
        this.date = date;
        this.city_name = city_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Informacao that = (Informacao) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0 && Double.compare(that.temperature, temperature) == 0 && humidity == that.humidity && Objects.equals(date, that.date) && Objects.equals(city_name, that.city_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, city_name, latitude, longitude, temperature, humidity);
    }

    @Override
    public String toString() {
        return "Informacao{" +
                "date=" + date +
                ", city_name='" + city_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }

    public String obterEstacao() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        int month = calendar.get(Calendar.MONTH);
        if (month < 3 || month >= 9) {
            return "Inverno+Outono";
        } else {
            return "Primavera+Verão";
        }
    }
}

import java.util.Objects;

public class Service {
    private String cod;
    private String desc;
    private String date;
    private String bed;

    public Service() {
    }

    public Service(String cod, String desc, String date, String bed) {
        this.cod = cod;
        this.desc = desc;
        this.date = date;
        this.bed = bed;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(cod, service.cod) &&
                Objects.equals(desc, service.desc) &&
                Objects.equals(date, service.date) &&
                Objects.equals(bed, service.bed);
    }

    @Override
    public String toString() {
        return "INSERT INTO service VALUES ('" + this.cod + "', '" + this.desc + "', '" + this.date + "', '" + this.bed + "')\n";
    }
}


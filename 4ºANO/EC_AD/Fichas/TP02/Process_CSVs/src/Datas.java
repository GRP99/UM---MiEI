import java.util.Objects;

public class Datas {
    private String symptom_start_date;
    private String confirmed_case_date;
    private String released_date;
    private String deceased_date;

    public Datas() {
    }

    public Datas(String symptom_start_date, String confirmed_case_date, String released_date, String deceased_date) {
        this.symptom_start_date = symptom_start_date;
        this.confirmed_case_date = confirmed_case_date;
        this.released_date = released_date;
        this.deceased_date = deceased_date;
    }

    public String getSymptom_start_date() {
        return symptom_start_date;
    }

    public void setSymptom_start_date(String symptom_start_date) {
        this.symptom_start_date = symptom_start_date;
    }

    public String getConfirmed_case_date() {
        return confirmed_case_date;
    }

    public void setConfirmed_case_date(String confirmed_case_date) {
        this.confirmed_case_date = confirmed_case_date;
    }

    public String getReleased_date() {
        return released_date;
    }

    public void setReleased_date(String released_date) {
        this.released_date = released_date;
    }

    public String getDeceased_date() {
        return deceased_date;
    }

    public void setDeceased_date(String deceased_date) {
        this.deceased_date = deceased_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Datas datas = (Datas) o;
        return Objects.equals(symptom_start_date, datas.symptom_start_date) &&
                Objects.equals(confirmed_case_date, datas.confirmed_case_date) &&
                Objects.equals(released_date, datas.released_date) &&
                Objects.equals(deceased_date, datas.deceased_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symptom_start_date, confirmed_case_date, released_date, deceased_date);
    }
}

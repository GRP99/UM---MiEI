import java.util.Objects;

public class Patient {
    private int infected_by;
    private int id_age;
    private int id_region;
    private int id_date;
    private int id_infection;
    private int id_origin;
    private int id_sex;
    private int id_state;

    public Patient() {
    }

    public Patient(int infected_by, int id_age, int id_region, int id_date, int id_infection, int id_origin, int id_sex, int id_state) {
        this.infected_by = infected_by;
        this.id_age = id_age;
        this.id_region = id_region;
        this.id_date = id_date;
        this.id_infection = id_infection;
        this.id_origin = id_origin;
        this.id_sex = id_sex;
        this.id_state = id_state;
    }

    public int getInfected_by() {
        return infected_by;
    }

    public void setInfected_by(int infected_by) {
        this.infected_by = infected_by;
    }

    public int getId_age() {
        return id_age;
    }

    public void setId_age(int id_age) {
        this.id_age = id_age;
    }

    public int getId_region() {
        return id_region;
    }

    public void setId_region(int id_region) {
        this.id_region = id_region;
    }

    public int getId_date() {
        return id_date;
    }

    public void setId_date(int id_date) {
        this.id_date = id_date;
    }

    public int getId_infection() {
        return id_infection;
    }

    public void setId_infection(int id_infection) {
        this.id_infection = id_infection;
    }

    public int getId_origin() {
        return id_origin;
    }

    public void setId_origin(int id_origin) {
        this.id_origin = id_origin;
    }

    public int getId_sex() {
        return id_sex;
    }

    public void setId_sex(int id_sex) {
        this.id_sex = id_sex;
    }

    public int getId_state() {
        return id_state;
    }

    public void setId_state(int id_state) {
        this.id_state = id_state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return infected_by == patient.infected_by &&
                id_age == patient.id_age &&
                id_region == patient.id_region &&
                id_date == patient.id_date &&
                id_infection == patient.id_infection &&
                id_origin == patient.id_origin &&
                id_sex == patient.id_sex &&
                id_state == patient.id_state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(infected_by, id_age, id_region, id_date, id_infection, id_origin, id_sex, id_state);
    }
}

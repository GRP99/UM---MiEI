import java.util.Objects;

public class Infection {
    private String infection_case;
    private String contact_number;

    public Infection() {
    }

    public Infection(String infection_case, String contact_number) {
        this.infection_case = infection_case;
        this.contact_number = contact_number;
    }

    public String getInfection_case() {
        return infection_case;
    }

    public void setInfection_case(String infection_case) {
        this.infection_case = infection_case;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Infection infection = (Infection) o;
        return Objects.equals(infection_case, infection.infection_case) &&
                Objects.equals(contact_number, infection.contact_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infection_case, contact_number);
    }
}

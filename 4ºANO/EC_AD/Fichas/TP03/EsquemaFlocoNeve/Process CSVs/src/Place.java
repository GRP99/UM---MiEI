import java.util.Objects;

public class Place {
    private String fk_id_state;
    private int fk_id_city;

    public Place() {
    }

    public Place(String fk_id_state, int fk_id_city) {
        this.fk_id_state = fk_id_state;
        this.fk_id_city = fk_id_city;
    }

    public String getFk_id_state() {
        return fk_id_state;
    }

    public void setFk_id_state(String fk_id_state) {
        this.fk_id_state = fk_id_state;
    }

    public int getFk_id_city() {
        return fk_id_city;
    }

    public void setFk_id_city(int fk_id_city) {
        this.fk_id_city = fk_id_city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return fk_id_city == place.fk_id_city &&
                Objects.equals(fk_id_state, place.fk_id_state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fk_id_state, fk_id_city);
    }
}

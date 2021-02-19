import java.util.Objects;

public class Place {
    private String state;
    private String city;

    public Place() {
    }

    public Place( String city, String state) {
        this.state = state;
        this.city = city;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(state, place.state) &&
                Objects.equals(city, place.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash( state, city);
    }
}

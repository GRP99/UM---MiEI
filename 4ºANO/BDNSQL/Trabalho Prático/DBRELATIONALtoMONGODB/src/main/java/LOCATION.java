import java.util.Objects;

public class LOCATION {
    private int id;
    private String street_address;
    private String postal_code;
    private String city;
    private String state_province;
    private COUNTRY country;

    public LOCATION() {
    }

    public LOCATION(int id, String street_address, String postal_code, String city, String state_province, COUNTRY country) {
        this.id = id;
        this.street_address = street_address;
        this.postal_code = postal_code;
        this.city = city;
        this.state_province = state_province;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public COUNTRY getCountry() {
        return country;
    }

    public void setCountry(COUNTRY country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LOCATION location = (LOCATION) o;
        return id == location.id && Objects.equals(street_address, location.street_address) && Objects.equals(postal_code, location.postal_code) && Objects.equals(city, location.city) && Objects.equals(state_province, location.state_province) && Objects.equals(country, location.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street_address, postal_code, city, state_province, country);
    }
}

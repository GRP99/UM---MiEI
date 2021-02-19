import java.util.Objects;

public class COUNTRY {
    private String id;
    private String country_name;
    private REGION region;

    public COUNTRY() {
    }

    public COUNTRY(String id, String country_name, REGION region) {
        this.id = id;
        this.country_name = country_name;
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public REGION getRegion() {
        return region;
    }

    public void setRegion(REGION region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        COUNTRY country = (COUNTRY) o;
        return id == country.id && Objects.equals(country_name, country.country_name) && Objects.equals(region, country.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country_name, region);
    }
}

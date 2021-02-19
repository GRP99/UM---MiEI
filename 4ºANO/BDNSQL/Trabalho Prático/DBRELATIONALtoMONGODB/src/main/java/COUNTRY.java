import java.util.Objects;

public class COUNTRY {
    private final String id;
    private final String country_name;
    private final REGION region;


    public COUNTRY(String id, String country_name, REGION region) {
        this.id = id;
        this.country_name = country_name;
        this.region = region;
    }

    public String getId() {
        return id;
    }


    public String getCountry_name() {
        return country_name;
    }


    public REGION getRegion() {
        return region;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        COUNTRY country = (COUNTRY) o;
        return id.equals(country.id) && Objects.equals(country_name, country.country_name) && Objects.equals(region, country.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country_name, region);
    }
}

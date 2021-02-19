import java.util.Objects;

public class Location {
    private final int id;
    private final int neighborhood;
    private final int city;
    private final int country;
    private final String latitude;
    private final String longitude;

    public Location(int id, int neighborhood, int city, int country, String latitude, String longitude) {
        this.id = id;
        this.neighborhood = neighborhood;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public int getNeighborhood() {
        return neighborhood;
    }

    public int getCity() {
        return city;
    }

    public int getCountry() {
        return country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && neighborhood == location.neighborhood && city == location.city && country == location.country && Objects.equals(latitude, location.latitude) && Objects.equals(longitude, location.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, neighborhood, city, country, latitude, longitude);
    }
}
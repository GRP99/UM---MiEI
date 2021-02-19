import java.util.Objects;

public class Amenities {
    private int installation;
    private int amenity;

    public Amenities() {
    }

    public Amenities(int installation, int amenity) {
        this.installation = installation;
        this.amenity = amenity;
    }

    public int getInstallation() {
        return installation;
    }

    public int getAmenity() {
        return amenity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amenities amenities = (Amenities) o;
        return installation == amenities.installation && amenity == amenities.amenity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(installation, amenity);
    }
}

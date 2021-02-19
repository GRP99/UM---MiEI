import java.util.Objects;

public class Installation {
    private final int id;
    private final int property;
    private final int room;
    private final int bed_type;
    private final String accommodates;
    private final String bedrooms;
    private final String beds;
    private final String bathrooms;

    public Installation(int id, int property, int room, int bed_type, String accommodates, String bedrooms, String beds, String bathrooms) {
        this.id = id;
        this.property = property;
        this.room = room;
        this.bed_type = bed_type;
        this.accommodates = accommodates;
        this.bedrooms = bedrooms;
        this.beds = beds;
        this.bathrooms = bathrooms;
    }

    public int getId() {
        return id;
    }

    public int getProperty() {
        return property;
    }

    public int getRoom() {
        return room;
    }

    public int getBed_type() {
        return bed_type;
    }

    public String getAccommodates() {
        return accommodates;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public String getBeds() {
        return beds;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Installation that = (Installation) o;
        return id == that.id && property == that.property && room == that.room && bed_type == that.bed_type && Objects.equals(accommodates, that.accommodates) && Objects.equals(bedrooms, that.bedrooms) && Objects.equals(beds, that.beds) && Objects.equals(bathrooms, that.bathrooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, property, room, bed_type, accommodates, bedrooms, beds, bathrooms);
    }
}
import java.util.Objects;

public class Origin {
    private String origin;

    public Origin() {
    }

    public Origin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Origin origin1 = (Origin) o;
        return Objects.equals(origin, origin1.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin);
    }
}

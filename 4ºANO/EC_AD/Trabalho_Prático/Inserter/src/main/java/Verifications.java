import java.util.Objects;

public class Verifications {
    private final int id_host;
    private final int id_verification;

    public Verifications(int id_host, int id_verification) {
        this.id_host = id_host;
        this.id_verification = id_verification;
    }

    public int getId_host() {
        return id_host;
    }

    public int getId_verification() {
        return id_verification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Verifications that = (Verifications) o;
        return id_host == that.id_host && id_verification == that.id_verification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_host, id_verification);
    }
}

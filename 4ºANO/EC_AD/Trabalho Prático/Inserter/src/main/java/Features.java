import java.util.Objects;

public class Features {
    private final int feature;
    private final int facto;

    public Features(int feature, int facto) {
        this.feature = feature;
        this.facto = facto;
    }

    public int getFeature() {
        return feature;
    }

    public int getFacto() {
        return facto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Features features = (Features) o;
        return feature == features.feature && facto == features.facto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feature, facto);
    }
}

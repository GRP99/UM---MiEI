import java.util.Objects;

public class Flee {
    private String flee;

    public Flee() {
    }

    public Flee(String flee) {
        this.flee = flee;
    }

    public String getFlee() {
        return flee;
    }

    public void setFlee(String flee) {
        this.flee = flee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flee flee1 = (Flee) o;
        return Objects.equals(flee, flee1.flee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flee);
    }
}

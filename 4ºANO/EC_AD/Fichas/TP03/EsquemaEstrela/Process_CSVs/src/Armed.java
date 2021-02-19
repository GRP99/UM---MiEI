import java.util.Objects;

public class Armed {

    private String armed;

    public Armed() {
    }

    public Armed(String armed) {
        this.armed = armed;
    }

    public String getArmed() {
        return armed;
    }

    public void setArmed(String armed) {
        this.armed = armed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Armed armed1 = (Armed) o;
        return Objects.equals(armed, armed1.armed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(armed);
    }
}

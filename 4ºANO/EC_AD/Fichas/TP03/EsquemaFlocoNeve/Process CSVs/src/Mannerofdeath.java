import java.util.Objects;

public class Mannerofdeath {
    private String mannerofdeath;

    public Mannerofdeath() {
    }

    public Mannerofdeath(String mannerofdeath) {
        this.mannerofdeath = mannerofdeath;
    }

    public String getMannerofdeath() {
        return mannerofdeath;
    }

    public void setMannerofdeath(String mannerofdeath) {
        this.mannerofdeath = mannerofdeath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mannerofdeath that = (Mannerofdeath) o;
        return Objects.equals(mannerofdeath, that.mannerofdeath);


    }

    @Override
    public int hashCode() {
        return Objects.hash(mannerofdeath);
    }
}

import java.util.Objects;

public class Threatlevel {
    private String threatlevel;

    public Threatlevel() {
    }

    public Threatlevel(String threatlevel) {
        this.threatlevel = threatlevel;
    }

    public String getThreatlevel() {
        return threatlevel;
    }

    public void setThreatlevel(String threatlevel) {
        this.threatlevel = threatlevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Threatlevel that = (Threatlevel) o;
        return Objects.equals(threatlevel, that.threatlevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threatlevel);
    }
}

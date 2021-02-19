import java.util.Objects;

public class DeathCircunstances {
    private int fk_manner;
    private int fk_armed;
    private int fk_threat;
    private int fk_flee;

    public DeathCircunstances() {
    }

    public DeathCircunstances(int fk_manner, int fk_armed, int fk_threat, int fk_flee) {
        this.fk_manner = fk_manner;
        this.fk_armed = fk_armed;
        this.fk_threat = fk_threat;
        this.fk_flee = fk_flee;
    }

    public int getFk_manner() {
        return fk_manner;
    }

    public void setFk_manner(int fk_manner) {
        this.fk_manner = fk_manner;
    }

    public int getFk_armed() {
        return fk_armed;
    }

    public void setFk_armed(int fk_armed) {
        this.fk_armed = fk_armed;
    }

    public int getFk_threat() {
        return fk_threat;
    }

    public void setFk_threat(int fk_threat) {
        this.fk_threat = fk_threat;
    }

    public int getFk_flee() {
        return fk_flee;
    }

    public void setFk_flee(int fk_flee) {
        this.fk_flee = fk_flee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeathCircunstances that = (DeathCircunstances) o;
        return fk_manner == that.fk_manner &&
                fk_armed == that.fk_armed &&
                fk_threat == that.fk_threat &&
                fk_flee == that.fk_flee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fk_manner, fk_armed, fk_threat, fk_flee);
    }
}

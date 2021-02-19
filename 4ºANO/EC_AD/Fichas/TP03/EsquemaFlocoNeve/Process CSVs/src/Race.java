import java.util.Objects;

public class Race {
    private String race;

    public Race() {
    }

    public Race(String race) {
        this.race = race;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race1 = (Race) o;
        return Objects.equals(race, race1.race);
    }

    @Override
    public int hashCode() {
        return Objects.hash(race);
    }
}

import java.util.Objects;

public class Careteam {
    private int idSensor;
    private int idDoctor;

    public Careteam() {
    }

    public Careteam(int idSensor, int idDoctor) {
        this.idSensor = idSensor;
        this.idDoctor = idDoctor;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Careteam careteam = (Careteam) o;
        return idSensor == careteam.idSensor &&
                idDoctor == careteam.idDoctor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSensor, idDoctor);
    }

    @Override
    public String toString() {
        return "INSERT INTO careteam VALUES ("
                + idSensor +
                "," + idDoctor +
                ")\n";
    }
}

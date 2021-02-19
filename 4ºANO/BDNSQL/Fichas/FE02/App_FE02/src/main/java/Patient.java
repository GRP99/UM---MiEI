import java.util.Objects;

public class Patient {
    private int id;
    private String name;
    private String birthdate;
    private int age;

    public Patient() {
    }

    public Patient(int id, String name, String birthdate, int age) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
                age == patient.age &&
                Objects.equals(name, patient.name) &&
                Objects.equals(birthdate, patient.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, age);
    }

    @Override
    public String toString() {
        return "INSERT INTO patient VALUES ("
                + id +
                ",'" + name + "'" +
                ",'" + birthdate + "'" +
                "," + age +
                ")\n";
    }
}

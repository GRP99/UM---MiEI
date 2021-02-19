import java.util.Objects;

public class Sex {
    private String sex;

    public Sex() {
    }

    public Sex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sex sex1 = (Sex) o;
        return Objects.equals(sex, sex1.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sex);
    }
}

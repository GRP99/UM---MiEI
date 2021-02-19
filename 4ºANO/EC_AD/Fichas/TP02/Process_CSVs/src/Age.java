import java.util.Objects;

public class Age {
    private String age;

    public Age(String a) {
        this.age = a;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Age age1 = (Age) o;
        return Objects.equals(age, age1.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }
}


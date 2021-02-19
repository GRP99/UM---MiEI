import java.util.Objects;

public class FatalPolice {
    private int id;
    private int fk_name;
    private String fk_date;
    private int fk_deathcircunstances;
    private int age;
    private int fk_gender;
    private int fk_race;
    private int fk_place;
    private String signs;
    private String body;

    public FatalPolice() {
    }

    public FatalPolice(int id, int fk_name, String fk_date, int fk_deathcircunstances, int age, int fk_gender, int fk_race, int fk_place, String signs, String body) {
        this.id = id;
        this.fk_name = fk_name;
        this.fk_date = fk_date;
        this.fk_deathcircunstances = fk_deathcircunstances;
        this.age = age;
        this.fk_gender = fk_gender;
        this.fk_race = fk_race;
        this.fk_place = fk_place;
        this.signs = signs;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_name() {
        return fk_name;
    }

    public void setFk_name(int fk_name) {
        this.fk_name = fk_name;
    }

    public String getFk_date() {
        return fk_date;
    }

    public void setFk_date(String fk_date) {
        this.fk_date = fk_date;
    }

    public int getFk_deathcircunstances() {
        return fk_deathcircunstances;
    }

    public void setFk_deathcircunstances(int fk_deathcircunstances) {
        this.fk_deathcircunstances = fk_deathcircunstances;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getFk_gender() {
        return fk_gender;
    }

    public void setFk_gender(int fk_gender) {
        this.fk_gender = fk_gender;
    }

    public int getFk_race() {
        return fk_race;
    }

    public void setFk_race(int fk_race) {
        this.fk_race = fk_race;
    }

    public int getFk_place() {
        return fk_place;
    }

    public void setFk_place(int fk_place) {
        this.fk_place = fk_place;
    }

    public String getSigns() {
        return signs;
    }

    public void setSigns(String signs) {
        this.signs = signs;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FatalPolice that = (FatalPolice) o;
        return id == that.id &&
                fk_name == that.fk_name &&
                fk_deathcircunstances == that.fk_deathcircunstances &&
                age == that.age &&
                fk_gender == that.fk_gender &&
                fk_race == that.fk_race &&
                fk_place == that.fk_place &&
                Objects.equals(fk_date, that.fk_date) &&
                Objects.equals(signs, that.signs) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fk_name, fk_date, fk_deathcircunstances, age, fk_gender, fk_race, fk_place, signs, body);
    }
}

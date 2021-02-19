import java.util.Objects;

public class FatalPolice {
    private int id;
    private int fk_name;
    private String fk_date;
    private int fk_manner;
    private int fk_armed;
    private int age;
    private int fk_gender;
    private int fk_race;
    private int fk_place;
    private String signs;
    private int fk_threat;
    private int fk_flee;
    private String body;

    public FatalPolice() {
    }

    public FatalPolice(int id, int fk_name, String fk_date, int fk_manner, int fk_armed, int age, int fk_gender, int fk_race, int fk_place, String signs, int fk_threat, int fk_flee, String body) {
        this.id = id;
        this.fk_name = fk_name;
        this.fk_date = fk_date;
        this.fk_manner = fk_manner;
        this.fk_armed = fk_armed;
        this.age = age;
        this.fk_gender = fk_gender;
        this.fk_race = fk_race;
        this.fk_place = fk_place;
        this.signs = signs;
        this.fk_threat = fk_threat;
        this.fk_flee = fk_flee;
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
                fk_manner == that.fk_manner &&
                fk_armed == that.fk_armed &&
                age == that.age &&
                fk_gender == that.fk_gender &&
                fk_race == that.fk_race &&
                fk_place == that.fk_place &&
                fk_threat == that.fk_threat &&
                fk_flee == that.fk_flee &&
                Objects.equals(fk_date, that.fk_date) &&
                Objects.equals(signs, that.signs) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fk_name, fk_date, fk_manner, fk_armed, age, fk_gender, fk_race, fk_place, signs, fk_threat, fk_flee, body);
    }
}

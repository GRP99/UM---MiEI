import java.util.Objects;

public class JOB {
    private String id;
    private String title;
    private int min_salary;
    private int max_salary;

    public JOB() {
    }

    public JOB(String id, String title, int min_salary, int max_salary) {
        this.id = id;
        this.title = title;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(int min_salary) {
        this.min_salary = min_salary;
    }

    public int getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(int max_salary) {
        this.max_salary = max_salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JOB job = (JOB) o;
        return min_salary == job.min_salary && max_salary == job.max_salary && Objects.equals(id, job.id) && Objects.equals(title, job.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, min_salary, max_salary);
    }
}

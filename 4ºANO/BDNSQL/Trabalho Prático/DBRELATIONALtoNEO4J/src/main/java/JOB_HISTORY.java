import java.util.Date;
import java.util.Objects;

public class JOB_HISTORY {
    private EMPLOYEE employee;
    private Date start_date;
    private Date end_date;
    private JOB job;
    private DEPARTMENT department;

    public JOB_HISTORY() {
    }

    public JOB_HISTORY(EMPLOYEE employee, Date start_date, Date end_date, JOB job, DEPARTMENT department) {
        this.employee = employee;
        this.start_date = start_date;
        this.end_date = end_date;
        this.job = job;
        this.department = department;
    }

    public EMPLOYEE getEmployee() {
        return employee;
    }

    public void setEmployee(EMPLOYEE employee) {
        this.employee = employee;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public JOB getJob() {
        return job;
    }

    public void setJob(JOB job) {
        this.job = job;
    }

    public DEPARTMENT getDepartment() {
        return department;
    }

    public void setDepartment(DEPARTMENT department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JOB_HISTORY that = (JOB_HISTORY) o;
        return Objects.equals(employee, that.employee) && Objects.equals(start_date, that.start_date) && Objects.equals(end_date, that.end_date) && Objects.equals(job, that.job) && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, start_date, end_date, job, department);
    }
}

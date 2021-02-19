import java.util.Date;
import java.util.Objects;

public class EMPLOYEE {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private Date hire_date;
    private JOB job;
    private int salary;
    private double commission_pct;
    private EMPLOYEE manager;
    private DEPARTMENT department;

    public EMPLOYEE() {
    }

    public EMPLOYEE(int id, String first_name, String last_name, String email, String phone_number, Date hire_date, JOB job, int salary, double commission_pct, EMPLOYEE manager, DEPARTMENT department) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.hire_date = hire_date;
        this.job = job;
        this.salary = salary;
        this.commission_pct = commission_pct;
        this.manager = manager;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {return first_name + " " + last_name;}

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }

    public JOB getJob() {
        return job;
    }

    public void setJob(JOB job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public double getCommission_pct() {
        return commission_pct;
    }

    public void setCommission_pct(double commission_pct) {
        this.commission_pct = commission_pct;
    }

    public EMPLOYEE getManager() {
        return manager;
    }

    public void setManager(EMPLOYEE manager) {
        this.manager = manager;
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
        EMPLOYEE employee = (EMPLOYEE) o;
        return id == employee.id && salary == employee.salary && commission_pct == employee.commission_pct && Objects.equals(first_name, employee.first_name) && Objects.equals(last_name, employee.last_name) && Objects.equals(email, employee.email) && Objects.equals(phone_number, employee.phone_number) && Objects.equals(hire_date, employee.hire_date) && Objects.equals(job, employee.job) && Objects.equals(manager, employee.manager) && Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, email, phone_number, hire_date, job, salary, commission_pct, manager, department);
    }
}

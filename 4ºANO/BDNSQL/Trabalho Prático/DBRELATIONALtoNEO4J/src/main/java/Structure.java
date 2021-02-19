import java.util.List;
import java.util.Map;

public class Structure {
    private Map<Integer, REGION> regionMap;
    private Map<String, COUNTRY> countryMap;
    private Map<Integer, LOCATION> locationMap;
    private Map<String, JOB> jobMap;
    private Map<Integer, DEPARTMENT> departmentMap;
    private Map<Integer, EMPLOYEE> employeeMap;
    private List<JOB_HISTORY> job_historyList;

    public Map<Integer, REGION> getRegionMap() {
        return regionMap;
    }

    public void setRegionMap(Map<Integer, REGION> regionMap) {
        this.regionMap = regionMap;
    }

    public Map<String, COUNTRY> getCountryMap() {
        return countryMap;
    }

    public void setCountryMap(Map<String, COUNTRY> countryMap) {
        this.countryMap = countryMap;
    }

    public Map<Integer, LOCATION> getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(Map<Integer, LOCATION> locationMap) {
        this.locationMap = locationMap;
    }

    public Map<String, JOB> getJobMap() {
        return jobMap;
    }

    public void setJobMap(Map<String, JOB> jobMap) {
        this.jobMap = jobMap;
    }

    public Map<Integer, DEPARTMENT> getDepartmentMap() {
        return departmentMap;
    }

    public void setDepartmentMap(Map<Integer, DEPARTMENT> departmentMap) {
        this.departmentMap = departmentMap;
    }

    public Map<Integer, EMPLOYEE> getEmployeeMap() {
        return employeeMap;
    }

    public void setEmployeeMap(Map<Integer, EMPLOYEE> employeeMap) {
        this.employeeMap = employeeMap;
    }

    public List<JOB_HISTORY> getJob_historyList() {
        return job_historyList;
    }

    public void setJob_historyList(List<JOB_HISTORY> job_historyList) {
        this.job_historyList = job_historyList;
    }
}

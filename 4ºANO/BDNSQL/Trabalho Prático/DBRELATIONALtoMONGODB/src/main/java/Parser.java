import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Parser {

    private Map<Integer, REGION> parserRegions(Statement stmt) {
        Map<Integer, REGION> regionMap = new HashMap<>();
        try {
            String query = "SELECT * FROM REGIONS";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int regionID = rs.getInt("REGION_ID");
                String region = rs.getString("REGION_NAME");
                REGION r = new REGION(regionID, region);
                if (!(regionMap.containsKey(regionID))) {
                    regionMap.put(regionID, r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regionMap;
    }

    private Map<String, COUNTRY> parserCountries(Statement stmt, Map<Integer, REGION> regionMap) {
        Map<String, COUNTRY> countryMap = new HashMap<>();
        try {
            String query = "SELECT * FROM COUNTRIES";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String country_id = rs.getString("COUNTRY_ID");
                String country_name = rs.getString("COUNTRY_NAME");
                int region_id = rs.getInt("REGION_ID");
                REGION r = regionMap.get(region_id);
                COUNTRY c = new COUNTRY(country_id, country_name, r);
                if (!(countryMap.containsKey(country_id))) {
                    countryMap.put(country_id, c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryMap;
    }

    private Map<Integer, LOCATION> parserLocations(Statement stmt, Map<String, COUNTRY> countryMap) {
        Map<Integer, LOCATION> locationMap = new HashMap<>();
        try {
            String query = "SELECT * FROM LOCATIONS";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int location_id = rs.getInt("LOCATION_ID");
                String street_address = rs.getString("STREET_ADDRESS");
                String postal_code = rs.getString("POSTAL_CODE");
                String city = rs.getString("CITY");
                String state_province = rs.getString("STATE_PROVINCE");
                String country_id = rs.getString("COUNTRY_ID");
                COUNTRY c = countryMap.get(country_id);
                LOCATION l = new LOCATION(location_id, street_address, postal_code, city, state_province, c);
                if (!(locationMap.containsKey(location_id))) {
                    locationMap.put(location_id, l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationMap;
    }

    private Map<String, JOB> parserJobs(Statement stmt) {
        Map<String, JOB> jobMap = new HashMap<>();
        try {
            String query = "SELECT * FROM JOBS";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String job_id = rs.getString("JOB_ID");
                String job_title = rs.getString("JOB_TITLE");
                int min_salary = rs.getInt("MIN_SALARY");
                int max_salary = rs.getInt("MAX_SALARY");
                JOB j = new JOB(job_id, job_title, min_salary, max_salary);
                if (!(jobMap.containsKey(job_id))) {
                    jobMap.put(job_id, j);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobMap;
    }

    private Map<Integer, DEPARTMENT> parserDepartments(Statement stmt, Map<Integer, LOCATION> locationMap) {
        Map<Integer, DEPARTMENT> departmentMap = new HashMap<>();
        try {
            String query = "SELECT * FROM DEPARTMENTS";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int department_id = rs.getInt("DEPARTMENT_ID");
                String department_name = rs.getString("DEPARTMENT_NAME");
                int manager_id = rs.getInt("MANAGER_ID");
                int location_id = rs.getInt("LOCATION_ID");
                LOCATION l = locationMap.get(location_id);
                DEPARTMENT d = new DEPARTMENT(department_id, department_name, manager_id, l);
                if (!(departmentMap.containsKey(department_id))) {
                    departmentMap.put(department_id, d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentMap;
    }

    private Map<Integer, EMPLOYEE> parserEmployees(Statement stmt, Map<String, JOB> jobMap, Map<Integer, DEPARTMENT> departmentMap) {
        Map<Integer, EMPLOYEE> employeeMap = new HashMap<>();
        try {
            String queryRegion = "SELECT * FROM EMPLOYEES";
            ResultSet rs = stmt.executeQuery(queryRegion);
            while (rs.next()) {
                int employee_id = rs.getInt("EMPLOYEE_ID");
                String first_name = rs.getString("FIRST_NAME");
                String last_name = rs.getString("LAST_NAME");
                String email = rs.getString("EMAIL");
                String phone_number = rs.getString("PHONE_NUMBER");
                Date hire_date = rs.getDate("HIRE_DATE");
                String job_id = rs.getString("JOB_ID");
                int salary = rs.getInt("SALARY");
                double commission_pct = rs.getDouble("COMMISSION_PCT");
                int manager_id = rs.getInt("MANAGER_ID");
                int department_id = rs.getInt("DEPARTMENT_ID");
                JOB j = jobMap.get(job_id);
                DEPARTMENT d = departmentMap.get(department_id);
                EMPLOYEE manager = employeeMap.get(manager_id);
                EMPLOYEE e = new EMPLOYEE(employee_id, first_name, last_name, email, phone_number, hire_date, j, salary, commission_pct, manager, d);
                if (!(employeeMap.containsKey(employee_id))) {
                    employeeMap.put(employee_id, e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeMap;
    }

    private List<JOB_HISTORY> parserJOB_HISTORY(Statement stmt, Map<Integer, EMPLOYEE> employeeMap, Map<String, JOB> jobMap, Map<Integer, DEPARTMENT> departmentMap) {
        List<JOB_HISTORY> job_historyList = new ArrayList<>();
        try {
            String query = "SELECT * FROM JOB_HISTORY";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int employee_id = rs.getInt("EMPLOYEE_ID");
                Date start_date = rs.getDate("START_DATE");
                Date end_date = rs.getDate("END_DATE");
                String job_id = rs.getString("JOB_ID");
                int department_id = rs.getInt("DEPARTMENT_ID");
                EMPLOYEE e = employeeMap.get(employee_id);
                JOB j = jobMap.get(job_id);
                DEPARTMENT d = departmentMap.get(department_id);
                JOB_HISTORY jh = new JOB_HISTORY(e, start_date, end_date, j, d);
                if (!(job_historyList.contains(jh))) {
                    job_historyList.add(jh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return job_historyList;
    }

    public Structure loadStructure(Statement stmt, Structure structure) {
        structure = new Structure();

        structure.setRegionMap(parserRegions(stmt));

        structure.setCountryMap(parserCountries(stmt, structure.getRegionMap()));

        structure.setLocationMap(parserLocations(stmt, structure.getCountryMap()));

        structure.setJobMap(parserJobs(stmt));

        structure.setDepartmentMap(parserDepartments(stmt, structure.getLocationMap()));

        structure.setEmployeeMap(parserEmployees(stmt, structure.getJobMap(), structure.getDepartmentMap()));

        structure.setJob_historyList(parserJOB_HISTORY(stmt, structure.getEmployeeMap(), structure.getJobMap(), structure.getDepartmentMap()));

        return structure;
    }
}

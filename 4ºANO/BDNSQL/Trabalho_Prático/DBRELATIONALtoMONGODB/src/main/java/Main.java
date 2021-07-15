import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connect connect = new Connect();
        Class.forName("oracle.jdbc.OracleDriver");
        connect.setConnection();

        if (connect.getConnection() != null) {
            System.out.println("Connected with :" + connect.getUrl());

            try (Statement stmt = connect.getConnection().createStatement()) {
                Structure structure = new Structure();
                Parser scripts = new Parser();

                structure = scripts.loadStructure(stmt, structure);

                File myFile = new File("scriptPovoamentoMongoDB.json");
                if (myFile.createNewFile()) {
                    System.out.println("File created: " + myFile.getName() + "\n");
                } else {
                    System.out.println("File already exists.\n");
                }

                FileWriter fr = new FileWriter(myFile, false);

                fr.write("[\n");
                for (Map.Entry<Integer, EMPLOYEE> entry : structure.getEmployeeMap().entrySet()) {
                    EMPLOYEE employee = entry.getValue();
                    JOB job = employee.getJob();
                    if (employee.getCommission_pct() == 0.0) {
                        if (employee.getManager() != null) {
                            EMPLOYEE manager = employee.getManager();
                            if (employee.getDepartment() != null) {
                                DEPARTMENT department = employee.getDepartment();
                                LOCATION location = department.getLocation();
                                COUNTRY country = location.getCountry();
                                REGION region = country.getRegion();
                                fr.write("{\"_id\":" + employee.getId()
                                        + ",\"first_name\":\"" + employee.getFirst_name() + "\""
                                        + ",\"last_name\":\"" + employee.getLast_name() + "\""
                                        + ",\"email\":\"" + employee.getLast_name() + "\""
                                        + ",\"phone_number\":\"" + employee.getPhone_number() + "\""
                                        + ",\"hire_date\":\"" + employee.getHire_date() + "\""
                                        + ",\"salary\":" + employee.getSalary()
                                        + ",\"job\":{"
                                        + "\"title\":\"" + job.getTitle() + "\""
                                        + ",\"min_salary\":" + job.getMin_salary()
                                        + ",\"max_salary\":" + job.getMax_salary()
                                        + "}"
                                        + ",\"manager_id\":" + manager.getId()
                                        + ",\"department\":{"
                                        + "\"department_id\":\"" + department.getId() + "\""
                                        + ",\"name\":\"" + department.getName() + "\""
                                        + ",\"manager_id\":\"" + department.getManager_id() + "\""
                                        + ",\"manager\":\"" + structure.getEmployeeMap().get(department.getManager_id()).getFull_name() + "\""
                                        + ",\"street_address\":\"" + location.getStreet_address() + "\""
                                        + ",\"postal_code\":\"" + location.getPostal_code() + "\""
                                        + ",\"city\":\"" + location.getCity() + "\""
                                        + ",\"state_province\":\"" + location.getState_province() + "\""
                                        + ",\"country_name\":\"" + country.getCountry_name() + "\""
                                        + ",\"region_name\":\"" + region.getName() + "\""
                                        + "}"
                                        + ",\"history\":["
                                        + printHistory(employee.getId(), structure)
                                        + "]"
                                        + "},");
                            } else {
                                fr.write("{\"_id\":" + employee.getId()
                                        + ",\"first_name\":\"" + employee.getFirst_name() + "\""
                                        + ",\"last_name\":\"" + employee.getLast_name() + "\""
                                        + ",\"email\":\"" + employee.getLast_name() + "\""
                                        + ",\"phone_number\":\"" + employee.getPhone_number() + "\""
                                        + ",\"hire_date\":\"" + employee.getHire_date() + "\""
                                        + ",\"salary\":" + employee.getSalary()
                                        + ",\"job\":{"
                                        + ",\"title\":\"" + job.getTitle() + "\""
                                        + ",\"min_salary\":" + job.getMin_salary()
                                        + ",\"max_salary\":" + job.getMax_salary()
                                        + "}"
                                        + ",\"manager_id\":" + manager.getId()
                                        + ",\"history\":["
                                        + printHistory(employee.getId(), structure)
                                        + "]"
                                        + "},");
                            }
                        } else {
                            if (employee.getDepartment() != null) {
                                DEPARTMENT department = employee.getDepartment();
                                LOCATION location = department.getLocation();
                                COUNTRY country = location.getCountry();
                                REGION region = country.getRegion();
                                fr.write("{\"_id\":" + employee.getId()
                                        + ",\"first_name\":\"" + employee.getFirst_name() + "\""
                                        + ",\"last_name\":\"" + employee.getLast_name() + "\""
                                        + ",\"email\":\"" + employee.getLast_name() + "\""
                                        + ",\"phone_number\":\"" + employee.getPhone_number() + "\""
                                        + ",\"hire_date\":\"" + employee.getHire_date() + "\""
                                        + ",\"salary\":" + employee.getSalary()
                                        + ",\"job\":{"
                                        + "\"title\":\"" + job.getTitle() + "\""
                                        + ",\"min_salary\":" + job.getMin_salary()
                                        + ",\"max_salary\":" + job.getMax_salary()
                                        + "}"
                                        + ",\"department\":{"
                                        + "\"department_id\":\"" + department.getId() + "\""
                                        + ",\"name\":\"" + department.getName() + "\""
                                        + ",\"manager_id\":\"" + department.getManager_id() + "\""
                                        + ",\"manager\":\"" + structure.getEmployeeMap().get(department.getManager_id()).getFull_name() + "\""
                                        + ",\"street_address\":\"" + location.getStreet_address() + "\""
                                        + ",\"postal_code\":\"" + location.getPostal_code() + "\""
                                        + ",\"city\":\"" + location.getCity() + "\""
                                        + ",\"state_province\":\"" + location.getState_province() + "\""
                                        + ",\"country_name\":\"" + country.getCountry_name() + "\""
                                        + ",\"region_name\":\"" + region.getName() + "\""
                                        + "}"
                                        + ",\"history\":["
                                        + printHistory(employee.getId(), structure)
                                        + "]"
                                        + "},");
                            } else {
                                fr.write("{\"_id\":" + employee.getId()
                                        + ",\"first_name\":\"" + employee.getFirst_name() + "\""
                                        + ",\"last_name\":\"" + employee.getLast_name() + "\""
                                        + ",\"email\":\"" + employee.getLast_name() + "\""
                                        + ",\"phone_number\":\"" + employee.getPhone_number() + "\""
                                        + ",\"hire_date\":\"" + employee.getHire_date() + "\""
                                        + ",\"salary\":" + employee.getSalary()
                                        + ",\"job\":{"
                                        + "\"title\":\"" + job.getTitle() + "\""
                                        + ",\"min_salary\":" + job.getMin_salary()
                                        + ",\"max_salary\":" + job.getMax_salary()
                                        + "}"
                                        + ",\"history\":["
                                        + printHistory(employee.getId(), structure)
                                        + "]"
                                        + "},");
                            }
                        }
                    } else {
                        if (employee.getManager() != null) {
                            EMPLOYEE manager = employee.getManager();
                            if (employee.getDepartment() != null) {
                                DEPARTMENT department = employee.getDepartment();
                                LOCATION location = department.getLocation();
                                COUNTRY country = location.getCountry();
                                REGION region = country.getRegion();
                                fr.write("{\"_id\":" + employee.getId()
                                        + ",\"first_name\":\"" + employee.getFirst_name() + "\""
                                        + ",\"last_name\":\"" + employee.getLast_name() + "\""
                                        + ",\"email\":\"" + employee.getLast_name() + "\""
                                        + ",\"phone_number\":\"" + employee.getPhone_number() + "\""
                                        + ",\"hire_date\":\"" + employee.getHire_date() + "\""
                                        + ",\"salary\":" + employee.getSalary()
                                        + ",\"commission_pct\":" + employee.getCommission_pct()
                                        + ",\"job\":{"
                                        + "\"title\":\"" + job.getTitle() + "\""
                                        + ",\"min_salary\":" + job.getMin_salary()
                                        + ",\"max_salary\":" + job.getMax_salary()
                                        + "}"
                                        + ",\"manager_id\":" + manager.getId()
                                        + ",\"department\":{"
                                        + "\"department_id\":\"" + department.getId() + "\""
                                        + ",\"name\":\"" + department.getName() + "\""
                                        + ",\"manager_id\":\"" + department.getManager_id() + "\""
                                        + ",\"manager\":\"" + structure.getEmployeeMap().get(department.getManager_id()).getFull_name() + "\""
                                        + ",\"street_address\":\"" + location.getStreet_address() + "\""
                                        + ",\"postal_code\":\"" + location.getPostal_code() + "\""
                                        + ",\"city\":\"" + location.getCity() + "\""
                                        + ",\"state_province\":\"" + location.getState_province() + "\""
                                        + ",\"country_name\":\"" + country.getCountry_name() + "\""
                                        + ",\"region_name\":\"" + region.getName() + "\""
                                        + "}"
                                        + ",\"history\":["
                                        + printHistory(employee.getId(), structure)
                                        + "]"
                                        + "},");
                            } else {
                                fr.write("{\"_id\":" + employee.getId()
                                        + ",\"first_name\":\"" + employee.getFirst_name() + "\""
                                        + ",\"last_name\":\"" + employee.getLast_name() + "\""
                                        + ",\"email\":\"" + employee.getLast_name() + "\""
                                        + ",\"phone_number\":\"" + employee.getPhone_number() + "\""
                                        + ",\"hire_date\":\"" + employee.getHire_date() + "\""
                                        + ",\"salary\":" + employee.getSalary()
                                        + ",\"commission_pct\":" + employee.getCommission_pct()
                                        + ",\"job\":{"
                                        + "\"title\":\"" + job.getTitle() + "\""
                                        + ",\"min_salary\":" + job.getMin_salary()
                                        + ",\"max_salary\":" + job.getMax_salary()
                                        + "}"
                                        + ",\"manager_id\":" + manager.getId()
                                        + ",\"history\":["
                                        + printHistory(employee.getId(), structure)
                                        + "]"
                                        + "},");
                            }
                        } else {
                            if (employee.getDepartment() != null) {
                                DEPARTMENT department = employee.getDepartment();
                                LOCATION location = department.getLocation();
                                COUNTRY country = location.getCountry();
                                REGION region = country.getRegion();
                                fr.write("{\"_id\":" + employee.getId()
                                        + ",\"first_name\":\"" + employee.getFirst_name() + "\""
                                        + ",\"last_name\":\"" + employee.getLast_name() + "\""
                                        + ",\"email\":\"" + employee.getLast_name() + "\""
                                        + ",\"phone_number\":\"" + employee.getPhone_number() + "\""
                                        + ",\"hire_date\":\"" + employee.getHire_date() + "\""
                                        + ",\"salary\":" + employee.getSalary()
                                        + ",\"commission_pct\":" + employee.getCommission_pct()
                                        + ",\"job\":{"
                                        + "\"title\":\"" + job.getTitle() + "\""
                                        + ",\"min_salary\":" + job.getMin_salary()
                                        + ",\"max_salary\":" + job.getMax_salary()
                                        + "}"
                                        + ",\"department\":{"
                                        + "\"department_id\":\"" + department.getId() + "\""
                                        + ",\"name\":\"" + department.getName() + "\""
                                        + ",\"manager_id\":\"" + department.getManager_id() + "\""
                                        + ",\"manager\":\"" + structure.getEmployeeMap().get(department.getManager_id()).getFull_name() + "\""
                                        + ",\"street_address\":\"" + location.getStreet_address() + "\""
                                        + ",\"postal_code\":\"" + location.getPostal_code() + "\""
                                        + ",\"city\":\"" + location.getCity() + "\""
                                        + ",\"state_province\":\"" + location.getState_province() + "\""
                                        + ",\"country_name\":\"" + country.getCountry_name() + "\""
                                        + ",\"region_name\":\"" + region.getName() + "\""
                                        + "}"
                                        + ",\"history\":["
                                        + printHistory(employee.getId(), structure)
                                        + "]"
                                        + "},");
                            } else {
                                fr.write("{\"_id\":" + employee.getId()
                                        + ",\"first_name\":\"" + employee.getFirst_name() + "\""
                                        + ",\"last_name\":\"" + employee.getLast_name() + "\""
                                        + ",\"email\":\"" + employee.getLast_name() + "\""
                                        + ",\"phone_number\":\"" + employee.getPhone_number() + "\""
                                        + ",\"hire_date\":\"" + employee.getHire_date() + "\""
                                        + ",\"salary\":" + employee.getSalary()
                                        + ",\"commission_pct\":" + employee.getCommission_pct()
                                        + ",\"job\":{"
                                        + "\"id\":\"" + job.getId() + "\""
                                        + ",\"title\":\"" + job.getTitle() + "\""
                                        + ",\"min_salary\":" + job.getMin_salary()
                                        + ",\"max_salary\":" + job.getMax_salary()
                                        + "}"
                                        + ",\"history\":["
                                        + printHistory(employee.getId(), structure)
                                        + "]"
                                        + "},");
                            }
                        }
                    }
                }
                fr.write("]\n");

                fr.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Connection Failed");
        }


    }

    public static String printHistory(int id_employee, Structure structure) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (JOB_HISTORY job_history : structure.getJob_historyList()) {
            if (job_history.getEmployee().getId() == id_employee) {
                if (i > 0) {
                    sb.append(",");
                }

                sb.append("{");
                sb.append("\"start_date\":\"" + job_history.getStart_date() + "\"");
                sb.append(",\"end_date\":\"" + job_history.getEnd_date() + "\"");
                sb.append(",\"job_title\":\"" + job_history.getJob().getTitle() + "\"");
                sb.append(",\"job_min_salary\":" + job_history.getJob().getMin_salary());
                sb.append(",\"job_max_salary\":" + job_history.getJob().getMax_salary());
                sb.append(",\"department_name\":\"" + job_history.getDepartment().getName() + "\"");
                sb.append(",\"department_manager\":" + job_history.getDepartment().getManager_id());
                sb.append(",\"street_address\":\"" + job_history.getDepartment().getLocation().getStreet_address() + "\"");
                sb.append(",\"postal_code\":\"" + job_history.getDepartment().getLocation().getPostal_code() + "\"");
                sb.append(",\"city\":\"" + job_history.getDepartment().getLocation().getCity() + "\"");
                sb.append(",\"state_province\":\"" + job_history.getDepartment().getLocation().getState_province() + "\"");
                sb.append(",\"country_name\":\"" + job_history.getDepartment().getLocation().getCountry().getCountry_name() + "\"");
                sb.append(",\"region_name\":\"" + job_history.getDepartment().getLocation().getCountry().getRegion().getName() + "\"");
                sb.append("}");

                i++;
            }
        }
        return sb.toString();
    }
}


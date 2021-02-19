import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
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

                File myFile = new File("scriptPovoamentoNEO4J.txt");
                if (myFile.createNewFile()) {
                    System.out.println("File created: " + myFile.getName() + "\n");
                } else {
                    System.out.println("File already exists.\n");
                }

                FileWriter fr = new FileWriter(myFile, false);

                for (HashMap.Entry<String, COUNTRY> entry : structure.getCountryMap().entrySet()) {
                    COUNTRY c = entry.getValue();
                    REGION r = c.getRegion();
                    fr.write("CREATE (" + entry.getKey() + ": COUNTRY { id:'" + c.getId() + "', country_name:'" + c.getCountry_name() + "', region_name:'" + r.getName() + "'})\n");
                }

                for (Map.Entry<Integer, LOCATION> entry : structure.getLocationMap().entrySet()) {
                    LOCATION l = entry.getValue();
                    if (l.getState_province() == null && l.getPostal_code() == null) {
                        fr.write("CREATE (l" + entry.getKey() + ": LOCATION { id:" + l.getId() + ", street_address:'" + l.getStreet_address() + "', city:'" + l.getCity() + "'})\n");
                    } else if (l.getState_province() == null) {
                        fr.write("CREATE (l" + entry.getKey() + ": LOCATION { id:" + l.getId() + ", street_address:'" + l.getStreet_address() + "', postal_code:'" + l.getPostal_code() + "',city:'" + l.getCity() + "'})\n");
                    } else {
                        fr.write("CREATE (l" + entry.getKey() + ": LOCATION { id:" + l.getId() + ", street_address:'" + l.getStreet_address() + "', postal_code:'" + l.getPostal_code() + "',city:'" + l.getCity() + "', state_province:'" + l.getState_province() + "'})\n");
                    }
                }

                for (HashMap.Entry<Integer, DEPARTMENT> entry : structure.getDepartmentMap().entrySet()) {
                    DEPARTMENT d = entry.getValue();
                    fr.write("CREATE (d" + entry.getKey() + " : DEPARTMENT { id:" + d.getId() + ", name:'" + d.getName() + "'})\n");
                }

                for (HashMap.Entry<String, JOB> entry : structure.getJobMap().entrySet()) {
                    JOB j = entry.getValue();
                    fr.write("CREATE (" + entry.getKey() + " : JOB { id:'" + j.getId() + "',title:'" + j.getTitle() + "',min_salary:" + j.getMin_salary() + ",max_salary:" + j.getMax_salary() + "})\n");
                }

                for (Map.Entry<Integer, EMPLOYEE> entry : structure.getEmployeeMap().entrySet()) {
                    EMPLOYEE e = entry.getValue();
                    if (e.getCommission_pct() == 0.0) {
                        fr.write("CREATE (e" + entry.getKey() + ": EMPLOYEE { id:" + e.getId() + ",first_name:'" + e.getFirst_name() + "',last_name:'" + e.getLast_name() + "',email:'" + e.getEmail() + "',phone_number:'" + e.getPhone_number() + "',hire_date:date(\"" + e.getHire_date() + "\"),salary:" + e.getSalary() + "})\n");
                    } else {
                        fr.write("CREATE (e" + entry.getKey() + ": EMPLOYEE { id:" + e.getId() + ",first_name:'" + e.getFirst_name() + "',last_name:'" + e.getLast_name() + "',email:'" + e.getEmail() + "',phone_number:'" + e.getPhone_number() + "',hire_date:date(\"" + e.getHire_date() + "\"),salary:" + e.getSalary() + ",commission_pct:" + e.getCommission_pct() + "})\n");
                    }
                }

                for (Map.Entry<Integer, LOCATION> entry : structure.getLocationMap().entrySet()) {
                    LOCATION l = entry.getValue();
                    fr.write("CREATE (l" + entry.getKey() + ")-[:CONTAINED_IN {}]->(" + l.getCountry().getId() + ")\n");
                }

                for (HashMap.Entry<Integer, DEPARTMENT> entry : structure.getDepartmentMap().entrySet()) {
                    DEPARTMENT d = entry.getValue();
                    fr.write("CREATE (d" + entry.getKey() + ")-[:HAVE {}]->(l" + d.getLocation().getId() + ")\n");
                    if(d.getManager_id() != 0){
                        fr.write("CREATE (d" + entry.getKey() + ")-[:MANAGED_BY {}]->(e" + d.getManager_id() + ")\n");
                    }
                }

                for (Map.Entry<Integer, EMPLOYEE> entry : structure.getEmployeeMap().entrySet()) {
                    EMPLOYEE e = entry.getValue();
                    fr.write("CREATE (e" + entry.getKey() + ")-[:DOES {}]->(" + e.getJob().getId() + ")\n");
                    if(e.getManager() != null){
                        fr.write("CREATE (e" + entry.getKey() + ")-[:IS_MANAGED_BY {}]->(e" + e.getManager().getId() + ")\n");
                    }
                    if(e.getDepartment() != null){
                        fr.write("CREATE (e" + entry.getKey() + ")-[:WORK_AT {}]->(d" + e.getDepartment().getId() + ")\n");
                    }
                }

                for(JOB_HISTORY  j : structure.getJob_historyList()){
                    EMPLOYEE e = j.getEmployee();
                    JOB job = j.getJob();
                    DEPARTMENT d = j.getDepartment();
                    fr.write("CREATE (e" + e.getId() + ")-[:DID {start_date:date(\"" + j.getStart_date() + "\"), end_date:date(\"" + j.getEnd_date() + "\")}]->(" + job.getId() + ")-[:IN {}]->(d" + d.getId() + ")\n");
                }

                fr.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Connection Failed");
        }


    }

}

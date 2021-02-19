import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, JSONException, InterruptedException, ClassNotFoundException, SQLException {


        Connect connect = new Connect();

        // registers Oracle JDBC driver - though this is no longer required
        // since JDBC 4.0, but added here for backward compatibility
        Class.forName("oracle.jdbc.OracleDriver");
        connect.setConnection();

        if (connect.getConnection() != null) {
            System.out.println("Connected with :" + connect.getUrl());

            Statement stmt = connect.getConnection().createStatement();


            boolean flag = true;
            Structure structure = new Structure();
            JSONReader reader = new JSONReader();

            do {
                List<JSONObject> list = reader.getFile("http://nosql.hpeixoto.me/api/sensor/300");

                BufferedReader br = null;
                JSONParser scripts = new JSONParser();

                structure = scripts.loadStructure(list, structure);


                try {
                    File myObj = new File("script_povoamento.sql");

                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                    } else {
                        System.out.println("File already exists.");
                    }

                    FileWriter fr = new FileWriter(myObj, false);
                    for (HashMap.Entry<Integer, Patient> entry : structure.getPatients().entrySet()) {
                        fr.write(entry.getValue().toString());
                        structure.getQueries().add(entry.getValue().toString());
                    }
                    for (HashMap.Entry<Integer, Doctor> entry : structure.getDoctors().entrySet()) {
                        fr.write(entry.getValue().toString());
                        structure.getQueries().add(entry.getValue().toString());

                    }
                    for (HashMap.Entry<Integer, Sensor> entry : structure.getSensores().entrySet()) {
                        fr.write(entry.getValue().toString());
                        structure.getQueries().add(entry.getValue().toString());

                    }
                    for (Careteam c : structure.getCareteams()) {
                        fr.write(c.toString());
                        structure.getQueries().add(c.toString());

                    }
                    for (Data d : structure.getInfo()) {
                        fr.write(d.toString());
                        structure.getQueries().add(d.toString());

                    }
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (flag) {
                    for (String s : structure.getQueries())
                        stmt.executeQuery(s);

                    flag = !flag;

                } else
                    for (int i = 30; i <= 34; i++)
                        stmt.executeQuery(structure.getQueries().get(i));


                System.out.println("Going to sleep for 3 minutes ...");

                Thread.sleep(180000);

                System.out.println("Woke up ...");

            }


            while (true);


        } else
            System.out.println("Connection Failed");


    }

}

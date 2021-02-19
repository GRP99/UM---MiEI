
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Pais {
    private String countryterritoryCode;
    private String name;
    private int population;

    public Pais(String ctc, String n, int p) {
        this.countryterritoryCode = ctc;
        this.name = n;
        this.population = p;
    }
}

public class Parser {
    private static Map<String, Pais> paises = new HashMap<String, Pais>();
    private static List<String> semanas = new ArrayList<String>();

    public static void processa_data(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);
            
            if (i != 0) {
                if (dados.length == 13) {
                    if (!(paises.containsKey(dados[8]))) {
                        Pais p = new Pais(dados[9], dados[7], Integer.parseInt(dados[10]));
                        paises.put(dados[8], p);
                        fr.write("INSERT INTO Country VALUES ('" + dados[8] + "','" + dados[7] + "'," + Integer.parseInt(dados[10]) + ",'" + dados[9] + "');\n");
                    }
                    if (!semanas.contains(dados[4])) {
                        semanas.add(dados[4]);
                        fr.write("INSERT INTO Week VALUES ('" + dados[4] + "');\n");
                    }
                    String d = "'"+ dados[3] + "-" + dados[2] + "-" + dados[1] + "'";
                    dados[12] = dados[12].replace(",", ".");
                    fr.write("INSERT INTO Data VALUES ('" + dados[8] + "','" + dados[4] + "'," + d + "," + Integer.parseInt(dados[5]) + "," + Integer.parseInt(dados[6]) + "," + Double.parseDouble(dados[12]) + ");\n");
                } else {
                    if (!(paises.containsKey(dados[8]))) {
                        Pais p = new Pais(dados[9], dados[7], Integer.parseInt(dados[10]));
                        paises.put(dados[8], p);
                        fr.write("INSERT INTO Country VALUES ('" + dados[8] + "','" + dados[7] + "'," + Integer.parseInt(dados[10]) + ",'" + dados[9] + "');\n");
                    }
                    if (!semanas.contains(dados[4])) {
                        semanas.add(dados[4]);
                        fr.write("INSERT INTO Week VALUES ('" + dados[4] + "');\n");
                    }
                    String d = "'"+ dados[3] + "-" + dados[2] + "-" + dados[1] + "'";
                    fr.write("INSERT INTO Data VALUES ('" + dados[8] + "','" + dados[4] + "'," + d + "," + Integer.parseInt(dados[5]) + "," + Integer.parseInt(dados[6]) + ",0.0" + ");\n");
                }
            }
            i++;
        }
        fr.close();
    }
    
    public static void processa_testing(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);
            
            if (i != 0) {
                if (dados.length == 9) {
                    try{
                    fr.write("INSERT INTO Testing VALUES('" + dados[1]+ "','"+ dados[2] + "'," + Integer.parseInt(dados[3]) + "," + Integer.parseInt(dados[4]) + "," + Double.parseDouble(dados[6]) + "," + Double.parseDouble(dados[7]) + ",'" + dados[8] + "');\n" );
                    }
                    catch(NumberFormatException e){
                        fr.write("INSERT INTO Testing VALUES('" + dados[1]+ "','"+ dados[2] + "'," + Integer.parseInt(dados[3]) + "," + Integer.parseInt(dados[4]) + "," + Double.parseDouble(dados[6]) + ",0.0,'" + dados[8] + "');\n" );
                    }
                }
                if (dados.length == 8) {
                    fr.write("INSERT INTO Testing VALUES('" + dados[1]+ "','"+ dados[2] + "'," + Integer.parseInt(dados[3]) + "," + Integer.parseInt(dados[4]) + "," + Double.parseDouble(dados[6]) + ",0.0,'" + dados[7] + "');\n" );
                }
            }
            i++;
        }
        fr.close();
    }


    public static void main(String[] args) {
        BufferedReader br = null;

        try {
            File myObj = new File("povoamento.sql");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            processa_data(myObj, "csv\\COVID19_EU_EEA_UK_DATA.csv");
            
            processa_testing(myObj,"csv\\COVID19_EU_EEA_UK_TESTING.csv");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    }
}

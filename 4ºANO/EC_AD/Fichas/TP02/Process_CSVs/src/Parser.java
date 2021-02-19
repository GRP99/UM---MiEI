import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Parser {
    private static Map<Integer, Sex> ssex = new HashMap<>();
    private static int countersex = 1;
    private static Map<Integer, Age> sage = new HashMap<>();
    private static int counterage = 1;
    private static Map<Integer, Origin> sorigin = new HashMap<>();
    private static int counterorigin = 1;
    private static Map<Integer, Region> sregion = new HashMap<>();
    private static int counterregion = 1;
    private static Map<Integer, Infection> sinfection = new HashMap<>();
    private static int counterinfection = 1;
    private static Map<Integer, Datas> sdata = new HashMap<>();
    private static int counterdata = 1;
    private static Map<Integer, State> sstate = new HashMap<>();
    private static int counterstate = 1;
    private static Map<Integer, Patient> spatient = new HashMap<>();
    private static int counterpatient = 1;

    public static void regista_data(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);

            if (i != 0) {
                if (dados.length == 14) {
                    Sex s = new Sex(dados[1]);
                    if (!(ssex.containsValue(s))) {
                        ssex.put(countersex, s);
                        fr.write("INSERT INTO dim_sex VALUES (" + countersex + ",'" + dados[1] + "');\n");
                        countersex++;
                    }
                    Age a = new Age(dados[2]);
                    if (!(sage.containsValue(a))) {
                        sage.put(counterage, a);
                        fr.write("INSERT INTO dim_age VALUES (" + counterage + ",'" + dados[2] + "');\n");
                        counterage++;
                    }

                    Origin o = new Origin(dados[3]);
                    if (!(sorigin.containsValue(o))) {
                        sorigin.put(counterorigin, o);
                        fr.write("INSERT INTO dim_origin VALUES (" + counterorigin + ",'" + dados[3] + "');\n");
                        counterorigin++;
                    }

                    Region r = new Region(dados[4], dados[5]);
                    if (!(sregion.containsValue(r))) {
                        sregion.put(counterregion, r);
                        fr.write("INSERT INTO dim_region VALUES (" + counterregion + ",'" + dados[4] + "','" + dados[5] + "');\n");
                        counterregion++;
                    }

                    String str = dados[6].replace("'", "");
                    Infection in = new Infection(str, dados[8]);
                    if (!(sinfection.containsValue(in))) {
                        sinfection.put(counterinfection, in);
                        fr.write("INSERT INTO dim_infection VALUES (" + counterinfection + ",'" + str + "','" + dados[8] + "');\n");
                        counterinfection++;
                    }

                    String dd1 = dados[9].replace("/", "-");
                    String dd2 = dados[10].replace("/", "-");
                    String dd3 = dados[11].replace("/", "-");
                    String dd4 = dados[12].replace("/", "-");
                    Datas d = new Datas(dd1, dd2, dd3, dd4);
                    if (!(sdata.containsValue(d))) {
                        sdata.put(counterdata, d);
                        fr.write("INSERT INTO dim_date VALUES (" + counterdata + ",'" + dd1 + "','" + dd2 + "','" + dd3 + "','" + dd4 + "');\n");
                        counterdata++;
                    }

                    State st = new State(dados[13]);
                    if (!(sstate.containsValue(st))) {
                        sstate.put(counterstate, st);
                        fr.write("INSERT INTO dim_state VALUES (" + counterstate + ",'" + dados[13] + "');\n");
                        counterstate++;
                    }
                }

            }
            i++;
        }
        fr.close();
    }

    public static void regista_patients(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);

            if (i != 0) {
                if (dados.length == 14) {
                    Patient p = new Patient();

                    Sex s = new Sex(dados[1]);
                    for (Map.Entry<Integer, Sex> entry : ssex.entrySet()) {
                        if (entry.getValue().equals(s)) {
                            p.setId_sex(entry.getKey());
                        }
                    }

                    Age a = new Age(dados[2]);
                    for (Map.Entry<Integer, Age> entry : sage.entrySet()) {
                        if (entry.getValue().equals(a)) {
                            p.setId_age(entry.getKey());
                        }
                    }

                    Origin o = new Origin(dados[3]);
                    for (Map.Entry<Integer, Origin> entry : sorigin.entrySet()) {
                        if (entry.getValue().equals(o)) {
                            p.setId_origin(entry.getKey());
                        }
                    }

                    Region r = new Region(dados[4], dados[5]);
                    for (Map.Entry<Integer, Region> entry : sregion.entrySet()) {
                        if (entry.getValue().equals(r)) {
                            p.setId_region(entry.getKey());
                        }
                    }

                    String str = dados[6].replace("'", "");
                    Infection in = new Infection(str, dados[8]);
                    for (Map.Entry<Integer, Infection> entry : sinfection.entrySet()) {
                        if (entry.getValue().equals(in)) {
                            p.setId_infection(entry.getKey());
                        }
                    }

                    String dd1 = dados[9].replace("/", "-");
                    String dd2 = dados[10].replace("/", "-");
                    String dd3 = dados[11].replace("/", "-");
                    String dd4 = dados[12].replace("/", "-");
                    Datas d = new Datas(dd1, dd2, dd3, dd4);
                    for (Map.Entry<Integer, Datas> entry : sdata.entrySet()) {
                        if (entry.getValue().equals(d)) {
                            p.setId_date(entry.getKey());
                        }
                    }

                    State st = new State(dados[13]);
                    for (Map.Entry<Integer, State> entry : sstate.entrySet()) {
                        if (entry.getValue().equals(st)) {
                            p.setId_state(entry.getKey());
                        }
                    }
                    fr.write("INSERT INTO fact_patient VALUES (" + Long.parseLong(dados[0]) + ",'" + dados[7] + "'," + p.getId_age() + "," + p.getId_region() + "," + p.getId_date() + "," + p.getId_infection() + "," + p.getId_origin() + "," + p.getId_sex() + "," + p.getId_state() + ");\n");
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

            regista_data(myObj, "csv\\South_Korea_Covid19.csv");
            regista_patients(myObj, "csv\\South_Korea_Covid19.csv");


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

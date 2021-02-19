import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Parser {
    private static final Map<Integer, Name> nameMap = new HashMap<>();
    private static final Map<String, Date> dateMap = new HashMap<>();
    private static final Map<Integer, Mannerofdeath> mannerofdeathMap = new HashMap<>();
    private static final Map<Integer, Armed> armedMap = new HashMap<>();
    private static final Map<Integer, Gender> genderMap = new HashMap<>();
    private static final Map<Integer, Race> raceMap = new HashMap<>();
    private static final Map<Integer, Place> placeMap = new HashMap<>();
    private static final Map<Integer, Threatlevel> threatlevelMap = new HashMap<>();
    private static final Map<Integer, Flee> fleeMap = new HashMap<>();
    private static int countername = 1;
    private static int countermannerofdeath = 1;
    private static int counterarmed = 1;
    private static int countergender = 1;
    private static int counterrace = 1;
    private static int counterplace = 1;
    private static int counterthreatlevel = 1;
    private static int counterflee = 1;

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

                    String str = dados[1].replace("'", "");
                    Name n = new Name(str);
                    if (!(nameMap.containsValue(n))) {
                        nameMap.put(countername, n);
                        fr.write("INSERT INTO dim_name VALUES (" + countername + ",'" + str + "');\n");
                        countername++;
                    }

                    String[] spl = dados[2].split("/");
                    String id = "20" + spl[2] + "-" + spl[1] + "-" + spl[0];
                    Date d = new Date(id,Integer.parseInt("20"+spl[0]), Integer.parseInt(spl[1]), Integer.parseInt(spl[2]));
                    if (!(dateMap.containsValue(d))) {
                        dateMap.put(id, d);
                        fr.write("INSERT INTO dim_date VALUES ('" + id + "'," + spl[2] + "," + spl[1] + "," + spl[0] +");\n");
                    }

                    Mannerofdeath m = new Mannerofdeath(dados[3]);
                    if (!(mannerofdeathMap.containsValue(m))) {
                        mannerofdeathMap.put(countermannerofdeath, m);
                        fr.write("INSERT INTO dim_mannerofdeath VALUES (" + countermannerofdeath + ",'" + dados[3] + "');\n");
                        countermannerofdeath++;
                    }

                    String str2 = dados[4].replace("'", "");
                    Armed a = new Armed(str2);
                    if (!(armedMap.containsValue(a))) {
                        armedMap.put(counterarmed, a);
                        fr.write("INSERT INTO dim_armed VALUES (" + counterarmed + ",'" + str2 + "');\n");
                        counterarmed++;
                    }

                    Gender g = new Gender(dados[6]);
                    if (!(genderMap.containsValue(g))) {
                        genderMap.put(countergender, g);
                        fr.write("INSERT INTO dim_gender VALUES (" + countergender + ",'" + dados[6] + "');\n");
                        countergender++;
                    }

                    Race r = new Race(dados[7]);
                    if (!(raceMap.containsValue(r))) {
                        raceMap.put(counterrace, r);
                        fr.write("INSERT INTO dim_race VALUES (" + counterrace + ",'" + dados[7] + "');\n");
                        counterrace++;
                    }

                    String str1 = dados[8].replace("'", "");
                    Place p = new Place(str1, dados[9]);
                    if (!(placeMap.containsValue(p))) {
                        placeMap.put(counterplace, p);
                        fr.write("INSERT INTO dim_place VALUES (" + counterplace + ",'" + dados[9] + "','" + str1 + "');\n");
                        counterplace++;
                    }

                    Threatlevel t = new Threatlevel(dados[11]);
                    if (!(threatlevelMap.containsValue(t))) {
                        threatlevelMap.put(counterthreatlevel, t);
                        fr.write("INSERT INTO dim_threatlevel VALUES (" + counterthreatlevel + ",'" + dados[11] + "');\n");
                        counterthreatlevel++;
                    }

                    Flee fl = new Flee(dados[12]);
                    if (!(fleeMap.containsValue(fl))) {
                        fleeMap.put(counterflee, fl);
                        fr.write("INSERT INTO dim_flee VALUES (" + counterflee + ",'" + dados[12] + "');\n");
                        counterflee++;
                    }

                }
            }
            i++;
        }
        fr.close();
    }

    public static void regista_fatals(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);

            if (i != 0) {
                if (dados.length == 14) {
                    FatalPolice fp = new FatalPolice();

                    String st = dados[1].replace("'", "");
                    Name n = new Name(st);
                    for (Map.Entry<Integer, Name> entry : nameMap.entrySet()) {
                        if (entry.getValue().equals(n)) {
                            fp.setFk_name(entry.getKey());
                        }
                    }

                    String[] spl = dados[2].split("/");
                    String id = "20" + spl[2] + "-" + spl[1] + "-" + spl[0];
                    Date d = new Date(id,Integer.parseInt("20"+spl[0]), Integer.parseInt(spl[1]), Integer.parseInt(spl[2]));
                    for (Map.Entry<String, Date> entry : dateMap.entrySet()) {
                        if (entry.getValue().equals(d)) {
                            fp.setFk_date(entry.getKey());
                        }
                    }

                    Mannerofdeath m = new Mannerofdeath(dados[3]);
                    for (Map.Entry<Integer, Mannerofdeath> entry : mannerofdeathMap.entrySet()) {
                        if (entry.getValue().equals(m)) {
                            fp.setFk_manner(entry.getKey());
                        }
                    }

                    String str2 = dados[4].replace("'", "");
                    Armed a = new Armed(str2);
                    for (Map.Entry<Integer, Armed> entry : armedMap.entrySet()) {
                        if (entry.getValue().equals(a)) {
                            fp.setFk_armed(entry.getKey());
                        }
                    }

                    Gender g = new Gender(dados[6]);
                    for (Map.Entry<Integer, Gender> entry : genderMap.entrySet()) {
                        if (entry.getValue().equals(g)) {
                            fp.setFk_gender(entry.getKey());
                        }
                    }

                    Race r = new Race(dados[7]);
                    for (Map.Entry<Integer, Race> entry : raceMap.entrySet()) {
                        if (entry.getValue().equals(r)) {
                            fp.setFk_race(entry.getKey());
                        }
                    }

                    String str1 = dados[8].replace("'", "");
                    Place p = new Place(str1, dados[9]);
                    for (Map.Entry<Integer, Place> entry : placeMap.entrySet()) {
                        if (entry.getValue().equals(p)) {
                            fp.setFk_place(entry.getKey());
                        }
                    }

                    Threatlevel t = new Threatlevel(dados[11]);
                    for (Map.Entry<Integer, Threatlevel> entry : threatlevelMap.entrySet()) {
                        if (entry.getValue().equals(t)) {
                            fp.setFk_threat(entry.getKey());
                        }
                    }

                    Flee fl = new Flee(dados[12]);
                    for (Map.Entry<Integer, Flee> entry : fleeMap.entrySet()) {
                        if (entry.getValue().equals(fl)) {
                            fp.setFk_flee(entry.getKey());
                        }
                    }
                    if (dados[5].equals("")) fr.write("INSERT INTO fatal_police_shootings VALUES (" + Long.parseLong(dados[0]) + "," + fp.getFk_name() + ",'" + fp.getFk_date() + "'," + fp.getFk_manner() + "," + fp.getFk_armed() + "," + 0 + "," + fp.getFk_gender() + ","+ fp.getFk_race()  + "," + fp.getFk_place() + "," + dados[10] + "," + fp.getFk_threat() + "," + fp.getFk_flee() + "," + dados[13] + ");\n");
                    else fr.write("INSERT INTO fatal_police_shootings VALUES (" + Long.parseLong(dados[0]) + "," + fp.getFk_name() + ",'" + fp.getFk_date() + "'," + fp.getFk_manner() + "," + fp.getFk_armed() + "," + dados[5] + "," + fp.getFk_gender() + ","+ fp.getFk_race()  + "," + fp.getFk_place() + "," + dados[10] + "," + fp.getFk_threat() + "," + fp.getFk_flee() + "," + dados[13] + ");\n");
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

            regista_data(myObj, "csv\\fatal_police_shootings_data.csv");
            regista_fatals(myObj, "csv\\fatal_police_shootings_data.csv");


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

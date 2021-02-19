import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Parser {
    private static final Map<String, State> stateMap = new HashMap<>();
    private static final Map<Integer, Name> nameMap = new HashMap<>();
    private static final Map<String, Date> dateMap = new HashMap<>();
    private static final Map<Integer, Mannerofdeath> mannerofdeathMap = new HashMap<>();
    private static final Map<Integer, Armed> armedMap = new HashMap<>();
    private static final Map<Integer, Gender> genderMap = new HashMap<>();
    private static final Map<Integer, Race> raceMap = new HashMap<>();
    private static final Map<Integer, City> cityMap = new HashMap<>();
    private static final Map<Integer, Threatlevel> threatlevelMap = new HashMap<>();
    private static final Map<Integer, Flee> fleeMap = new HashMap<>();
    private static final Map<Integer, Place> placeMap = new HashMap<>();
    private static final Map<Integer, DeathCircunstances> deathmap = new HashMap<>();
    private static int countername = 1;
    private static int countermannerofdeath = 1;
    private static int counterarmed = 1;
    private static int countergender = 1;
    private static int counterrace = 1;
    private static int countercity = 1;
    private static int counterthreatlevel = 1;
    private static int counterflee = 1;
    private static int counterplace = 1;
    private static int counterdeaths = 1;

    private static void regista_fatals(File f, String csvFile) throws IOException {
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
                    Date d = new Date(id, Integer.parseInt("20" + spl[0]), Integer.parseInt(spl[1]), Integer.parseInt(spl[2]));
                    for (Map.Entry<String, Date> entry : dateMap.entrySet()) {
                        if (entry.getValue().equals(d)) {
                            fp.setFk_date(entry.getKey());
                        }
                    }

                    Mannerofdeath m = new Mannerofdeath(dados[3]);
                    int idm = 0;
                    for (Map.Entry<Integer, Mannerofdeath> entry : mannerofdeathMap.entrySet()) {
                        if (entry.getValue().equals(m)) {
                            idm = entry.getKey();
                        }
                    }
                    String str2 = dados[4].replace("'", "");
                    Armed a = new Armed(str2);
                    int ida = 0;
                    for (Map.Entry<Integer, Armed> entry : armedMap.entrySet()) {
                        if (entry.getValue().equals(a)) {
                            ida = entry.getKey();
                        }
                    }
                    Threatlevel t = new Threatlevel(dados[11]);
                    int idt = 0;
                    for (Map.Entry<Integer, Threatlevel> entry : threatlevelMap.entrySet()) {
                        if (entry.getValue().equals(t)) {
                            idt = entry.getKey();
                        }
                    }
                    Flee fl = new Flee(dados[12]);
                    int idf = 0;
                    for (Map.Entry<Integer, Flee> entry : fleeMap.entrySet()) {
                        if (entry.getValue().equals(fl)) {
                            idf = entry.getKey();
                        }
                    }
                    DeathCircunstances dc = new DeathCircunstances(idm, ida, idt, idf);
                    for (Map.Entry<Integer, DeathCircunstances> entry : deathmap.entrySet()) {
                        if (entry.getValue().equals(dc)) {
                            fp.setFk_deathcircunstances(entry.getKey());
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
                    City c = new City(str1);
                    int idc = 0;
                    for (Map.Entry<Integer, City> entry : cityMap.entrySet()) {
                        if (entry.getValue().equals(c)) {
                            idc = entry.getKey();
                        }
                    }
                    Place p = new Place(dados[9], idc);
                    for (Map.Entry<Integer, Place> entry : placeMap.entrySet()) {
                        if (entry.getValue().equals(p)) {
                            fp.setFk_place(entry.getKey());
                        }
                    }

                    if (dados[5].equals(""))
                        fr.write("INSERT INTO fatal_police_shootings VALUES (" + Long.parseLong(dados[0]) + "," + fp.getFk_name() + ",'" + fp.getFk_date() + "'," + fp.getFk_deathcircunstances() + "," + 0 + "," + fp.getFk_gender() + "," + fp.getFk_race() + "," + fp.getFk_place() + "," + dados[10] + "," + dados[13] + ");\n");
                    else
                        fr.write("INSERT INTO fatal_police_shootings VALUES (" + Long.parseLong(dados[0]) + "," + fp.getFk_name() + ",'" + fp.getFk_date() + "'," + fp.getFk_deathcircunstances() + "," + dados[5] + "," + fp.getFk_gender() + "," + fp.getFk_race() + "," + fp.getFk_place() + "," + dados[10] + "," + dados[13] + ");\n");
                }

            }
            i++;
        }
        fr.close();
    }

    private static void regista_deaths(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);

            if (i != 0) {
                if (dados.length == 14) {
                    DeathCircunstances dc = new DeathCircunstances();

                    Mannerofdeath m = new Mannerofdeath(dados[3]);
                    for (Map.Entry<Integer, Mannerofdeath> entry : mannerofdeathMap.entrySet()) {
                        if (entry.getValue().equals(m)) {
                            dc.setFk_manner(entry.getKey());
                        }
                    }

                    String str2 = dados[4].replace("'", "");
                    Armed a = new Armed(str2);
                    for (Map.Entry<Integer, Armed> entry : armedMap.entrySet()) {
                        if (entry.getValue().equals(a)) {
                            dc.setFk_armed(entry.getKey());
                        }
                    }

                    Threatlevel t = new Threatlevel(dados[11]);
                    for (Map.Entry<Integer, Threatlevel> entry : threatlevelMap.entrySet()) {
                        if (entry.getValue().equals(t)) {
                            dc.setFk_threat(entry.getKey());
                        }
                    }

                    Flee fl = new Flee(dados[12]);
                    for (Map.Entry<Integer, Flee> entry : fleeMap.entrySet()) {
                        if (entry.getValue().equals(fl)) {
                            dc.setFk_flee(entry.getKey());
                        }
                    }

                    deathmap.put(counterdeaths, dc);
                    fr.write("INSERT INTO dim_deathcircunstances VALUES (" + counterdeaths + "," + dc.getFk_manner() + "," + dc.getFk_armed() + "," + dc.getFk_threat() + "," + dc.getFk_flee() + ");\n");
                    counterdeaths++;
                }
            }
            i++;

        }
        fr.close();
    }

    private static void regista_place(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);

            if (i != 0) {
                if (dados.length == 14) {
                    Place p = new Place();

                    String str1 = dados[8].replace("'", "");
                    City c = new City(str1);
                    for (Map.Entry<Integer, City> entry : cityMap.entrySet()) {
                        if (entry.getValue().equals(c)) {
                            p.setFk_id_city(entry.getKey());
                        }
                    }

                    for (Map.Entry<String, State> entry : stateMap.entrySet()) {
                        if (entry.getKey().equals(dados[9])) {
                            p.setFk_id_state(entry.getKey());
                        }
                    }

                    placeMap.put(counterplace, p);
                    fr.write("INSERT INTO dim_place VALUES (" + counterplace + ",'" + p.getFk_id_state() + "'," + p.getFk_id_city() + ");\n");
                    counterplace++;
                }
            }
            i++;

        }
        fr.close();
    }

    private static void regista_data(File f, String csvFile) throws IOException {
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
                    Date d = new Date(id, Integer.parseInt("20" + spl[0]), Integer.parseInt(spl[1]), Integer.parseInt(spl[2]));
                    if (!(dateMap.containsValue(d))) {
                        dateMap.put(id, d);
                        fr.write("INSERT INTO dim_date VALUES ('" + id + "'," + spl[2] + "," + spl[1] + "," + spl[0] + ");\n");
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
                    City c = new City(str1);
                    if (!(cityMap.containsValue(c))) {
                        cityMap.put(countercity, c);
                        fr.write("INSERT INTO dim_city VALUES (" + countercity + ",'" + str1 + "');\n");
                        countercity++;
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

    public static void regista_states(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);
            if (i != 0) {
                if (dados.length == 3) {
                    State st = new State(dados[0]);
                    if (!(stateMap.containsValue(st))) {
                        stateMap.put(dados[2], st);
                        fr.write("INSERT INTO dim_state VALUES ('" + dados[2] + "','" + dados[0] + "');\n");
                    }
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

            regista_states(myObj, "csv\\csv_states.csv");
            regista_data(myObj, "csv\\fatal_police_shootings_data.csv");
            regista_place(myObj, "csv\\fatal_police_shootings_data.csv");
            regista_deaths(myObj, "csv\\fatal_police_shootings_data.csv");
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

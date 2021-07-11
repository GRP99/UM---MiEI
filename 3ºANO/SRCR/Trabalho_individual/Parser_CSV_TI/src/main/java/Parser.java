
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Parser {
    private static ArrayList<Integer> paragens;
    
    public static void cria_estimativas(File f) throws IOException{
        FileWriter fr = new FileWriter(f, true);
        Random gerador = new Random();
        int i=0;
        
        fr.write("\n% estima( Paragem,TempoEspera ) \n");
        
        
        for (i = 0; i < paragens.size(); i++) {
            fr.write("estima( " + paragens.get(i) + "," + gerador.nextInt(300) + " ).\n" );
        }
        fr.close();
    }
    

    public static void processa_carreiras(File f, ArrayList<String> carreiras) throws IOException {
        String line = "";
        String cvsSplitBy = ";";

        FileWriter fr = new FileWriter(f, true);
        fr.write("\n% ligacao( Origem,Destino,Carreira ) \n");

        for (int i = 0; i < carreiras.size(); i++) {
            String carreiraAtual = carreiras.get(i);
            String[] path1 = carreiraAtual.split("\\\\");
            String name = path1[1].substring(0, path1[1].lastIndexOf('.'));
            int nameI = Integer.parseInt(name);

            int x = 0;
            int y = 0;

            BufferedReader br = new BufferedReader(new FileReader(carreiraAtual));

            String gidAnterior = "";
            while ((line = br.readLine()) != null) {
                String[] adjadencia = line.split(cvsSplitBy);
                if (x > 0) {
                    if (y == 0) {
                        gidAnterior = adjadencia[0];
                    } else {
                        fr.write("ligacao( " + gidAnterior + "," + adjadencia[0] + "," + nameI + " ).\n");
                        gidAnterior = adjadencia[0];
                    }
                    y++;
                }
                x++;
            }
            fr.write("\n");
        }
        fr.close();
    }

    public static void processa_paragens(File f, String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;

        FileWriter fr = new FileWriter(f, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] paragem = line.split(cvsSplitBy);

            if (paragem.length == 11 && i > 0) {
                fr.write("paragem( " + paragem[0] + "," + paragem[1] + "," + paragem[2] + ",\"" + paragem[3] + "\",\"" + paragem[4] + "\",\"" + paragem[5] + "\",\"" + paragem[6] + "\",[" + paragem[7] + "]," + paragem[8] + ",\"" + paragem[9] + "\",\"" + paragem[10] + "\").\n");
                paragens.add(Integer.parseInt(paragem[0]));
            }
            if (i == 0) {
                fr.write("%paragem( " + paragem[0] + "," + paragem[1] + "," + paragem[2] + "," + paragem[3] + "," + paragem[4] + "," + paragem[5] + "," + paragem[6] + "," + paragem[7] + "," + paragem[8] + "," + paragem[9] + "," + paragem[10] + " ).\n");
            }
            if (paragem.length == 10) {
                fr.write("paragem( " + paragem[0] + "," + paragem[1] + "," + paragem[2] + ",\"" + paragem[3] + "\",\"" + paragem[4] + "\",\"" + paragem[5] + "\",\"" + paragem[6] + "\",[" + paragem[7] + "]," + paragem[8] + ",\"" + paragem[9] + "\", freguesia_desconhecida).\n");
                paragens.add(Integer.parseInt(paragem[0]));
            }
            i++;
        }
        fr.close();
    }

    public static void main(String[] args) {
        BufferedReader br = null;
        paragens = new ArrayList<Integer>();
        
        
        ArrayList<String> carreiras = new ArrayList<>();
        carreiras.add("csv\\01.csv");
        carreiras.add("csv\\02.csv");
        carreiras.add("csv\\06.csv");
        carreiras.add("csv\\07.csv");
        carreiras.add("csv\\10.csv");
        carreiras.add("csv\\11.csv");
        carreiras.add("csv\\12.csv");
        carreiras.add("csv\\13.csv");
        carreiras.add("csv\\15.csv");
        carreiras.add("csv\\23.csv");
        carreiras.add("csv\\101.csv");
        carreiras.add("csv\\102.csv");
        carreiras.add("csv\\106.csv");
        carreiras.add("csv\\108.csv");
        carreiras.add("csv\\111.csv");
        carreiras.add("csv\\112.csv");
        carreiras.add("csv\\114.csv");
        carreiras.add("csv\\115.csv");
        carreiras.add("csv\\116.csv");
        carreiras.add("csv\\117.csv");
        carreiras.add("csv\\119.csv");
        carreiras.add("csv\\122.csv");
        carreiras.add("csv\\125.csv");
        carreiras.add("csv\\129.csv");
        carreiras.add("csv\\158.csv");
        carreiras.add("csv\\162.csv");
        carreiras.add("csv\\171.csv");
        carreiras.add("csv\\184.csv");
        carreiras.add("csv\\201.csv");
        carreiras.add("csv\\467.csv");
        carreiras.add("csv\\468.csv");
        carreiras.add("csv\\470.csv");
        carreiras.add("csv\\471.csv");
        carreiras.add("csv\\479.csv");
        carreiras.add("csv\\714.csv");
        carreiras.add("csv\\748.csv");
        carreiras.add("csv\\750.csv");
        carreiras.add("csv\\751.csv");
        carreiras.add("csv\\776.csv");

        try {
            File myObj = new File("script.sql");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            processa_paragens(myObj, "csv\\paragens_processado.csv");

            processa_carreiras(myObj, carreiras);
            
            cria_estimativas(myObj);

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

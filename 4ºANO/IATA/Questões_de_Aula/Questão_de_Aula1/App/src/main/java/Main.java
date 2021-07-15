import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static final Set<Informacao> informacaoSet = new HashSet<>();
    private static final Map<String, Estatisticas> estatisticasMap = new HashMap<>();

    public static void processa_info(File log, File logSeason, File logEstatisticas, String csvFile) throws IOException, ParseException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;
        FileWriter frLog = new FileWriter(log, true);
        FileWriter frLogSeason = new FileWriter(logSeason, true);
        FileWriter frLogEstatisticas = new FileWriter(logEstatisticas, true);
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        frLog.write("<!DOCTYPE html> \n <html> \n <head> \n <title>QA1</title> \n <meta charset=\"utf-8\" /> \n </head> \n <body> \n");
        frLogSeason.write("<!DOCTYPE html> \n <html> \n <head> <style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-collapse: collapse;\n" +
                "}\n" +
                "th, td {\n" +
                "  padding: 5px; text-align: center;\n" +
                "}\n" +
                "</style>\n <title>QA1</title> \n <meta charset=\"utf-8\" /> \n </head> \n <body> \n");
        frLogSeason.write("<h3 style=\"text-align:center\"> Valores de conforto de temperatura considerados</h3>\n");
        frLogSeason.write("<ul> <li> Inverno+Outono : 15ºC </li> <li> Primavera+Verão : 25ºC </li> </ul>\n");
        frLogSeason.write("<hr>\n");
        frLogSeason.write("<table style=\"width:100%\">");
        frLogSeason.write("<tr>\n" + "    <th>DT_ISO</th>\n" + "    <th>Estação do Ano</th> \n" + "    <th>Ação a executar</th>\n" + "  </tr>");
        frLogEstatisticas.write("<!DOCTYPE html> \n <html> \n <head> \n <title>QA1</title> \n <meta charset=\"utf-8\" /> \n </head> \n <body> \n");
        frLogEstatisticas.write("<h3 style=\"text-align:center\"> Valores de conforto de temperatura considerados</h3>\n");
        frLogEstatisticas.write("<ul> <li> Inverno+Outono : 15ºC </li> <li> Primavera+Verão : 25ºC </li> </ul>\n");
        frLogEstatisticas.write("<hr>\n");
        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);
            if (i != 0) {
                if (dados.length == 6) {
                    Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.ENGLISH).parse(dados[0]);
                    Informacao informacao = new Informacao(date, dados[1], Double.parseDouble(dados[2]), Double.parseDouble(dados[3]), Double.parseDouble(dados[4]), Integer.parseInt(dados[5]));
                    String estacao = informacao.obterEstacao();
                    int conforto = 0;
                    if (estacao.equals("Primavera+Verão")) {
                        conforto = 25;
                    }
                    if (estacao.equals("Inverno+Outono")) {
                        conforto = 15;
                    }
                    if (conforto != 0 && (!(estacao.equals("")))) {
                        if (informacaoSet.add(informacao)) {
                            double diferenca = 0;
                            if (informacao.getTemperature() > conforto) {
                                diferenca = informacao.getTemperature() - conforto;
                                DecimalFormat df = new DecimalFormat("#.00",
                                        DecimalFormatSymbols.getInstance(Locale.US));
                                frLog.write("<p> <i>airconditioning-</i>{" + df.format(diferenca) + "}</p>\n");
                                frLogSeason.write("<tr>\n" +
                                        "    <td>" + informacao.getDate() + "</td>\n" +
                                        "    <td>" + estacao + "</td>\n" +
                                        "    <td> <i>airconditioning-</i>{" + df.format(diferenca) + "}</td>\n" +
                                        "  </tr>");
                            } else if (informacao.getTemperature() < conforto) {
                                diferenca = conforto - informacao.getTemperature();
                                DecimalFormat df = new DecimalFormat("#.00",
                                        DecimalFormatSymbols.getInstance(Locale.US));
                                frLog.write("<p> <i>airconditioning+</i>{" + df.format(diferenca) + "}</p>\n");
                                frLogSeason.write("<tr>\n" +
                                        "    <td>" + informacao.getDate() + "</td>\n" +
                                        "    <td>" + estacao + "</td>\n" +
                                        "    <td> <i>airconditioning+</i>{" + df.format(diferenca) + "}</td>\n" +
                                        "  </tr>");
                            } else {
                                frLog.write("<p> <i>airconditioning=</i>{" + diferenca + "} </p>\n");
                                frLogSeason.write("<tr>\n" +
                                        "    <td>" + informacao.getDate() + "</td>\n" +
                                        "    <td>" + estacao + "</td>\n" +
                                        "    <td> <i>airconditioning=</i>{0.00}</td>\n" +
                                        "  </tr>");
                            }

                            if (estatisticasMap.containsKey(informacao.getCity_name())) {
                                Estatisticas estatisticas = estatisticasMap.get(informacao.getCity_name());
                                if (estacao.equals("Inverno+Outono")) {
                                    if (estatisticas.getMaiortemperaturaInvernoOutono() == null) {
                                        estatisticas.setMaiortemperaturaInvernoOutono(informacao.getTemperature());
                                        estatisticas.setDatemaiortemperaturaInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMenortemperaturaInvernoOutono() == null) {
                                        estatisticas.setMenortemperaturaInvernoOutono(informacao.getTemperature());
                                        estatisticas.setDatemenortemperaturaInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiordiferencaInvernoOutono() == null) {
                                        estatisticas.setMaiordiferencaInvernoOutono(informacao.getTemperature());
                                        estatisticas.setDatemaiordiferencaInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMenordiferencaInvernoOutono() == null) {
                                        estatisticas.setMenordiferencaInvernoOutono(informacao.getTemperature());
                                        estatisticas.setDatemenordiferencaInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiorhumidadeInvernoOutono() == null) {
                                        estatisticas.setMaiorhumidadeInvernoOutono(informacao.getHumidity());
                                        estatisticas.setDatemaiorhumidadeInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMenorhumidadeInvernoOutono() == null) {
                                        estatisticas.setMenorhumidadeInvernoOutono(informacao.getHumidity());
                                        estatisticas.setDatemenorhumidadeInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiortemperaturaInvernoOutono() < informacao.getTemperature()) {
                                        estatisticas.setMaiortemperaturaInvernoOutono(informacao.getTemperature());
                                        estatisticas.setDatemaiortemperaturaInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMenortemperaturaInvernoOutono() > informacao.getTemperature()) {
                                        estatisticas.setMenortemperaturaInvernoOutono(informacao.getTemperature());
                                        estatisticas.setDatemenortemperaturaInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiordiferencaInvernoOutono() < diferenca) {
                                        estatisticas.setMaiordiferencaInvernoOutono(diferenca);
                                        estatisticas.setDatemaiordiferencaInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMenordiferencaInvernoOutono() > diferenca) {
                                        estatisticas.setMenordiferencaInvernoOutono(diferenca);
                                        estatisticas.setDatemenordiferencaInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiorhumidadeInvernoOutono() < informacao.getHumidity()) {
                                        estatisticas.setMaiorhumidadeInvernoOutono(informacao.getHumidity());
                                        estatisticas.setDatemaiorhumidadeInvernoOutono(informacao.getDate());
                                    }
                                    if (estatisticas.getMenorhumidadeInvernoOutono() > informacao.getHumidity()) {
                                        estatisticas.setMenorhumidadeInvernoOutono(informacao.getHumidity());
                                        estatisticas.setDatemenorhumidadeInvernoOutono(informacao.getDate());
                                    }
                                }
                                if (estacao.equals("Primavera+Verão")) {
                                    if (estatisticas.getMaiortemperaturaPrimaveraVerao() == null) {
                                        estatisticas.setMaiortemperaturaPrimaveraVerao(informacao.getTemperature());
                                        estatisticas.setDatemaiortemperaturaPrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMenortemperaturaPrimaveraVerao() == null) {
                                        estatisticas.setMenortemperaturaPrimaveraVerao(informacao.getTemperature());
                                        estatisticas.setDatemenortemperaturaPrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiordiferencaPrimaveraVerao() == null) {
                                        estatisticas.setMaiordiferencaPrimaveraVerao(informacao.getTemperature());
                                        estatisticas.setDatemaiordiferencaPrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMenordiferencaPrimaveraVerao() == null) {
                                        estatisticas.setMenordiferencaPrimaveraVerao(informacao.getTemperature());
                                        estatisticas.setDatemenordiferencaPrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiorhumidadePrimaveraVerao() == null) {
                                        estatisticas.setMaiorhumidadePrimaveraVerao(informacao.getHumidity());
                                        estatisticas.setDatemaiorhumidadePrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMenorhumidadePrimaveraVerao() == null) {
                                        estatisticas.setMenorhumidadePrimaveraVerao(informacao.getHumidity());
                                        estatisticas.setDatemenorhumidadePrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiortemperaturaPrimaveraVerao() < informacao.getTemperature()) {
                                        estatisticas.setMaiortemperaturaPrimaveraVerao(informacao.getTemperature());
                                        estatisticas.setDatemaiortemperaturaPrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMenortemperaturaPrimaveraVerao() > informacao.getTemperature()) {
                                        estatisticas.setMenortemperaturaPrimaveraVerao(informacao.getTemperature());
                                        estatisticas.setDatemenortemperaturaPrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiordiferencaPrimaveraVerao() < diferenca) {
                                        estatisticas.setMaiordiferencaPrimaveraVerao(diferenca);
                                        estatisticas.setDatemaiordiferencaPrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMenordiferencaPrimaveraVerao() > diferenca) {
                                        estatisticas.setMenordiferencaPrimaveraVerao(diferenca);
                                        estatisticas.setDatemenordiferencaPrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMaiorhumidadePrimaveraVerao() < informacao.getHumidity()) {
                                        estatisticas.setMaiorhumidadePrimaveraVerao(informacao.getHumidity());
                                        estatisticas.setDatemaiorhumidadePrimaveraVerao(informacao.getDate());
                                    }
                                    if (estatisticas.getMenorhumidadePrimaveraVerao() > informacao.getHumidity()) {
                                        estatisticas.setMenorhumidadePrimaveraVerao(informacao.getHumidity());
                                        estatisticas.setDatemenorhumidadePrimaveraVerao(informacao.getDate());
                                    }
                                }
                                estatisticasMap.replace(informacao.getCity_name(), estatisticas);
                            } else {
                                if (estacao.equals("Inverno+Outono")) {
                                    Estatisticas estatisticas = new Estatisticas(null, null, informacao.getTemperature(), informacao.getTemperature(), null, null, diferenca, diferenca, null, null, informacao.getHumidity(), informacao.getHumidity());
                                    estatisticasMap.put(informacao.getCity_name(), estatisticas);
                                }
                                if (estacao.equals("Primavera+Verão")) {
                                    Estatisticas estatisticas = new Estatisticas(informacao.getTemperature(), informacao.getTemperature(), null, null, diferenca, diferenca, null, null, informacao.getHumidity(), informacao.getHumidity(), null, null);
                                    estatisticasMap.put(informacao.getCity_name(), estatisticas);
                                }
                            }
                        }
                    }
                }
            }
            i++;
        }
        frLogEstatisticas.write("<h4 style=\"text-align:center\"> Estatísticas para as cidades encontradas</h4>\n");
        frLogEstatisticas.write("<ul>\n");
        for (HashMap.Entry<String, Estatisticas> entry : estatisticasMap.entrySet()) {
            frLogEstatisticas.write("<li><b>" + entry.getKey() + "</b>");
            frLogEstatisticas.write(entry.getValue().toString());
            frLogEstatisticas.write("</li> \n");
        }
        frLogEstatisticas.write("</ul>\n");

        frLogSeason.write("</table>");
        frLog.write("</body>\n" + "</html>");
        frLogSeason.write("</body>\n" + "</html>");
        frLogEstatisticas.write("</body>\n" + "</html>");
        frLog.close();
        frLogSeason.close();
        frLogEstatisticas.close();
    }


    public static void main(String[] args) {
        BufferedReader br = null;

        try {
            File log = new File("log.html");
            File logSeason = new File("logSeason.html");
            File logEstatisticas = new File("logEstatisticas.html");
            if (log.createNewFile()) {
                System.out.println("File created: " + log.getName());
            } else {
                System.out.println("File already exists.");
            }
            if (logSeason.createNewFile()) {
                System.out.println("File created: " + logSeason.getName());
            } else {
                System.out.println("File already exists.");
            }
            if (logEstatisticas.createNewFile()) {
                System.out.println("File created: " + logEstatisticas.getName());
            } else {
                System.out.println("File already exists.");
            }
            processa_info(log, logSeason, logEstatisticas, "csv\\temperatura.csv");


        } catch (IOException | ParseException e) {
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


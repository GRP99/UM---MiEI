import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Class Client to iteract with server
public class Client {

    // List to save all words associate a emotion
    private static final List<String> positive = new ArrayList<>();
    private static final List<String> negative = new ArrayList<>();
    private static final List<String> anger = new ArrayList<>();
    private static final List<String> anticipation = new ArrayList<>();
    private static final List<String> disgust = new ArrayList<>();
    private static final List<String> fear = new ArrayList<>();
    private static final List<String> joy = new ArrayList<>();
    private static final List<String> sadness = new ArrayList<>();
    private static final List<String> surprise = new ArrayList<>();
    private static final List<String> trust = new ArrayList<>();

    // Split the text by spaces and for each word associate a number of emotion
    // Give the emotion with highest value or instead alphabetical order or Neutral with don't found emotions
    private static String getEmotion(String text)   {
        String emotion = "Neutral";
        String cvsSplitBy = " ";
        int max = 0;

        TreeMap<String, Integer> emotionmap = new TreeMap<>();

        String[] dados = text.split(cvsSplitBy);

        for (int i = 0; i < dados.length; i++) {
            if (positive.contains(dados[i])) {
                if (emotionmap.containsKey("positive")) {
                    int value = emotionmap.get("positive");
                    value = value + 1;
                    emotionmap.replace("positive", value);
                } else {
                    emotionmap.put("positive", 1);
                }
            }
            if (negative.contains(dados[i])) {
                if (emotionmap.containsKey("negative")) {
                    int value = emotionmap.get("negative");
                    value = value + 1;
                    emotionmap.replace("negative", value);
                } else {
                    emotionmap.put("negative", 1);
                }
            }
            if (anger.contains(dados[i])) {
                if (emotionmap.containsKey("anger")) {
                    int value = emotionmap.get("anger");
                    value = value + 1;
                    emotionmap.replace("anger", value);
                } else {
                    emotionmap.put("anger", 1);
                }
            }
            if (anticipation.contains(dados[i])) {
                if (emotionmap.containsKey("anticipation")) {
                    int value = emotionmap.get("anticipation");
                    value = value + 1;
                    emotionmap.replace("anticipation", value);
                } else {
                    emotionmap.put("anticipation", 1);
                }
            }
            if (disgust.contains(dados[i])) {
                if (emotionmap.containsKey("disgust")) {
                    int value = emotionmap.get("disgust");
                    value = value + 1;
                    emotionmap.replace("disgust", value);
                } else {
                    emotionmap.put("disgust", 1);
                }
            }
            if (fear.contains(dados[i])) {
                if (emotionmap.containsKey("fear")) {
                    int value = emotionmap.get("fear");
                    value = value + 1;
                    emotionmap.replace("fear", value);
                } else {
                    emotionmap.put("fear", 1);
                }
            }
            if (joy.contains(dados[i])) {
                if (emotionmap.containsKey("joy")) {
                    int value = emotionmap.get("joy");
                    value = value + 1;
                    emotionmap.replace("joy", value);
                } else {
                    emotionmap.put("joy", 1);
                }
            }
            if (sadness.contains(dados[i])) {
                if (emotionmap.containsKey("sadness")) {
                    int value = emotionmap.get("sadness");
                    value = value + 1;
                    emotionmap.replace("sadness", value);
                } else {
                    emotionmap.put("sadness", 1);
                }
            }
            if (surprise.contains(dados[i])) {
                if (emotionmap.containsKey("surprise")) {
                    int value = emotionmap.get("surprise");
                    value = value + 1;
                    emotionmap.replace("surprise", value);
                } else {
                    emotionmap.put("surprise", 1);
                }
            }
            if (trust.contains(dados[i])) {
                if (emotionmap.containsKey("trust")) {
                    int value = emotionmap.get("trust");
                    value = value + 1;
                    emotionmap.replace("trust", value);
                } else {
                    emotionmap.put("trust", 1);
                }
            }
        }

        if (emotionmap.size() != 0) {
            for (Map.Entry<String, Integer> stringIntegerEntry : emotionmap.entrySet()) {
                if (stringIntegerEntry.getValue() > max) {
                    max = stringIntegerEntry.getValue();
                    emotion = stringIntegerEntry.getKey();
                }
            }
        }

        return emotion;
    }

    // Get the resources
    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        return path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }

    // Write the header of HTML file
    private static void writeHeader(FileWriter fr) throws IOException {
        fr.write("<html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html\"; charset=\"UTF-8\"> <title> Chat Messages </title>  <style>\n" +
                "body {\n" +
                "  margin: 0 auto;\n" +
                "  max-width: 800px;\n" +
                "  padding: 0 20px;\n" +
                "}\n" +
                "\n" +
                ".container {\n" +
                "  border: 2px solid #dedede;\n" +
                "  background-color: #f1f1f1;\n" +
                "  border-radius: 5px;\n" +
                "  padding: 10px;\n" +
                "  margin: 10px 0;\n" +
                "}\n" +
                "\n" +
                ".darker {\n" +
                "  border-color: #ccc;\n" +
                "  background-color: #ddd;\n" +
                "}\n" +
                "\n" +
                ".container::after {\n" +
                "  content: \"\";\n" +
                "  clear: both;\n" +
                "  display: table;\n" +
                "}\n" +
                "\n" +
                ".container img {\n" +
                "  float: left;\n" +
                "  max-width: 60px;\n" +
                "  width: 100%;\n" +
                "  margin-right: 20px;\n" +
                "  border-radius: 50%;\n" +
                "}\n" +
                "\n" +
                ".container img.right {\n" +
                "  float: right;\n" +
                "  margin-left: 20px;\n" +
                "  margin-right:0;\n" +
                "}\n" +
                "\n" +
                ".time-right {\n" +
                "  float: right;\n" +
                "  color: #aaa;\n" +
                "}\n" +
                "\n" +
                ".time-left {\n" +
                "  float: left;\n" +
                "  color: #999;\n" +
                "}\n" +
                "</style> " + "<meta charset=\"utf-8\"/>\n" +
                "            <link rel=\"stylesheet\" href=\"html/w3.css\"/>" +
                "</head> <body>");
    }

    // Write what client say in html file
    private static void writeTextClient(FileWriter fr, String textClient, String emotion) throws IOException {
        LocalTime now = LocalTime.now();
        fr.write("<div class=\"container\">\n" +
                "  <img src=\"html/person.png\" alt=\"Avatar\" style=\"width:100%;\">\n" +
                "  <p>" + "<b>[" + emotion + "]</b></p>\n" +
                "  <p> " + textClient + "</p>\n" +
                "  <span class=\"time-right\">" + now.format(DateTimeFormatter.ofPattern("HH:mm")) + "</span>\n" +
                "</div>");
    }

    // Write what client say in html file
    private static void writeTextServer(FileWriter fr, String textServer, String emotion) throws IOException {
        LocalTime now = LocalTime.now();
        fr.write("<div class=\"container darker\">\n" +
                "  <img src=\"html/anonymous.png\" alt=\"Avatar\" class=\"right\" style=\"width:100%;\">\n" +
                "  <p>" + "<b>[" + emotion + "]</b></p>\n" +
                "  <p>" + textServer + "</p>\n" +
                "  <span class=\"time-left\">" + now.format(DateTimeFormatter.ofPattern("HH:mm")) + "</span>\n" +
                "</div>");
    }

    // Write the footer of HTML file
    private static void closeHTML(FileWriter fr) throws IOException {
        fr.write("</body>\n" + "</html>");
    }

    private static void startCommunication(String address, int port) throws IOException {
        /* SERVER */
        Socket socket = new Socket(address, port);
        System.out.println("Connected");
        DataInputStream input = new DataInputStream(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        /* CHATTERBOT */
        MagicBooleans.trace_mode = false;
        // Create a bot with name super and all AIML files present in resources directory
        Bot bot = new Bot("super", getResourcesPath());
        // Create a chatSession to learn in conversation and get the previous messages
        Chat chatSession = new Chat(bot);
        bot.brain.nodeStats();

        /* FILE */
        File fileChat;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date d = new Date();
        String filename = dateFormat.format(d) + ".html";
        // Creating a file with actual hour to save all iteractions with chatbot
        fileChat = new File(filename);
        if (fileChat.createNewFile()) {
            System.out.println("File created: " + fileChat.getName() + "\n");
        } else {
            System.out.println("File already exists.\n");
        }
        FileWriter fr = new FileWriter(fileChat, false);
        writeHeader(fr);

        /*EMOTION*/
        String emotion = "";

        String textServer;
        String textClient = "";

        while (!textClient.equals("wq")) {
            // Read what client write
            System.out.print(">> ");
            textClient = input.readLine();
            if (!textClient.equals("wq")) {
                emotion = getEmotion(textClient);

                writeTextClient(fr, textClient, emotion);
            }

            // Send to server
            out.println(textClient);
            out.flush();

            // Read what server send
            textServer = in.readLine();
            if (!textClient.equals("wq")) {
                emotion = getEmotion(textServer);

                writeTextServer(fr, textServer, emotion);

                System.out.println("Server: " + textServer + " ( " + emotion + " ) ");
            }

            if ((textServer == null) || (textServer.length() < 1)) {
                textServer = MagicStrings.null_input;
            } else if (!(textClient.equals("wq"))) {
                if (MagicBooleans.trace_mode) {
                    System.out.println("STATE=" + textServer + ":THAT=" + chatSession.thatHistory.get(0).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
                }

                // Get the answer give by chatbot dependent of text that human write
                String response = chatSession.multisentenceRespond(textServer);

                while (response.contains("&lt;")) {
                    response = response.replace("&lt;", "<");
                }
                while (response.contains("&gt;")) {
                    response = response.replace("&gt;", ">");
                }

                // Write what chatbot like a suggestion
                System.out.println("Suggestion : " + response);
            }
        }

        closeHTML(fr);

        fr.close();

        // One way chatbot learn but the human have to write wq
        bot.writeQuit();

        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }

    // Search all csv file given and fills in the list with respective emotion
    private static void registerEmotions(String csvFile) throws IOException {
        String line = "";
        String cvsSplitBy = ";";
        int i = 0;

        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        while ((line = br.readLine()) != null) {
            String[] dados = line.split(cvsSplitBy);
            if (i != 0) {
                if (dados.length == 11) {
                    if (Integer.parseInt(dados[1]) == 1) {
                        positive.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[2]) == 1) {
                        negative.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[3]) == 1) {
                        anger.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[4]) == 1) {
                        anticipation.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[5]) == 1) {
                        disgust.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[6]) == 1) {
                        fear.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[7]) == 1) {
                        joy.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[8]) == 1) {
                        sadness.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[9]) == 1) {
                        surprise.add(dados[0]);
                    }
                    if (Integer.parseInt(dados[10]) == 1) {
                        trust.add(dados[0]);
                    }
                }
            }
            i++;
        }
    }

    public static void main(String[] args) {
        try {
            registerEmotions("csv\\NRC-Emotion-Lexicon.csv");

            startCommunication("127.0.0.1", 12345);

        } catch (IOException u) {
            System.out.println(u);
        }
    }
}

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class Server to iteract with multiple clients
public class Server {

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
    private static String getEmotion(String text) {
        String emotion = "Neutral";
        String cvsSplitBy = " ";
        int max = 0;

        HashMap<String, Integer> emotionmap = new HashMap<>();

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

    private static void start(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server started");

        while (true) {
            System.out.println("Waiting for a client ...");
            Socket socket = server.accept();
            System.out.println("Client accepted");
            DataInputStream input = new DataInputStream(System.in);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            /* CHATTERBOT */
            MagicBooleans.trace_mode = false;
            // Create a bot with name super and all AIML files present in resources directory
            Bot bot = new Bot("super", getResourcesPath());
            // Create a chatSession to learn in conversation and get the previous messages
            Chat chatSession = new Chat(new Bot("super", getResourcesPath()));
            bot.brain.nodeStats();

            String textServer;
            String textClient = "";

            while (textClient != null && !textClient.equals("wq")) {
                // Read what client send
                textClient = in.readLine();
                if (!(textClient.equals("wq"))) {
                    System.out.println("Client: " + textClient + " ( " + getEmotion(textClient) + " ) ");
                }

                if ((textClient == null) || (textClient.length() < 1)) {
                    textClient = MagicStrings.null_input;
                } else if (!(textClient.equals("wq"))) {
                    if (MagicBooleans.trace_mode) {
                        System.out.println("STATE=" + textClient + ":THAT=" + chatSession.thatHistory.get(0).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
                    }

                    String response;
                    // Get the answer give by chatbot dependent of text that human write
                    response = chatSession.multisentenceRespond(textClient);

                    while (response.contains("&lt;")) {
                        response = response.replace("&lt;", "<");
                    }
                    while (response.contains("&gt;")) {
                        response = response.replace("&gt;", ">");
                    }

                    // Write what chatbot like a suggestion
                    System.out.println("Suggestion : " + response);

                    // Read what server write
                    System.out.print(">> ");
                    textServer = input.readLine();

                    // Send to client
                    out.println(textServer);
                    out.flush();
                }
            }

            // One way chatbot learn
            bot.writeQuit();

            System.out.println("Closing connection");
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }
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

            // Port use to comunnication
            start(12345);
        } catch (IOException u) {
            System.out.println(u);
        }
    }
}

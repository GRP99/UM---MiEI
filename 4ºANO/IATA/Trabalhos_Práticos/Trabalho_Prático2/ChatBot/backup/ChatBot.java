import org.alicebot.ab.*;
import org.alicebot.ab.utils.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Class only comunication with chatbot
public class ChatBot {
    private static final boolean TRACE_MODE = false;

    public static void main(String[] args) {
        try {
            // Get AIML Files
            String resourcesPath = getResourcesPath();

            MagicBooleans.trace_mode = TRACE_MODE;

            // Create a bot with name super and all AIML files present in resources directory
            Bot bot = new Bot("super", resourcesPath);

            // Create a chatSession to learn in conversation and get the previous messages
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine;


            File fileChat;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date d = new Date();

            try {
                // Creating a file with actual hour to save all iteractions with chatbot
                String filename = dateFormat.format(d) + ".html";
                fileChat = new File(filename);
                if (fileChat.createNewFile()) {
                    System.out.println("File created: " + fileChat.getName() + "\n");
                } else {
                    System.out.println("File already exists.\n");
                }
                FileWriter fr = new FileWriter(fileChat, false);

                boolean flag = true;
                while (flag) {
                    System.out.print("Human : ");

                    // Read what human say
                    textLine = IOUtils.readInputTextLine();

                    // Write what human say
                    fr.write("HUMAN: " + textLine + "\n");

                    if ((textLine == null) || (textLine.length() < 1))
                        textLine = MagicStrings.null_input;
                    if (textLine.equals("q")) {
                        flag = false;
                    } else if (textLine.equals("wq")) {
                        // One way chatbot learn but the human have to write wq
                        bot.writeQuit();
                        flag = false;
                    } else {
                        if (MagicBooleans.trace_mode)
                            System.out.println("STATE=" + textLine + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));

                        // Get the answer give by chatbot dependent of text that human write
                        String response = chatSession.multisentenceRespond(textLine);

                        while (response.contains("&lt;"))
                            response = response.replace("&lt;", "<");

                        while (response.contains("&gt;"))
                            response = response.replace("&gt;", ">");

                        // Write what chatbot say to terminal
                        System.out.println("Robot : " + response);

                        // Write what chatbot say to file
                        fr.write("ROBOT: " + response + "\n");
                    }
                }

                fr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the resources
    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        return path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }

}

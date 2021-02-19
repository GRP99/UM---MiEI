import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {

    public JSONReader() {
    }

    public String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


    public List<JSONObject> getFile(String url) {

        List<JSONObject> list = new ArrayList<>();

        try {

            for (int i = 1; i <= 5; i++) {

                JSONObject json = readJsonFromUrl(url + i);
                list.add(json);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;

    }


}

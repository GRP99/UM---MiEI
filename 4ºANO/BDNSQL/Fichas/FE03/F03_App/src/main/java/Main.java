import com.mongodb.*;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

        System.out.println("Connected with :" + mongoClient.getConnectPoint());

        JSONReader reader = new JSONReader();
        List<JSONObject> listCardiac = reader.getFile("http://nosql.hpeixoto.me/api/sensor/300");
        List<JSONObject> listBlood = reader.getFile("http://nosql.hpeixoto.me/api/sensor/400");

        Structure structure = new Structure();
        JSONParser parser = new JSONParser();
        structure = parser.loadStructure(listCardiac, listBlood, structure);

        DB database = mongoClient.getDB("Sensors");
        DBCollection collection = database.getCollection("sensor");

        for (HashMap.Entry<Integer, SensorC> entry : structure.getSensorsC().entrySet()) {
            SensorC sc = entry.getValue();

            List<BasicDBObject> list = new ArrayList<>();
            for (Doctor doc : sc.getCareteam()) {
                BasicDBObject d = new BasicDBObject("_id", doc.getId())
                        .append("name", doc.getName());
                list.add(d);
            }

            List<BasicDBObject> listdata = new ArrayList<>();
            for (Data data : sc.getData()) {
                BasicDBObject d = new BasicDBObject()
                        .append("bodytemp", data.getBodytemp())
                        .append("bloodpress_systolic", data.getBloodpress_systolic())
                        .append("bloodpress_diastolic", data.getBloodpress_diastolic())
                        .append("bpm", data.getBpm())
                        .append("sato2", data.getSato2())
                        .append("timestamp", data.getTimestamp());
                listdata.add(d);
            }

            DBObject sensor = new BasicDBObject("_id", sc.getId())
                    .append("number_of_sensors", sc.getNumber_of_sensors())
                    .append("sensornum", sc.getNum())
                    .append("type_of_sensor", sc.getType_of_sensor())
                    .append("servicecod", sc.getServicecod())
                    .append("servicedec", sc.getServicedec())
                    .append("admdate", sc.getAdmdate())
                    .append("bed", sc.getBed())
                    .append("patient", new BasicDBObject("_id", sc.getPatient().getId())
                            .append("name", sc.getPatient().getName())
                            .append("birthdate", sc.getPatient().getBirthdate())
                            .append("age", sc.getPatient().getAge()))
                    .append("careteam", list)
                    .append("data", listdata);

            collection.insert(sensor);
        }

        for (HashMap.Entry<Integer, SensorB> entry : structure.getSensorsB().entrySet()) {
            SensorB sb = entry.getValue();

            List<BasicDBObject> list = new ArrayList<>();
            for (Doctor doc : sb.getCareteam()) {
                BasicDBObject d = new BasicDBObject("_id", doc.getId())
                        .append("name", doc.getName());
                list.add(d);
            }

            List<BasicDBObject> list2 = new ArrayList<>();
            for (Doctor doc : sb.getNurseteam()) {
                BasicDBObject d = new BasicDBObject("_id", doc.getId())
                        .append("name", doc.getName());
                list2.add(d);
            }

            List<BasicDBObject> listdata = new ArrayList<>();
            for (Data data : sb.getData()) {
                BasicDBObject d = new BasicDBObject()
                        .append("bodytemp", data.getBodytemp())
                        .append("bloodpress_systolic", data.getBloodpress_systolic())
                        .append("bloodpress_diastolic", data.getBloodpress_diastolic())
                        .append("bpm", data.getBpm())
                        .append("sato2", data.getSato2())
                        .append("timestamp", data.getTimestamp());
                listdata.add(d);
            }

            DBObject sensor = new BasicDBObject("_id", sb.getId())
                    .append("number_of_sensors", sb.getNumber_of_sensors())
                    .append("sensornum", sb.getNum())
                    .append("type_of_sensor", sb.getType_of_sensor())
                    .append("servicecod", sb.getServicecod())
                    .append("servicedec", sb.getServicedec())
                    .append("admdate", sb.getAdmdate())
                    .append("bed", sb.getBed())
                    .append("patient", new BasicDBObject("_id", sb.getPatient().getId())
                            .append("name", sb.getPatient().getName())
                            .append("birthdate", sb.getPatient().getBirthdate())
                            .append("age", sb.getPatient().getAge()))
                    .append("careteam", list)
                    .append("nurseteam", list2)
                    .append("data", listdata);

            collection.insert(sensor);
        }

        try {
            while (true) {
                System.out.println("Going to sleep for 3 minutes ...");
                Thread.sleep(180000);
                System.out.println("Woke up ...");

                List<JSONObject> newlistCardiac = reader.getFile("http://nosql.hpeixoto.me/api/sensor/300");
                List<JSONObject> newlistBlood = reader.getFile("http://nosql.hpeixoto.me/api/sensor/400");

                structure = parser.updateStructure(newlistCardiac, newlistBlood, structure);

                for (HashMap.Entry<Integer, Data> entry : structure.getNewdata().entrySet()) {
                    Data d = entry.getValue();
                    int id = entry.getKey();

                    BasicDBObject newdata = new BasicDBObject("_id", id);

                    BasicDBObject update = new BasicDBObject();
                    update.put("bodytemp", d.getBodytemp());
                    update.put("bloodpress_systolic", d.getBloodpress_systolic());
                    update.put("bloodpress_diastolic", d.getBloodpress_diastolic());
                    update.put("bpm", d.getBpm());
                    update.put("sato2", d.getSato2());
                    update.put("timestamp", d.getTimestamp());

                    collection.update(newdata, new BasicDBObject("$push", new BasicDBObject("data", update)), false, false);
                }
            }
        } catch (Exception e) {
            mongoClient.close();
        }
    }
}

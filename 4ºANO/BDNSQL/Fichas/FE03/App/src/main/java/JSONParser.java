import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    private Data parseJSONData(JSONObject j) {
        Data d = new Data();
        d.setBodytemp((int) j.get("bodytemp"));
        d.setBpm((int) j.get("bpm"));
        d.setSato2((int) j.get("sato2"));
        d.setTimestamp((String) j.get("timestamp"));
        JSONObject aux = j.getJSONObject("bloodpress");
        d.setBloodpress_systolic((int) aux.get("systolic"));
        d.setBloodpress_diastolic((int) aux.get("diastolic"));
        return d;
    }

    private SensorC parseJSONSensorCardiac(JSONObject j) {
        SensorC s = new SensorC();
        s.setNumber_of_sensors((int) j.get("number_of_sensors"));
        s.setId((int) j.get("sensorid"));
        s.setNum((int) j.get("sensornum"));
        s.setType_of_sensor((String) j.get("type_of_sensor"));
        s.setServicecod((String) j.get("servicecod"));
        s.setServicedec((String) j.get("servicedesc"));
        s.setAdmdate((String) j.get("admdate"));
        s.setBed((String) j.get("bed"));
        Patient p = new Patient();
        JSONObject aux = j.getJSONObject("patient");
        p.setId((int) aux.get("patientid"));
        p.setName((String) aux.get("patientname"));
        p.setBirthdate((String) aux.get("patientbirthdate"));
        p.setAge((int) aux.get("patientage"));
        s.setPatient(p);
        List<Doctor> lista = new ArrayList<>();
        JSONArray array = j.getJSONArray("careteam");
        for (int i = 0; i < array.length(); i++) {
            Doctor d = new Doctor();
            JSONObject doctorPart = array.getJSONObject(i);
            d.setId((int) doctorPart.get("id"));
            d.setName((String) doctorPart.get("nome"));
            lista.add(d);
        }
        s.setCareteam(lista);
        List<Data> dataList = new ArrayList<>();
        Data d = parseJSONData(j);
        dataList.add(d);
        s.setData(dataList);
        return s;
    }

    private SensorB parseJSONSensorBlood(JSONObject j) {
        SensorB s = new SensorB();
        s.setNumber_of_sensors((int) j.get("number_of_sensors"));
        s.setId((int) j.get("sensorid"));
        s.setNum((int) j.get("sensornum"));
        s.setType_of_sensor((String) j.get("type_of_sensor"));
        s.setServicecod((String) j.get("servicecod"));
        s.setServicedec((String) j.get("servicedesc"));
        s.setAdmdate((String) j.get("admdate"));
        s.setBed((String) j.get("bed"));
        Patient p = new Patient();
        JSONObject aux = j.getJSONObject("patient");
        p.setId((int) aux.get("patientid"));
        p.setName((String) aux.get("patientname"));
        p.setBirthdate((String) aux.get("patientbirthdate"));
        p.setAge((int) aux.get("patientage"));
        s.setPatient(p);
        List<Doctor> list = new ArrayList<>();
        JSONArray array = j.getJSONArray("careteam");
        for (int i = 0; i < array.length(); i++) {
            Doctor d = new Doctor();
            JSONObject doctorPart = array.getJSONObject(i);
            d.setId((int) doctorPart.get("id"));
            d.setName((String) doctorPart.get("nome"));
            list.add(d);
        }
        s.setCareteam(list);
        List<Doctor> list2 = new ArrayList<>();
        JSONArray array2 = j.getJSONArray("nurseteam");
        for (int i = 0; i < array2.length(); i++) {
            Doctor d = new Doctor();
            JSONObject nursePart = array2.getJSONObject(i);
            d.setId((int) nursePart.get("id"));
            d.setName((String) nursePart.get("nome"));
            list2.add(d);
        }
        s.setNurseteam(list2);
        List<Data> dataList = new ArrayList<>();
        Data d = parseJSONData(j);
        dataList.add(d);
        s.setData(dataList);
        return s;
    }

    public Structure loadStructure(List<JSONObject> listCardiac, List<JSONObject> listBlood, Structure structure) {
        structure = new Structure();

        for (JSONObject json : listCardiac) {
            SensorC sc;
            sc = parseJSONSensorCardiac(json);
            structure.getSensorsC().put(sc.getId(), sc);
        }

        for (JSONObject json : listBlood) {
            SensorB sb;
            sb = parseJSONSensorBlood(json);
            structure.getSensorsB().put(sb.getId(), sb);
        }

        return structure;
    }

    public Structure updateStructure(List<JSONObject> newlistCardiac, List<JSONObject> newlistBlood, Structure structure) {
        structure = new Structure();
        for (JSONObject json : newlistCardiac) {
            int id = (int) json.get("sensorid");
            Data d;
            d = parseJSONData(json);
            structure.getNewdata().put(id, d);
        }

        for (JSONObject json : newlistBlood) {
            int id = (int) json.get("sensorid");
            Data d;
            d = parseJSONData(json);
            structure.getNewdata().put(id, d);
        }

        return structure;
    }
}

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public JSONParser() {
    }

    public Patient parseJsonPatient(JSONObject j) {
        Patient p = new Patient();
        JSONObject aux = j.getJSONObject("patient");
        p.setId((int) aux.get("patientid"));
        p.setName((String) aux.get("patientname"));
        p.setBirthdate((String) aux.get("patientbirthdate"));
        p.setAge((int) aux.get("patientage"));
        return p;
    }

    public List<Doctor> parseJsonDoctor(JSONObject j) {
        List<Doctor> lista = new ArrayList<>();
        JSONArray array = j.getJSONArray("careteam");
        for (int i = 0; i < array.length(); i++) {
            Doctor d = new Doctor();
            JSONObject doctorPart = array.getJSONObject(i);
            d.setId((int) doctorPart.get("id"));
            d.setName((String) doctorPart.get("nome"));
            lista.add(d);
        }
        return lista;
    }

    public Sensor parseJsonSensor(JSONObject j, int idPatient) {
        Sensor s = new Sensor();
        s.setNumber_of_sensors((int) j.get("number_of_sensors"));
        s.setId((int) j.get("sensorid"));
        s.setNum((int) j.get("sensornum"));
        s.setType_of_sensor((String) j.get("type_of_sensor"));
        s.setIdPatient(idPatient);
        s.setServicecod((String) j.get("servicecod"));
        s.setServicedec((String) j.get("servicedesc"));
        s.setAdmdate((String) j.get("admdate"));
        s.setBed((String) j.get("bed"));
        return s;
    }

    public Data parseJsonData(JSONObject j, int idSensor) {
        Data d = new Data();
        d.setIdSensor(idSensor);
        d.setBodytemp((int) j.get("bodytemp"));
        d.setBpm((int) j.get("bpm"));
        d.setSato2((int) j.get("sato2"));
        d.setTimestamp((String) j.get("timestamp"));
        JSONObject aux = j.getJSONObject("bloodpress");
        d.setBloodpress_systolic((int) aux.get("systolic"));
        d.setBloodpress_diastolic((int) aux.get("diastolic"));
        return d;
    }


    public Structure loadStructure(List<JSONObject> list, Structure structure) {
        structure = new Structure();

        for (JSONObject json : list) {
            Patient p;
            p = parseJsonPatient(json);
            if (!(structure.getPatients().containsKey(p.getId())))
                structure.getPatients().put(p.getId(), p);

            Sensor s;
            s = parseJsonSensor(json, p.getId());
            if (!(structure.getSensores().containsKey(s.getId())))
                structure.getSensores().put(s.getId(), s);

            List<Doctor> l;
            l = parseJsonDoctor(json);
            for (Doctor d : l) {
                if (!(structure.getDoctors().containsKey(d.getId())))
                    structure.getDoctors().put(d.getId(), d);


                Careteam c = new Careteam(s.getId(), d.getId());
                if (!(structure.getCareteams().contains(c)))
                    structure.getCareteams().add(c);
            }

            Data d;
            d = parseJsonData(json, s.getId());
            structure.getInfo().add(d);
        }

        return structure;


    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Structure {

    private Map<Integer, Patient> patients;
    private Map<Integer, Doctor> doctors;
    private Map<Integer, Sensor> sensores;
    private List<Careteam> careteams;
    private List<Data> info;
    private List<String> queries;

    public Structure() {
        this.patients = new HashMap<>();
        this.doctors = new HashMap<>();
        this.sensores = new HashMap<>();
        this.careteams = new ArrayList<>();
        this.info = new ArrayList<>();
        this.queries = new ArrayList<>();
    }

    public Map<Integer, Patient> getPatients() {
        return patients;
    }

    public void setPatients(Map<Integer, Patient> patients) {
        this.patients = patients;
    }

    public Map<Integer, Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Map<Integer, Doctor> doctors) {
        this.doctors = doctors;
    }

    public Map<Integer, Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(Map<Integer, Sensor> sensores) {
        this.sensores = sensores;
    }

    public List<Careteam> getCareteams() {
        return careteams;
    }

    public void setCareteams(List<Careteam> careteams) {
        this.careteams = careteams;
    }

    public List<Data> getInfo() {
        return info;
    }

    public void setInfo(List<Data> info) {
        this.info = info;
    }

    public List<String> getQueries() {
        return queries;
    }

    public void setQueries(List<String> queries) {
        this.queries = queries;
    }
}

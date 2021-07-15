import java.util.List;
import java.util.Objects;

public class SensorB {
    private int number_of_sensors;
    private int id;
    private int num;
    private String type_of_sensor;
    private List<Doctor> nurseteam;
    private List<Doctor> careteam;
    private Patient patient;
    private String servicecod;
    private String servicedec;
    private String admdate;
    private String bed;
    private List<Data> data;

    public SensorB() {
    }

    public SensorB(int number_of_sensors, int id, int num, String type_of_sensor, List<Doctor> nurseteam, List<Doctor> careteam, Patient patient, String servicecod, String servicedec, String admdate, String bed, List<Data> data) {
        this.number_of_sensors = number_of_sensors;
        this.id = id;
        this.num = num;
        this.type_of_sensor = type_of_sensor;
        this.nurseteam = nurseteam;
        this.careteam = careteam;
        this.patient = patient;
        this.servicecod = servicecod;
        this.servicedec = servicedec;
        this.admdate = admdate;
        this.bed = bed;
        this.data = data;
    }

    public int getNumber_of_sensors() {
        return number_of_sensors;
    }

    public void setNumber_of_sensors(int number_of_sensors) {
        this.number_of_sensors = number_of_sensors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType_of_sensor() {
        return type_of_sensor;
    }

    public void setType_of_sensor(String type_of_sensor) {
        this.type_of_sensor = type_of_sensor;
    }

    public List<Doctor> getNurseteam() {
        return nurseteam;
    }

    public void setNurseteam(List<Doctor> nurseteam) {
        this.nurseteam = nurseteam;
    }

    public List<Doctor> getCareteam() {
        return careteam;
    }

    public void setCareteam(List<Doctor> careteam) {
        this.careteam = careteam;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getServicecod() {
        return servicecod;
    }

    public void setServicecod(String servicecod) {
        this.servicecod = servicecod;
    }

    public String getServicedec() {
        return servicedec;
    }

    public void setServicedec(String servicedec) {
        this.servicedec = servicedec;
    }

    public String getAdmdate() {
        return admdate;
    }

    public void setAdmdate(String admdate) {
        this.admdate = admdate;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorB sensorB = (SensorB) o;
        return number_of_sensors == sensorB.number_of_sensors &&
                id == sensorB.id &&
                num == sensorB.num &&
                Objects.equals(type_of_sensor, sensorB.type_of_sensor) &&
                Objects.equals(nurseteam, sensorB.nurseteam) &&
                Objects.equals(careteam, sensorB.careteam) &&
                Objects.equals(patient, sensorB.patient) &&
                Objects.equals(servicecod, sensorB.servicecod) &&
                Objects.equals(servicedec, sensorB.servicedec) &&
                Objects.equals(admdate, sensorB.admdate) &&
                Objects.equals(bed, sensorB.bed) &&
                Objects.equals(data, sensorB.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number_of_sensors, id, num, type_of_sensor, nurseteam, careteam, patient, servicecod, servicedec, admdate, bed, data);
    }
}

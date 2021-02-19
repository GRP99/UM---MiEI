import java.util.List;
import java.util.Objects;

public class SensorC {
    private int number_of_sensors;
    private int id;
    private int num;
    private String type_of_sensor;
    private String servicecod;
    private String servicedec;
    private String admdate;
    private String bed;
    private Patient patient;
    private List<Doctor> careteam;
    private List<Data> data;

    public SensorC() {
    }

    public SensorC(int number_of_sensors, int id, int num, String type_of_sensor, String servicecod, String servicedec, String admdate, String bed, Patient patient, List<Doctor> careteam, List<Data> data) {
        this.number_of_sensors = number_of_sensors;
        this.id = id;
        this.num = num;
        this.type_of_sensor = type_of_sensor;
        this.servicecod = servicecod;
        this.servicedec = servicedec;
        this.admdate = admdate;
        this.bed = bed;
        this.patient = patient;
        this.careteam = careteam;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Doctor> getCareteam() {
        return careteam;
    }

    public void setCareteam(List<Doctor> careteam) {
        this.careteam = careteam;
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
        SensorC sensorC = (SensorC) o;
        return number_of_sensors == sensorC.number_of_sensors &&
                id == sensorC.id &&
                num == sensorC.num &&
                Objects.equals(type_of_sensor, sensorC.type_of_sensor) &&
                Objects.equals(servicecod, sensorC.servicecod) &&
                Objects.equals(servicedec, sensorC.servicedec) &&
                Objects.equals(admdate, sensorC.admdate) &&
                Objects.equals(bed, sensorC.bed) &&
                Objects.equals(patient, sensorC.patient) &&
                Objects.equals(careteam, sensorC.careteam) &&
                Objects.equals(data, sensorC.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number_of_sensors, id, num, type_of_sensor, servicecod, servicedec, admdate, bed, patient, careteam, data);
    }
}

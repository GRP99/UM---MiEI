import java.util.Objects;

public class Sensor {
    private int number_of_sensors;
    private int id;
    private int num;
    private String type_of_sensor;
    private int idPatient;
    private String servicecod;
    private String servicedec;
    private String admdate;
    private String bed;

    public Sensor() {
    }

    public Sensor(int number_of_sensors, int id, int num, String type_of_sensor, int idPatient, String servicecod, String servicedec, String admdate, String bed) {
        this.number_of_sensors = number_of_sensors;
        this.id = id;
        this.num = num;
        this.type_of_sensor = type_of_sensor;
        this.idPatient = idPatient;
        this.servicecod = servicecod;
        this.servicedec = servicedec;
        this.admdate = admdate;
        this.bed = bed;
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

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return number_of_sensors == sensor.number_of_sensors &&
                id == sensor.id &&
                num == sensor.num &&
                idPatient == sensor.idPatient &&
                Objects.equals(type_of_sensor, sensor.type_of_sensor) &&
                Objects.equals(servicecod, sensor.servicecod) &&
                Objects.equals(servicedec, sensor.servicedec) &&
                Objects.equals(admdate, sensor.admdate) &&
                Objects.equals(bed, sensor.bed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number_of_sensors, id, num, type_of_sensor, idPatient, servicecod, servicedec, admdate, bed);
    }

    @Override
    public String toString() {
        return "INSERT INTO sensor VALUES ("
                + number_of_sensors +
                "," + id +
                "," + num +
                ", '" + type_of_sensor + "'" +
                "," + idPatient +
                ",'" + servicecod + "'" +
                ",'" + servicedec + "'" +
                ",'" + admdate + "'" +
                ",'" + bed + "'" +
                ")\n";
    }
}

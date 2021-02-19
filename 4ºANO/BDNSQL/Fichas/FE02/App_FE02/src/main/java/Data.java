import java.util.Objects;

public class Data {
    private int idSensor;
    private int bodytemp;
    private int bloodpress_systolic;
    private int bloodpress_diastolic;
    private int bpm;
    private int sato2;
    private String timestamp;

    public Data() {
    }

    public Data(int idSensor, int bodytemp, int bloodpress_systolic, int bloodpress_diastolic, int bpm, int date2, String timestamp) {
        this.idSensor = idSensor;
        this.bodytemp = bodytemp;
        this.bloodpress_systolic = bloodpress_systolic;
        this.bloodpress_diastolic = bloodpress_diastolic;
        this.bpm = bpm;
        this.sato2 = sato2;
        this.timestamp = timestamp;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public int getBodytemp() {
        return bodytemp;
    }

    public void setBodytemp(int bodytemp) {
        this.bodytemp = bodytemp;
    }

    public int getBloodpress_systolic() {
        return bloodpress_systolic;
    }

    public void setBloodpress_systolic(int bloodpress_systolic) {
        this.bloodpress_systolic = bloodpress_systolic;
    }

    public int getBloodpress_diastolic() {
        return bloodpress_diastolic;
    }

    public void setBloodpress_diastolic(int bloodpress_diastolic) {
        this.bloodpress_diastolic = bloodpress_diastolic;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getSato2() {
        return sato2;
    }

    public void setSato2(int sato2) {
        this.sato2 = sato2;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return idSensor == data.idSensor &&
                bodytemp == data.bodytemp &&
                bloodpress_systolic == data.bloodpress_systolic &&
                bloodpress_diastolic == data.bloodpress_diastolic &&
                bpm == data.bpm &&
                sato2 == data.sato2 &&
                Objects.equals(timestamp, data.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSensor, bodytemp, bloodpress_systolic, bloodpress_diastolic, bpm, sato2, timestamp);
    }

    @Override
    public String toString() {
        return "INSERT INTO data VALUES ("
                + "NULL," +
                + idSensor +
                "," + bodytemp +
                "," + bloodpress_systolic +
                "," + bloodpress_diastolic +
                "," + bpm +
                "," + sato2 +
                ",'" + timestamp +
                "')\n";
    }
}

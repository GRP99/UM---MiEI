import java.util.HashMap;
import java.util.Map;

public class Structure {
    Map<Integer, SensorC> sensorsC;
    Map<Integer, SensorB> sensorsB;
    HashMap<Integer, Data> newdata;

    public Structure() {
        this.sensorsB = new HashMap<>();
        this.sensorsC = new HashMap<>();
        this.newdata = new HashMap<>();
    }

    public Structure(Map<Integer, SensorC> sensorsC, Map<Integer, SensorB> sensorsB, HashMap<Integer, Data> newdata) {
        this.sensorsC = sensorsC;
        this.sensorsB = sensorsB;
        this.newdata = newdata;
    }

    public Map<Integer, SensorC> getSensorsC() {
        return sensorsC;
    }

    public void setSensorsC(Map<Integer, SensorC> sensorsC) {
        this.sensorsC = sensorsC;
    }

    public Map<Integer, SensorB> getSensorsB() {
        return sensorsB;
    }

    public void setSensorsB(Map<Integer, SensorB> sensorsB) {
        this.sensorsB = sensorsB;
    }

    public HashMap<Integer, Data> getNewdata() {
        return newdata;
    }

    public void setNewdata(HashMap<Integer, Data> newdata) {
        this.newdata = newdata;
    }
}

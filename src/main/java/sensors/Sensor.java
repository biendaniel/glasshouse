package sensors;

import conditions.Condition;

import java.util.*;

public class Sensor {
    private  float value;

    public Sensor(float value) {
        this.value = value;
    }

    public Sensor() {

    }

    public  void returnValue(List<Condition> conditionList) {
        Random random = new Random();
        int index = random.nextInt(4);
        conditionList.get(index).setValue(conditionList.get(index).getValue() + (random.nextInt(4) - 2) + random.nextFloat());
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
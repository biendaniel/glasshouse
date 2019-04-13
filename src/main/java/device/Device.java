package device;

import conditions.Condition;

import java.util.*;

/**
 * 
 */
abstract public class Device implements Handler {


    protected float increasedValue;

    protected Device device ;

    public Device(float increasedValue) {
        this.increasedValue = increasedValue;
    }

    public void setDevice(Device device) {
        this.device = device;
    }


    public void decreaseValue(Condition condition, Condition currentCondition) {
        System.out.println("DECREASE");
        float value =  (condition.getValue() + (condition.getValue() * GlobalElements.PERCENTTOLERANCEINADJUST));
        while (currentCondition.getValue() >= value) {
            currentCondition.setValue(currentCondition.getValue() - increasedValue);
            System.out.printf("=== %.2f ===  %.2f \n", currentCondition.getValue(), value);
        }
    }

    public void increaseValue(Condition condition, Condition currentCondition) {
        System.out.println("INCREASE");
        float value =  (condition.getValue() + (condition.getValue() * GlobalElements.PERCENTTOLERANCEINADJUST));
        while (currentCondition.getValue() <= value) {
            currentCondition.setValue(currentCondition.getValue() + increasedValue);
            System.out.printf("=== %.2f === %.2f \n", currentCondition.getValue(), value);
        }
    }
    public Device getDevice() {
        return device;
    }

    protected void forward(Condition condition, Condition currentCondition){
        if(this.device != null){
            this.getDevice().handle(condition, currentCondition);
        }
    }

    public float getIncreasedValue() {
        return increasedValue;
    }

    public void setIncreasedValue(float increasedValue) {
        this.increasedValue = increasedValue;
    }

    public Device() {
    }


}
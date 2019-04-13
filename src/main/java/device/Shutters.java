package device;

import conditions.Condition;

public class Shutters extends Device {

    public Shutters(float increasedValue) {
        this.increasedValue = increasedValue;
    }

    @Override
    public void handle(Condition plantCondition, Condition currentCondition) {
        float substract = currentCondition.getValue() - plantCondition.getValue();
        if ((currentCondition.getValue() > plantCondition.getValue()) && (substract < increasedValue * GlobalElements.DEVICEITERATIONAMOUNT)) {
            System.out.println("Shutters ON");
            decreaseValue(plantCondition, currentCondition);
            System.out.println("Shutters OFF");
        } else
            forward(plantCondition, currentCondition);
    }

}

package device;

import conditions.Condition;

public class Fan extends Device implements Handler {

    public Fan(float increasedValue) {
        this.increasedValue = increasedValue;
    }

    @Override
    public void handle(Condition plantCondition, Condition currentCondition) {
        float substract = currentCondition.getValue() - plantCondition.getValue();
        if ((currentCondition.getValue() > plantCondition.getValue()) && (substract  < increasedValue * GlobalElements.DEVICEITERATIONAMOUNT)) {
            System.out.println("Fan ON");
            decreaseValue(plantCondition, currentCondition);
            System.out.println("Fan OFF");
        }
        else
            forward(plantCondition, currentCondition);
    }
}

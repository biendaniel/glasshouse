package device;

import conditions.Condition;

public class Lamp extends Device  {

    public Lamp(float increasedValue ) {
        this.increasedValue = increasedValue;
    }


    @Override
    public void handle(Condition plantCondition, Condition currentCondition) {
        float substract = currentCondition.getValue() - plantCondition.getValue();
        if ((currentCondition.getValue() > plantCondition.getValue()) && (substract  < increasedValue * GlobalElements.DEVICEITERATIONAMOUNT)) {
            System.out.println("Lamp ON");
            decreaseValue(plantCondition, currentCondition);
            System.out.println("Lamp OFF");
        } else
            forward(plantCondition, currentCondition);
    }

}

package device;

import conditions.Condition;

public class Sprinkler extends Device {

    public Sprinkler(float increasedValue ) {
        this.increasedValue = increasedValue;
    }

    @Override
    public void handle(Condition plantCondition, Condition currentCondition) {
        float substract = currentCondition.getValue() - plantCondition.getValue();
        if ((currentCondition.getValue() < plantCondition.getValue()) && (substract  < increasedValue * GlobalElements.DEVICEITERATIONAMOUNT)) {
            System.out.println("Sprinkler ON");
            increaseValue(plantCondition, currentCondition);
            System.out.println("Sprinkler OFF");
        } else
            forward(plantCondition, currentCondition);
    }

}

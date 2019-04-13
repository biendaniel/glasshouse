package device;

import conditions.Condition;

public class Evapolator extends Device{


    public Evapolator(float increasedValue ) {
        this.increasedValue = increasedValue;
    }


    @Override
    public void handle(Condition plantCondition, Condition currentCondition) {
        float substract =  plantCondition.getValue() - currentCondition.getValue() ;
        if ((currentCondition.getValue() < plantCondition.getValue()) && (substract  < increasedValue * GlobalElements.DEVICEITERATIONAMOUNT)) {
            System.out.println("Evapolator ON");
            increaseValue(plantCondition, currentCondition);
            System.out.println("Evapolator OFF");
        } else
            forward(plantCondition, currentCondition);
    }
}

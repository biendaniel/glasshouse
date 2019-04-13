package device;

import conditions.Condition;

public class Heater extends Device  {
    public Heater(float increasedValue ) {
        this.increasedValue = increasedValue;
    }



//    @Override
    public void handle(Condition plantCondition, Condition currentCondition) {
        float substract =  plantCondition.getValue() - currentCondition.getValue() ;
        if ((currentCondition.getValue() < plantCondition.getValue()) && (substract  < increasedValue * GlobalElements.DEVICEITERATIONAMOUNT)){
            System.out.println("Heater ON");
            increaseValue(plantCondition, currentCondition);
            System.out.println("Heater OFF");
        }
        else
            forward(plantCondition, currentCondition);

    }


}

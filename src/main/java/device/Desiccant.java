package device;

import conditions.Condition;

public class Desiccant extends Device implements  Handler{

    public Desiccant(float increasedValue ) {
        this.increasedValue = increasedValue;
    }


    @Override
    public void handle(Condition plantCondition, Condition currentCondition) {
        float substract = currentCondition.getValue() - plantCondition.getValue();
        if ((currentCondition.getValue() > plantCondition.getValue()) && (substract  < increasedValue * GlobalElements.DEVICEITERATIONAMOUNT)) {
            System.out.println("Dessiscante ON");
            decreaseValue(plantCondition, currentCondition);
            System.out.println("Dessiscante OFF");
        } else
            forward(plantCondition, currentCondition);
    }
}

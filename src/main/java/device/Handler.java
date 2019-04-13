package device;

import conditions.Condition;

public interface Handler {

    void handle(Condition plantCondition, Condition currentCondition);
}

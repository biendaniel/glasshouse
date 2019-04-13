package deviceControl;

import conditions.Condition;

public class AdjustTemperature implements DeviceControlStrategy {

    @Override
    public void adjustDevice(Condition condition, Condition currentCondition) {
        devices.fillTemperatureDevices();
        devices.getTemperatureDevice().handle(condition, currentCondition);
    }
}

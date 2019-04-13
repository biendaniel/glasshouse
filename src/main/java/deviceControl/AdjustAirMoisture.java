package deviceControl;

import conditions.Condition;

public class AdjustAirMoisture implements DeviceControlStrategy {

    @Override
    public void adjustDevice(Condition condition, Condition currentCondition) {
        devices.fillAirMoistureDevices();
        devices.getAirMoistureDevice().handle(condition, currentCondition);
    }
}

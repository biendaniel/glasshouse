package deviceControl;

import conditions.Condition;

public class AdjustSoilMoisture implements DeviceControlStrategy {

    @Override
    public void adjustDevice(Condition condition, Condition currentCondition) {
        devices.fillSoilMoistureDevices();
        devices.getSoilMoistureDevice().handle(condition, currentCondition);
    }
}

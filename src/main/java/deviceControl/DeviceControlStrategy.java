package deviceControl;

import conditions.Condition;
import device.Devices;

public interface DeviceControlStrategy {
     Devices devices = new Devices();
    void adjustDevice(Condition condition, Condition currentCondition);
}

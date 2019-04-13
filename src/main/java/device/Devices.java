package device;

import java.util.ArrayList;
import java.util.List;

public class Devices {
    private List<Device> temperatureDevices = new ArrayList<>();
    private List<Device> airMoistureDevices = new ArrayList<>();
    private List<Device> soilMoistureDevices = new ArrayList<>();
    private List<Device> lightIntensityDevices = new ArrayList<>();


    public Device getAirMoistureDevice() {
        return airMoistureDevices.get(0);
    }


    public Device getSoilMoistureDevice() {
        return soilMoistureDevices.get(0);
    }


    public Device getLightIntensityDevice() {
        return lightIntensityDevices.get(0);
    }


    public Device getTemperatureDevice() {
        return temperatureDevices.get(0);
    }


    private        void fillDevices(List <Device> list){
            for(int i = 0; i < list.size()-1; i++){
                list.get(i).setDevice(list.get(++i));}

    }
    public void fillTemperatureDevices(){
        temperatureDevices.add(new Fan(GlobalElements.FIRSTDEVICEVALUE));
        temperatureDevices.add(new Fan(GlobalElements.SECONDDEVICEVALUE));
        temperatureDevices.add(new Fan(GlobalElements.THIRDDEVICEVALUE));
        temperatureDevices.add(new Heater(GlobalElements.FIRSTDEVICEVALUE));
        temperatureDevices.add(new Heater(GlobalElements.SECONDDEVICEVALUE));
        temperatureDevices.add(new Heater(GlobalElements.THIRDDEVICEVALUE));
        fillDevices(temperatureDevices);
    }

    public void fillAirMoistureDevices(){
        airMoistureDevices.add(new Evapolator(GlobalElements.FIRSTDEVICEVALUE));
        airMoistureDevices.add(new Evapolator(GlobalElements.SECONDDEVICEVALUE));
        airMoistureDevices.add(new Evapolator(GlobalElements.THIRDDEVICEVALUE));
        airMoistureDevices.add(new Desiccant(GlobalElements.FIRSTDEVICEVALUE));
        airMoistureDevices.add(new Desiccant(GlobalElements.SECONDDEVICEVALUE));
        airMoistureDevices.add(new Desiccant(GlobalElements.THIRDDEVICEVALUE));
        fillDevices(airMoistureDevices);
    }

    public void fillSoilMoistureDevices(){
        soilMoistureDevices.add(new Sprinkler(GlobalElements.FIRSTDEVICEVALUE));
        soilMoistureDevices.add(new Sprinkler(GlobalElements.SECONDDEVICEVALUE));
        soilMoistureDevices.add(new Sprinkler(GlobalElements.THIRDDEVICEVALUE));
   fillDevices(soilMoistureDevices);
    }

    public void fillLightIntensityDevices(){

        lightIntensityDevices.add(new Lamp(GlobalElements.FIRSTDEVICEVALUE));
        lightIntensityDevices.add(new Lamp(GlobalElements.SECONDDEVICEVALUE));
        lightIntensityDevices.add(new Lamp(GlobalElements.THIRDDEVICEVALUE));
        lightIntensityDevices.add(new Shutters(GlobalElements.FIRSTDEVICEVALUE));
        lightIntensityDevices.add(new Shutters(GlobalElements.SECONDDEVICEVALUE));
        lightIntensityDevices.add(new Shutters(GlobalElements.THIRDDEVICEVALUE));
    fillDevices(lightIntensityDevices);
    }


}

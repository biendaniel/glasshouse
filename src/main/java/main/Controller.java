package main;
import conditions.Condition;
import device.Device;
import device.Devices;
import device.GlobalElements;
import deviceControl.*;
import sensors.Sensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;


public class Controller {
    private  int id;
    private Sensor sensors = new Sensor();
    private List<Condition> currentConditions = new LinkedList<>();
    private Place place;
    private Plant plant;
    private Devices devices;
    private DeviceControlStrategy deviceControlStrategy;

    public Controller( ) throws SQLException {
    }

    public Controller(int id) throws SQLException, FileNotFoundException, ClassNotFoundException {
        this.id=id;
        addDevices();
        loadControllerFromDataBase();
        fillCurrentConditionList();

    }

    public void startThread(){
        Thread t1 = new Thread(new checkCurrentConditionFromSensorThread());
        Thread t2 = new Thread(new compareConditonThread());
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();

    }
    public void setCurrentConditions(List<Condition> currentConditions) {
        this.currentConditions = currentConditions;
    }

    //////////////////
    public void addDevices(){
        devices = new Devices();
        devices.fillTemperatureDevices();
        devices.fillAirMoistureDevices();
        devices.fillSoilMoistureDevices();
        devices.fillLightIntensityDevices();
    }

    public void addController(int placeID, int plantID) throws SQLException, ClassNotFoundException, FileNotFoundException {
        ConnectorDB.getInstance().addController(placeID, plantID);
        ConnectorDB.getInstance().updatePlaceStatus(placeID, "zajety");
        Login.turnOn();
    }

    public Devices getDevices() {
        return devices;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }


    public Plant getPlant() {
        return plant;
    }


    public Place getPlace() {
        return place;
    }


    public int getId() {
        return id;
    }


    public List<Condition> getCurrentConditions() {
        return currentConditions;
    }

    private void fillCurrentConditionList() throws SQLException, ClassNotFoundException {
        currentConditions = ConnectorDB.getInstance().loadCurrentConditions(getPlace().getID());
    }




    private void checkCurrentConditionFromSensors(){
        sensors.returnValue(currentConditions);
    }


    private void loadControllerFromDataBase() throws SQLException, ClassNotFoundException {
        ConnectorDB.getInstance().returnControllerByID(this);
    }

    public static List<String> fillControllerIDList() throws SQLException, ClassNotFoundException {
            List<String> idList = new LinkedList<>();
            List<Integer> idControllerList = ConnectorDB.getInstance().loadControllersID();
            for(Integer it: idControllerList)
                idList.add(String.valueOf(it));
            return idList;
    }

    public void editController(int id, int plantID, int placeID, int prevPlaceID) throws SQLException, ClassNotFoundException {
        ConnectorDB.getInstance().updatePlaceStatus(prevPlaceID, "wolny");
        System.out.printf("COntrollerID %d  place   %d   prevplace  %d    plant %d", id, placeID, prevPlaceID, plantID);
        ConnectorDB.getInstance().editController(id, plantID, placeID);
        ConnectorDB.getInstance().updatePlaceStatus(placeID, "zajety");
    }


    private void saveConditionsToDataBase() throws SQLException, ClassNotFoundException {
        ConnectorDB.getInstance().saveConditionsToDataBase(place.getID(), currentConditions); //TODO tego jeszcze nie sprawdzalem
    }

    private DeviceControlStrategy runStrategy(int index){
        if(index == 0)
            return new AdjustTemperature();
        else if(index == 1)
            return new AdjustAirMoisture();
        else if (index == 2)
            return new AdjustSoilMoisture();
        else if (index == 3)
            return new AdjustLighting();
        else
            return null;
    }


    public void compareCurrentValueWithPlantRequirement() { //TODO to trzeba zamienić na klase Devices
        int j = 0;
        float currentValue = plant.getConditionsList().get(j).getValue();
        float valueTolerance = 0;
        for(Condition i : currentConditions){
            valueTolerance = currentValue * GlobalElements.PERCENTTOLERANCE;
            if((i.getValue() > (currentValue + valueTolerance)) || i.getValue() < (currentValue - valueTolerance)){
                System.out.println("strategia uruchomiona dla:  " + j);
                deviceControlStrategy = runStrategy(j);
            deviceControlStrategy.adjustDevice(plant.getConditionsList().get(j), i);
            }
            System.out.printf("wynik[%d] ----  %.2f", j, i.getValue());

            ++j;
        }
    }


    public void addPlantToController(int id) throws SQLException, ClassNotFoundException {
        Plant plant = new Plant(id);
        plant.loadPlantConditionFromDataBase();
        plant.loadPlantConditionFromDataBase();
        this.plant = plant;
    }

    public void addPlaceToController(int id) throws SQLException, ClassNotFoundException {
        Place place = new Place(id);
        place.loadPlaceFromDataBase(id);
        this.place = place;
    }

    private class checkCurrentConditionFromSensorThread extends  Thread{
        @Override
        public void run() {
            while(CurrentController.getIdCurrentController() != this.getId()) {
                checkCurrentConditionFromSensors();
                System.out.println(currentConditions);
                CurrentController.setConditions(currentConditions);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class compareConditonThread extends Thread{
        @Override
        public void run(){
            while(CurrentController.getIdCurrentController() != this.getId()){
                compareCurrentValueWithPlantRequirement();
                System.out.println("Po poprawie:: " + currentConditions);
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
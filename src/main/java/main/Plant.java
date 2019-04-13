package main;

import conditions.Condition;

import java.sql.SQLException;
import java.util.*;

public class Plant implements Cloneable {
    public Plant() {
    }

    public Plant(int id) throws SQLException, ClassNotFoundException {
        loadPlantFromDataBase(id);
    }

    public Plant(String string) throws SQLException, ClassNotFoundException {
        loadPlantFromDataBase(string);
    }

    public Object clone() {
        Object clone = null;

        try {
            clone = super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public void addClonedPlant(String name) throws SQLException, ClassNotFoundException {
        Plant clonedPlant = (Plant) this.clone();
        clonedPlant.setName(name);
        clonedPlant.savePlantToDataBase();
    }

    private List<Condition> conditionsList = new LinkedList<>();
    protected String name;
    private int conditionID;

    public List<Condition> getConditionsList() {
        return conditionsList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    private void addConditionsToPlant(List<Condition> conditionsList) {
        this.conditionsList = conditionsList;
    }

    public void addPlantParameters(List<Condition> conditionsList, String name) throws SQLException, ClassNotFoundException {
        addConditionsToPlant(conditionsList);
        savePlantConditionsToDataBase();
        setName(name);
        savePlantToDataBase();
    }


    public void loadPlantFromDataBase(String name) throws SQLException, ClassNotFoundException {
        conditionID = ConnectorDB.getInstance().loadPlants(name);
        this.name = name;
        loadPlantConditionFromDataBase();
    }


    public void loadPlantFromDataBase(int id) throws SQLException, ClassNotFoundException {
        ConnectorDB connectorDB = ConnectorDB.getInstance();
        String name = connectorDB.findPlantNameByID(id);
        loadPlantFromDataBase(name);
    }


    public void viewConditionsList() {
        for (Condition condition : conditionsList)
            System.out.println(condition.toString());
    }

    public void savePlantToDataBase() throws SQLException, ClassNotFoundException {
        ConnectorDB.getInstance().savePlant(name, conditionID);
    }

    public void editPlantConditionsInDataBase(List<Condition> conditionsList) throws SQLException, ClassNotFoundException {
        ConnectorDB connectorDB = ConnectorDB.getInstance();
        connectorDB.editPlantConditionsInDataBase(conditionsList, conditionID);
        addConditionsToPlant(conditionsList);

    }

    private void savePlantConditionsToDataBase() throws SQLException, ClassNotFoundException {
        conditionID = ConnectorDB.getInstance().addPlantConditions(conditionsList);
    }


    public void loadPlantConditionFromDataBase() throws SQLException, ClassNotFoundException {
        conditionsList = ConnectorDB.getInstance().returnPlantConditions(conditionID);
    }

    public static List<String> fillPlantTypeList() throws SQLException, ClassNotFoundException {
        LinkedList<String> plantTypeList = new LinkedList<>();
        plantTypeList = (LinkedList<String>) ConnectorDB.getInstance().returnPlantsName();
        return plantTypeList;
    }


    public static String deletePlant(String name) throws SQLException, ClassNotFoundException {
        String result = ConnectorDB.getInstance().deletePlant(name);
        return result;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "conditionsList=" + conditionsList +
                ", name='" + name + '\'' +
                ", conditionID=" + conditionID +
                '}';
    }
}
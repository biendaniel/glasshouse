package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Place {

    public Place() {
    }

    public Place(int id) throws SQLException, ClassNotFoundException {


        loadPlaceFromDataBase(id);
    }

    private int ID;
    private String status;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static List<String> fillPlaceListByController(){
        List <String> list = new LinkedList<>();
        List <Controller> controllerList = Login.getControllerList();
        for (Controller it: controllerList)
            list.add(String.valueOf(it.getPlace().getID()));
        return list;
    }

    public static List<String> fillPlaceList() throws SQLException, ClassNotFoundException {
        return ConnectorDB.getInstance().loadPlaceList();
    }

    public static List<String> fillFreePlaceList() throws SQLException, ClassNotFoundException {
        return ConnectorDB.getInstance().loadFreePlaceList();
    }


    public void loadPlaceFromDataBase(int id) throws SQLException, ClassNotFoundException {
        ID = id;  // nie przypisywaliśmy obiektom przekazanego ID dlatego zawsze pokazywało nam Obszar 0 :)
        status = ConnectorDB.getInstance().loadPlaceFromDataBase(id);
    }

    @Override
    public String toString() {
        return "Place{" +
                "ID=" + ID +
                ", status='" + status + '\'' +
                '}';
    }
}
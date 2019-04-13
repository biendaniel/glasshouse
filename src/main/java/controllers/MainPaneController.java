package controllers;


import conditions.Condition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.*;
import sensors.Sensor;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPaneController implements Initializable {
    public Label lbTemperatureValue;
    public Label lbLightIntensityValue;
    public Label lbAirMoistureValue;
    public Label lbSoilMoistureValue;
    public Label lbSoilPhValue;
    public Label lbPlace;
    public Button btUndo;
    public AnchorPane mainPane;

    private static MainPaneController mainPaneController;
    public ComboBox cbPlace;

    public static MainPaneController getInstance() {
        if (mainPaneController == null) {
            mainPaneController = new MainPaneController();
            return mainPaneController;
        } else
            return mainPaneController;
    }

    public ObservableList<String> fillComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList;
        observableList = FXCollections.observableList(Place.fillPlaceListByController());
        return observableList;
    }

    private void loadMeasurements() {
        List<Float> measurementList = new LinkedList<>();
        measurementList.clear();
        for (Condition condition : CurrentController.getController().getCurrentConditions()) {
            measurementList.add(condition.getValue());
        }
        lbTemperatureValue.setText(String.valueOf(measurementList.get(0)));
        lbAirMoistureValue.setText(String.valueOf(measurementList.get(1)));
        lbSoilMoistureValue.setText(String.valueOf(measurementList.get(2)));
        lbLightIntensityValue.setText(String.valueOf(measurementList.get(3)));
    }


    public void clickUndo(ActionEvent actionEvent) throws IOException {
        LoaderPane.checkPaneForEmployee(mainPane);
    }

    public void setPlaceList() throws SQLException, ClassNotFoundException {
        cbPlace.getItems().clear();
        cbPlace.setItems(fillComboBox());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbPlace.setItems(fillComboBox());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadMeasurements();
        if (!LoggedUser.logged) {
            btUndo.setVisible(false);
        } else {
            btUndo.setVisible(true);
        }
        lbPlace.setText("Obszar " + CurrentController.getController().getPlace().getID());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> loadMeasurements());
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }).start();
    }


    public void clickCheckPlace(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        System.out.println(CurrentController.getController().getCurrentConditions().get(0).getValue());
        int i = Integer.parseInt(String.valueOf(cbPlace.getValue()));
        int controllerID = ConnectorDB.getInstance().loadControllerIDbyPlaceID(i);
        System.out.println(String.valueOf(i));
        lbPlace.setText("Obszar: " + i);
        Controller controller = Login.getControllerList().get(controllerID - 1);
        CurrentController.setController(controller);
        controller.startThread();
        loadMeasurements();
    }
}




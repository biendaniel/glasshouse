package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddControllerController implements Initializable {
    public Button btEdit;
    public Button btAdd;
    public AnchorPane addPlantPane;
    public Label lbPaneInfo;
    public Button btUndo;
    public Label lbInfo;
    public ComboBox cbControllerSelect;
    public ComboBox cbCheckPlace;
    public ComboBox cbCheckPlant;


    private static AddControllerController addControllerController;

    public static AddControllerController getInstance() {
        if (addControllerController == null) {
            addControllerController = new AddControllerController();
            return addControllerController;
        } else
            return addControllerController;
    }

    private ObservableList<String> fillCbCheckPlant() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList;
        observableList = FXCollections.observableList(Plant.fillPlantTypeList());
        return observableList;
    }

    private ObservableList<String> fillCbCheckPlace() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList;
        observableList = FXCollections.observableList(Place.fillFreePlaceList());
        return observableList;
    }

    private ObservableList<String> fillCbControllerSelect() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList;
        observableList = FXCollections.observableList(Controller.fillControllerIDList());
        return observableList;
    }


    public void clickAdd() throws SQLException, ClassNotFoundException, FileNotFoundException {
        Controller controller = new Controller();
        int placeID = Integer.parseInt(String.valueOf(cbCheckPlace.getValue()));
        int plantID = ConnectorDB.getInstance().findPlantIDByName(String.valueOf(cbCheckPlant.getValue()));

        controller.addController(placeID, plantID);
    }

    public void clickEdit(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, FileNotFoundException {
        int plantID;
        int placeID;
        int prevPlaceID;
        if (cbControllerSelect.getValue() == null)
            lbInfo.setText("Wybierz sterownik, który chcesz edytować");
        else {
            int controllerID = Integer.parseInt(String.valueOf(cbControllerSelect.getValue()));
            Controller controller = new Controller(controllerID);
            prevPlaceID = controller.getPlace().getID();

            if (cbCheckPlant.getValue() == null)
                plantID = ConnectorDB.getInstance().findPlantIDByName(controller.getPlant().getName());
            else
                plantID = ConnectorDB.getInstance().findPlantIDByName(String.valueOf(cbCheckPlant.getValue()));

            if (cbCheckPlace.getValue() == null)
                placeID = controller.getPlace().getID();
            else
                placeID = Integer.parseInt(String.valueOf(cbCheckPlace.getValue()));
            controller.editController(controllerID, plantID, placeID, prevPlaceID);
            Login.getControllerList();
            MainPaneController.getInstance().setPlaceList();
            lbInfo.setText("Edytowano sterownik");
        }
    }

    public void clickUndo(ActionEvent actionEvent) throws IOException {
        LoaderPane.checkPaneForEmployee(addPlantPane);
    }

    public void clickSelectPlace(ActionEvent actionEvent) {
    }

    public void clickSelectPlant(ActionEvent actionEvent) {
    }

    public void clickSelectController(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbControllerSelect.setItems(fillCbControllerSelect());
            cbCheckPlant.setItems(fillCbCheckPlant());
            cbCheckPlace.setItems(fillCbCheckPlace());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.LoaderPane;
import main.LoggedUser;
import main.Plant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RemovePlantController implements Initializable {

    public Label lbInfo;
    @FXML
    private AnchorPane removePlantPane;

    @FXML
    private Button btRemove;

    @FXML
    private Button btUndo;

    @FXML
    private ComboBox cbPlantSelect;

    @FXML
    private Label lbName;

    @FXML
    private Label lbTemperature;

    @FXML
    private Label lbLightIntensity;

    @FXML
    private Label lbAirMoisture;

    @FXML
    private Label lbSoilMoisture;



    private static RemovePlantController removePlantController;

    public static RemovePlantController getInstance(){
        if(removePlantController==null) {
            removePlantController = new RemovePlantController();
            return removePlantController;
        } else
            return removePlantController;
    }


    private void setTextLabelPlantParameters(Plant plant){
        lbName.setText(String.valueOf(plant.getName()));
        lbTemperature.setText(String.valueOf(plant.getConditionsList().get(0).getValue()));
        lbAirMoisture.setText(String.valueOf(plant.getConditionsList().get(1).getValue()));
        lbSoilMoisture.setText(String.valueOf(plant.getConditionsList().get(2).getValue()));
        lbLightIntensity.setText(String.valueOf(plant.getConditionsList().get(3).getValue()));
    }

    private void setEmptyLabel(){
        lbName.setText("");
        lbTemperature.setText("");
        lbAirMoisture.setText("");
        lbSoilMoisture.setText("");
        lbLightIntensity.setText("");
    }

    private ObservableList<String> fillComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList;
        observableList = FXCollections.observableList(Plant.fillPlantTypeList());
        return observableList;
    }


    public void clickUndo() throws IOException {
        LoaderPane.checkPaneForEmployee(removePlantPane);
    }

    @FXML
    private void openConfirmWindow() throws IOException, SQLException, ClassNotFoundException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz usunąć " + cbPlantSelect.getValue() +" ?", ButtonType.YES, ButtonType.NO  );
    alert.showAndWait();
    if(alert.getResult() == ButtonType.YES) {
        lbInfo.setText(Plant.deletePlant(String.valueOf(cbPlantSelect.getValue())));
        try {
            Plant.deletePlant(String.valueOf(cbPlantSelect.getValue()));
        } catch(Exception e){
            lbInfo.setText("Nie można usunąć rośliny. \n Prawdopodobnie jest przypisana do któregoś z obszarów");
        }
        setComboBoxValues();
    }
    else
        alert.close();
    }


    private Plant returnPlantByName(String name) throws SQLException, ClassNotFoundException {
        Plant plant = new Plant();
        plant.loadPlantFromDataBase(name);
        System.out.println(plant);
        return plant;
    }

    private void setComboBoxValues() throws SQLException, ClassNotFoundException {
        cbPlantSelect.setItems(fillComboBox());
        cbPlantSelect.setPromptText("Wybierz roślinę");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setComboBoxValues();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void chooseValue(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(cbPlantSelect.getValue() == null){
            setEmptyLabel();
            setComboBoxValues();
        } else
    setTextLabelPlantParameters(returnPlantByName(String.valueOf(cbPlantSelect.getValue())));
    }
}

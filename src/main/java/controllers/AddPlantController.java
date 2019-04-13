package controllers;

import conditions.Condition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.LoaderPane;
import main.Plant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPlantController implements Initializable {

    public Label label;
    public AnchorPane addPlantPane;
    public ComboBox cbPlantSelect;
    public Label lbInfo;
    public Button btEdit;
    public Label lbPaneInfo;
    public Label lbName;
    @FXML
    public TextField tfName;

    @FXML
    private TextField tfTemperature;

    @FXML
    private TextField tfLightIntensity;

    @FXML
    private TextField tfAirMoisture;

    @FXML
    private TextField tfSoilMoisture;


    @FXML
    public Button btAdd;

    private List<TextField> textFields = new LinkedList<>();

    @FXML
    private Button btUndo;
    public List<Condition> conditionList;

    private static AddPlantController addPlantController;


    public static AddPlantController getInstance() {
        if (addPlantController == null) {
            addPlantController = new AddPlantController();
            return addPlantController;
        } else
            return addPlantController;
    }


    private List<Condition> parseTextFieldValueToFloat() {
        List<Condition> conditionList = new LinkedList<>();

        for (TextField textField : textFields) {
            if (textField.getText().equals(""))
                textField.setText("0.0");
            conditionList.add(new Condition(Float.parseFloat(textField.getText())));
        }
        return conditionList;
    }


    private void fillTextFieldsList() {
        textFields.clear();
        textFields.add(tfTemperature);
        textFields.add(tfAirMoisture);
        textFields.add(tfSoilMoisture);
        textFields.add(tfLightIntensity);
    }


    private void setTextLabelPlantParameters(Plant plant) {

        int i = 0;
        for (TextField textField : textFields) {
            System.out.println(plant.getConditionsList().get(i).getValue());
            textField.setPromptText(String.valueOf(plant.getConditionsList().get(i++).getValue()));
        }
    }


    private void setTextFieldAsPromptText() {

        for (TextField textField : textFields)
            if (textField.getText().equals(""))
                textField.setText(textField.getPromptText());
    }

    private void setTextFields(String string) {
        for (TextField textField : textFields)
            textField.setText(string);
    }

    private void checkTextLabel() {

    }


    private Plant returnPlantByName(String name) throws SQLException, ClassNotFoundException {
        Plant plant = new Plant();
        plant.loadPlantFromDataBase(name);
        System.out.println(plant);
        return plant;
    }

    public void clickAdd() throws FileNotFoundException, SQLException, ClassNotFoundException {
        Plant plant = new Plant();
        plant.addPlantParameters(parseTextFieldValueToFloat(), tfName.getText());
        if(!plant.getName().equalsIgnoreCase("null")) {
            lbInfo.setText("Roślina dodana");
        }
    }

    public void clickUndo(ActionEvent actionEvent) throws IOException {
        LoaderPane.checkPaneForEmployee(addPlantPane);
    }


    public void clickTest(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setTextFields("");
        lbInfo.setText("");
        setTextLabelPlantParameters(returnPlantByName(String.valueOf(cbPlantSelect.getValue())));
    }


    public void clickEdit(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Plant plant = new Plant(String.valueOf(cbPlantSelect.getValue()));
        System.out.println(plant.getName());
        setTextFieldAsPromptText();
        plant.editPlantConditionsInDataBase(parseTextFieldValueToFloat());
        if(!plant.getName().equalsIgnoreCase("null")) {
            lbInfo.setText("Roślina edytowana");
        }
    }

    private ObservableList<String> fillComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList;
        observableList = FXCollections.observableList(Plant.fillPlantTypeList());
        return observableList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            fillTextFieldsList();
            cbPlantSelect.setItems(fillComboBox());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String increaseValue(TextField textField) {
        float value;
        if (textField.getText().equals("") && textField.getPromptText().equals(""))
            return "";
        if (textField.getText().equals(""))
            value = Float.parseFloat(textField.getPromptText()) + 0.5f;
        else {
            value = Float.parseFloat(textField.getText()) + 0.5f;
        }

        return String.valueOf(value);
    }

    private String decreaseValue(TextField textField) {
        float value = 0.0f;
        if (textField.getText().equals("") && textField.getPromptText().equals(""))
            return "";
        if ((textField.getPromptText().equals("0.0") || textField.getText().equals("0.0")))
            return String.valueOf(value);
        if (textField.getText().equals(""))
            value = Float.parseFloat(textField.getPromptText()) - 0.5f;
        else {
            value = Float.parseFloat(textField.getText()) - 0.5f;
        }
        return String.valueOf(value);
    }


    public void clickMinusTemperature(ActionEvent actionEvent) {
        lbInfo.setText("");
        tfTemperature.setText(decreaseValue(tfTemperature));
    }

    public void clickPlusTemperature(ActionEvent actionEvent) {
        lbInfo.setText("");
        tfTemperature.setText(increaseValue(tfTemperature));
    }

    public void clickPlusLightIntensity(ActionEvent actionEvent) {
        lbInfo.setText("");
        tfLightIntensity.setText(increaseValue(tfLightIntensity));
    }

    public void clickMinusLightIntensity(ActionEvent actionEvent) {
        lbInfo.setText("");
        tfLightIntensity.setText(decreaseValue(tfLightIntensity));
    }

    public void clickMinusAirMoisture(ActionEvent actionEvent) {
        lbInfo.setText("");
        tfAirMoisture.setText(decreaseValue(tfAirMoisture));
    }

    public void clickMinusSoilMoisture(ActionEvent actionEvent) {
        lbInfo.setText("");
        tfSoilMoisture.setText(decreaseValue(tfSoilMoisture));
    }


    public void clickPlusAirMoisture(ActionEvent actionEvent) {
        lbInfo.setText("");
        tfAirMoisture.setText(increaseValue(tfAirMoisture));
    }

    public void clickPlusSoilMoisture(ActionEvent actionEvent) {
        lbInfo.setText("");
        tfSoilMoisture.setText(increaseValue(tfSoilMoisture));
    }


    private void checkIfFloat(TextField textField) {
        try {
            Float.parseFloat(textField.getText());
        } catch (NumberFormatException e) {
            textField.setText("");
        }
    }

    public void clickedMouse(MouseEvent mouseEvent) {
        tfTemperature.setText("");
    }

    public void checkTemperatureValue(KeyEvent keyEvent) {
        checkIfFloat(tfTemperature);
    }

    public void checkLightIntensityValue(KeyEvent keyEvent) {
        checkIfFloat(tfLightIntensity);
    }

    public void checkAirMoistureValue(KeyEvent keyEvent) {
        checkIfFloat(tfAirMoisture);
    }

    public void checkSoilMoistureValue(KeyEvent keyEvent) {
        checkIfFloat(tfSoilMoisture);
    }


}

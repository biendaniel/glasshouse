package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.LoaderPane;
import main.Plant;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClonePlantController implements Initializable {
    public AnchorPane clonePlantPane;
    public Label lbName;
    public TextField tfName;
    public Label lbPaneInfo;
    public Button btUndo;
    public Label lbInfo;
    public ComboBox cbPlantSelect;
    private Plant plant = null;

    private static ClonePlantController clonePlantController;


    public static ClonePlantController getInstance() {
        if (clonePlantController == null) {
            clonePlantController = new ClonePlantController();
            return clonePlantController;
        } else
            return clonePlantController;
    }

    private ObservableList<String> fillComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList;
        observableList = FXCollections.observableList(Plant.fillPlantTypeList());
        return observableList;
    }

    public void clickUndo(ActionEvent actionEvent) throws IOException {
        LoaderPane.checkPaneForEmployee(clonePlantPane);
    }


    public void clickClone(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            checkCloneParametersAndAdd();
    }

    private void checkCloneParametersAndAdd() throws SQLException, ClassNotFoundException {
        if (plant == null)
            lbInfo.setText("Wybierz roślinę, którą chcesz sklonować");
        else if (tfName.getText().equals(""))
            lbInfo.setText("Podaj nazwę rośliny");
        else {
            plant.addClonedPlant(tfName.getText());
            lbInfo.setText("Dodano rosline");
        }
    }


    public void clickTest(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        plant = new Plant(String.valueOf(cbPlantSelect.getValue()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cbPlantSelect.setItems(fillComboBox());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

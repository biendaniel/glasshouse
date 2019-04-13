package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import main.LoaderPane;

import java.io.IOException;

public class EmployeePaneController{

    private static EmployeePaneController employeePaneController;
    public Button btAddPlant;
    public Button btRemovePlant;
    public Button btCheckCondition;
    public Button btEditUser;
    public Button btAddUser;
    public Pane employeePane;
    public Button btEditPlant;
    public Button btClonePlant;
    public Button btEditController;
    public Button btAddController;

    public static EmployeePaneController getInstance(){
        if(employeePaneController==null) {
            employeePaneController = new EmployeePaneController();
            return employeePaneController;
        } else
            return employeePaneController;
    }

    public void clickAddPlant() throws IOException {
        LoaderPane.setAddPlantPane(employeePane);
        setPlantPaneToAdd();
    }
    public void clickEditPlant(ActionEvent actionEvent) throws IOException {
        LoaderPane.setAddPlantPane(employeePane);
        setPlantPaneToEdit();
    }
    public void clickRemovePlant(ActionEvent actionEvent) throws IOException {
        LoaderPane.setRemovePlantPane(employeePane);
    }
    public void clickAddUser(ActionEvent actionEent) throws IOException {
        LoaderPane.setAddUserPane(employeePane);
    }
    public void clickRemoveUser(ActionEvent actionEvent) throws IOException {
        LoaderPane.setRemovePlantPane(employeePane);
    }
    public void clickEditUser(ActionEvent actionEvent) throws IOException {
        LoaderPane.setAddUserPane(employeePane);
    }
    public void clickCheckConditions(ActionEvent actionEvent) throws IOException {
        LoaderPane.setMainPane(employeePane);
    }

    public void clickClonePlant(ActionEvent actionEvent) throws IOException {
        LoaderPane.setClonePlantPane(employeePane);
    }
    
    public void setPlantPaneToAdd(){
        AddPlantController.getInstance().lbPaneInfo.setText("Dodaj roślinę");
        AddPlantController.getInstance().btEdit.setVisible(false);
        AddPlantController.getInstance().btAdd.setVisible(true);
        AddPlantController.getInstance().cbPlantSelect.setVisible(false);
        AddPlantController.getInstance().lbName.setVisible(true);
        AddPlantController.getInstance().tfName.setVisible(true);
    }

    public void setPlantPaneToEdit(){
        AddPlantController.getInstance().lbPaneInfo.setText("Edytuj roślinę");
        AddPlantController.getInstance().btEdit.setVisible(true);
        AddPlantController.getInstance().btAdd.setVisible(false);
        AddPlantController.getInstance().cbPlantSelect.setVisible(true);
        AddPlantController.getInstance().lbName.setVisible(false);
        AddPlantController.getInstance().tfName.setVisible(false);
    }

    private void setControllerPaneToAdd(){
        AddControllerController.getInstance().lbPaneInfo.setText("Dodaj sterownik");
        AddControllerController.getInstance().cbControllerSelect.setVisible(false);
        AddControllerController.getInstance().btAdd.setVisible(true);
        AddControllerController.getInstance().btEdit.setVisible(false);


    }

    private void setControllerPaneToEdit(){
        AddControllerController.getInstance().lbPaneInfo.setText("Edytuj ustawienia sterownika");
        AddControllerController.getInstance().cbControllerSelect.setVisible(true);
        AddControllerController.getInstance().btAdd.setVisible(false);
        AddControllerController.getInstance().btEdit.setVisible(true);
    }

    public void clickAddController(ActionEvent actionEvent) throws IOException {
        LoaderPane.setAddControllerPane(employeePane);
        setControllerPaneToAdd();
    }

    public void clickEditController(ActionEvent actionEvent) throws IOException {
        LoaderPane.setAddControllerPane(employeePane);
    setControllerPaneToEdit();
    }


    public void clickShowUsers() throws IOException {
        LoaderPane.setUserListPane(employeePane);
    }
}
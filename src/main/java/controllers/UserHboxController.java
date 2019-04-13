package controllers;

import employees.Employee;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.ConnectorDB;
import main.ListPaneController;
import main.Plant;

import java.sql.SQLException;

public class UserHboxController {
    public Button btZaloguj;
    public AnchorPane anchorPane;
    public Label lbID;
    public Label lbSurname;
    public Label lbUsername;
    public Label lbName;
    public Label lbType;
    public HBox hbox;
    public Button btDelete;
    private Employee employee;


    public HBox getHbox() {
        return hbox;
    }

    public UserHboxController(Employee employee) throws SQLException, ClassNotFoundException {
        this.employee = employee;
        setElementsEmployeeValue();
    }


    public UserHboxController() throws SQLException, ClassNotFoundException {
        setHeader();
    }

    private void viewConfirmationWindow() throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz usunąć " + employee.getUsername() + " ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            try {
                String string = ConnectorDB.getInstance().removeUser(employee.getUsername());
                viewAlerWindow(string);
            } catch (SQLException e) {
            }
        } else
            alert.close();
    }

    private void viewAlerWindow(String string) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, string);
        ListPaneController.getInstance().addElement();
    }


    public void setLabel(Label lb) {
        lb.setPrefWidth(80);
    }

    public void setLabels() {
        setLabel(lbID);
        setLabel(lbUsername);
        setLabel(lbName);
        setLabel(lbSurname);
        setLabel(lbType);
    }

    public void addElementsToHBox() {
        hbox.getChildren().add(lbID);
        hbox.getChildren().add(lbUsername);
        hbox.getChildren().add(lbName);
        hbox.getChildren().add(lbSurname);
        hbox.getChildren().add(lbType);
    }

    public void setHeader() {

        hbox = new HBox();
        lbID = new Label("ID");
        lbUsername = new Label("LOGIN");
        lbName = new Label("IMIE");
        lbSurname = new Label("NAZWISKO");
        lbType = new Label("TYP");
        setLabels();
        addElementsToHBox();
        hbox.setPadding(new Insets(5, 0, 5, 10));
        hbox.getChildren().add(new Label(" "));
        hbox.getChildren().add(new Label(" "));
        hbox.setSpacing(40);
    }

    public void setElementsEmployeeValue() {
        btZaloguj = new Button();
        anchorPane = new AnchorPane();
        hbox = new HBox();
        lbID = new Label(String.valueOf(employee.getID()));
        lbUsername = new Label(employee.getUsername());
        lbName = new Label(employee.getName());
        lbSurname = new Label(employee.getSurname());
        lbType = new Label(employee.getType());
        btDelete = new Button("usun");
        setLabels();
        addElementsToHBox();
        hbox.setPadding(new Insets(5, 0, 5, 10));
        hbox.getChildren().add(btDelete);
        hbox.setSpacing(40);
        btDelete.setOnAction(e -> {
            try {
                viewConfirmationWindow();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
    }


}

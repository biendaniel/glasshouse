package main;

import controllers.UserHboxController;
import employees.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


public class ListPaneController implements Initializable {


    public List<HBox> list = new LinkedList<>();
    public VBox vbox;
    public Label lbName;
    public AnchorPane anchorPane;
    private List<Employee> employeeList = new LinkedList<>();

    private static ListPaneController listPaneController;

    public static ListPaneController getInstance() {
        if (listPaneController == null) {
            listPaneController = new ListPaneController();
            return listPaneController;
        } else
            return listPaneController;
    }


    public void addElement() throws SQLException, ClassNotFoundException {
        employeeList = ConnectorDB.getInstance().loadUsers();
        vbox.getChildren().clear();
        UserHboxController userHboxController1 = new UserHboxController();
        vbox.getChildren().add(userHboxController1.getHbox());
        for(Employee it: employeeList) {
            UserHboxController userHboxController = new UserHboxController(it);
            vbox.getChildren().add(userHboxController.getHbox());
        }
        }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            addElement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void clickUndo(ActionEvent actionEvent) throws IOException {
        LoaderPane.checkPaneForEmployee(anchorPane);
    }
}

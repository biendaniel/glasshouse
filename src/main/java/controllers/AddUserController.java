package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import main.LoaderPane;
import main.LoggedUser;
import main.RegistrationUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {
    public PasswordField pfRepassword;
    public TextField taUsername;
    public PasswordField pfPassword;
    public TextField taName;
    public TextField taSurname;
    public ComboBox<String> cbTypeList;
    public Label lbInfo;
    public Pane addUserPane;


    private static AddUserController addUserController;

    public static AddUserController getInstance() {
        if (addUserController == null) {
            addUserController = new AddUserController();
            return addUserController;
        } else
            return addUserController;
    }

    private ObservableList<String> fillComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList;
        observableList = FXCollections.observableList(RegistrationUser.fillEmployeeTypeList());
        return observableList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            cbTypeList.setItems(fillComboBox());
            cbTypeList.getSelectionModel().selectFirst();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void clearFields() {
        pfPassword.clear();
        pfRepassword.clear();
        taUsername.clear();
        taName.clear();
        taSurname.clear();
    }

    public void ClickRegistry() throws SQLException, ClassNotFoundException {
        lbInfo.setText("");
        lbInfo.setText(RegistrationUser.addUser(taUsername.getText(), pfPassword.getText(), pfRepassword.getText(), taName.getText(), taSurname.getText(), RegistrationUser.checkEmployeeTypeID(cbTypeList.getValue())));
        //clearFields();
    }


    public void clickUndo(ActionEvent actionEvent) throws IOException {
        LoaderPane.checkPaneForEmployee(addUserPane);
    }
}

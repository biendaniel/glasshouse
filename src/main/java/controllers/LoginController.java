package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.LoggedUser;
import main.LoginClass;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController{

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField tfUsername;
    @FXML
    public PasswordField pfPassword;
    @FXML
    public Label lbInfo;
    public Button btZaloguj;




    private void closeWindow(){
            Stage stage = (Stage) btZaloguj.getScene().getWindow();
            stage.close();
    }



    @FXML
    public void clickPassword(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        clickZaloguj(actionEvent);
    }
    @FXML
    public void clickZaloguj(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        lbInfo.setText(LoginClass.checkPass(tfUsername.getText(), pfPassword.getText()));
        if(LoggedUser.logged) {
            closeWindow();
        }
    }
}

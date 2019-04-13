package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public Label lbName;
    public Pane mainPane;
    public Button btLogin;


    private static MainWindowController mainWindowController;

    public static MainWindowController getInstance() {
        if (mainWindowController == null) {
            mainWindowController = new MainWindowController();
            return mainWindowController;
        } else
            return mainWindowController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // setMainPaine();
            LoaderPane.setMainPane(mainPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class CheckIfLoggedThread extends Thread {
        @Override
        public void run() {
            while (!LoggedUser.logged) {
                System.out.print(""); //  bez tej linii nie działa :D wtf?
                if (LoggedUser.logged) {
                    makeChangesLogin();
                }
            }
        }
    }


    private void makeChangesLogin() {
        do {
            Platform.runLater(() -> {
                lbName.setText(LoggedUser.username);
                btLogin.setText("Wyloguj");
                try {
                    LoaderPane.checkPaneForEmployee(mainPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } while (!LoggedUser.logged);
    }

    private void makeChangesLogout() {
        do {
            Platform.runLater(() -> {
                lbName.setText("");
                btLogin.setText("Zaloguj");
            });
        } while (!LoggedUser.logged);
    }


    private void openLoginWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxmlFile/login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }


    public void clickLogin() throws IOException {
        CheckIfLoggedThread checkIfLoggedThread = new CheckIfLoggedThread();
        if (LoggedUser.isUserLogin) {
            if (!LoggedUser.logged) {
                checkIfLoggedThread.start();
                openLoginWindow();
            } else
                System.out.println("Ktos juz jest zalogowany");

        } else {
            makeChangesLogout();
            LoaderPane.setMainPane(mainPane);
            MainPaneController.getInstance().btUndo.setVisible(false);
            LoginClass.logout();
        }
    }


}




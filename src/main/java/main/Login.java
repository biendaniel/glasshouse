package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Login extends Application {
    public Login() {
    }

    private static List<Controller> controllerList = new LinkedList<>();

    public static List<Controller> getControllerList() {
        return controllerList;
    }

    private static List<Integer> controllerIDList = new LinkedList<>();

    private static void fillControllerIDList() throws SQLException, ClassNotFoundException {
         controllerIDList.clear();
        controllerIDList = ConnectorDB.getInstance().loadControllersID();
    }


    public static void turnOn() throws SQLException, FileNotFoundException, ClassNotFoundException {
        controllerList.clear();
        fillControllerIDList();
        for (Integer it : controllerIDList) {
            controllerList.add(new Controller(it));
        }
        CurrentController.setController(controllerList.get(0));
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        turnOn();

       Parent root = FXMLLoader.load(this.getClass().getResource("/fxmlFile/mainWindow.fxml"));

        primaryStage.setTitle("Szklarnia");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

package main;

import controllers.*;
import employees.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoaderPane {

    public static void checkPaneForEmployee(Pane pane) throws IOException {
        setEmployeePane(pane);
     LoaderPaneProxy.checkEmployeeType();
    }

    public static void setAddPlantPane(Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("../fxmlFile/addPlantPane.fxml"));
        loader.setControllerFactory(c -> AddPlantController.getInstance());
        Pane newPane = loader.load();
        pane.getChildren().setAll(newPane);
    }

    public static void setEmployeePane(Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("../fxmlFile/employeePane.fxml"));
        loader.setControllerFactory(c -> EmployeePaneController.getInstance());
        Pane newPane = loader.load();
        pane.getChildren().setAll(newPane);
    }


    public static void setAddUserPane(Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("../fxmlFile/addUserPane.fxml"));
        loader.setControllerFactory(c -> AddUserController.getInstance());
        Pane newPane = loader.load();
        pane.getChildren().setAll(newPane);
    }

    public static void setClonePlantPane(Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("../fxmlFile/clonePlantPane.fxml"));
        loader.setControllerFactory(c -> ClonePlantController.getInstance());
        Pane newPane = loader.load();
        pane.getChildren().setAll(newPane);
    }

    public static void setMainPane(Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("../fxmlFile/mainPane.fxml"));
        loader.setControllerFactory(c -> MainPaneController.getInstance());
        Pane newPane = loader.load();
        pane.getChildren().setAll(newPane);
    }


    public static void setRemovePlantPane(Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("../fxmlFile/removePlantPane.fxml"));
        loader.setControllerFactory(c -> RemovePlantController.getInstance());
        Pane newPane = loader.load();
        pane.getChildren().setAll(newPane);
    }

    public static void setAddControllerPane(Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("../fxmlFile/addControllerPane.fxml"));
        loader.setControllerFactory(c -> AddControllerController.getInstance());
        Pane newPane = loader.load();
        pane.getChildren().setAll(newPane);
    }

    public static void setUserListPane(Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindowController.class.getResource("../fxmlFile/userList.fxml"));
        loader.setControllerFactory(c -> ListPaneController.getInstance());
        Pane newPane = loader.load();
        pane.getChildren().setAll(newPane);
    }




}

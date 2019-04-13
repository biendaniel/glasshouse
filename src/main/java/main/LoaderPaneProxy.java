package main;

import controllers.EmployeePaneController;
import controllers.MainWindowController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoaderPaneProxy {

    public static void checkEmployeeType(){
        if(LoggedUser.employeePane.equals("managerPane"))
            setButtonForManager();
        else if(LoggedUser.employeePane.equals("advancedGardenerPane"))
            setButtonForAdvancedGardener();
        else if(LoggedUser.employeePane.equals("gardenerPane"))
            setButtonForGardener();
    }

    private static void setAllVisibleTrue(){
        EmployeePaneController.getInstance().btAddUser.setVisible(true);
        EmployeePaneController.getInstance().btEditUser.setVisible(true);
        EmployeePaneController.getInstance().btAddPlant.setVisible(true);
        EmployeePaneController.getInstance().btRemovePlant.setVisible(true);
        EmployeePaneController.getInstance().btEditPlant.setVisible(true);
        EmployeePaneController.getInstance().btCheckCondition.setVisible(true);
        EmployeePaneController.getInstance().btClonePlant.setVisible(true);
        EmployeePaneController.getInstance().btAddController.setVisible(true);
        EmployeePaneController.getInstance().btEditController.setVisible(true);

    }

    private static void setButtonForManager(){
        setAllVisibleTrue();
        EmployeePaneController.getInstance().btAddPlant.setVisible(false);
        EmployeePaneController.getInstance().btRemovePlant.setVisible(false);
        EmployeePaneController.getInstance().btEditPlant.setVisible(false);
        EmployeePaneController.getInstance().btClonePlant.setVisible(false);
        EmployeePaneController.getInstance().btAddController.setVisible(false);
        EmployeePaneController.getInstance().btEditController.setVisible(false);
    }

    private static void setButtonForAdvancedGardener(){
        setAllVisibleTrue();
        EmployeePaneController.getInstance().btAddUser.setVisible(false);
        EmployeePaneController.getInstance().btEditUser.setVisible(false);
    }

    private static void setButtonForGardener(){
        setAllVisibleTrue();
        EmployeePaneController.getInstance().btAddUser.setVisible(false);
        EmployeePaneController.getInstance().btEditUser.setVisible(false);
        EmployeePaneController.getInstance().btRemovePlant.setVisible(false);
        EmployeePaneController.getInstance().btAddController.setVisible(false);
        EmployeePaneController.getInstance().btEditController.setVisible(false);
    }
}

package main;

import conditions.Condition;

import java.util.List;

public class CurrentController {
    static private Controller controller ;
    static private int idCurrentController;



    public CurrentController() {

    }

    public static Controller getController() {
        return controller;
    }

    public static void setController(Controller ccontroller, int idController) {
        controller = ccontroller;
        idCurrentController = idController;
    }

    public static void setController(Controller ccontroller) {
        controller = ccontroller;
    }

    public static int getIdCurrentController() {
        return idCurrentController;
    }

    public static void setConditions(List<Condition> conditions){
        controller.setCurrentConditions(conditions);
    }


}

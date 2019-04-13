package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginClass {


private static void changeLoggedUserData(String username, String employeeType){
    LoggedUser.logged = true;
    LoggedUser.username = username;
    LoggedUser.isUserLogin = false;
    LoggedUser.employeePane =  employeeType;
}

    public static String checkPass(String username, String password) throws SQLException, ClassNotFoundException {

    String employeeType = ConnectorDB.getInstance().selectUser(username, password);
                if(employeeType != null) {
                    changeLoggedUserData(username, employeeType);
                    return "Pomyślnie zalogowany jako " + username;
                }
        return "Błędny login lub hasło";
        }



    public static void logout(){
        LoggedUser.isUserLogin = true;
        LoggedUser.logged = false;
    }
}



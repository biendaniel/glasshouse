package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class RegistrationUser {

 private final static int usernameLength = 5;
 private final static int passwordLength = 8;
 private final static int nameLength = 2;
 private final static int surnameLength = 2;


    public static String addUser(String username, String password, String rePassword, String name, String surname, int employeeType) throws SQLException, ClassNotFoundException {
        String string = checkDataEntered(username, password, name, surname);
        if(!string.equals(""))
            return string;
        else if(!checkUsername(username) && checkPassword(password, rePassword)) {
            ConnectorDB.getInstance().addUser(name, surname, username, password,employeeType);
            return "Dodano użytkownika " + username;
        }
        else
            if(checkUsername(username))
                return "Nazwa użytkownika jest już zajęta.";
            else
                return "Hasło się nie zgadza.";
        
    }

    private  static boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
        return ConnectorDB.getInstance().checkUsernameExistence(username);
    }

    private static String checkDataEntered(String username, String password, String name, String surname){

        if (!checkLength(username, usernameLength))
            return "Username musi mieć minimum " + usernameLength + " znaków"; // to trzeba później zmienić, bo niełądnie wygląda
        else if (!checkLength(password, passwordLength))                       // wydaje mi się, że będzie trzeba do tej klasy dodać klasę Employee, zrobić konstruktor etc
            return "Hasło musi mieć minimum " + passwordLength + " znaków";      // ale to już później
        else if (!checkLength(name, nameLength))
            return "Imię musi mieć minimum " + nameLength + " znaki";
        else if (!checkLength(surname, surnameLength))
            return "Nazwisko musi mieć minimum " + surname + " znaki";
    return "";

    }

    private static boolean checkPassword(String password, String rePassword){
        if(password.equals(rePassword))
            return true;
        else
            return false;
    }

    private static boolean checkLength(String word, int length){
        if(word.length() < length)
            return false;
        else
            return true;
    }



    public static List<String> fillEmployeeTypeList() throws SQLException, ClassNotFoundException {
        return ConnectorDB.getInstance().returnEmployeeTypeList();
    }


    public static int checkEmployeeTypeID(String employeeTypeName) throws SQLException, ClassNotFoundException {
        return ConnectorDB.getInstance().returnEmployeeTypeID(employeeTypeName);
    }
}


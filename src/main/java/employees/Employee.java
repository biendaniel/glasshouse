package employees;

import main.ConnectorDB;

import java.sql.SQLException;
import java.util.*;

public  class Employee {

    public Employee() {
    }

    private int ID;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String type;

    public Employee(int ID, String username, String password, String name, String surname, String type) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.type = type;
    }

    public Employee(int ID, String username, String name, String surname, String type) {
        this.ID = ID;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.type = type;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static List<Employee> fillEmployeeList() throws SQLException, ClassNotFoundException {
        return ConnectorDB.getInstance().loadUsers();
    }


    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
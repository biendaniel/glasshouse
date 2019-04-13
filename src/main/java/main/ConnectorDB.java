package main;

import conditions.Condition;
import employees.Employee;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ConnectorDB {
    private static String URL = "jdbc:sqlserver://localhost:1433;databaseName=szklarniaDB";
    private static String USER = "sa";
    private static String PASS = "password1";

    private static ConnectorDB connectorDB;

    private ConnectorDB() {
    }

    public static ConnectorDB getInstance() {
        if (connectorDB == null)
            connectorDB = new ConnectorDB();
        return connectorDB;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        connection = DriverManager.getConnection(URL, USER, PASS);

        return connection;
    }


    private void closingConnection(PreparedStatement ps, Connection connection) throws SQLException {
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }

    }

    private void closingConnection(PreparedStatement ps, Connection connection, ResultSet resultSet) throws SQLException {

        if (resultSet != null) {

            resultSet.close();
        }
        if (ps != null) {

            ps.close();
        }
        if (connection != null) {
            connection.close();
        }

    }
    //// USER/////

    public String selectUser(String username, String password) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select users.username, users.password, employeetype.paneName from users, employeetype  where users.EmployeeTypeID = employeetype.id";
        String result = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (username.equalsIgnoreCase(resultSet.getString(1)) && password.equalsIgnoreCase(resultSet.getString(2)))
                    result = resultSet.getString(3);
            }
        } catch (SQLException e) {
            System.out.println("!!!!!!!!!!!selectUser");
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }

        return result;
    }

    public void addUser(String name, String surname, String username, String password, int employeeType) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, IdKeeper.returnId("users"));
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);
            preparedStatement.setInt(6, employeeType);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("blad dodawnia usera");
        } finally {
            closingConnection(preparedStatement, connection);
        }
    }

    public List<String> returnEmployeeTypeList() throws SQLException, ClassNotFoundException {
        List<String> employeeTypeList = new LinkedList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String query = "select name from EmployeeType";
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employeeTypeList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Błąd przy zwracaniu listy");
        } finally {
            closingConnection(preparedStatement, connection);
        }
        return employeeTypeList;
    }


    public int returnEmployeeTypeID(String employeeTypeName) throws SQLException, ClassNotFoundException {
        int result = 0;
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement("select EmployeeType.id from EmployeeType where  EmployeeType.name = ?");
            preparedStatement.setString(1, employeeTypeName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return result;
    }

    public boolean checkUsernameExistence(String username) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = getConnection();
        boolean result = false;
        try {
            preparedStatement = connection.prepareStatement("select count(*) from users where username like ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1)
                    result = true;
                else
                    result = false;
            }
            result = false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return result;
    }

    public List<Employee> loadUsers() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = getConnection();
        String query = "SELECT users.id, users.username, users.name, users.surname, EmployeeType.name FROM users, EmployeeType WHERE users.EmployeeTypeID = EmployeeType.id;";
        List<Employee> employeeList = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                employeeList.add(new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
        } catch (SQLException e) {

        } finally {

        }
        return employeeList;
    }

    public String removeUser(String username) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        String query = "DELETE FROM users where username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.execute();
            return "Usunięto użytkownika";
        } catch (SQLException e) {
            return "Coś poszło nie tak";
        } finally {
            closingConnection(preparedStatement, connection);
        }
    }


    /////   PLANTS ///////
    public int loadPlants(String name) throws SQLException, ClassNotFoundException {
        int conditionID = 0;
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select conditionsID from plant where name = ?;");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                conditionID = resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("!!!!!!!!!!!LOAD PLANTS");
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return conditionID;
    }

    public String findPlantNameByID(int id) throws SQLException, ClassNotFoundException {// TODO w porzpedniej wersji byla funkcja, ktora po id
        // TODO Zwracala ConditionID i name, ale jak na razie cos takiego wymyslilem
        String name = "";
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select name from plant where id = ? ;");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                name = resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println("!!!!!!!!!!!findPlantName byID");
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return name;
    }

    public int findPlantIDByName(String name) throws SQLException, ClassNotFoundException {
        int id = 0;
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select id from plant where name = ? ;");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                id = resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("!!!!!!!!!!!findPlantName byID");
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return id;
    }


    public List<String> returnPlantsName() throws SQLException, ClassNotFoundException {
        List<String> plantTypeList = new LinkedList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT name FROM Plant";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                plantTypeList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("!!!!!!!!!!!returnPlantsName");
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return plantTypeList;
    }


    public String deletePlant(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement("delete from Plant where name =  ? ");
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            if (!name.equalsIgnoreCase("null")) {
                return "Usunięto roślinę";
            } else return "";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Nie można usunąć rośliny. \n Prawdopodobnie jest przypisana do któregoś z obszarów";
        } finally {
            closingConnection(preparedStatement, connection);
        }
    }

    public void savePlant(String name, int conditionID) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement("insert into plant values (?, ?, ?);");

            preparedStatement.setInt(1, IdKeeper.returnId("plant"));
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, conditionID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection);
        }
    }

    public void editPlantConditionsInDataBase(List<Condition> conditionsList, int id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        int i = 1;
        try {
            preparedStatement = connection.prepareStatement("UPDATE Conditions SET Temperature = ?, AirMoisture = ?, SoilMoisture = ?,  LightIntensity = ? WHERE id = ?;");
            for (Condition condition : conditionsList) {
                preparedStatement.setFloat(i++, condition.getValue());
            }
            preparedStatement.setInt(i, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        } finally {
            closingConnection(preparedStatement, connection);
        }
    }


    public int addPlantConditions(List<Condition> conditionsList) throws SQLException, ClassNotFoundException {
        int conditionID = IdKeeper.returnId("conditions");
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        int i = 1;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO Conditions values (?, ?, ?, ?, ?)");
            preparedStatement.setInt(i++, conditionID);
            for (Condition condition : conditionsList) {
                preparedStatement.setFloat(i++, condition.getValue());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection);
        }
        return conditionID;
    }

    public List<Condition> returnPlantConditions(int conditionID) throws SQLException, ClassNotFoundException {
        List<Condition> conditionsList = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM conditions WHERE id = ?;");
            preparedStatement.setInt(1, conditionID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                for (int i = 2; i < 6; ++i)
                    conditionsList.add(new Condition(resultSet.getFloat(i)));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return conditionsList;
    }

    public void saveConditionsToDataBase(int placeID, List<Condition> condition) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        try {
        preparedStatement = connection.prepareStatement("INSERT INTO measurement VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? ");
        preparedStatement.setInt(1, IdKeeper.returnId("measurement"));
        preparedStatement.setInt(2, placeID);
        preparedStatement.setString(3, "GETDATE()");
        int i = 4;
        for (Condition it : condition)
            preparedStatement.setFloat(i++, it.getValue());
        preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        } finally {
            closingConnection(preparedStatement, connection);
        }
    }

    public String loadPlaceFromDataBase(int id) throws SQLException, ClassNotFoundException {
        String placeStatus = "";
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT status FROM place WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                placeStatus = resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return placeStatus;
    }


    public List<String> loadPlaceList() throws SQLException, ClassNotFoundException {
        List<String> list = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id FROM place");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                list.add(resultSet.getString(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return list;
    }

    public List<String> loadFreePlaceList() throws SQLException, ClassNotFoundException {
        List<String> list = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id FROM place where status = 'wolny'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                list.add(resultSet.getString(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return list;
    }

    public void updatePlaceStatus(int id, String status) throws SQLException, ClassNotFoundException {
        String query = "UPDATE place SET status = ? where id = ?";
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
        } finally {
            closingConnection(preparedStatement, connection);
        }


    }

    public List<Condition> loadCurrentConditions(int id) throws SQLException, ClassNotFoundException {
        List<Condition> conditions = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select * from BasicReadingsFromSensors where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conditions.add(new Condition(resultSet.getFloat(2)));
                conditions.add(new Condition(resultSet.getFloat(3)));
                conditions.add(new Condition(resultSet.getFloat(4)));
                conditions.add(new Condition(resultSet.getFloat(5)));
            }
        } catch (SQLException e) {
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return conditions;
    }

    public void returnControllerByID(Controller controller) throws SQLException, ClassNotFoundException {
        int plantID = 0;
        int placeID = 0;
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT plantID, placeID FROM Controller WHERE id = ?");
            preparedStatement.setInt(1, controller.getId()); //TODO tutaj trzeba dodać dynamiczne wczytywanie id;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                plantID = resultSet.getInt(1);
                placeID = resultSet.getInt(2);
            }
            controller.addPlantToController(plantID);
            controller.addPlaceToController(placeID);
        } catch (SQLException e) {
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
    }


    public int checkId(String tableName) throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(id) FROM " + tableName;
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        int value = 1;
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                value = resultSet.getInt(1);
            }
        } catch (SQLException e) {
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return value;
    }

    public List<Integer> loadPlaceFromController() throws SQLException, ClassNotFoundException {
        List<Integer> placeList = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        String query = "SELECT placeId FROM Controller";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                placeList.add(resultSet.getInt(1));
        } catch (SQLException e) {
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return placeList;
    }

    public int loadControllerIDbyPlaceID(int placeID) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        int controllerID = 0;
        String query = "SELECT id FROM controller WHERE placeID = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, placeID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                controllerID = resultSet.getInt(1);

        } catch (SQLException e) {
        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return controllerID;
    }

    public void addController(int placeID, int plantID) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        String query = "INSERT INTO controller values(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, IdKeeper.returnId("Controller"));
            preparedStatement.setInt(2, plantID);
            preparedStatement.setInt(3, placeID);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
        } finally {
            closingConnection(preparedStatement, connection);
        }
    }

    public List<Integer> loadControllersID() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet resultSet = null;
        String query = "SELECT id FROM controller";
        List<Integer> idList = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                idList.add(resultSet.getInt(1));
        } catch (SQLException e) {

        } finally {
            closingConnection(preparedStatement, connection, resultSet);
        }
        return idList;
    }

    public void editController(int id, int plantID, int placeID) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        String query = "UPDATE controller SET plantID = ?, placeID = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, plantID);
            preparedStatement.setInt(2, placeID);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

        } finally {

        }
    }
}
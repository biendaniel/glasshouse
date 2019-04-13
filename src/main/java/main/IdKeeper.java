package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IdKeeper {
    final static String IdQuery = "Select max(id) from "; //TODO dodać do singletona

    private static int checkId(String tableName) throws SQLException, ClassNotFoundException {
        return ConnectorDB.getInstance().checkId(tableName);
    }

    public static int returnId(String tableName) throws SQLException, ClassNotFoundException {
        int tmp = ConnectorDB.getInstance().checkId(tableName);
        return ++tmp;
    }
}

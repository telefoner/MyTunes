package bll;

import dal.DBConnector;

import java.sql.SQLException;

public class DBConnectionSettler {
    public static void setConnection() throws SQLException {
        DBConnector.connectDb();
    }
}

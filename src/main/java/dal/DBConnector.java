package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    public static void main(String[] args) throws SQLException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("EASV-DB4");
        ds.setPortNumber(1433);
        ds.setDatabaseName("YOUR_PREFIX_TicTacToe");
        ds.setUser("YOUR_USERNAME");
        ds.setPassword("YOUR_PASSWORD");
        ds.setTrustServerCertificate(true);
        try (Connection con = ds.getConnection()) {
            System.out.println("Connected to " + con.getCatalog());

        }
    }
}
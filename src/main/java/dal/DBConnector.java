package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DBConnector {
    public static void main(String[] args) throws SQLException {

        SQLServerDataSource ds = new SQLServerDataSource();

        ds.setServerName("*****");
        ds.setPortNumber(1433);
        ds.setDatabaseName("****");
        ds.setUser("******");
        ds.setPassword("****");
        ds.setTrustServerCertificate(true);

        try (Connection con = ds.getConnection()) {
            System.out.println("Connected to " + con.getCatalog());
        }

    }
}

package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class DBConnector {
    public static void connectDb() throws SQLException {
        String serverName = "";
        String databaseName = "";
        String user = "";
        String password = "";

        File config = new File("src/main/resources/config.settings");

        try (Scanner reader = new Scanner(config)) {
            serverName = reader.nextLine().split("=", 2)[1];
            databaseName = reader.nextLine().split("=", 2)[1];
            user = reader.nextLine().split("=", 2)[1];
            password = reader.nextLine().split("=", 2)[1];
        } catch (FileNotFoundException e) {
            System.out.println("config.settings Was not found.");
        }

        SQLServerDataSource ds = new SQLServerDataSource();

        ds.setServerName(serverName);
        ds.setPortNumber(1433);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);
        ds.setTrustServerCertificate(true);

        try (Connection con = ds.getConnection()) {
            System.out.println("Connected to " + con.getCatalog());
        }

    }
}

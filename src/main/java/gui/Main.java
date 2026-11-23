package gui;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) throws SQLException {

        SQLServerDataSource ds = new SQLServerDataSource();

        ds.setServerName("DELETED");
        ds.setPortNumber(1433);
        ds.setDatabaseName("master");
        ds.setUser("CS2026b_e_11");
        ds.setPassword("CS2026bE11#26");
        ds.setTrustServerCertificate(true);

        try (Connection con = ds.getConnection()) {
            System.out.println("Connected to " + con.getCatalog());
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("MyTunes");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

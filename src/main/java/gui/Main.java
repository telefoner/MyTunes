package gui;

import bll.DBConnectionSettler;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DBConnectionSettler.setConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("MyTunes");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

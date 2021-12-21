package presentation.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.JdbcPokemonRepository;
import persistence.PokemonRepository;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class app extends Application{
    //public static final String JDBC_URL = "jdbc:sqlserver://IFSQL-01.htl-stp.if:1433;databaseName=db_pokemon_goetz_pils;user=sa";
    //private Connection connection;
    //private PokemonRepository repository;

    @Override
    public void start(Stage stage) throws Exception{
        //connection = DriverManager.getConnection(JDBC_URL);
        //repository = new JdbcPokemonRepository(connection);
        URL url = new File("src/main/resources/PokemonFXML.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pokemon");
        stage.show();
        stage.setResizable(false);
    }

    /*@Override
    public void stop() throws SQLException{
        connection.close();
    }*/
}

package presentation.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class app extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        URL url = new File("src/main/resources/PokemonFXML.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Pokemon");
        stage.show();
        stage.setResizable(false);
    }
}

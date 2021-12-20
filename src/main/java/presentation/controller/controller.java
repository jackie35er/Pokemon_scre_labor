package presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class controller implements Initializable{
    @FXML
    private ImageView allyGender;

    @FXML
    private Rectangle allyHealthbar;

    @FXML
    private ImageView allyImg;

    @FXML
    private Label allyLvl;

    @FXML
    private Label allyName;

    @FXML
    private ImageView background;

    @FXML
    private Label emenyName;

    @FXML
    private ImageView enemyGender;

    @FXML
    private Rectangle enemyHealthbar;

    @FXML
    private ImageView enemyImg;

    @FXML
    private Label enemyLvl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadImages();
    }

    private void loadImages(){
        background.setImage(new Image("file:src/main/resources/background.png"));
    }
}
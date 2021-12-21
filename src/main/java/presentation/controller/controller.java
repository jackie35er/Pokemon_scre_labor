package presentation.controller;

import domain.Pokemon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import persistence.JdbcPokemonRepository;
import persistence.PokemonRepository;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class controller implements Initializable{
    @FXML
    private Rectangle allyHealthbar;

    @FXML
    private Label allyHp;

    @FXML
    private ImageView allyImg;

    @FXML
    private Label allyName;

    @FXML
    private ImageView background;

    @FXML
    private Button buttonAttack1;

    @FXML
    private Button buttonAttack2;

    @FXML
    private Button buttonAttack3;

    @FXML
    private Button buttonAttack4;

    @FXML
    private Button buttonBag;

    @FXML
    private Button buttonFight;

    @FXML
    private Button buttonPokemon;

    @FXML
    private Button buttonRun;

    @FXML
    private Rectangle enemyHealthbar;

    @FXML
    private Label enemyHp;

    @FXML
    private ImageView enemyImg;

    @FXML
    private Label enemyName;

    @FXML
    private Label labelInfo;

    public static final String JDBC_URL = "jdbc:sqlserver://IFSQL-01.htl-stp.if:1433;databaseName=db_pokemon_goetz_pils;user=sa";
    public Connection connection;
    public PokemonRepository repository;

    public Pokemon ally;
    public Pokemon enemy;
    public int allyMaxHp;
    public int enemyMaxHp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            repository = new JdbcPokemonRepository(connection);
            background.setImage(new Image("file:src/main/resources/background.png"));
            selectPokemon();
            buttonFightEventSetup();
            connection.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectPokemon() throws SQLException {
        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
        Random random = new Random();
        ally = repository.findById(random.nextInt(150) + 1).get();
        enemy = repository.findById(random.nextInt(150) + 1).get();

        allyMaxHp = ally.stats().getHealth().get();
        allyName.setText(String.valueOf(ally.getName()).toUpperCase(Locale.ROOT));
        allyHp.setText(allyMaxHp + "/" + allyMaxHp);
        allyImg.setImage(new Image(url + "back/" + ally.getId() + ".png"));

        enemyMaxHp = enemy.stats().getHealth().get();
        enemyName.setText(String.valueOf(enemy.getName()).toUpperCase(Locale.ROOT));
        enemyHp.setText(enemyMaxHp + "/" + enemyMaxHp);
        enemyImg.setImage(new Image(url + enemy.getId() + ".png"));
    }

    public void buttonFightEventSetup(){
        List<Button> attackButtons = List.of(buttonAttack1, buttonAttack2, buttonAttack3, buttonAttack4);
        buttonFight.setOnAction(event -> {
            labelInfo.setText("How will you attack?");
            for(Button button : attackButtons){
                button.setVisible(true);
            }
        });
        for(Button button : attackButtons){
            button.setOnAction(event -> {
                labelInfo.setText("What will you do?");
                for (Button tempButton : attackButtons){
                    tempButton.setVisible(false);
                }
            });
        }
    }
}
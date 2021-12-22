package presentation.controller;

import domain.Pokemon;
import domain.PokemonInterface;
import domain.gameLogic.GameDirector;
import domain.gameLogic.GameStateImpl;
import domain.gameLogic.SimpleGameDirector;
import domain.moveImplementaions.MoveBase;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import persistence.JdbcPokemonRepository;
import persistence.JdbcTypeRepository;
import persistence.PokemonRepository;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable{
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
    public static Connection connection;
    public PokemonRepository repository;

    public PokemonInterface ally;
    public PokemonInterface enemy;
    public int allyMaxHp;
    public int enemyMaxHp;
    private GameDirector gameDirector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Logger is für loser");
            connection = DriverManager.getConnection(JDBC_URL);
            repository = new JdbcPokemonRepository(connection);
            MoveBase.setTypeRepository(new JdbcTypeRepository(connection));
            background.setImage(new Image("file:src/main/resources/background.png"));
            selectPokemon();
            buttonFightEventSetup();
            setupEnd();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        allyHealthbar.widthProperty().bind(new IntegerBinding() {
            {
                super.bind(allyHealthbar.widthProperty(),ally.getStats().getHealth());
            }

            @Override
            protected int computeValue() {
                double value = ally.getStats().getHealth().getValue() /(double) allyMaxHp * 100 * 2;
                if(value <= 0)
                    return 200;
                return (int) (200 - value);
            }
        });
        enemyHealthbar.widthProperty().bind(new IntegerBinding() {
            {
                super.bind(enemyHealthbar.widthProperty(),enemy.getStats().getHealth());
            }

            @Override
            protected int computeValue() {
                double value = enemy.getStats().getHealth().getValue() /(double) enemyMaxHp * 100 * 2;
                if(value <= 0)
                    return 200;
                return (int) (200 - value);
            }
        });
    }


    public void selectPokemon() throws SQLException {
        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
        Random random = new Random();
        ally = repository.findById(random.nextInt(150) + 1).orElseThrow(() -> new IllegalStateException("Something wrong with the database") );
        enemy = repository.findById(random.nextInt(150) + 1).orElseThrow(() -> new IllegalStateException("Something wrong with the database") );

        this.gameDirector = new SimpleGameDirector(new GameStateImpl(ally,enemy),new JdbcTypeRepository(connection));

        buttonAttack1.setText(ally.getMoveSet().getMove(0).getName());
        buttonAttack2.setText(ally.getMoveSet().getMove(1).getName());
        buttonAttack3.setText(ally.getMoveSet().getMove(2).getName());
        buttonAttack4.setText(ally.getMoveSet().getMove(3).getName());

        allyMaxHp = ally.getStats().getHealth().get();
        allyName.setText(String.valueOf(ally.getName()).toUpperCase(Locale.ROOT));
        allyHp.setText(allyMaxHp + "/" + allyMaxHp);
        allyImg.setImage(new Image(url + "back/" + ally.getId() + ".png"));

        enemyMaxHp = enemy.getStats().getHealth().get();
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
//        for(Button button : attackButtons){
//            button.setOnAction(event -> {
//                labelInfo.setText("What will you do?");
//                for (Button tempButton : attackButtons){
//                    tempButton.setVisible(false);
//                }
//            });
//        }
        buttonAttack1.setOnAction(event ->{
            labelInfo.setText("What will you do?");
            for (Button tempButton : attackButtons){
                tempButton.setVisible(false);
            }
            gameDirector.makeTurn(0,new Random().nextInt(4));
        });
        buttonAttack2.setOnAction(event ->{
            labelInfo.setText("What will you do?");
            for (Button tempButton : attackButtons){
                tempButton.setVisible(false);
            }
            gameDirector.makeTurn(1,new Random().nextInt(4));
        });
        buttonAttack3.setOnAction(event ->{
            labelInfo.setText("What will you do?");
            for (Button tempButton : attackButtons){
                tempButton.setVisible(false);
            }
            gameDirector.makeTurn(2,new Random().nextInt(4));
        });
        buttonAttack4.setOnAction(event ->{
            labelInfo.setText("What will you do?");
            for (Button tempButton : attackButtons){
                tempButton.setVisible(false);
            }
            gameDirector.makeTurn(3,new Random().nextInt(4));
        });
    }

    public void setupEnd(){
        List<Button> attackButtons = List.of(buttonFight, buttonBag, buttonPokemon, buttonRun);
        ally.getStats().getHealth().addListener((observable,oldValue,newValue) -> {
            if(newValue.intValue() <= 0){
                attackButtons.forEach(n -> n.setVisible(false));
                this.labelInfo.setText("You lost");
            }
        });
        enemy.getStats().getHealth().addListener((observable,oldValue,newValue) -> {
            if(newValue.intValue() <= 0){
                attackButtons.forEach(n -> n.setVisible(false));
                this.labelInfo.setText("You won");
            }
        });
    }

}
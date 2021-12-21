package persistence;

import domain.Pokemon;
import domain.Stats;
import domain.Type;
import domain.Types;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.*;
import persistence.setup.TestConnectionSupplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JdbcPokemonRepositoryTest {

    private final TestConnectionSupplier connectionSupplier = new TestConnectionSupplier();
    private PokemonRepository pokemonRepository;
    private Connection connection;

    @BeforeEach
    void createRepository() throws SQLException {
        connection = connectionSupplier.getConnection();
        pokemonRepository = new JdbcPokemonRepository(connection);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        connection.close();
    }

    @Nested
    class Finding{
        @Test
        void findByIdTest() throws SQLException {
            var pokemon = pokemonRepository.findById(1).get();

            assert pokemon.getId() == 1;
        }

        @Test
        void findByNameTest() throws SQLException {
            var pokemon = pokemonRepository.findByName("bulbasaur").get();

            assert pokemon.getName().equals("bulbasaur");
        }

        @Test
        void findAllTest() throws SQLException {
            List<Pokemon> pokemonList = pokemonRepository.findAll();

            assert pokemonList.size() > 0;
        }

        @Test
        void findAllByPrimaryTypeTest() throws SQLException {
            List<Pokemon> pokemonList = pokemonRepository.findAllByPrimaryType(new Type(11, "test"));

            assert pokemonList.size() > 0;
        }

        @Test
        void findAllBySecondaryTypeTest() throws SQLException {
            List<Pokemon> pokemonList = pokemonRepository.findAllBySecondaryType(new Type(4, "test"));

            assert pokemonList.size() > 0;
        }
    }

    @Nested
    class Deleting{
        @Test
        void deleteByIdTest() throws SQLException {
            var pokemon = new Pokemon(152,
                    "test",
                    new Stats(new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1)),
                    new Types(new Type(1, "test"),
                            new Type(2, "test")));

            pokemonRepository.save(pokemon);

            assert pokemonRepository.deleteById(pokemon.id()) == 1;
        }

        @Test
        void deleteByNameTest() throws SQLException {
            var pokemon = new Pokemon(152,
                    "test",
                    new Stats(new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1),
                            new SimpleIntegerProperty(1)),
                    new Types(new Type(1, "test"),
                            new Type(2, "test")));

            pokemonRepository.save(pokemon);

            assert pokemonRepository.deleteByName(pokemon.name()) > 0;
        }

        @Test
        void deleteTest() throws SQLException {
            //TODO
            //TEST ONLY WITH CLONED DATABASE!!!
            //OTHERWISE WILL DELETE EXISTING POKEMON DATA!!!
        }
    }
}

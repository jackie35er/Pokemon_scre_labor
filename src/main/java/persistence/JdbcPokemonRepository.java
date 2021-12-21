package persistence;

import domain.Pokemon;
import domain.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public record JdbcPokemonRepository (Connection connection) implements PokemonRepository{
    @Override
    public Optional<Pokemon> findById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Pokemon> findByName(String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Pokemon> findAll() throws SQLException {
        return null;
    }

    @Override
    public List<Pokemon> findAllByPrimaryType(Type type) throws SQLException {
        return null;
    }

    @Override
    public List<Pokemon> findAllBySecondaryType(Type type) throws SQLException {
        return null;
    }

    @Override
    public boolean save(Pokemon pokemon) throws SQLException {
        var sql = """
                insert into Pokemons values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, pokemon.id());
            statement.setString(2, pokemon.name());
            statement.setInt(3, pokemon.stats().getHealth().get());
            statement.setInt(4, pokemon.stats().getAttack().get());
            statement.setInt(5, pokemon.stats().getDefense().get());
            statement.setInt(6, pokemon.stats().getSpecialAttack().get());
            statement.setInt(7, pokemon.stats().getSpecialDefense().get());
            statement.setInt(8, pokemon.stats().getSpeed().get());
            statement.setInt(9, pokemon.types().primaryType().id());
            statement.setInt(10, pokemon.types().secondaryType().id());
            return statement.executeUpdate() == 1;
        }
    }

    @Override
    public void deleteById(int id) throws SQLException{

    }

    @Override
    public void deleteByName(String name) throws SQLException{

    }

    @Override
    public void delete() throws SQLException{

    }

    @Override
    public double getMultiplierForTypes(Type user, Type target) {
        return 0;
    }
}

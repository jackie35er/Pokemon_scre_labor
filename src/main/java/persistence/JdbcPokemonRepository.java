package persistence;

import domain.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record JdbcPokemonRepository(Connection connection) implements PokemonRepository {
    @Override
    public Optional<Pokemon> findById(int id) throws SQLException {
        var sql = """
                select Pokemons.*, Types1.name as primary_name, Types2.name as secondary_name from Pokemons
                inner join Types as Types1 on Pokemons.primary_type = Types1.id
                left join Types as Types2 on Pokemons.secondary_type = Types2.id
                where Pokemons.id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(createPokemon(resultSet));
        }
    }

    @Override
    public Optional<Pokemon> findByName(String name) throws SQLException {
        var sql = """
                select Pokemons.*, Types1.name as primary_name, Types2.name as secondary_name from Pokemons
                inner join Types as Types1 on Pokemons.primary_type = Types1.id
                left join Types as Types2 on Pokemons.secondary_type = Types2.id
                where Pokemons.name = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Optional.of(createPokemon(resultSet));
        }
    }

    @Override
    public List<Pokemon> findAll() throws SQLException {
        var sql = """
                select Pokemons.*, Types1.name as primary_name, Types2.name as secondary_name from Pokemons
                inner join Types as Types1 on Pokemons.primary_type = Types1.id
                left join Types as Types2 on Pokemons.secondary_type = Types2.id
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Pokemon> pokemonList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                pokemonList.add(createPokemon(resultSet));
            return pokemonList;
        }
    }

    @Override
    public List<Pokemon> findAllByPrimaryType(Type type) throws SQLException {
        var sql = """
                select Pokemons.*, Types1.name as primary_name, Types2.name as secondary_name from Pokemons
                inner join Types as Types1 on Pokemons.primary_type = Types1.id
                left join Types as Types2 on Pokemons.secondary_type = Types2.id
                where Pokemons.primary_type = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, type.id());
            List<Pokemon> pokemonList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                pokemonList.add(createPokemon(resultSet));
            return pokemonList;
        }
    }

    @Override
    public List<Pokemon> findAllBySecondaryType(Type type) throws SQLException {
        var sql = """
                select Pokemons.*, Types1.name as primary_name, Types2.name as secondary_name from Pokemons
                inner join Types as Types1 on Pokemons.primary_type = Types1.id
                left join Types as Types2 on Pokemons.secondary_type = Types2.id
                where Pokemons.secondary_type = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, type.id());
            List<Pokemon> pokemonList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pokemonList.add(createPokemon(resultSet));
            }
            return pokemonList;
        }
    }

    @Override
    public boolean save(Pokemon pokemon) throws SQLException {
        var sql = """
                insert into Pokemons values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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
    public int deleteById(int id) throws SQLException {
        var sql = """
                delete from Pokemons
                where id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    @Override
    public int deleteByName(String name) throws SQLException {
        var sql = """
                delete from Pokemons
                where name = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            return statement.executeUpdate();
        }
    }

    @Override
    public int delete() throws SQLException {
        var sql = """
                delete from Pokemons
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        }
    }

    public Pokemon createPokemon(ResultSet resultSet) throws SQLException {
        return new Pokemon(resultSet.getInt("id"),
                resultSet.getString("name"),
                new Stats(new SimpleIntegerProperty(resultSet.getInt("hp")),
                        new SimpleIntegerProperty(resultSet.getInt("attack")),
                        new SimpleIntegerProperty(resultSet.getInt("defense")),
                        new SimpleIntegerProperty(resultSet.getInt("sp_attack")),
                        new SimpleIntegerProperty(resultSet.getInt("sp_defense")),
                        new SimpleIntegerProperty(resultSet.getInt("speed"))),
                new Types(
                        new Type(resultSet.getInt("primary_type"), resultSet.getString("primary_name")),
                        new Type(resultSet.getInt("secondary_type"), resultSet.getString("secondary_name"))),
                new MoveSetImpl());
    }

    @Override
    public double getMultiplierForTypes(Type user, Type target) {
        return 0;
    }
}

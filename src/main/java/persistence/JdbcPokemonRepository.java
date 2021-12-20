package persistence;

import domain.Pokemon;
import domain.Type;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcPokemonRepository implements PokemonRepository{
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
    public Pokemon save(Pokemon pokemon) throws SQLException {
        return null;
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
}

package persistence;

import domain.Pokemon;
import domain.Type;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PokemonRepository {
    /**
     * Searches for pokemon with given id
     *
     * @param id id to search for
     * @return pokemon with id
     * @throws SQLException
     */
    Optional<Pokemon> findById(int id) throws SQLException;

    /**
     * Searches for pokemon with given name
     *
     * @param name name to search for
     * @return pokemon with name
     * @throws SQLException
     */
    Optional<Pokemon> findByName(String name) throws SQLException;

    /**
     * Extracts all pokemon
     *
     * @return list of pokemon
     * @throws SQLException
     */
    List<Pokemon> findAll() throws SQLException;

    /**
     * Searches for pokemon with given primary type
     *
     * @param type primary type
     * @return list of pokemon
     * @throws SQLException
     */
    List<Pokemon> findAllByPrimaryType(Type type) throws SQLException;

    /**
     * Searches for pokemon with given secondary type
     *
     * @param type secondary type
     * @return list of pokemon
     * @throws SQLException
     */
    List<Pokemon> findAllBySecondaryType(Type type) throws SQLException;

    /**
     * Saves new pokemon to database
     *
     * @param pokemon pokemon to save
     * @return pokemon containing at least data given
     * @throws SQLException
     */
    boolean save(Pokemon pokemon) throws SQLException;

    /**
     * Delete pokemon with given id
     *
     * @param id id to delete
     */
    void deleteById(int id) throws SQLException;

    /**
     * Delete pokemon with given name
     *
     * @param name name to delete
     */
    void deleteByName(String name) throws SQLException;

    /**
     * Delete all pokemon
     */
    void delete() throws SQLException;

    /**
     * return the multiplier used when user attacks the target;
     * @param user user type
     * @param target target type
     * @return the multiplier
     */
    double getMultiplierForTypes(Type user,Type target);
}
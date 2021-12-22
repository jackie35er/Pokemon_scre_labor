package persistence;

import domain.Type;

import java.sql.SQLException;
import java.util.Optional;

public interface TypeRepository {
    /**
     * return the multiplier used when user attacks the target;
     * @param user user type
     * @param target target type
     * @return the multiplier
     */
    double getMultiplierForTypes(Type user, Type target) throws SQLException;

    /**
     * Searches for types with given id
     *
     * @param id id to search for
     * @return type with id
     * @throws SQLException
     */
    Optional<Type> getTypeById(int id) throws SQLException;

    /**
     * Searches for types with given name
     *
     * @param name id to search for
     * @return type with name
     * @throws SQLException
     */
    Optional<Type> getTypeByName(String name) throws SQLException;
}

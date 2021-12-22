package persistence;

import domain.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public record JdbcTypeRepository(Connection connection) implements TypeRepository{

    public double getMultiplierForTypes(Type user, Type target) throws SQLException {
        String sql = """
                Select multiplier from Multipliers
                where userType = ? and targetType = ?
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(2,user.id());
            preparedStatement.setInt(1,target.id());
            var result = preparedStatement.executeQuery();
            if(result.next())
                return result.getDouble(1);
        }
        return 1;
    }

    /**
     * Searches for types with given id
     *
     * @param id id to search for
     * @return type with id
     * @throws SQLException
     */
    @Override
    public Optional<Type> getTypeById(int id) throws SQLException {
        String sql = """
                Select * from Types
                where id = ?
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            var result = preparedStatement.executeQuery();
            if(result.next())
                return Optional.of(new Type(result.getInt("id"),result.getString("name")));
        }

        return Optional.empty();
    }

    /**
     * Searches for types with given name
     *
     * @param name id to search for
     * @return type with name
     * @throws SQLException
     */
    @Override
    public Optional<Type> getTypeByName(String name) throws SQLException {
        String sql = """
                Select * from Types
                where name = ?
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            var result = preparedStatement.executeQuery();
            if(result.next())
                return Optional.of(new Type(result.getInt("id"),result.getString("name")));
        }

        return Optional.empty();
    }

}

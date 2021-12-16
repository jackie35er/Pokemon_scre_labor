import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonStat;

import java.sql.*;
import java.util.List;

public record DefaultPokemonInserter(Connection connection) {

    public void loadPokemonInDatabase(int from, int toInclusive) throws SQLException {
        truncateTable();
        PokeApi pokeApi = new PokeApiClient();
        for(int i = from; i <= toInclusive; i++){
            Pokemon currentPokemon = pokeApi.getPokemon(i);
            safePokemon(currentPokemon);
        }
    }

    public void truncateTable() throws SQLException {
        String sql = "Truncate table Pokemons";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    public void safePokemon(Pokemon pokemon) throws SQLException {
        String sql = "Insert into Pokemons values(?,?,?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,pokemon.getId());
            preparedStatement.setString(2,pokemon.getName());
            List<PokemonStat> stats = pokemon.getStats();
            preparedStatement.setInt(3,stats.get(0).getBaseStat());
            preparedStatement.setInt(4,stats.get(1).getBaseStat());
            preparedStatement.setInt(5,stats.get(2).getBaseStat());
            preparedStatement.setInt(6,stats.get(3).getBaseStat());
            preparedStatement.setInt(7,stats.get(4).getBaseStat());
            preparedStatement.setInt(8,stats.get(5).getBaseStat());
            preparedStatement.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
        String connectionURL = "jdbc:sqlserver://IFSQL-01.htl-stp.if:1433;databaseName=db_pokemon_goetz_pils;user=sa";
        Connection connection = DriverManager.getConnection(connectionURL);
        DefaultPokemonInserter defaultPokemonInserter = new DefaultPokemonInserter(connection);
        defaultPokemonInserter.loadPokemonInDatabase(1,151);
    }
}

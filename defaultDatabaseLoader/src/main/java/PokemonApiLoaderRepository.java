import me.sargunvohra.lib.pokekotlin.model.Pokemon;

import java.sql.SQLException;

public interface PokemonApiLoaderRepository {
    void loadPokemonInDatabase(int from, int toInclusive) throws SQLException;

    void loadTypesInDatabase() throws SQLException;

    void loadMultipliersInDatabase() throws SQLException;

    void truncateTable(String table) throws SQLException;

    void safePokemon(Pokemon pokemon) throws SQLException;
}

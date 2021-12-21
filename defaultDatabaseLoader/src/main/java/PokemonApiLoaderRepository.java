import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PokemonApiLoaderRepository(Connection connection) {

    public void loadPokemonInDatabase(int from, int toInclusive) throws SQLException {
        PokeApi pokeApi = new PokeApiClient();

        for (int i = from; i <= toInclusive; i++) {
            Pokemon currentPokemon = pokeApi.getPokemon(i);
            safePokemon(currentPokemon);
        }
    }

    public void loadTypesInDatabase() throws SQLException {

        PokeApi pokeApi = new PokeApiClient();
        String sqlTypes = "Insert into Types values(?,?)";

        for (int i = 1; i <= 18; i++) {//18 Types
            var type = pokeApi.getType(i);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlTypes)) {
                preparedStatement.setInt(1, type.getId());
                preparedStatement.setString(2, type.getName());
                preparedStatement.executeUpdate();
            }
        }
    }

    public void loadMultipliersInDatabase() throws SQLException {

        PokeApi pokeApi = new PokeApiClient();
        //this sql statements only inserts if it doesnt exists yet. Would be 10 times easier in MySQL, just saying
        String sqlMuliplier ="if not exists (Select * From Multipliers where userType = ? and targetType = ?) " +
                "begin insert into Multipliers values(?,?,?) end ";
        for(int i = 1; i <= 18; i++){
            var type = pokeApi.getType(i);
            //damage Multipliers
            var mappedMultiplier = getMappedMultipliers(type,getTimes1Multipliers(type,pokeApi.getTypeList(0,18).getResults()));
            for (var entry : mappedMultiplier.entrySet()){
                double multiplier = entry.getValue();
                for(var currentType : entry.getKey()){
                    try(PreparedStatement preparedStatement = connection.prepareStatement(sqlMuliplier)){
                        preparedStatement.setInt(1,type.getId());
                        preparedStatement.setInt(2,currentType.getId());
                        preparedStatement.setInt(3,type.getId());
                        preparedStatement.setInt(4,currentType.getId());
                        preparedStatement.setDouble(5,multiplier);
                        preparedStatement.executeUpdate();
                    }
                }
            }
        }
    }

    private Map<List<NamedApiResource>, Double> getMappedMultipliers(Type type,List<NamedApiResource> times1Multipliers) {
        Map<List<NamedApiResource>, Double> mappedMultiplier = new HashMap<>();
        mappedMultiplier.put(type.getDamageRelations().getDoubleDamageFrom(), 2.0);
        mappedMultiplier.put(type.getDamageRelations().getDoubleDamageTo()  , 2.0);
        mappedMultiplier.put(type.getDamageRelations().getHalfDamageFrom()  , 0.5);
        mappedMultiplier.put(type.getDamageRelations().getHalfDamageTo()    , 0.5);
        mappedMultiplier.put(type.getDamageRelations().getNoDamageFrom()    , 0.0);
        mappedMultiplier.put(type.getDamageRelations().getNoDamageFrom()    , 0.0);
        mappedMultiplier.put(times1Multipliers                              , 1.0);
        return mappedMultiplier;
    }
    private List<NamedApiResource> getTimes1Multipliers(Type type,List<NamedApiResource> types){
        var damageRelations = new ArrayList<NamedApiResource>();
        damageRelations.addAll(type.getDamageRelations().getDoubleDamageFrom());
        damageRelations.addAll(type.getDamageRelations().getDoubleDamageTo());
        damageRelations.addAll(type.getDamageRelations().getHalfDamageFrom());
        damageRelations.addAll(type.getDamageRelations().getHalfDamageTo());
        damageRelations.addAll(type.getDamageRelations().getNoDamageFrom());
        damageRelations.addAll(type.getDamageRelations().getNoDamageTo());
        var times1Multiplier = new ArrayList<NamedApiResource>();
        for(var currentType : types){
            if(currentType.getId() == type.getId())
                continue;
            if (damageRelations.stream().noneMatch(n -> n.getId() == type.getId())){
                times1Multiplier.add(currentType);
            }
        }
        return times1Multiplier;
    }


    public void truncateTable(String table) throws SQLException {
        String sql = "delete from " + table;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }


    public void safePokemon(Pokemon pokemon) throws SQLException {
        String sql = "Insert into Pokemons values(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pokemon.getId());
            preparedStatement.setString(2, pokemon.getName());
            List<PokemonStat> stats = pokemon.getStats();
            preparedStatement.setInt(3, stats.get(0).getBaseStat());
            preparedStatement.setInt(4, stats.get(1).getBaseStat());
            preparedStatement.setInt(5, stats.get(2).getBaseStat());
            preparedStatement.setInt(6, stats.get(3).getBaseStat());
            preparedStatement.setInt(7, stats.get(4).getBaseStat());
            preparedStatement.setInt(8, stats.get(5).getBaseStat());
            var types = pokemon.getTypes();
            preparedStatement.setInt(9, types.get(0).getType().getId());//pokemon always have a primary type
            if (types.size() > 1) {
                preparedStatement.setInt(10, types.get(1).getType().getId());
            } else {
                preparedStatement.setNull(10, Types.INTEGER);
            }
            preparedStatement.executeUpdate();
        }
    }

    private void safeMoves() throws SQLException{
        PokeApi pokeApi = new PokeApiClient();

    }

    public static void main(String[] args) throws SQLException {
        String connectionURL = "jdbc:sqlserver://IFSQL-01.htl-stp.if:1433;databaseName=db_pokemon_goetz_pils;user=sa";
        Connection connection = DriverManager.getConnection(connectionURL);
        PokemonApiLoaderRepository defaultPokemonInserter = new PokemonApiLoaderRepository(connection);
        defaultPokemonInserter.truncateTable("Multipliers");
        defaultPokemonInserter.truncateTable("Pokemons");
        defaultPokemonInserter.truncateTable("Types");
        defaultPokemonInserter.loadTypesInDatabase();
        defaultPokemonInserter.loadMultipliersInDatabase();
        defaultPokemonInserter.loadPokemonInDatabase(1, 151);
    }
}

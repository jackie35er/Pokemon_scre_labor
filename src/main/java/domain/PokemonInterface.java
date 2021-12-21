package domain;

public interface PokemonInterface {

    int getId();

    String getName();

    StatsInteface getStats();

    Types getTypes();

    MoveSet getMoveSet();
}

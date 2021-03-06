package domain;

public record Pokemon(Integer id,
                      String name,
                      StatsInteface stats,
                      Types types,
                      MoveSet moveSet) implements PokemonInterface {
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public StatsInteface getStats() {
        return this.stats;
    }

    @Override
    public Types getTypes() {
        return this.types;
    }

    @Override
    public MoveSet getMoveSet() {
        return this.moveSet;
    }
}
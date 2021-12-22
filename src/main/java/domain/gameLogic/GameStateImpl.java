package domain.gameLogic;

import domain.PokemonInterface;

public record GameStateImpl(PokemonInterface pokemon1,PokemonInterface pokemon2) implements GameState{


    @Override
    public PokemonInterface getPokemon1() {
        return pokemon1;
    }

    @Override
    public PokemonInterface getPokemon2() {
        return pokemon2;
    }
}

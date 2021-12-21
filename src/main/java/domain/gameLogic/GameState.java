package domain.gameLogic;

import domain.PokemonInterface;

public interface GameState {

    PokemonInterface getPokemon1();

    PokemonInterface getPokemon2();
}

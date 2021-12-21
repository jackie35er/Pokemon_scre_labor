package domain.gameLogic;


import domain.Move;
import domain.Pokemon;
import domain.PokemonInterface;
import persistence.PokemonRepository;

import java.util.Random;

public class SimpleGameDirector implements GameDirector {
    private GameState gameState;
    private Random random = new Random();
    private PokemonRepository pokemonRepository;

    public SimpleGameDirector(GameState gameState, PokemonRepository pokemonRepository){
        this.gameState = gameState;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public void makeTurn(int moveIndexPokemon1, int moveIndexPokemon2){
        Move movePokemon1 = gameState.getPokemon1().getMoveSet().getMove(moveIndexPokemon1);
        Move movePokemon2 = gameState.getPokemon2().getMoveSet().getMove(moveIndexPokemon2);

        boolean first = decideWhosFirst(movePokemon1,movePokemon2);

        PokemonInterface firstPokemon = first ? gameState.getPokemon1() : gameState.getPokemon2();
        PokemonInterface secondPokemon = first ? gameState.getPokemon2() : gameState.getPokemon1();

        boolean selfUse = firstPokemon.getMoveSet().getMove(moveIndexPokemon1).isSelfUse();
        firstPokemon.getMoveSet().getMove(moveIndexPokemon1)
                .execute((selfUse ? firstPokemon : secondPokemon),
                        getMultiplier(firstPokemon,secondPokemon,firstPokemon.getMoveSet().getMove(moveIndexPokemon1)));

        selfUse = firstPokemon.getMoveSet().getMove(moveIndexPokemon2).isSelfUse();
        firstPokemon.getMoveSet().getMove(moveIndexPokemon2)
                .execute((selfUse ? secondPokemon : firstPokemon),
                        getMultiplier(secondPokemon,firstPokemon,secondPokemon.getMoveSet().getMove(moveIndexPokemon2)));
    }

    private boolean decideWhosFirst(Move movePokemon1,Move movePokemon2){
        if(movePokemon1.getPriority() == movePokemon2.getPriority()){
            return decideBasedOnSpeed();
        }
        else return movePokemon1.getPriority() > movePokemon2.getPriority();
    }

    private boolean decideBasedOnSpeed(){
        int speedPokemon1 = gameState.getPokemon1().getStats().getSpeed().get();
        int speedPokemon2 = gameState.getPokemon2().getStats().getSpeed().get();
        if(speedPokemon1 == speedPokemon2){
            //if speed and prio are equal its random
            return random.nextBoolean();
        }
        else return speedPokemon1 > speedPokemon2;
    }

    private double getMultiplier(PokemonInterface user, PokemonInterface target, Move move){
        if (move.getDamageType().equals(Move.DamageType.STATUS)){
            return 1.0;
        }
        else{
            //https://bulbapedia.bulbagarden.net/wiki/Damage
            int power = move.getPower();
            int level = 50;
            double attackDefenseRatio = calcAttackDefenseRatio(user,target,move);
            double randomMultiplier = random.nextDouble(0.85,1.0);
            double stab = calcStab(user,move);
            double typeAdvantageMultiplier = calcTypeAdvantageMultiplier(target,move);
            return ((2 * level/5.0 +2) * power * attackDefenseRatio/ 50.0) * randomMultiplier * stab * typeAdvantageMultiplier;

        }
    }

    private double calcTypeAdvantageMultiplier(PokemonInterface target, Move move) {
        double multiplier = 1.0;
        multiplier *= pokemonRepository.getMultiplierForTypes(move.getType(),target.getTypes().primaryType());
        if(target.getTypes().secondaryType() != null){
            multiplier *= pokemonRepository.getMultiplierForTypes(move.getType(), target.getTypes().secondaryType());
        }
        return multiplier;
    }

    private double calcAttackDefenseRatio(PokemonInterface user, PokemonInterface target, Move move) {
        if(move.getDamageType().equals(Move.DamageType.PHYSICAL))
            return user.getStats().getAttack().get() /(double) target.getStats().getDefense().get();
        else
            return user.getStats().getSpecialAttack().get() /(double) target.getStats().getSpecialDefense().get();
    }

    private double calcStab(PokemonInterface user, Move move) {
        return user.getTypes().contains(move.getType()) ? 1.5 : 1.0;
    }
}

package domain.gameLogic;


import domain.Move;
import domain.PokemonInterface;
import persistence.TypeRepository;

import java.sql.SQLException;
import java.util.Random;

public class SimpleGameDirector implements GameDirector {
    private GameState gameState;
    private Random random = new Random();
    private TypeRepository typeRepository;

    public SimpleGameDirector(GameState gameState, TypeRepository pokemonRepository){
        this.gameState = gameState;
        this.typeRepository = pokemonRepository;
    }

    @Override
    public void makeTurn(int moveIndexPokemon1, int moveIndexPokemon2){
        Move movePokemon1 = gameState.getPokemon1().getMoveSet().getMove(moveIndexPokemon1);
        Move movePokemon2 = gameState.getPokemon2().getMoveSet().getMove(moveIndexPokemon2);

        boolean first = decideWhosFirst(movePokemon1,movePokemon2);

        PokemonInterface firstPokemon = first ? gameState.getPokemon1() : gameState.getPokemon2();
        PokemonInterface secondPokemon = first ? gameState.getPokemon2() : gameState.getPokemon1();

        Move firstMove = first ? movePokemon1 : movePokemon2;
        Move secondMove = first ? movePokemon2 : movePokemon1;

        boolean selfUse = firstMove.isSelfUse();
        System.out.println(firstPokemon.getName() + " used " + firstMove.getName());
        firstMove.execute((selfUse ? firstPokemon : secondPokemon),
                        getDamage(firstPokemon,secondPokemon,firstMove));

        selfUse = secondMove.isSelfUse();
        System.out.println(secondPokemon.getName() + " used " + secondMove.getName());
        secondMove.execute((selfUse ? secondPokemon : firstPokemon),
                        getDamage(secondPokemon,firstPokemon,secondMove));
        System.out.println();
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

    private int getDamage(PokemonInterface user, PokemonInterface target, Move move){
        if(random.nextInt(0,100) >= move.getAccuarcy()){
            System.out.println(move.getName() + " missed");
            return 0;
        }

        if (move.getDamageType().equals(Move.DamageType.STATUS))
            return 0;

        else{
            //https://bulbapedia.bulbagarden.net/wiki/Damage
            int power = move.getPower();
            int level = 25;
            double attackDefenseRatio = calcAttackDefenseRatio(user,target,move);
            //this only works in language level 17 but if you dont want to install it just put 1
            //double randomMultiplier = 1.0;
            double randomMultiplier = random.nextDouble(0.85,1.0);
            double stab = calcStab(user,move);
            double typeAdvantageMultiplier = calcTypeAdvantageMultiplier(target,move);
            return (int) ((int) ((2 * level/5.0 +2) * power * attackDefenseRatio/ 50.0) * randomMultiplier * stab * typeAdvantageMultiplier);

        }
    }

    private double calcTypeAdvantageMultiplier(PokemonInterface target, Move move) {
        double multiplier = 1.0;
        try {
            multiplier *= typeRepository.getMultiplierForTypes(move.getType(),target.getTypes().primaryType());
            if(target.getTypes().secondaryType() != null){
                multiplier *= typeRepository.getMultiplierForTypes(move.getType(), target.getTypes().secondaryType());
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
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

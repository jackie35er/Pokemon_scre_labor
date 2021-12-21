package domain;

public interface Move {

    void execute(PokemonInterface target, double effectMultiplier);

    int getAccuarcy();

    int getPower();

    int getName();

    int getPriority();

    Type getType();

    boolean isSelfUse();

    DamageType getDamageType();

    enum DamageType{
        PHYSICAL,
        SPECIAL,
        STATUS
    }
}

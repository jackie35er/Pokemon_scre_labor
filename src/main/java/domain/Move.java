package domain;

public interface Move {

    void execute(PokemonInterface target, int damage);

    int getAccuarcy();

    int getPower();

    String getName();

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

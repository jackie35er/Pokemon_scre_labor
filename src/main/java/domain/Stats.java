package domain;

import javafx.beans.property.IntegerProperty;

public record Stats (IntegerProperty hp,
                     IntegerProperty attack,
                     IntegerProperty defense,
                     IntegerProperty spAttack,
                     IntegerProperty spDefense,
                     IntegerProperty speed) implements StatsInteface {

    @Override
    public IntegerProperty getHealth() {
        return this.hp;
    }

    @Override
    public IntegerProperty getAttack() {
        return this.attack;
    }

    @Override
    public IntegerProperty getDefense() {
        return this.defense;
    }

    @Override
    public IntegerProperty getSpecialAttack() {
        return this.spAttack;
    }

    @Override
    public IntegerProperty getSpecialDefense() {
        return this.spDefense;
    }

    @Override
    public IntegerProperty getSpeed() {
        return this.speed;
    }
}

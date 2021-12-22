package domain.moveImplementaions;

import domain.Type;

public abstract class SpecialMove extends MoveBase{
    public SpecialMove(int accuarcy, int power, String name, int priority, Type type, boolean selfUse) {
        super(accuarcy, power, name, priority, type, selfUse);
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.SPECIAL;
    }
}


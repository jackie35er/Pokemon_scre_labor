package domain.moveImplementaions;

import domain.PokemonInterface;
import domain.Type;

public abstract class PhysicalMove extends MoveBase{
    public PhysicalMove(int accuarcy, int power, String name, int priority, Type type, boolean selfUse) {
        super(accuarcy, power, name, priority, type, selfUse);
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.PHYSICAL;
    }
}

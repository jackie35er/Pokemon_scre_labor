package domain.moveImplementaions;

import domain.Move;
import domain.Type;
import persistence.TypeRepository;

public abstract class MoveBase implements Move {
    private int accuarcy;
    private int power;
    private String name;
    private int priority;
    private Type type;
    private boolean selfUse;

    protected static TypeRepository typeRepository;

    public static void setTypeRepository(TypeRepository typeRepository) {
        MoveBase.typeRepository = typeRepository;
    }

    public MoveBase(int accuarcy, int power, String name, int priority, Type type, boolean selfUse) {
        this.accuarcy = accuarcy;
        this.power = power;
        this.name = name;
        this.priority = priority;
        this.type = type;
        this.selfUse = selfUse;
    }

    @Override
    public int getAccuarcy() {
        return this.accuarcy;
    }

    @Override
    public int getPower() {
        return this.power;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public boolean isSelfUse() {
        return this.selfUse;
    }

}

package domain;

public class Multiplier {
    private final Type userType;
    private final Type targetType;
    private Double multiplier;

    public Multiplier(Type userType, Type targetType, Double multiplier) {
        this.userType = userType;
        this.targetType = targetType;
        this.multiplier = multiplier;
    }
}
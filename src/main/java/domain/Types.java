package domain;

public record Types (Type primaryType,
                     Type secondaryType){

    public boolean contains(Type type){
        return type.equals(primaryType) || type.equals(secondaryType);
    }
}

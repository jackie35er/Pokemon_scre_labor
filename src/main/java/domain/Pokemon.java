package domain;

public class Pokemon {
    private final Integer id;
    private String name;
    private Integer hp;
    private Integer attack;
    private Integer defense;
    private Integer spAttack;
    private Integer spDefense;
    private Integer speed;
    private Type primaryType;
    private Type secondaryType;

    public Pokemon(Integer id, String name, Integer hp, Integer attack, Integer defense, Integer spAttack, Integer spDefense, Integer speed, Type primaryType, Type secondaryType) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
    }
}
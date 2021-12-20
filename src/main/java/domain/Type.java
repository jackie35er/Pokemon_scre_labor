package domain;

public class Type {
    private final Integer id;
    private String name;


    public Type(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type(String name) {
        this(null, name);
    }
}
package domain.moveImplementaions;

import domain.Move;
import domain.PokemonInterface;
import domain.Type;

import java.sql.SQLException;

public class Tackle extends PhysicalMove {
    private Tackle(int accuarcy, int power, String name, int priority, Type type, boolean selfUse) {
        super(accuarcy, power, name, priority, type, selfUse);
    }

    public static Move getTackle(){
        try {
            return new Tackle(100,40,"Tackle",0, MoveBase.typeRepository.getTypeByName("normal").orElseThrow(() -> new IllegalStateException("Something is wrong with the database")),false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;//can't be reached
    }

    @Override
    public void execute(PokemonInterface target, int damage) {
        var targetHealth = target.getStats().getHealth();
        System.out.println(target.getName() + " health was decreased by " + damage);
        targetHealth.setValue(targetHealth.getValue() - damage);
    }
}
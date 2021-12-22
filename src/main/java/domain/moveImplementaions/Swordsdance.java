package domain.moveImplementaions;

import domain.Move;
import domain.PokemonInterface;
import domain.Type;

import java.sql.SQLException;

public class Swordsdance extends StatusMove{
    private int count;
    private Swordsdance(int accuarcy, int power, String name, int priority, Type type, boolean selfUse) {
        super(accuarcy, power, name, priority, type, selfUse);
    }

    public static Move getSwordsdance(){
        try {
            return new Swordsdance(100,0,"Swordsdance",0,MoveBase.typeRepository.getTypeByName("normal").orElseThrow(() -> new IllegalStateException("Something is wrong with the database")),true );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;//can't be reached
    }

    @Override
    public void execute(PokemonInterface target, int damage) {
        if(count++ >= 3){
            System.out.println(target.getName() + " failed");
            return;
        }
        System.out.println(target.getName() + " attack was incresed by 2");
        var attack = target.getStats().getAttack();
        attack.setValue(attack.getValue() * 2);
    }
}

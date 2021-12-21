package domain;

import javafx.beans.property.IntegerProperty;

public interface StatsInteface {
    IntegerProperty getHealth();

    IntegerProperty getAttack();

    IntegerProperty getDefense();

    IntegerProperty getSpecialAttack();

    IntegerProperty getSpecialDefense();

    IntegerProperty getSpeed();
}

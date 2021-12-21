package domain;

import java.util.Collection;

public interface MoveSet {

    int getMoveCount();

    Collection<Move> getMoves();

    Move getMove(int i);
}

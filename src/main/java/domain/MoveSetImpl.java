package domain;

import domain.moveImplementaions.Bite;
import domain.moveImplementaions.Confusion;
import domain.moveImplementaions.Swordsdance;
import domain.moveImplementaions.Tackle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MoveSetImpl implements MoveSet{

    List<Move> moves = new ArrayList<>();

    //this class is scuffed as fuck but we got no time
    public MoveSetImpl(){
        this.moves.add(Tackle.getTackle());
        this.moves.add(Swordsdance.getSwordsdance());
        this.moves.add(Confusion.getConfusion());
        this.moves.add(Bite.getBite());
    }

    @Override
    public int getMoveCount() {
        return moves.size();
    }

    @Override
    public Collection<Move> getMoves() {
        return new ArrayList<>(moves);
    }

    @Override
    public Move getMove(int i) {
        return moves.get(i);
    }
}

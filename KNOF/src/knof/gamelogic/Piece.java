package knof.gamelogic;

import knof.model.game.Player;
import knof.model.game.Side;

/**
 * This class represents a piece as placed on the board
 * Can be extended for custom piece types.
 * Example: a piece which can change owner *hint* *hint*
 */
public class Piece {
    protected Player owner;

    public Piece() {
        this(null);
    }

    public Piece(Player owner) {
        this.owner = owner;
    }

    public Side getSide() {
        return this.owner.getSide();
    }

    public String getIdentifier() {
        return this.getSide().getName();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj instanceof Piece) {
            return this.owner == ((Piece) obj).owner;
        }
        return false;
    }
}

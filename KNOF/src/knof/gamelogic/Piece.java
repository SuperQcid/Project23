package knof.gamelogic;

import knof.model.game.Player;
import knof.model.game.Side;

/**
 * This class represents a piece as placed on the board
 * Can be extended for custom piece types.
 * Example: a piece which can change side *hint* *hint*
 */
public class Piece {
    protected Side side;

    public Piece() {
        this(null);
    }

    public Piece(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return this.side;
    }

    public String getIdentifier() {
        return this.side.getName();
    }
}

package knof.gamelogic;

import knof.model.Player;

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
}

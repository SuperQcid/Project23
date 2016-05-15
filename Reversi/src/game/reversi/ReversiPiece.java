package game.reversi;

import knof.gamelogic.Piece;
import knof.model.game.Side;

public class ReversiPiece extends Piece {
    public ReversiPiece(Side side) {
        super(side);
    }

    public void flip() {
        this.side = (this.side==ReversiGame.BLACK)?ReversiGame.WHITE:ReversiGame.BLACK;
    }
}

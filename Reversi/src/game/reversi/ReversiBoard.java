package game.reversi;

import knof.gamelogic.Board;
import knof.gamelogic.Direction;
import knof.gamelogic.Piece;
import knof.model.game.Game;
import knof.model.game.Player;
import knof.model.game.Side;

import java.util.List;

public class ReversiBoard extends Board {
    public ReversiBoard(Game game) {
        super(8, 8, game);

        this.board[this.pos(3, 3).toInt()] = new Piece(ReversiGame.WHITE);
        this.board[this.pos(4, 4).toInt()] = new Piece(ReversiGame.WHITE);

        this.board[this.pos(3, 4).toInt()] = new Piece(ReversiGame.BLACK);
        this.board[this.pos(4, 3).toInt()] = new Piece(ReversiGame.BLACK);
    }

    @Override
    public Board clone() {
        return null;
    }

    @Override
    public int getScore(Side side) {
        return 0;
    }

    @Override
    public Side getWinningSide() {
        return null;
    }

    @Override
    public Side getNextSide() {
        Piece next = new Piece(super.getNextSide());

        if(this.getValidPositions(next).size() > 0) {
            return next.getSide();
        }
        else {
            return previousSide;
        }
    }

    public boolean checkDirectionValid(Pos pos, Piece piece, Direction direction) {
        Pos p = pos;
        boolean jumped = false;
        p.add(direction);
        while(this.validPos(p)) {
            Piece pieceAtLocation = this.getPieceAtPosition(p.toInt());
            boolean empty = pieceAtLocation == null;
            boolean same = piece.equals(pieceAtLocation);
            if(empty) return false;
            if(same) return jumped;
            if(!same) {
                jumped = true;
            }
            else {
                return jumped;
            }
            p = p.add(direction);
        }
        return false;
    }

    @Override
    public boolean isValid(int index, Piece piece) {
        for(Direction dir: Direction.values()) {
            if(this.checkDirectionValid(this.pos(index), piece, dir)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean place(int index, Piece piece) {
        return super.place(index, piece);
    }
}

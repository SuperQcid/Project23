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
        Player blackPlayer = game.getSidePlayer(ReversiGame.BLACK);
        Player whitePlayer = game.getSidePlayer(ReversiGame.WHITE);

        this.board[this.pos(3, 3).toInt()] = new Piece(whitePlayer);
        this.board[this.pos(4, 4).toInt()] = new Piece(whitePlayer);

        this.board[this.pos(3, 4).toInt()] = new Piece(blackPlayer);
        this.board[this.pos(4, 3).toInt()] = new Piece(blackPlayer);
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
    public Piece getNextPiece() {
        Piece next = super.getNextPiece();
        //TODO: Replace with more efficient method
        if(this.getValidPositions(next).size() > 0) {
            return next;
        }
        else {
            return previousPiece;
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

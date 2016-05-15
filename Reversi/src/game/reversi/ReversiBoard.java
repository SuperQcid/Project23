package game.reversi;

import knof.gamelogic.Board;
import knof.gamelogic.Direction;
import knof.gamelogic.Piece;
import knof.model.game.Game;
import knof.model.game.Player;
import knof.model.game.Side;

import java.util.LinkedList;
import java.util.List;

public class ReversiBoard extends Board {
    public ReversiBoard(Game game) {
        super(8, 8, game);

        this.board[this.pos(3, 3).toInt()] = new ReversiPiece(ReversiGame.WHITE);
        this.board[this.pos(4, 4).toInt()] = new ReversiPiece(ReversiGame.WHITE);

        this.board[this.pos(3, 4).toInt()] = new ReversiPiece(ReversiGame.BLACK);
        this.board[this.pos(4, 3).toInt()] = new ReversiPiece(ReversiGame.BLACK);
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
        // System.err.println("Check if direction "+direction+" is valid for side "+piece.getSide()+ " at "+pos);
        boolean jumped = false;
        Pos p = pos.add(direction);
        while(this.validPos(p)) {
            Piece pieceAtLocation = this.getPieceAtPosition(p.toInt());
            boolean empty = pieceAtLocation == null;
            boolean same = piece.equals(pieceAtLocation);

            // System.err.format("%s\t\tempty: %s\t\tsame: %s\t\tjumped: %s\n", p, empty, same, jumped);

            if(empty) return false;
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

    public List<ReversiPiece> getPiecesToFlip(Pos pos, Piece piece, Direction direction) {
        LinkedList<ReversiPiece> pieces = new LinkedList<>();

        boolean jumped = false;
        Pos p = pos.add(direction);
        while(this.validPos(p)) {
            Piece pieceAtLocation = this.getPieceAtPosition(p.toInt());
            boolean empty = pieceAtLocation == null;
            boolean same = piece.equals(pieceAtLocation);

            // System.err.format("%s\t\tempty: %s\t\tsame: %s\t\tjumped: %s\n", p, empty, same, jumped);

            if(empty) break;
            if(!same) {
                pieces.add((ReversiPiece) pieceAtLocation);
                jumped = true;
            }
            else {
                if(jumped) {
                    return pieces;
                }
                break;
            }
            p = p.add(direction);
        }
        pieces.clear();
        return pieces;
    }

    public void flipPieces(Pos pos, Piece piece) {
        for(Direction dir: Direction.values()) {
            List<ReversiPiece> pieces = getPiecesToFlip(pos, piece, dir);
            for(ReversiPiece p: pieces) {
                p.flip();
            }
        }
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
    public boolean place(int index, Side side) {
        return this.place(index, new ReversiPiece(side));
    }

    @Override
    protected boolean place(int index, Piece piece) {
        if(isValid(index, piece)) {
            previousSide = piece.getSide();
            this.board[index] = piece;
            this.flipPieces(pos(index), piece);
            return true;
        }
        return false;
    }
}

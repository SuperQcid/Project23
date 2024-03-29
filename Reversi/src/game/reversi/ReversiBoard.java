package game.reversi;

import knof.gamelogic.Board;
import knof.gamelogic.Direction;
import knof.gamelogic.Piece;
import knof.model.game.Game;
import knof.model.game.Side;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReversiBoard extends Board {

    /**
     * Board Values
     */

    private int[][] boardValues = new int[][]{
          /*   A   B   C   D   E   F   G   H   */
            {100, -1,  5,  2,  2,  5, -1,100}, //1
            { -1,-20,  1,  1,  1,  1,-20, -1}, //2
            {  5,  1,  1,  1,  1,  1,  1,  5}, //3
            {  2,  1,  1,  0,  0,  1,  1,  2}, //4
            {  2,  1,  1,  0,  0,  1,  1,  2}, //5
            {  5,  1,  1,  1,  1,  1,  1,  5}, //6
            { -1,-20,  1,  1,  1,  1,-20, -1}, //7
            {100, -1,  5,  2,  2,  5, -1,100}  //8
    };

    public ReversiBoard(Game game) {
        super(8, 8, game);

        this.board[this.pos(3, 3).toInt()] = new ReversiPiece(ReversiGame.WHITE);
        this.board[this.pos(4, 4).toInt()] = new ReversiPiece(ReversiGame.WHITE);

        this.board[this.pos(3, 4).toInt()] = new ReversiPiece(ReversiGame.BLACK);
        this.board[this.pos(4, 3).toInt()] = new ReversiPiece(ReversiGame.BLACK);
    }

    /**
     * Copy constructor
     * @param board
     */
    public ReversiBoard(ReversiBoard board){
        super(board);
    }

    @Override
    public ReversiBoard clone() {
        //return new ReversiBoard(this);
        int length = board.length;
        ReversiBoard reversiBoard = new ReversiBoard(game);
        reversiBoard.board = Arrays.copyOf(board,length);
        return reversiBoard;
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
        while(p.isValid()) {
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
        while(p.isValid()) {
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
        if(!super.isValid(index, piece)) return false;
        for(Direction dir: Direction.values()) {
            if(this.checkDirectionValid(this.pos(index), piece, dir)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean place(int index, Side side) {
        System.out.println(this.pos(index));
        System.out.println(this.getValidPositions(side));
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

    public int getFieldValue(int x, int y) throws IndexOutOfBoundsException {
        return boardValues[x][y];
    }
}

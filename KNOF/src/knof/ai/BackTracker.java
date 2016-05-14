package knof.ai;

import knof.gamelogic.Board;
import knof.gamelogic.Piece;
import knof.model.game.Side;

import java.util.List;

/**
 * Created by Thijs on 10/05/2016.
 */

public class BackTracker {
    private Board board;
    private int depth;

    public BackTracker(Board board, int depth) {
        this.board = board;
        this.depth = depth;
    }

    /**
     *
     * @param piece The side for which the move is being calculated
     * @param board The board for which the move is being calculated
     * @param depth The amount of depth the recursive function is allowed to go
     * @return The best move the functions are able to calculate
     */
    public BestMove getBestMove(Piece piece, Board board, int depth) {
        if(depth==0) {
            return getBestScoringMove(board, piece);
        }
        else {
            return getBestMoveRecursive(board, piece, depth);
        }
    }

    /**
     *
     * @param piece The side for which the move is being calculated
     * @param board The board for which the move is being calculated
     * @param depth The amount of depth the function is allowed to go
     * @return The best move the function is able to calculate
     */
    private BestMove getBestMoveRecursive(Board board, Piece piece, int depth) {
        List<Board.Pos> legalMoves = board.getValidPositions();
        BestMove bestMove = null;
        for(Board.Pos move: legalMoves) {
            Board clonedBoard = board.clone();
            clonedBoard.place(move.toInt(), new Piece());

            Side nextSide = clonedBoard.getNextSide();
            BestMove newBestMove;

            if(!clonedBoard.getValidPositions(new Piece(nextSide)).isEmpty()) {
                newBestMove = getBestMove(new Piece(nextSide), clonedBoard, depth - 1);

            }
            else {
                newBestMove = new BestMove(move, clonedBoard);
            }

            if (bestMove == null || newBestMove.endState.getScore(piece.getSide()) > bestMove.endState.getScore(piece.getSide())) {
                bestMove = new BestMove(move, newBestMove.endState);
            }
        }
        return bestMove;
    }

    /**
     * Get move that results in the highest score for side, return null if no more moves can be made
     * @param board
     * @param piece
     * @return best move
     */
    private BestMove getBestScoringMove(Board board, Piece piece) {
        List<Board.Pos> legalPositions = board.getValidPositions(piece);
        if(legalPositions.isEmpty()) return null;
        BestMove bestMove = null;

        for(Board.Pos position: legalPositions) {
            Board newState = board.clone();
            newState.place(position.toInt(), piece);

            int score = newState.getScore(piece.getSide());
            if(bestMove==null || score > bestMove.endState.getScore(piece.getSide())) {
                bestMove = new BestMove(position, newState);
            }
        }
        return bestMove;
    }

    public static class BestMove {
        public Board endState;
        public final Board.Pos position;

        public BestMove(Board.Pos position, Board endState) {
            this.position = position;
            this.endState = endState;

        }
    }
}

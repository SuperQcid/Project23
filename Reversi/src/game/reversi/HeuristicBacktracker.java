package game.reversi;

import knof.gamelogic.Board;
import knof.model.game.Side;

/**
 * Created by Kevin on 5/16/2016.
 */
public class HeuristicBacktracker {

    private Side side1;
    private Side side2;
    private ReversiBoard board;

    public HeuristicBacktracker(Side side1, Side side2, ReversiBoard board) {
        this.side1 = side1;
        this.side2 = side2;
        this.board = board;
    }

    public Board.Pos getBestMove(int depth) {

        if (board.getValidPositions(side1) != null && !board.getValidPositions(side1).isEmpty()) {
            int score = Integer.MIN_VALUE;
            Board.Pos winningpos = null;
            for (Board.Pos pos : board.getValidPositions(side1)) {
                int s = search(board.clone(),depth,side1);
                if (s > score) {
                    score = s;
                    winningpos = pos;
                }
            }
            return winningpos;
        }
        else {
            return null;
        }
    }

    private int search(ReversiBoard board, int depth, Side side) {
        int max = Integer.MIN_VALUE;
        if (depth < 1 || board.getValidPositions(side).isEmpty()) {
            return max;
        }
        for (ReversiBoard.Pos pos : board.getValidPositions(side)) {


            max = board.getFieldValue(pos.x, pos.y);
            board.place(pos.toInt(),new ReversiPiece(side));
            int v = - search(board.clone(),depth -1,otherSide(side));
            board.remove(pos.toInt());

            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    private Side otherSide(Side side) {
        if (side.equals(side1)) {
            return side2;
        } else {
            return side1;
        }
    }

}

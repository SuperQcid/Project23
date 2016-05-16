package game.reversi;

import knof.ai.HeuristicAI;
import knof.gamelogic.Board;
import knof.model.game.Side;

import java.util.List;

/**
 * Created by Kevin on 5/16/2016.
 */
public class HeuristicBacktracker extends Thread {

    private Side side1;
    private Side side2;
    private ReversiBoard board;

    public HeuristicBacktracker(Side side1, Side side2, ReversiBoard board) {
        this.side1 = side1;
        this.side2 = side2;
        this.board = board;
    }

    public Board.Pos getBestMove(int depth) {

        HeuristicAI heuristicAI = new HeuristicAI();

        Board.Pos endpos = null;
        int max = Integer.MIN_VALUE;
        for (Board.Pos pos : board.getValidPositions(side1)) {
            int v = compute(board,depth,side1,pos).getValue();
            if (v > max) {max = v;
                endpos = pos;}
        }
        return endpos;
    }

    private Result compute(ReversiBoard board, int depth, Side side, Board.Pos thisPos) {

//        int currPosVal = board.getFieldValue(current.x, current.y);
//        List<Board.Pos> validPositions = board.getValidPositions(side1);
//        if(depth < 1 || validPositions == null || validPositions.isEmpty()){
//            return new Best(currPosVal);
//        }
//        int value = this.side1.equals(side1) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        Board.Pos bestPos = null;
//        for(Board.Pos pos : validPositions){
//            board.place(pos.toInt(), side1);
//            Best oppBest = compute(board.clone(), depth -1, side2, side1,pos);
//            if((side1 == this.side1 && oppBest.val > value) || (side1 == this.side2 && oppBest.val < value)){
//                value = oppBest.val;
//                bestPos = pos;
//            }
//            board.remove(pos.toInt());
//        }
//        return new Best(value, bestPos);
        try {
            HeuristicThread t = new HeuristicThread(board, depth, side, side1, side2, thisPos);
            t.start();
            t.join();
            return t.best;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new Result(thisPos, board.getFieldValue(thisPos.x, thisPos.y));
        }
    }

    private Side otherSide(Side side) {
        if (side.equals(side1)) {
            return side2;
        } else {
            return side1;
        }
    }

    public static class Result {

        private Board.Pos pos;
        private int value;

        public Result(Board.Pos pos, int value){
            this.pos = pos;
            this.value = value;
        }

        public Board.Pos getPos() {
            return pos;
        }

        public int getValue() {
            return value;
        }
    }

}

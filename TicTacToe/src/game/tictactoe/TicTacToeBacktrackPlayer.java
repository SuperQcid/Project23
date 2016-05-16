package game.tictactoe;

import knof.connection.Connection;
import knof.gamelogic.Board;
import knof.model.game.AIPlayer;
import knof.model.game.Game;
import knof.model.game.Side;

/**
 * Created by Henk Dieter Oordt on 16-5-2016.
 */
public class TicTacToeBacktrackPlayer extends AIPlayer {
    private static final int LOCAL_WINS  =  1;
    private static final int DRAW        =  0;
    private static final int UNCLEAR     = -1;
    private static final int REMOTE_WINS = -2;

    public TicTacToeBacktrackPlayer(String name, Side side, Connection connection, Game game) {
        super(name, side, connection, game);
    }

    @Override
    protected int calculateMove() {
        TicTacToeGame tttGame = ((TicTacToeGame) game);
        Best bestMove = calculateMove(side, game.getRemotePlayer().getSide(), (TicTacToeGame) game);
        return bestMove.pos.toInt();
    }

    private Best calculateMove(Side me, Side opponent, TicTacToeGame tttGame){
        TicTacToeBoard tttBoard = tttGame.getBoard();
        int currPosVal = positionValue();
        if(currPosVal != UNCLEAR){
            return new Best(currPosVal);
        }
        int value = me.equals(side) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Board.Pos bestPos = null;
        for(Board.Pos pos : tttBoard.getValidPositions(me)){
            tttBoard.place(pos.toInt(), me);
            Best oppBest = calculateMove(opponent, me, tttGame);
            if((me == side && oppBest.val > value) || (me == tttGame.getRemotePlayer().getSide() && oppBest.val < value)){
                value = oppBest.val;
                bestPos = pos;
            }
            tttBoard.clear(pos.toInt());
        }
        return new Best(value, bestPos);
    }

    private int positionValue(){
        TicTacToeGame tttGame = (TicTacToeGame) game;
        Side winningSide = tttGame.getBoard().getWinningSide();
        if(winningSide != null) {
            if (winningSide.equals(side)) {
                return LOCAL_WINS;
            } else {
                return REMOTE_WINS;
            }
        } else if (tttGame.getBoard().full()){
            return DRAW;
        } else {
            return UNCLEAR;
        }
    }

    private class Best {
        Board.Pos pos;
        int val;

        public Best(int v) {
            val = v;
        }

        public Best(int v, Board.Pos pos) {
            val = v;
            this.pos = pos;
        }
    }
}

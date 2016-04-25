package Guess;

import knof.gamelogic.Board;
import knof.gamelogic.Move;
import knof.gamelogic.Side;
import knof.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henk Dieter Oordt on 25-4-2016.
 */
public class GuessGame extends Game {

    public GuessGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal) {
        super(playerOneName, playerTwoName, playerOneIsLocal);
    }

    @Override
    public void initBoard() {
        board = new GuessBoard();
    }

    @Override
    public boolean move(int move, Side side) {
        return board.place(new Move(move, 1, side));
    }

}

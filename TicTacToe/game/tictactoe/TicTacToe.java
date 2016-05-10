package game.tictactoe;

import knof.connection.Connection;
import knof.model.game.Game;
import knof.plugin.Plugin;

/**
 * Created by Thijs on 31/03/2016.
 */
public class TicTacToe extends Plugin {

    @Override
    public String getGameName() {
        return "TicTacToe";
    }

    @Override
    public Game createGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        return new TicTacToeGame(playerOneName, playerTwoName, playerOneIsLocal, connection);
    }

    @Override
    public String getPlayerTypeName() {
        return "bot";
    }
}
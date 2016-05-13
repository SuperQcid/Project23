package game.tictactoe;

import knof.connection.Connection;
import knof.model.game.Game;
import knof.model.game.HumanPlayer;
import knof.model.game.Player;
import knof.plugin.Plugin;

/**
 * Created by Thijs on 31/03/2016.
 */
public class TicTacToe extends Plugin {

    private final String localName = "X";
    private final String remoteName = "O";

    public TicTacToe() {
        playerTypes.put("human", (connection, game, side, playerName, options) -> new HumanPlayer(playerName, side, connection));
    }

    @Override
    public String getGameName() {
        return "Tic-tac-toe";
    }

    @Override
    public Game createGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        TicTacToeGame tttGame = new TicTacToeGame(playerOneName, playerTwoName, playerOneIsLocal, connection, remoteName, localName);
        return tttGame;
    }
    @Override
    public String getPlayerTypeName() {
        return "human";
    }
}
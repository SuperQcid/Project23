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

    public TicTacToe() {
        playerTypes.put("human", (connection, game, side, playerName, options) -> new HumanPlayer(playerName, side, connection, game));
    }

    @Override
    public String getGameName() {
        return "Tic-tac-toe";
    }

    @Override
    public Game createGame(Connection connection) {
        TicTacToeGame tttGame = new TicTacToeGame(connection);
        return tttGame;
    }
    @Override
    public String getPlayerTypeName() {
        return "human";
    }
}
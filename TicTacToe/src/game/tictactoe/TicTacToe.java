package game.tictactoe;

import knof.connection.Connection;
import knof.gamelogic.HumanGridGamePlayer;
import knof.model.game.Game;
import knof.model.game.HumanPlayer;
import knof.model.game.Player;
import knof.plugin.Plugin;

/**
 * Created by Thijs on 31/03/2016.
 */
public class TicTacToe extends Plugin {

    public TicTacToe() {
        playerTypes.put("human", (connection, game, side, playerName, options) -> new HumanGridGamePlayer<>(playerName, side, connection, (TicTacToeGame)game, false));
    }

    @Override
    public String getGameName() {
        return "Tic-tac-toe";
    }

    @Override
    public Game createGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        TicTacToeGame tttGame = new TicTacToeGame(playerOneName, playerTwoName, playerOneIsLocal, connection);
        return tttGame;
    }
    @Override
    public String getPlayerTypeName() {
        return "human";
    }
}
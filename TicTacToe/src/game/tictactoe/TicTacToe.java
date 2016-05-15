package game.tictactoe;

import knof.connection.Connection;
import knof.gamelogic.HumanGridGamePlayer;
import knof.gamelogic.GridGame;
import knof.gamelogic.players.RandomGridPlayer;
import knof.model.game.Game;
import knof.plugin.Plugin;

/**
 * Created by Thijs on 31/03/2016.
 */
public class TicTacToe extends Plugin {

    public TicTacToe() {
        playerTypes.put("human", (connection, game, side, playerName, options) -> new HumanGridGamePlayer<>(playerName, side, connection, (TicTacToeGame)game, false));
        playerTypes.put("random", (connection, game, side, playerName, options) -> new RandomGridPlayer(playerName, side, connection, (GridGame<TicTacToeBoard>)game));
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
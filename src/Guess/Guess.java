package guess;

import knof.connection.Connection;
import knof.controllers.GameController;
import knof.model.Game;
import knof.plugin.Plugin;

/**
 * Created by Henk Dieter Oordt on 25-4-2016.
 */
public class Guess extends Plugin {
    @Override
    public String getGameName() {
        return "Guess Game";
    }

    @Override
    public Game createGame(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection) {
        return new GuessGame(playerOneName, playerTwoName, playerOneIsLocal, connection);
    }
}

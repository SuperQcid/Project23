package Guess;

import knof.controllers.GameController;
import knof.model.Game;
import knof.plugin.Plugin;

/**
 * Created by Henk Dieter Oordt on 25-4-2016.
 */
public class Guess implements Plugin {
    @Override
    public String getGameName() {
        return "Guess Game";
    }

    @Override
    public Game createGame(String playerOne, String playerTwo) {
        return null;
    }

    @Override
    public GameController createGameController(Game game) {
        return null;
    }
}

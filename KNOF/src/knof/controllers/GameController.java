package knof.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import knof.model.game.Game;

public abstract class GameController implements InvalidationListener {
    @FXML
    public Label localPlayerName, remotePlayerName;

    public abstract void update(Game game);

    @Override
    public void invalidated(Observable observable){
        if(observable instanceof Game){
            update((Game) observable);
        }
    }
}

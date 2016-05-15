package knof.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import knof.model.game.Game;

public abstract class GameController implements InvalidationListener {
    @FXML
    public Label localPlayerName, remotePlayerName;

    @FXML
    public Circle localPlayerMark, remotePlayerMark;

    public abstract void update(Game game);

    private final void editPlayerMarks(Game game){
        if(game.sideUp != null){
            if(game.sideUp.equals(game.getLocalPlayer().getSide())){
                localPlayerMark.setFill(Color.LIGHTGREEN);
                remotePlayerMark.setFill(Color.RED);
            } else {
                remotePlayerMark.setFill(Color.LIGHTGREEN);
                localPlayerMark.setFill(Color.RED);
            }
            localPlayerMark.setVisible(true);
            remotePlayerMark.setVisible(true);
        } else {
            localPlayerMark.setVisible(false);
            remotePlayerMark.setVisible(false);
        }
    }

    @Override
    public final void invalidated(Observable observable){
        if(observable instanceof Game){
            Game game = (Game) observable;
            editPlayerMarks(game);
            update(game);
        }
    }
}

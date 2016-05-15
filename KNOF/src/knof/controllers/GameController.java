package knof.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import knof.model.game.Game;

public abstract class GameController implements InvalidationListener {
    @FXML
    public Label localPlayerName, remotePlayerName;

    @FXML
    public Circle localPlayerMark, remotePlayerMark;

    public abstract void update(Game game);

    @Override
    public void invalidated(Observable observable){
        if(observable instanceof Game){
            Game game = (Game) observable;
            if(game.sideUp != null){
                localPlayerMark.setVisible(true);
                remotePlayerMark.setVisible(true);
                if(game.sideUp.equals(game.getLocalPlayer().getSide())){

                } else {
                    System.out.println("Remote player is up!");
                }
            } else {
                localPlayerMark.setVisible(false);
                remotePlayerMark.setVisible(false);
                //TODO No one is up, remove marks
            }
            update(game);
        }
    }
}

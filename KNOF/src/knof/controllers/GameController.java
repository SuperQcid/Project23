package knof.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import knof.event.EventHandler;
import knof.event.events.GameResultEvent;
import knof.model.game.Game;

public abstract class GameController implements InvalidationListener {
    @FXML
    public Label localPlayerName, remotePlayerName;

    @FXML
    public Circle localPlayerMark, remotePlayerMark;

    protected Game game;

    public abstract void update();

    @FXML
    public abstract void close();

    protected void editPlayerMarks(Game game){
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
    public void invalidated(Observable observable){
        if(observable instanceof Game){
            Game game = (Game) observable;
            editPlayerMarks(game);
            update();
        }
    }

    public void setGame(Game game) {
        this.game = game;
        game.result.addListener((observable, oldValue, newValue) -> {
            System.out.println("game end!!");
            this.showResult();
        });
    }

    public abstract void showResult();
}

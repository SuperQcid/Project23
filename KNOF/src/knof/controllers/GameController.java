package knof.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import knof.model.game.Game;

public abstract class GameController implements InvalidationListener {
	
    public abstract void update();

    @Override
    public void invalidated(Observable observable){
        if(observable instanceof Game){
            update();
        }
    }
}

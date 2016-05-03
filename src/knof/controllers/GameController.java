package knof.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import knof.model.Game;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class GameController implements InvalidationListener {

    public abstract void update(Game game);

    @Override
    public void invalidated(Observable observable){
        if(observable instanceof Game){
            update((Game) observable);
        }
    }
}

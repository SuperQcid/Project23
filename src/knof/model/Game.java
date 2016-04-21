package knof.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import knof.event.IEvent;
import knof.event.events.GameResultEvent;
import knof.event.events.MatchEvent;
import knof.gamelogic.Move;
import knof.gamelogic.Side;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class Game {

    public String gameName;
    public SimpleListProperty<IEvent> eventsToHandle = new SimpleListProperty<IEvent>();
    private InvalidationListener listener;

    public final void init(Side side, InvalidationListener listener){
        this.listener = listener;
        this.eventsToHandle.addListener(listener);
        start(Side.PLAYERONE);
    }

    public abstract void move(Move move);

    public final void destroy(GameResultEvent grev){
        this.eventsToHandle.removeListener(this.listener);
        end(grev);
    }

    public abstract void start(Side playerOneSide);

    public abstract void end(GameResultEvent grev);

    public final void invalidate(IEvent event){
        eventsToHandle.add(event);
    }
}

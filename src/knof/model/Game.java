package knof.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import knof.event.EventHandler;
import knof.event.IEvent;
import knof.event.events.GameResultEvent;
import knof.event.events.MoveEvent;
import knof.event.events.TurnEvent;
import knof.gamelogic.Move;
import knof.gamelogic.Side;

import java.util.LinkedList;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class Game implements Observable {
    public Player localPlayer, remotePlayer;

    private LinkedList<InvalidationListener> listeners = new LinkedList<>();

    public IEvent latestEvent;

    public Game(String playerOneName, String playerTwoName, boolean playerOneIsLocal){
        if(playerOneIsLocal){
            localPlayer = new Player(playerOneName, Side.PLAYERONE);
            remotePlayer = new Player(playerTwoName, Side.PLAYERTWO);
        } else {
            remotePlayer = new Player(playerOneName, Side.PLAYERONE);
            localPlayer = new Player(playerTwoName, Side.PLAYERTWO);
        }
    }

    public abstract void doMove(Move move);

    public final void yourTurn(){
        localPlayer.doMove();
    }

    public final void endGame(GameResultEvent event){
        this.latestEvent = event;
        for(InvalidationListener il : listeners){
            il.invalidated(this);
        }
    }

    @Override
    public void addListener(InvalidationListener listener){
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener){
        listeners.remove(listener);
    }


    @EventHandler(later = true)
    public final void onMove(MoveEvent event) {
        Side side;
        if(localPlayer.getName().equals(event.player)){
            side = localPlayer.getSide();
        } else {
            side = remotePlayer.getSide();
        }
        //TODO coordinates to single number
        this.doMove(new Move(0,0, side));
    }

    @EventHandler(later = true)
    public final void onTurn(TurnEvent event){
        this.yourTurn();
    }

    @EventHandler(later = true)
    public final void onGameResult(GameResultEvent event){
        this.endGame(event);
    }
}

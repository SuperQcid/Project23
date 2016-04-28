package knof.model;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import knof.controllers.GameController;
import knof.event.EventHandler;
import knof.event.IEvent;
import knof.event.events.GameResultEvent;
import knof.event.events.MatchEvent;
import knof.event.events.MoveEvent;
import knof.event.events.TurnEvent;
import knof.gamelogic.Board;
import knof.gamelogic.Side;

import java.util.ArrayList;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class Game implements Observable {

    protected final int WIDTH;
    protected final int HEIGHT;

    protected Player localPlayer, remotePlayer;
    protected Board board;

    protected final ArrayList<InvalidationListener> listeners = new ArrayList<>();

    protected IEvent latestEvent;

    public Game(String playerOneName, String playerTwoName, boolean playerOneIsLocal, int width, int height){
        if(playerOneIsLocal){
            localPlayer = new Player(playerOneName, Side.PLAYERONE);
            remotePlayer = new Player(playerTwoName, Side.PLAYERTWO);
        } else {
            remotePlayer = new Player(playerOneName, Side.PLAYERONE);
            localPlayer = new Player(playerTwoName, Side.PLAYERTWO);
        }
        this.HEIGHT = height;
        this.WIDTH = width;
        initBoard();
    }

    protected abstract void initBoard();

    protected abstract boolean move(int move, Side side);

    protected abstract GameController initGameController();

    public final GameController createGameController(){
        GameController gc = initGameController();
        addListener(gc);
        return gc;
    }

    public final void startGame(MatchEvent event){
        invalidate(event);
    }

    public final void yourTurn(){
        localPlayer.doMove();
    }

    public final Board getBoard(){
        return board;
    }

    public final IEvent getLatestEvent(){
        return latestEvent;
    }

    private synchronized final void invalidate(IEvent event){
        this.latestEvent = event;
        for(InvalidationListener il : listeners){
            Platform.runLater(() -> {
                il.invalidated(this);
            });
        }
    }

    @Override
    public final void addListener(InvalidationListener listener){
        listeners.add(listener);
    }

    @Override
    public final void removeListener(InvalidationListener listener){
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
        this.move(event.move, side);
        invalidate(event);
    }

    @EventHandler(later = true)
    public final void onTurn(TurnEvent event){
        this.yourTurn();
        invalidate(event);
    }

    @EventHandler(later = true)
    public final void onGameResult(GameResultEvent event){
        invalidate(event);
    }
}

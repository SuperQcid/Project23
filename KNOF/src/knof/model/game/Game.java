package knof.model.game;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import knof.connection.Connection;
import knof.controllers.GameController;
import knof.event.EventHandler;
import knof.event.IEvent;
import knof.event.events.GameResultEvent;
import knof.event.events.MatchEvent;
import knof.event.events.MoveEvent;
import knof.event.events.TurnEvent;
import knof.gamelogic.Side;

import java.util.ArrayList;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class Game implements Observable {

    protected LocalPlayer localPlayer;
    protected DummyPlayer remotePlayer;
    protected Connection connection;

    protected final ArrayList<InvalidationListener> listeners = new ArrayList<>();

    public final SimpleObjectProperty<GameResult> result = new SimpleObjectProperty<>();

    public Game(String playerOneName, String playerTwoName, boolean playerOneIsLocal, Connection connection, String remoteName, String localName){
        if(playerOneIsLocal){
            localPlayer = initLocalPlayer(playerOneName, new Side(localName),connection);
            remotePlayer = initRemotePlayer(playerTwoName, new Side(remoteName), connection);
        } else {
            remotePlayer = initRemotePlayer(playerOneName, new Side(remoteName), connection);
            localPlayer = initLocalPlayer(playerTwoName, new Side(localName), connection);
        }
        this.connection = connection;
    }

    /**
     * Instantiates a local player
     * @param playerName
     * @param side
     * @param connection
     * @return
     */
    protected abstract LocalPlayer initLocalPlayer(String playerName, Side side, Connection connection);

    /**
     * Instatiates a remote player
     * @param playerName
     * @param side
     * @param connection
     * @return
     */
    protected abstract DummyPlayer initRemotePlayer(String playerName, Side side, Connection connection);

    /**
     * Add the move to the represetation of the game and returns the result: true if legal, false if illegal
     * @param move The move received from the server
     * @param side The side of the plaeyr who did the move
     * @return true if the move was legal and successfully placed, false otherwise
     */
    protected abstract boolean move(int move, Side side);

    protected abstract GameController initGameController();

    public Player getLocalPlayer(){
        return this.localPlayer;
    }

    public Player getRemotePlayer(){
        return this.remotePlayer;
    }

    public final GameController createGameController(){
        GameController gc = initGameController();
        addListener(gc);
        return gc;
    }

    public final void startGame(MatchEvent event){
        invalidate(event);
    }

    public final void yourTurn(){
        localPlayer.setTurn();
    }

    public Player getSidePlayer(Side side) {
        if(localPlayer.side.equals(side)) {
            return localPlayer;
        }
        else if(remotePlayer.side.equals(side)) {
            return remotePlayer;
        }
        return null;
    }

    private synchronized final void invalidate(IEvent event){
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
        this.result.set(new GameResult(event));
        invalidate(event);
    }
}

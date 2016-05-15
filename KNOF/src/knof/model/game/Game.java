package knof.model.game;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import knof.command.Command;
import knof.connection.Connection;
import knof.controllers.GameController;
import knof.event.EventHandler;
import knof.event.IEvent;
import knof.event.events.GameResultEvent;
import knof.event.events.MatchEvent;
import knof.event.events.MoveEvent;
import knof.event.events.TurnEvent;

import java.util.ArrayList;

/**
 * Created by Henk Dieter Oordt on 21-4-2016.
 */
public abstract class Game implements Observable {

    protected Player localPlayer;
    protected Player remotePlayer;
    protected Connection connection;

    protected final ArrayList<InvalidationListener> listeners = new ArrayList<>();

    public final SimpleObjectProperty<GameResult> result = new SimpleObjectProperty<>();

    public Side sideUp;

    public Game(Connection connection){
        this.connection = connection;
    }

    public void setLocalPlayer(Player localPlayer) {
        this.localPlayer = localPlayer;
    }

    public void setRemotePlayer(Player remotePlayer) {
        this.remotePlayer = remotePlayer;
    }

    /**
     * Add the move to the represetation of the game and returns the result: true if legal, false if illegal
     * @param move The move received from the server
     * @param side The side of the plaeyr who did the move
     * @return true if the move was legal and successfully placed, false otherwise
     */
    protected abstract boolean move(int move, Side side);

    protected abstract GameController initGameController();

    public void setForfeitOnClose(Stage stage){
        stage.setOnCloseRequest((value)->{
            connection.sendCommand(Command.CHALLENGE_FORFEIT);
        });
    }

    public Player getLocalPlayer(){
        return this.localPlayer;
    }

    public Player getRemotePlayer(){
        return this.remotePlayer;
    }

    public final GameController createGameController(){
        GameController gc = initGameController();
        gc.localPlayerName.setText(localPlayer.getName());
        gc.remotePlayerName.setText(remotePlayer.getName());
        addListener(gc);
        return gc;
    }

    public final void startGame(MatchEvent event){
        invalidate();
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

    private synchronized final void invalidate(){
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
        //TODO: Remove debug output
        System.err.println("ONMOVE");
        Side side;
        if(localPlayer.getName().equals(event.player)){
            side = localPlayer.getSide();
            this.sideUp = remotePlayer.getSide();
        } else {
            side = remotePlayer.getSide();
        }
        this.move(event.move, side);
        invalidate();
    }

    @EventHandler(later = true)
    public final void onTurn(TurnEvent event){
        //TODO: Remove debug output
        System.err.println("ONTURN");
        this.yourTurn();
        this.sideUp = localPlayer.getSide();
        invalidate();
    }

    @EventHandler(later = true)
    public final void onGameResult(GameResultEvent event){
        this.result.set(new GameResult(event));
        this.sideUp = null;
        invalidate();
    }

    protected Side getSide(int player) {
        return player==0?getSide1():getSide2();
    }

    public abstract Side getSide1();
    public abstract Side getSide2();
}

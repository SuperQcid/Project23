package knof.model;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import knof.command.Command;
import knof.command.CommandTask;
import knof.connection.Connection;
import knof.event.EventHandler;
import knof.event.events.ListEvent;
import knof.event.events.MatchEvent;
import knof.gamelogic.Side;
import knof.plugin.PluginLoader;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Waar wordt plugin ingeladen? vanaf hier?
 * Waarom eigenlijk alle plugins tegelijk inladen?
 *
 * A model representing a server that has been connected to
 */
public class Server {
    public final Connection connection;
    public final ObservableList<String> games;
    public final ObservableList<String> players;
    public final ObservableList<Challenge> challenges;

    private final Timer timer = new Timer(true);

    public Server(Connection connection) {
        this.connection = connection;

        ObservableList<String> serverGameList = FXCollections.observableArrayList();
        this.games = FXCollections.synchronizedObservableList(serverGameList);

        ObservableList<String> playerList = FXCollections.observableArrayList();
        this.players = FXCollections.synchronizedObservableList(playerList);

        ObservableList<Challenge> challengeList = FXCollections.observableArrayList();
        this.challenges = FXCollections.synchronizedObservableList(challengeList);

        this.connection.eventSystem.register(this);

        this.timer.scheduleAtFixedRate(new CommandTask(connection, this, Command.GET_PLAYERLIST), 0, 4000);


    }

    @EventHandler
    public void onPlayerList(ListEvent.Players event) {
        Platform.runLater(() -> {
        	/*
            this.players.removeIf((String player) -> !event.contains(player));
            event.removeIf(this.players::contains);
            */
        	this.players.clear();
            this.players.addAll(event);
            System.out.println("EVENT: " + this.players.toString());
        });
    }

    @EventHandler
    public void onMatch(MatchEvent event){
        Platform.runLater(() -> {

        });
        //TODO this.game = de geladen game met als naam event.gameType
        // PlayerOne afleiden uit event.playertomove

    }
}

package knof.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import knof.command.Command;
import knof.command.CommandTask;
import knof.connection.Connection;
import knof.event.EventHandler;
import knof.event.events.ChallengeEvent;
import knof.event.events.ListEvent;

import java.util.Timer;

/**
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

        ObservableList<String> gameList = FXCollections.observableArrayList();
        this.games = FXCollections.synchronizedObservableList(gameList);

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
    public void onChallengeReceived(ChallengeEvent e){
        Platform.runLater(() -> {
            this.challenges.add(new Challenge(e.turnTime, e.challenger, e.gameType, e.id));
            System.out.println("Challenge received from " + e.challenger + " for a game of " + e.gameType + ".");
        });
    }


}

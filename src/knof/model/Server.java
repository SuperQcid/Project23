package knof.model;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knof.app.KnofApplication;
import knof.command.Command;
import knof.command.CommandTask;
import knof.connection.Connection;
import knof.controllers.PopupController;
import knof.event.EventHandler;
import knof.event.events.*;
import knof.gamelogic.Move;
import knof.gamelogic.Side;
import knof.plugin.Plugin;

import java.io.IOException;
import java.util.Timer;

/**
 * Waar wordt plugin ingeladen? vanaf hier?
 * Waarom eigenlijk alle plugins tegelijk inladen?
 * <p>
 * A model representing a server that has been connected to
 */
public class Server implements InvalidationListener {
    public final Connection connection;
    public final ObservableList<String> games;
    public final ObservableList<String> players;
    public final ObservableList<Challenge> challenges;

    public Game currentGame;

    public String playerName;

    private final Timer timer = new Timer(true);

    public Server(Connection connection, String playerName) {
        this.connection = connection;
        this.playerName = playerName;
        ObservableList<String> gameList = FXCollections.observableArrayList();
        this.games = FXCollections.synchronizedObservableList(gameList);

        ObservableList<String> playerList = FXCollections.observableArrayList();
        this.players = FXCollections.synchronizedObservableList(playerList);

        ObservableList<Challenge> challengeList = FXCollections.observableArrayList();
        this.challenges = FXCollections.synchronizedObservableList(challengeList);

        this.connection.eventSystem.register(this);

        this.timer.scheduleAtFixedRate(new CommandTask(connection, this, Command.GET_PLAYERLIST), 0, 4000);


        this.timer.scheduleAtFixedRate(new CommandTask(connection, this, Command.GET_PLAYERLIST), 0, 4000);
        this.timer.scheduleAtFixedRate(new CommandTask(connection, this, Command.GET_GAMELIST), 0, 60000);
    }


    @EventHandler(later = true)
    public void onGameList(ListEvent.Games event) {
        System.out.println(event);
        this.games.removeIf((String game) -> !event.contains(game));
        event.removeIf(this.games::contains);

        this.games.addAll(event);
        System.out.println(this.games);
    }


    public void onGameClicked(String game) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            try {
                stage.setScene(new Scene(loader.load(getClass().getResource("../controllers/PopupController.fxml").openStream())));
                stage.setTitle("Subscribed to game");
                PopupController popupController = loader.getController();
                popupController.addGameToText(game);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @EventHandler(later = true)
    public void onPlayerList(ListEvent.Players event) {
        this.players.removeIf((String player) -> !event.contains(player));
        event.removeIf(this.players::contains);
        this.players.addAll(event);
    }

    @EventHandler(later = true)
    public void onMatch(MatchEvent event) {
        Plugin p = KnofApplication.getPlugin(event.gameType);
        String playerOne = event.playerToMove;
        String playerTwo = event.playerToMove.equals(event.opponent) ? playerName : event.opponent;
        p.createGame(playerOne, playerTwo);
    }


    @Override
    public void invalidated(Observable observable) {

    }
}

package knof.model;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knof.app.KnofApplication;
import knof.command.Command;
import knof.command.CommandTask;
import knof.connection.Connection;
import knof.controllers.popup.SubscribePopupController;
import knof.event.EventHandler;
import knof.event.events.*;
import knof.model.game.DummyPlayer;
import knof.model.game.Game;
import knof.model.game.Player;
import knof.model.game.Side;
import knof.plugin.Plugin;
import knof.event.events.ChallengeEvent;
import knof.event.events.ListEvent;
import knof.util.DialogHelper;

import java.io.IOException;
import java.util.Timer;
import java.util.function.Predicate;

/**
 * A model representing a server that has been connected to
 */
public class Server implements InvalidationListener {
    public final Connection connection;
    public final ObservableList<GameSettings> games;
    public final ObservableList<String> players;
    public final ObservableList<Challenge> challenges;

    public final ObjectProperty<Game> currentGame = new SimpleObjectProperty<>(null);

    public String playerName;

    private final Timer timer = new Timer(true);

    public Server(Connection connection, String playerName) {
        this.connection = connection;
        this.playerName = playerName;
        ObservableList<GameSettings> gameList = FXCollections.observableArrayList();
        this.games = FXCollections.synchronizedObservableList(gameList);

        ObservableList<String> playerList = FXCollections.observableArrayList();
        this.players = FXCollections.synchronizedObservableList(playerList);

        ObservableList<Challenge> challengeList = FXCollections.observableArrayList();
        this.challenges = FXCollections.synchronizedObservableList(challengeList);

        this.connection.eventSystem.register(this);

        this.timer.scheduleAtFixedRate(new CommandTask(connection, this, Command.GET_PLAYERLIST), 0, 4000);
        this.timer.scheduleAtFixedRate(new CommandTask(connection, this, Command.GET_GAMELIST), 0, 60000);
    }


    @EventHandler(later = true)
    public void onGameList(ListEvent.Games event) {
        System.out.println(event);
        this.games.removeIf((GameSettings game) -> !event.contains(game.toString()));
        event.removeIf(this.games::contains);

        event.forEach(game -> this.games.add(new GameSettings(game)));
		System.out.println(this.games);

	}

	public void challengePlayer(String playerName){

	}


	public void onGameClicked(String game){
		Platform.runLater(() -> {
			Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            try {
                stage.setScene(new Scene(loader.load(getClass().getResource("../controllers/popup/SubscribePopupController.fxml").openStream())));
                stage.setTitle("Subscribed to game");
                SubscribePopupController popupController = loader.getController();
                popupController.addGameToText(game);
                connection.sendCommand(Command.SUBSCRIBE, game);
                popupController.setServer(this);
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
		event.remove(connection.getPlayerName());
		this.players.addAll(event);
    }

    @EventHandler(later = false)
    public void onMatch(MatchEvent event) {
        //TODO Build jar and use it
        Plugin p = KnofApplication.getPlugin(event.gameType);
        if(p != null) {

            Game game = p.createGame(connection);

            boolean playerOneLocal = !event.playerToMove.equals(event.opponent);

            Side localSide, remoteSide;
            if (playerOneLocal) {
                localSide = game.getSide1();
                remoteSide = game.getSide2();
            } else {
                localSide = game.getSide2();
                remoteSide = game.getSide1();
            }

            Player localPlayer = p.createPlayer(connection, game, localSide, playerName);
            Player remotePlayer = new DummyPlayer(event.opponent, remoteSide, connection);

            game.setLocalPlayer(localPlayer);
            game.setRemotePlayer(remotePlayer);

            this.connection.eventSystem.register(game);

            game.addListener(this);
            game.result.addListener((observable, oldValue, newValue) -> {
                terminate();
            });
            Platform.runLater(() -> {
                currentGame.setValue(game);
                game.startGame(event);
            });
        }
    }


    private void terminate(){
        currentGame.setValue(null);
    }

    @Override
    public void invalidated(Observable observable) {
        if(observable instanceof Game){
            Game game = (Game) observable;
        }
    }

    @EventHandler (later=true)
    public void onChallengeReceived(ChallengeEvent e){
		this.challenges.add(new Challenge(e.turnTime, e.challenger, e.gameType, e.id, this));
		System.out.println("Challenge received from " + e.challenger + " for a game of " + e.gameType + ".");
    }

    @EventHandler (later=true)
    public void onChallengeCancelled(ChallengeEvent.Cancel e) {
    	this.challenges.removeIf(challenge -> challenge.id == e.id);
    }

    @EventHandler
    public void onGameEndEvent(GameResultEvent gameResultEvent) {
        DialogHelper.createDialogPane("Game Ended!", gameResultEvent.getMessage());
    }

    public GameSettings getGameSettings(String game) {
        return this.games.filtered(gameSettings -> gameSettings.toString().equals(game)).get(0);
    }

}
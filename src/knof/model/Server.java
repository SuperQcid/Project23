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
import knof.controllers.PopupController;
import knof.event.EventHandler;
import knof.event.events.*;
import knof.plugin.Plugin;
import knof.event.events.ChallengeEvent;
import knof.event.events.ListEvent;
import java.io.IOException;
import java.util.Timer;

/**
 * A model representing a server that has been connected to
 */
public class Server implements InvalidationListener {
    public final Connection connection;
    public final ObservableList<String> games;
    public final ObservableList<String> players;
    public final ObservableList<Challenge> challenges;

    public final ObjectProperty<Game> currentGame = new SimpleObjectProperty<>(null);

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

	public void challengePlayer(String playerName){

	}


	public void onGameClicked(String game){
		Platform.runLater(() -> {
			Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            try {
                stage.setScene(new Scene(loader.load(getClass().getResource("../controllers/PopupController.fxml").openStream())));
                stage.setTitle("Subscribed to game");
                PopupController popupController = loader.getController();
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

    @EventHandler(later = true)
    public void onMatch(MatchEvent event) {
        String playerOne, playerTwo;
        boolean playerOneLocal;
        //TODO Build jar and use it
        Plugin p = KnofApplication.getPlugin(event.gameType);
        if(p != null) {
            if (event.playerToMove.equals(event.opponent)) {
                playerOne = event.opponent;
                playerTwo = playerName;
                playerOneLocal = false;
            } else {
                playerOne = playerName;
                playerTwo = event.opponent;
                playerOneLocal = true;
            }
            Game game = p.createGame(playerOne, playerTwo, playerOneLocal, connection);
            game.addListener(this);
            currentGame.setValue(game);
            game.startGame(event);
        }
    }


    private void terminate(){
        currentGame.setValue(null);
    }

    @Override
    public void invalidated(Observable observable) {
        if(observable instanceof Game){
            Game game = (Game) observable;
            if(game.latestEvent instanceof GameResultEvent){
                terminate();
            }
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

}

package knof.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knof.command.Command;
import knof.command.CommandTask;
import knof.connection.Connection;
import knof.controllers.PopupController;
import knof.controllers.ServerController;
import knof.event.EventHandler;
import knof.event.events.ListEvent;
import knof.event.events.ListEvent.Games;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

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

		ObservableList<Challenge> chalengeList = FXCollections.observableArrayList();
		this.challenges = FXCollections.synchronizedObservableList(chalengeList);

		this.connection.eventSystem.register(this);

		this.timer.scheduleAtFixedRate(new CommandTask(connection, this, Command.GET_PLAYERLIST), 0, 4000);
		this.timer.scheduleAtFixedRate(new CommandTask(connection, this, Command.GET_GAMELIST), 0, 60000);
	}


	@EventHandler
	public void onGameList(ListEvent.Games event) {
		Platform.runLater(() -> {
			System.out.println(event);
			this.games.removeIf((String game) -> !event.contains(game));
			event.removeIf(this.games::contains);

			this.games.addAll(event);
			System.out.println(this.games);
		});
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
}

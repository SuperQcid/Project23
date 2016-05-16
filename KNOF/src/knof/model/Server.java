package knof.model;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import knof.command.Command;
import knof.command.CommandTask;
import knof.connection.Connection;
import knof.event.EventHandler;
import knof.event.events.*;
import knof.model.game.DummyPlayer;
import knof.model.game.Game;
import knof.model.game.Player;
import knof.model.game.Side;
import knof.plugin.Plugin;
import knof.plugin.PluginLoader;
import knof.util.DialogHelper;

import java.util.HashMap;
import java.util.Timer;

/**
 * A model representing a server that has been connected to
 */
public class Server implements InvalidationListener {
    public final Connection connection;
    public final ObservableList<GameEntry> games;
    public final ObservableList<String> players;
    public final ObservableList<Challenge> challenges;

    public final SimpleObjectProperty<String> currentSubscription = new SimpleObjectProperty<>(null);

    public final ObjectProperty<Game> currentGame = new SimpleObjectProperty<>(null);
    public final ObjectProperty<String> currentChallenge = new SimpleObjectProperty<>(null);
    private HashMap<String, Plugin> pluginList = new PluginLoader().InitializePlugins();

    public String playerName;
    public String opponentName;

    private final Timer timer = new Timer(true);

    public Server(Connection connection, String playerName) {
        this.connection = connection;
        this.playerName = playerName;
        ObservableList<GameEntry> gameList = FXCollections.observableArrayList();
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
        this.games.removeIf((GameEntry game) -> !event.contains(game.toString()));
        this.games.forEach(gameEntry -> event.remove(gameEntry.toString()));

        event.forEach(game -> this.games.add(new GameEntry(game, this)));
		System.out.println(this.games);

	}

    public void subscribe(String game) {
        connection.sendCommandWithCallBackLater(statusEvent -> {
            if (statusEvent instanceof StatusEvent.Ok) {
                this.currentSubscription.set(game);
            }
        }, Command.SUBSCRIBE, game);
    }

    public void unsubscribe() {
        connection.sendCommandWithCallBackLater(statusEvent -> {
            this.currentSubscription.set(null);
            if (statusEvent instanceof StatusEvent.Error) {
                System.err.println(((StatusEvent.Error) statusEvent).reason);
            }
        }, Command.UNSUBSCRIBE);
    }

    public void challenge(String player, String game, int turntime) {
        connection.sendCommandWithCallBackLater(statusEvent -> {
            this.currentChallenge.set(player);
        }, Command.CHALLENGE_TURNTIME, player, game, turntime);
    }

    public void challenge(String player, String game) {
        connection.sendCommandWithCallBackLater(statusEvent -> {
            this.currentChallenge.set(player);
        }, Command.CHALLENGE, player, game);
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
        Plugin p = this.getPlugin(event.gameType);
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
            this.opponentName = remotePlayer.getName();
            game.sideUp = playerOneLocal ? localSide : remoteSide;

            game.setLocalPlayer(localPlayer);
            game.setRemotePlayer(remotePlayer);

            this.connection.eventSystem.register(game);

            game.addListener(this);
            game.result.addListener((observable, oldValue, newValue) -> {
                terminate();
            });
            Platform.runLater(() -> {
                currentChallenge.set(null);
                currentSubscription.set(null);
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
        String winner = playerName;
        String loser = opponentName;
        if (gameResultEvent instanceof GameResultEvent.Loss) {
            winner = opponentName;
            loser = playerName;
        }


        DialogHelper.createDialogPane("Game Ended!", gameResultEvent.getMessage() + "\n" +

                winner + " has won! \n" +
                "Score: " + gameResultEvent.playerOneScore + " - " + gameResultEvent.playerTwoScore

        );
    }

    public GameEntry getGameSettings(String game) {
        return this.games.filtered(gameSettings -> gameSettings.toString().equals(game)).get(0);
    }

    public Plugin getPlugin(String name) {
        return pluginList.get(name);
    }

}
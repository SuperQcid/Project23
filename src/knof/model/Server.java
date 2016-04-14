package knof.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A model representing a server that has been connected to
 */
public class Server {
    public final ObservableList<String> games;
    public final ObservableList<String> players;
    public final ObservableList<Challenge> challenges;

    public Server() {
        ObservableList<String> gameList = FXCollections.observableArrayList();
        this.games = FXCollections.synchronizedObservableList(gameList);

        ObservableList<String> playerList = FXCollections.observableArrayList();
        this.players = FXCollections.synchronizedObservableList(playerList);

        ObservableList<Challenge> chalengeList = FXCollections.observableArrayList();
        this.challenges = FXCollections.synchronizedObservableList(chalengeList);
    }
}

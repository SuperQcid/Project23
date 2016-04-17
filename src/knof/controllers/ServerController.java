package knof.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import knof.model.Challenge;
import knof.model.Server;

public class ServerController {
    @FXML
    public ListView<String> gameList;

    @FXML
    public ListView<String> playerList;

    @FXML
    public ListView<Challenge> challengeList;

    private Server server;

    public void setServer(Server server) {
        this.server = server;

        this.gameList.setItems(server.games);
        this.playerList.setItems(server.players);
        this.challengeList.setItems(server.challenges);
    }
}

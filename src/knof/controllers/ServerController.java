package knof.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import knof.model.Challenge;
import knof.model.Server;

import java.io.IOException;

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

        this.playerList.setCellFactory(param -> new PlayerCell());

        this.gameList.setItems(server.games);
        this.playerList.setItems(server.players);
        this.challengeList.setItems(server.challenges);
    }

    static class PlayerCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            FXMLLoader loader = new FXMLLoader();
            try {
                GridPane loaded = loader.load(getClass().getResource("../controllers/PlayerCell.fxml").openStream());
                this.setGraphic(loaded);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

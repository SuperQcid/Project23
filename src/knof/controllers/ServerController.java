package knof.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
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

    public void setServer(Server server) {

        this.playerList.setCellFactory((ListView<String> param) -> new PlayerCell());

        this.gameList.setItems(server.games);
        this.playerList.setItems(server.players);
        this.challengeList.setItems(server.challenges);
    }

    // TODO: Refactor this to something more generic
    static class PlayerCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty && item != null) {
                try {
                	FXMLLoader loader = new FXMLLoader();
                    GridPane loaded = loader.load(getClass().getResource("../controllers/PlayerController.fxml").openStream());
                    PlayerController playerController = loader.getController();
                    playerController.playerName.setText(item);
                	//setText(item);
                    this.setGraphic(loaded);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
            	this.setGraphic(null);
            }
        }
    }
}

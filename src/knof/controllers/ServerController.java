package knof.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private Server server;

    public void setServer(Server server) {
        this.server = server;

        this.playerList.setCellFactory(param -> new PlayerCell());

        this.gameList.setItems(server.games);
        this.gameList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {          
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				//TODO when a game is clicked.
			}
        });
        this.playerList.setItems(server.players);
        this.challengeList.setItems(server.challenges);
    }

    // TODO: Refactor this to something more generic
    static class PlayerCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if(!empty) {
                FXMLLoader loader = new FXMLLoader();
                try {
                    GridPane loaded = loader.load(getClass().getResource("../controllers/PlayerController.fxml").openStream());
                    PlayerController playerController = loader.getController();
                    playerController.playerName.setText(item);

                    this.setGraphic(loaded);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

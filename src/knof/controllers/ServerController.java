package knof.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import knof.model.Challenge;
import knof.model.Game;
import knof.model.Server;

import java.io.IOException;

public class ServerController implements InvalidationListener {
    @FXML
    public ListView<String> gameList;

    @FXML
    public ListView<String> playerList;

    @FXML
    public ListView<Challenge> challengeList;

    public Game currentGame;

    public void setServer(Server server) {

        this.playerList.setCellFactory((ListView<String> param) -> new PlayerCell());

        this.gameList.setItems(server.games);

        //this might be a crappy way of adding a listener...
        this.gameList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				server.onGameClicked(gameList.getSelectionModel().getSelectedItem());
			}
        });

        this.playerList.setItems(server.players);
        this.challengeList.setItems(server.challenges);
        this.currentGame = server.currentGame;
    }

    @Override
    public void invalidated(Observable observable) {
        if(observable instanceof Game){
            Game game = (Game) observable;
        }
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

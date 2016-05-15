package knof.controllers.popup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import knof.command.Command;
import knof.controllers.NumberTextField;
import knof.model.GameEntry;
import knof.model.Server;
import knof.util.DebugSettings;

public class ChallengePopupController {

	private Server server;
	public String player;
	public String game;
	private ObservableList<GameEntry> gamelist = FXCollections.observableArrayList();
	
    @FXML
    private NumberTextField turntime;

    @FXML
    private Button challengeButton;

    @FXML
    private CheckBox turntimeBox;
    
    @FXML
    private ChoiceBox<GameEntry> gameBox;

    @FXML
    public void challenge(ActionEvent event) {
    	game = gameBox.getValue().toString();
    	if(game != null) {
			if(turntimeBox.isSelected()) {
				server.challenge(player, game, Integer.valueOf(turntime.getText()));
			} else {
				if (DebugSettings.DEBUG) {
					server.challenge(player, game, 300);
				} else {
					server.challenge(player, game);
				}
			}
			Stage stage = (Stage) challengeButton.getScene().getWindow();
			stage.close();
    	}
    }
    
    @FXML
    public void customTurntime(ActionEvent event) {
    	if(turntimeBox.isSelected()) {
    		turntime.setDisable(false);
    	} else {
    		turntime.setDisable(true);
    	}
    }

	public void setServer(Server server) {
		this.server = server;
		this.gamelist.addAll(server.games);
		this.gamelist.removeIf(gameSettings -> !gameSettings.hasPlugin());
		this.gameBox.setItems(this.gamelist);
		if(gamelist.size() > 0) {
			this.gameBox.selectionModelProperty().getValue().selectFirst();
		} else {
			this.challengeButton.setText("No games available");
			this.challengeButton.disarm();
			this.challengeButton.setDisable(true);
		}
	}
}

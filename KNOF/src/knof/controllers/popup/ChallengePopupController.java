package knof.controllers.popup;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import knof.app.KnofApplication;
import knof.command.Command;
import knof.controllers.NumberTextField;
import knof.model.GameSettings;
import knof.model.Server;
import knof.plugin.Plugin;
import knof.util.DebugSettings;

public class ChallengePopupController {

	private Server server;
	public String player;
	public String game;
	private ObservableList<GameSettings> gamelist = FXCollections.observableArrayList();
	
    @FXML
    private NumberTextField turntime;

    @FXML
    private Button challengeButton;

    @FXML
    private CheckBox turntimeBox;
    
    @FXML
    private ChoiceBox<GameSettings> gameBox;

    @FXML
    public void challenge(ActionEvent event) {
    	game = gameBox.getValue().toString();
    	if(game != null) {
			if(turntimeBox.isSelected()) {
				server.connection.sendCommand(Command.CHALLENGE_TURNTIME, player, game, turntime.getText());
			} else {
				if (DebugSettings.DEBUG) {
					server.connection.sendCommand(Command.CHALLENGE_TURNTIME, player, game, 300);
				} else {
					server.connection.sendCommand(Command.CHALLENGE, player, game);
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
    
//    @FXML
//    public void initialize() {
//		ObservableList<GameSettings> gamelist = FXCollections.observableArrayList();
//    	/*
//		ObservableList<String> gamelist = FXCollections.observableArrayList();
//    	HashMap<String, Plugin> pluginList = KnofApplication.getPluginList();
//    	for(String gameName: pluginList.keySet()) {
//    	    gamelist.add(gameName);
//    	}
//    	gameBox.setItems(gamelist);
//    	*/
//    }

	public void setServer(Server server) {
		this.server = server;
		this.gamelist.addAll(server.games);
	}
}

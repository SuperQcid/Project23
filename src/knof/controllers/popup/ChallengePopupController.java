package knof.controllers.popup;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import knof.app.KnofApplication;
import knof.command.Command;
import knof.controllers.NumberTextField;
import knof.model.Server;
import knof.plugin.Plugin;

public class ChallengePopupController {

	public Server server;
	public String player;
	
    @FXML
    private NumberTextField turntime;

    @FXML
    private Button challengeButton;

    @FXML
    private CheckBox turntimeBox;
    
    @FXML
    private ChoiceBox<String> gameBox;

    @FXML
    public void challenge(ActionEvent event) {
    	if(turntimeBox.isSelected()) {
    		server.connection.sendCommand(Command.CHALLENGE_TURNTIME, player, gameBox.getValue(), turntime.getText());
    	} else {
    		server.connection.sendCommand(Command.CHALLENGE, player, gameBox.getValue());
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
    
    @FXML
    public void initialize() {
    	ObservableList<String> gamelist = FXCollections.observableArrayList();
    	HashMap<String, Plugin> pluginList = KnofApplication.getPluginList();
    	for(String gameName: pluginList.keySet()) {
    	    gamelist.add(gameName);
    	}
    	gameBox.setItems(gamelist);
    }
}

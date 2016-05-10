package knof.controllers.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import knof.command.Command;
import knof.controllers.NumberTextField;
import knof.model.Server;

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
    public void challenge(ActionEvent event) {
        server.connection.sendCommand(Command.CHALLENGE, player, "Reversi");
    }

    @FXML
    public void customTurntime(ActionEvent event) {
    	if(turntimeBox.isSelected()) {
    		turntime.setDisable(false);
    	} else {
    		turntime.setDisable(true);
    	}
    }
}

package knof.controllers.listcell.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import knof.command.Command;
import knof.model.Server;

public class PlayerController implements ListCellController {
    @FXML
    public Label playerName;
    
    @FXML
    public Button challenge;
    
    @FXML
    public ProgressIndicator loadingSign;
    
    public Server server;
    
    @FXML
    public void challenge() {
        System.out.println("CHALLENGE!!!");
        loadingSign.setVisible(true);
        challenge.setVisible(false);
        challenge.setDisable(true);
        if(isServerSet()){
        	server.connection.sendCommand(Command.CHALLENGE, playerName.getText(), "Reversi");
        }
    }    
    public void setServer(Server server){
    	this.server = server;
    }
    
    public boolean isServerSet(){
		return (server != null);
	}
}

package knof.controllers.listcell.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import knof.command.Command;
import knof.model.Server;

public class PlayerController extends ListCellController {
    
    
   
    
    public Server server;
    
    @FXML
    public void onButton(ActionEvent e) {

        System.out.println("CHALLENGE!!!");
        loadingSign.setVisible(true);

        button.setVisible(false);
        button.setDisable(true);
        if(isServerSet()){
        	server.connection.sendCommand(Command.CHALLENGE, cell.getText(), "Reversi");
        }
    }    
    public void setServer(Server server){
    	this.server = server;
    }
    
    public boolean isServerSet(){
		return (server != null);
	}
}

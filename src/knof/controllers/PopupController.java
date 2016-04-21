package knof.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import knof.command.Command;
import knof.event.EventHandler;
import knof.event.events.MatchEvent;
import knof.model.Server;

public class PopupController {
	
	@FXML
	public Button cancel;
	
	@FXML
	public Text text;
	
	@FXML
	public Text waiting;
	
	public Server server;
	
	public void cancelButtonPressed(){
	    Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	    if(isServerSet()){
	    	server.connection.sendCommand(Command.UNSUBSCRIBE);
	    } else {
	    	System.out.println("Server not set. Cannot unsubscribe.");
	    }
	    
	}
	
	@EventHandler(later = true)
	public void closePopupOnGameStart(MatchEvent match){
		Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	public void addGameToText(String game){
		this.text.setText(this.text.getText() + " " + game);
	}
	
	public void setServer(Server server){
		this.server = server;
		this.server.connection.eventSystem.register(this);
	}
	
	private boolean isServerSet(){
		if(this.server != null){
			return true;
		}
		return false;
	}
	

}

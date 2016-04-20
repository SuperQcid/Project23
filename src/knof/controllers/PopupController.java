package knof.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PopupController {
	
	@FXML
	public Button cancel;
	
	@FXML
	public Text text;
	
	@FXML
	public Text waiting;
	
	public void cancelButtonPressed(){
	    Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	public void addGameToText(String game){
		this.text.setText(this.text.getText() + " " + game);
	}
	

}

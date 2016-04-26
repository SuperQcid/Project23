package knof.controllers.listcell.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public abstract class ListCellController {

	@FXML
	public Label cell;
	
	@FXML
	public Button button;
	
	@FXML
	public ProgressIndicator loadingSign;
	
	@FXML
	public abstract void onButton(ActionEvent e);
}

package knof.controllers.listcell.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;

public abstract class ListCellController extends GridPane { 
	
	@FXML
	public Label cell;
	
	@FXML
	public Button button;
	
	@FXML
	public ProgressIndicator loadingSign;
	
	@FXML
	public abstract void onButton(ActionEvent e);
	
	/**
	 * Returns the name of the view associated with this controller.
	 * Uses "ListCell" by default.
	 * Override this method to use a different view.
	 * @return viewName
	 */
	public String getViewName() {
		return "ListCell";
	}
}

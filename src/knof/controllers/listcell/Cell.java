package knof.controllers.listcell;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import knof.controllers.listcell.controllers.ListCellController;

public abstract class Cell extends ListCell<String> {
	
	protected Node loaded;
	
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty && item != null) {
			cell(item);
			this.setGraphic(loaded);
        } else {
        	this.setGraphic(null);
        }
    }
    
    /**
     * Load a ListCellController for a cell to use.
     * @param name
     * @return controller.
     */
    public ListCellController loadController(String name) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loaded = loader.load(getClass().getResource("controllers/" + name + ".fxml").openStream());
			ListCellController controller = loader.getController();
			return controller;
		} catch (IOException e) {
			return null;
		}
	}
    
    /**
     * Define what the cell should do for each item.
     * @param item
     */
    public abstract void cell(String item);
    	
}

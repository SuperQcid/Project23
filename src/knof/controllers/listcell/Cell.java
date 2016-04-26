package knof.controllers.listcell;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import knof.controllers.listcell.controllers.ListCellController;

public abstract class Cell<T> extends ListCell<T> {
	
	protected Node loaded;
	
    @Override
    public void updateItem(T item, boolean empty) {
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
    @Deprecated
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
    
    public void setController(ListCellController controller) {
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setRoot(controller);
			loader.setController(controller);
			loaded = loader.load(getClass().getResource("controllers/ListCellController.fxml").openStream());
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Define what the cell should do for each item.
     * @param item
     */
    public abstract void cell(T item);
    	
}

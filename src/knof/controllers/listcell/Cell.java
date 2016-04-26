package knof.controllers.listcell;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import knof.controllers.listcell.controllers.ListCellController;

public abstract class Cell<T> extends ListCell<T> {
	
	protected Node loaded;
	protected ListCellController controller;
	
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty && item != null) {
        	controller = getController();
        	addControllerToView();
			cell(item);
			this.setGraphic(loaded);
        } else {
        	this.setGraphic(null);
        }
    }
    
    /**
     * Adds the controller to the loaded ListCell view.
     */
    private void addControllerToView() {
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setRoot(controller);
			loader.setController(controller);
			loaded = loader.load(getClass().getResource("controllers/ListCell.fxml").openStream());
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Define what the cell should do for each item.
     * @param item
     */
    public abstract void cell(T item);
    	
    /**
     * Children should set the appropriate controller to use for the ListCell view.
     * @return controller;
     */
    public abstract ListCellController getController();
}

package knof.controllers.listcell;

import knof.controllers.listcell.controllers.GameController;
import knof.controllers.listcell.controllers.ListCellController;

public class GameCell extends Cell<String> {

	private GameController controller = new GameController();
	
	@Override
	public void cell(String item) {
		controller.cell.setText(item);
	}

	@Override
	public ListCellController getController() {
		return controller;
	}
}

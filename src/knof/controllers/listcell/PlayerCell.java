package knof.controllers.listcell;

import knof.controllers.listcell.controllers.ListCellController;
import knof.controllers.listcell.controllers.PlayerController;

public class PlayerCell extends Cell<String> {
	
	private PlayerController controller = new PlayerController();

	@Override
	public void cell(String item) {
		controller.cell.setText(item);
	}

	@Override
	public ListCellController getController() {
		return controller;
	}

}

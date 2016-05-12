package knof.controllers.listcell;

import knof.app.KnofApplication;
import knof.controllers.listcell.controllers.GameController;
import knof.controllers.listcell.controllers.ListCellController;
import knof.plugin.Plugin;

public class GameCell extends Cell<String> {

	private GameController controller = new GameController();
	
	@Override
	public void cell(String item) {
		controller.cell.setText(item);

		Plugin p = KnofApplication.getPlugin(item);
		if(p != null) {
			controller.button.setText("Subscribe");
			controller.populateAIChoiceBox(p.getPlayerTypes(true));
		} else {
			controller.getChildren().remove(controller.ai);
			controller.button.setText("Unavailable");
			controller.button.setDisable(true);
		}
	}

	@Override
	public ListCellController getController() {
		return controller;
	}
}

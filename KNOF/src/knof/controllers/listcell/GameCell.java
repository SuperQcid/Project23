package knof.controllers.listcell;

import knof.controllers.listcell.controllers.GameController;
import knof.controllers.listcell.controllers.ListCellController;
import knof.model.GameEntry;
import knof.plugin.Plugin;

public class GameCell extends Cell<GameEntry> {

	private GameController controller = new GameController();
	
	@Override
	public void cell(GameEntry item) {
		controller.cell.setText(item.toString());
		controller.bindGame(item);

		Plugin p = item.server.getPlugin(item.toString());
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

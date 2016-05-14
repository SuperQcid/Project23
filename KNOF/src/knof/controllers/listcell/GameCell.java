package knof.controllers.listcell;

import knof.app.KnofApplication;
import knof.controllers.listcell.controllers.GameController;
import knof.controllers.listcell.controllers.ListCellController;
import knof.model.GameSettings;
import knof.plugin.Plugin;

public class GameCell extends Cell<GameSettings> {

	private GameController controller = new GameController();
	
	@Override
	public void cell(GameSettings item) {
		controller.cell.setText(item.toString());
		controller.bindGame(item);

		Plugin p = KnofApplication.getPlugin(item.toString());
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

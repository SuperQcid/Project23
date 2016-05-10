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
		controller.button.setText("Subscribe");
		Plugin p = KnofApplication.getPlugin(item);
		if(p != null) {
			controller.pupulateAIChoiceBox(p.getPlayerTypes(true));
		} else {
			controller.getChildren().remove(controller.ai);
//			controller.button.setVisible(false);
			controller.button.setText("Unavailable");
			controller.button.setDisable(true);
		}
	}

	@Override
	public ListCellController getController() {
		return controller;
	}
}

package knof.controllers.listcell;

import knof.controllers.listcell.controllers.ChallengeController;
import knof.controllers.listcell.controllers.ListCellController;
import knof.model.Challenge;

public class ChallengeCell extends Cell<Challenge> {
	
	private ChallengeController controller = new ChallengeController(); 

	@Override
	public void cell(Challenge item) {
        controller.setChallengeID(item.id);
        controller.setServer(item.server);
        controller.cell.setText(item.player);
        controller.button.setText("Accept");
		controller.gameType.setText(item.game);
	}

	@Override
	public ListCellController getController() {
		return controller;
	}
}

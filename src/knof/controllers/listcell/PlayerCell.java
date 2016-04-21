package knof.controllers.listcell;

import knof.controllers.listcell.controllers.PlayerController;

public class PlayerCell extends Cell<String> {

	@Override
	public void cell(String item) {
		PlayerController controller = (PlayerController) loadController("PlayerController");
		controller.playerName.setText(item);
	}
}

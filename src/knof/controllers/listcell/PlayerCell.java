package knof.controllers.listcell;

import javafx.application.Platform;
import knof.controllers.listcell.controllers.PlayerController;
import knof.model.Server;

public class PlayerCell extends Cell<String> {

	public Server server;

	@Override
	public void cell(String item) {
		PlayerController controller = (PlayerController) loadController("PlayerController");
		if (!controller.isServerSet()) {
			controller.setServer(this.server);
		}
		Platform.runLater(() -> {
			controller.playerName.setText(item);
		});
	}

	public PlayerCell(Server server) {
		super();
		this.server = server;
	}

}

package knof.controllers.listcell;

import javafx.scene.control.Label;
import knof.controllers.listcell.controllers.ChallengeController;
import knof.model.Challenge;

public class ChallengeCell extends Cell<Challenge> {

	@Override
	public void cell(Challenge item) {
		ChallengeController controller = (ChallengeController) loadController("ChallengeController");
        controller.setChallengeID(item.id);
        controller.setServer(item.server);
        controller.challengeName.setLabelFor(new Label(item.player));
	}
}

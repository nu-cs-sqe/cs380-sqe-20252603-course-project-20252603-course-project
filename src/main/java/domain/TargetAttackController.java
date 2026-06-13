package domain;

import ui.TargetAttackControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TargetAttackController implements CardController {
    private final TargetAttackControllerView controllerView;

    public TargetAttackController() {
        this.controllerView = new TargetAttackControllerView();
    }

    TargetAttackController(TargetAttackControllerView controllerView) {
        this.controllerView = controllerView;
    }

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player initiator,
                                                  Optional<Player> target) {
        Game game = gameController.getGame();
        ArrayList<Player> alivePlayers = new ArrayList<Player>(game.getAlivePlayers());

        controllerView.displayAlivePlayers(alivePlayers, initiator);
        while (true) {
            String userChoice = controllerView.getTargetPlayerIndex();
            try {

                int targetIndex = Integer.parseInt(userChoice.trim());

                Player actualTarget = alivePlayers.get(targetIndex);

                if (!actualTarget.isAlive()) {
                    controllerView.displayInvalidTarget("Target must be alive");
                }

                else if (actualTarget == initiator) {
                    controllerView.displayInvalidTarget("Target and initiator must be different players.");
                }

                else {
                    gameController.setNextPlayerTurnsLeft(2);
                    break;
                }

            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                controllerView.displayInvalidIndex(userChoice);
            }
        }

        return Optional.empty();
    }
}
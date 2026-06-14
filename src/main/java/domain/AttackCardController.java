package domain;

import java.util.List;
import java.util.Optional;

public class AttackCardController implements CardController {
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target){
        gameController.setNextPlayerTurnsLeft(gameController.getCurrentPlayerTurnsLeft() + 2);
        gameController.setCurrentPlayerTurnsLeft(0);
        return Optional.empty();
    }
}

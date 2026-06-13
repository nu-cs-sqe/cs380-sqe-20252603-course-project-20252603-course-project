package domain;

import java.util.List;
import java.util.Optional;

public class HailMaryController implements CardController {
    @Override
    public Optional<List<Card>> executeCardAction(GameController gameController, Player initiator, Optional<Player> target) {
        if (initiator.getHandSize() == 0) {
            throw new IllegalStateException("initiator's hand cannot be empty");
        }
        return Optional.empty();
    }
}

package domain;

import java.util.List;
import java.util.Optional;

public interface CardController {
   Optional<List<Card>> executeCardAction(GameController gameController,
                                          Player initiator,
                                          Optional<Player> target);
}
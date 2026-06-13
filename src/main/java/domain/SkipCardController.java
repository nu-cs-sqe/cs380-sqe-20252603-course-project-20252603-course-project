package domain;

import java.util.List;
import java.util.Optional;

public class SkipCardController implements CardController {
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target){
        return Optional.empty();
    }
}

package domain;

import java.util.List;
import java.util.Optional;

public class CatCardController implements CardController {
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target){
        throw new UnsupportedOperationException("not yet implemented");
    }
}

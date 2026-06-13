package domain;

import java.util.List;
import java.util.Optional;

public class DefuseCardController implements CardController {
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target){
        throw new IllegalStateException("defuse cards can not be played directly");
    }
}
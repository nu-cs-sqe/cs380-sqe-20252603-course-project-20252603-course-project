package domain;

import java.util.List;
import java.util.Optional;

public class ShuffleCardController implements CardController {
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                Player user,
                                                Optional<Player> target){
        Deck deck = gameController.getGame().getDeck();
        deck.shuffle();

        return Optional.empty();
    }
}

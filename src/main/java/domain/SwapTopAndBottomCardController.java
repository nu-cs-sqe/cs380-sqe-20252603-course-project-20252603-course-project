package domain;

import java.util.List;
import java.util.Optional;

public class SwapTopAndBottomCardController implements CardController {

    @Override
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        Deck deck = gameController.getGame().getDeck();

        if (deck.count() < 2) {
            throw new IllegalStateException("not enough cards to swap");
        }

        Card topCard = deck.takeTopCard();

        int bottomIndex = deck.count() - 1;
        Card bottomCard = deck.getCards().get(bottomIndex);
        deck.discard(bottomCard);

        deck.insert(bottomCard, 0);
        deck.insert(topCard, deck.count());

        return Optional.empty();
    }
}
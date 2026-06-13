package domain;

import java.util.List;
import java.util.Optional;

public class HailMaryController implements CardController {
    @Override
    public Optional<List<Card>> executeCardAction(
            GameController gameController,
            Player initiator,
            Optional<Player> target)
    {
        int handSize = initiator.getHandSize();
        if (handSize == 0) {
            throw new IllegalStateException("initiator's hand cannot be empty");
        }

        Deck deck = gameController.getGame().getDeck();
        if (deck.count() < handSize) {
            throw new IllegalStateException("not enough cards in deck to redraw");
        }

        for (Card card : initiator.getHand()) {
            initiator.removeCard(card);
        }

        for (int i = 0; i < handSize; i++) {
            initiator.addCard(deck.takeTopCard());
        }

        return Optional.empty();
    }
}

package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeeTheFutureCardController implements CardController {
    private static final int MINIMUM_CARDS_FOR_PEEKING_3    = 3;
    private static final int BOTTOM_CARD = 0;
    private static final int SECOND_CARD = 1;
    private static final int THIRD_CARD = 2;

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        List<Card> cardsToReturn = new ArrayList<>();
        List<Card> cards = gameController.getGame().getDeck().getCards();
        int deckSize = cards.size();
        if (deckSize >= MINIMUM_CARDS_FOR_PEEKING_3) {
            cardsToReturn.add(cards.get(BOTTOM_CARD));
            cardsToReturn.add(cards.get(SECOND_CARD));
            cardsToReturn.add(cards.get(THIRD_CARD));
        } else {
            for (int i = 0; i < deckSize; i++) {
                cardsToReturn.add(cards.get(i));
            }
        }
        return Optional.of(cardsToReturn);
    }
}
package domain;

import ui.SeeTheFutureCardControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeeTheFutureCardController implements CardController {
    private static final int MINIMUM_CARDS_FOR_PEEKING_3 = 3;
    private final SeeTheFutureCardControllerView view;

    public SeeTheFutureCardController() {
        this.view = new SeeTheFutureCardControllerView();
    }

    SeeTheFutureCardController(SeeTheFutureCardControllerView view) {
        this.view = view;
    }

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        List<Card> cards = gameController.getGame().getDeck().getCards();
        int deckSize = cards.size();
        int cardsToPeek = Math.min(deckSize, MINIMUM_CARDS_FOR_PEEKING_3);

        List<Card> topCards = new ArrayList<>(cards.subList(0, cardsToPeek));
        view.displayTopCards(topCards);
        return Optional.of(topCards);
    }
}

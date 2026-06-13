package domain;

import ui.FavorCardControllerView;

import java.util.List;
import java.util.Optional;

public class FavorCardController implements CardController {
    private final FavorCardControllerView controllerView;

    public FavorCardController() {
        this.controllerView = new FavorCardControllerView();
    }

    FavorCardController(FavorCardControllerView controllerView) {
        this.controllerView = controllerView;
    }

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        Player t = target.get();
        Card card = controllerView.getCardToGive(t.getHand());

        t.removeCard(card);
        user.addCard(card);

        return Optional.empty();
    }
}
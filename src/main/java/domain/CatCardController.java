package domain;

import ui.CatCardControllerView;

import java.util.List;
import java.util.Optional;

public class CatCardController implements CardController {

    private final int cardsPlayed;
    private final CatCardControllerView controllerView;

    public CatCardController(int cardsPlayed) {
        this.cardsPlayed = cardsPlayed;
        this.controllerView = new CatCardControllerView();
    }

    CatCardController(int cardsPlayed, CatCardControllerView controllerView) {
        this.cardsPlayed = cardsPlayed;
        this.controllerView = controllerView;
    }

    @Override
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player initiator,
                                                  Optional<Player> target) {
        if (target.isEmpty()) return Optional.empty();
        if (target.get().equals(initiator)) return Optional.empty();

        return Optional.empty();
    }
}
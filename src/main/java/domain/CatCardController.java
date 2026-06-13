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
        if (!gameController.getGame().getAlivePlayers().contains(target.get())) return Optional.empty();

        Player actualTarget = target.get();

        if (cardsPlayed == 2) {
            if (actualTarget.getHandSize() == 0) return Optional.empty();

            List<Card> targetHandSnapshot = actualTarget.getHand();
            int randomIndex = (int) (Math.random() * targetHandSnapshot.size());
            Card stolenCard = targetHandSnapshot.get(randomIndex);

            actualTarget.removeCard(stolenCard);
            initiator.addCard(stolenCard);

            return Optional.of(List.of(stolenCard));
        }

        if (cardsPlayed == 3) {
            Optional<CardType> requestedType = controllerView.getRequestedCardType();
            if (requestedType.isEmpty()) return Optional.empty();
            if (!actualTarget.hasCard(requestedType.get())) return Optional.empty();

            Card stolenCard = actualTarget.getHand().stream()
                    .filter(card -> card.getType() == requestedType.get())
                    .findFirst()
                    .get();

            actualTarget.removeCard(stolenCard);
            initiator.addCard(stolenCard);

            return Optional.of(List.of(stolenCard));
        }

        return Optional.empty();
    }
}
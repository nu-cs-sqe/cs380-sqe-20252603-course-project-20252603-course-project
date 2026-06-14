package domain;

import ui.OneCatPolicyCardControllerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OneCatPolicyCardController {
    private final OneCatPolicyCardControllerView controllerView;

    public OneCatPolicyCardController() {
        this.controllerView = new OneCatPolicyCardControllerView();
    }

    OneCatPolicyCardController(OneCatPolicyCardControllerView controllerView) {
        this.controllerView = controllerView;
    }

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {

        Game game = gameController.getGame();

        int nextPlayerIndex = gameController.getNextPlayerIndex();
        Player nextPlayer = game.getAlivePlayers().get(nextPlayerIndex);

        Map<CardType, Integer> catCounts = new HashMap<>();
        int penaltyTurns = 0;

        for (Card card : nextPlayer.getHand()) {
            CardType type = card.getType();

            if (type == CardType.TACOCAT ||
                    type == CardType.CAT_CARD_2 ||
                    type == CardType.CAT_CARD_3 ||
                    type == CardType.CAT_CARD_4) {

                catCounts.put(type, catCounts.getOrDefault(type, 0) + 1);
            }
        }

        for (int count : catCounts.values()) {
            if (count >= 2) {
                penaltyTurns += 1;
            }
        }

        if (penaltyTurns > 0) {
            int currentNextTurns = gameController.getNextPlayerTurnsLeft();
            gameController.setNextPlayerTurnsLeft(currentNextTurns + penaltyTurns);
            controllerView.displayTurnsAdded(penaltyTurns);
        } else {
            controllerView.displayNoTurnsAdded();
        }

        return Optional.empty();
    }
}
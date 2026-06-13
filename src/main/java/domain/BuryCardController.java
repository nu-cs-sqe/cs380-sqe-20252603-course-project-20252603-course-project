package domain;

import ui.BuryCardControllerView;

import java.util.List;
import java.util.Optional;

public class BuryCardController {
    private final BuryCardControllerView controllerView;

    public BuryCardController() {
        this.controllerView = new BuryCardControllerView();
    }

    BuryCardController(BuryCardControllerView controllerView) {
        this.controllerView = controllerView;
    }

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        Game game = gameController.getGame();
        Deck deck = game.getDeck();
        if (deck.count() < 1) {
            controllerView.displayNoCardsInDeck();
            return Optional.empty();
        }

        Card drawnCard = deck.takeTopCard();

        controllerView.displayDrawnCard(drawnCard);

        while (true) {
            String userChoice = controllerView.getIndexChoice(deck);

            try {
                int cardIndex = Integer.parseInt(userChoice.trim());
                deck.insert(drawnCard, cardIndex);
                controllerView.displayValidInsert(drawnCard, cardIndex);
                break;
            }
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                controllerView.displayInvalidIndex(userChoice);
            }
        }

        int currentTurns = gameController.getCurrentPlayerTurnsLeft();
        gameController.setCurrentPlayerTurnsLeft(currentTurns - 1);

        return Optional.empty();
    }
}
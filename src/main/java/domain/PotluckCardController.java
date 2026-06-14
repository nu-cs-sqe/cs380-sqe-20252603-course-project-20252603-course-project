package domain;

import ui.PotluckCardControllerView;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class PotluckCardController implements CardController {
    private final PotluckCardControllerView controllerView;

    public PotluckCardController() {
        this.controllerView = new PotluckCardControllerView();
    }

    PotluckCardController(PotluckCardControllerView controllerView) {
        this.controllerView = controllerView;
    }

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        Game game = gameController.getGame();
        Deck deck = game.getDeck();
        ArrayList<Player> alivePlayers = new ArrayList<Player>(game.getAlivePlayers());
        int alivePlayersCount = game.getAlivePlayerCount();
        int currentIndex = gameController.getCurrentPlayerIndex();

        for (int i = 0; i < alivePlayersCount; i++) {
            Player player = alivePlayers.get((i + currentIndex) % alivePlayersCount);

            int handSize = player.getHandSize();
            if (handSize == 0) {
                controllerView.displayNoCardsAvailable(player);
                continue;
            }

            while (true) {
                ArrayList<Card> hand = player.getHand();
                controllerView.displayPlayerAndCardsInHand(player);
                String userChoice = controllerView.getCardChoice();

                try {
                    int cardIndex = Integer.parseInt(userChoice.trim());
                    Card card = hand.get(cardIndex);

                    player.removeCard(card);
                    deck.insert(card, 0);
                    controllerView.displayValidCard(card);
                    break;
                }
                catch (NumberFormatException | IndexOutOfBoundsException e) {
                    controllerView.displayInvalidIndex(userChoice);
                }

            }
        }


        return Optional.empty();
    }
}
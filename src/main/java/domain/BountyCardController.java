package domain;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BountyCardController implements CardController {
    private static final Random RANDOM = new Random();

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        List<Player> alivePlayers = gameController.getGame().getAlivePlayers();
        for (Player currentPlayer : alivePlayers) {
            if (currentPlayer == user) continue;
            if (currentPlayer.getHandSize() > 0) {
                List<Card> hand = currentPlayer.getHand();
                Card stolen = hand.get(RANDOM.nextInt(hand.size()));
                currentPlayer.removeCard(stolen);
                user.addCard(stolen);
            }
        }
        return Optional.empty();
    }
}
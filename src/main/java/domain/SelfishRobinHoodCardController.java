package domain;

import java.util.List;
import java.util.Optional;

public class SelfishRobinHoodCardController implements CardController {

    @Override
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player initiator,
                                                  Optional<Player> target) {
        int snapshotHandSize = initiator.getHandSize();

        for (Player currentPlayer : gameController.getGame().getAlivePlayers()) {
            boolean stealFromTarget = currentPlayer.getHandSize() > snapshotHandSize;

            if (stealFromTarget) {
                List<Card> targetHand = currentPlayer.getHand();

                int randomIndex = (int) (Math.random() * targetHand.size());
                Card stolenCard = targetHand.get(randomIndex);

                currentPlayer.removeCard(stolenCard);
                initiator.addCard(stolenCard);
            }
        }

        return Optional.empty();
    }
}

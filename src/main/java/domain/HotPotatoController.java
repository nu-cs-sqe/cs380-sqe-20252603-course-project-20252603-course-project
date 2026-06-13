package domain;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class HotPotatoController implements CardController {
    private final Random random;

    HotPotatoController() { this(new Random()); }
    HotPotatoController(Random random) { this.random = random; }

    @Override
    public Optional<List<Card>> executeCardAction(
            GameController gameController,
            Player initiator,
            Optional<Player> target)
    {
        if(initiator.getHandSize() == 0){
            throw new IllegalStateException("Initiator's hand cannot be empty");
        }

        List<Card> hand = initiator.getHand();
        Card card = hand.get(random.nextInt(hand.size()));
        initiator.removeCard(card);

        int nextPlayerIndex = gameController.getNextPlayerIndex();
        Player nextPlayer = gameController.getGame().getAlivePlayers().get(nextPlayerIndex);
        nextPlayer.addCard(card);

        return Optional.empty();
    }
}

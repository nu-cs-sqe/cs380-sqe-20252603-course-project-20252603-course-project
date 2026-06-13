package domain;

import java.util.List;
import java.util.Optional;

public class HotPotatoController implements CardController {
    @Override
    public Optional<List<Card>> executeCardAction(
            GameController gameController,
            Player initiator,
            Optional<Player> target)
    {
        initiator.getHandSize();
        throw new IllegalStateException("Initiator's hand cannot be empty");
    }
}

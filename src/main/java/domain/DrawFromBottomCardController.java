package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrawFromBottomCardController implements CardController {
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target){
        if (gameController.getGame().getDeck().getCards().isEmpty()) {
            throw new IllegalArgumentException("no cards left in deck");
        }
        ArrayList<Card> gameDeckCards = gameController.getGame().getDeck().getCards();
        int deckSize = gameDeckCards.size();

        Card bottomCard = gameDeckCards.get(deckSize - 1);
        gameController.getGame().getDeck().discard(bottomCard);

        user.addCard(bottomCard);

        return Optional.empty();
    }
}

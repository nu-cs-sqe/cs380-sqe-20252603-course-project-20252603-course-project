package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrawTwoController {
    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        if (gameController.getGame().getDeck().count() < 2) {
            String err_msg = "deck needs at least two cards to play draw two card";
            throw new IllegalArgumentException(err_msg);
        }

        Card first = gameController.getGame().getDeck().takeTopCard();
        Card second = gameController.getGame().getDeck().takeTopCard();

        user.addCard(first);
        user.addCard(second);

        return Optional.empty();
    }
}
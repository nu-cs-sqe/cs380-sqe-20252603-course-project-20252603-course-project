package domain;

import ui.ExplodingKittenCardControllerView;

import java.util.List;
import java.util.Optional;

public class ExplodingKittenCardController implements CardController {
    private final ExplodingKittenCardControllerView controllerView;

    public ExplodingKittenCardController() {
        this.controllerView = new ExplodingKittenCardControllerView();
    }

    ExplodingKittenCardController(ExplodingKittenCardControllerView controllerView) {
        this.controllerView = controllerView;
    }

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        Game game = gameController.getGame();
        if (!user.hasDefuse()) {
            user.kill();
            game.removeAlivePlayer(user);
            return Optional.empty();
        }

        for (Card card : user.getHand()) {
            if (card.getType() == CardType.DEFUSE) {
                user.removeCard(card);
                break;
            }
        }

        int position = controllerView.getInsertPosition(game.getDeck().count());
        game.getDeck().insert(new Card(CardType.EXPLODING_KITTEN), position);

        return Optional.empty();
    }
}
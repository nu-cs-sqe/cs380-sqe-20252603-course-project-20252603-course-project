package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReverseCardController implements CardController {

    public Optional<List<Card>> executeCardAction(GameController gameController,
                                                  Player user,
                                                  Optional<Player> target) {
        List<Player> alivePlayers = gameController.getGame().getAlivePlayers();
        int playerCount = alivePlayers.size();

        List<Player> reversedOrder = new ArrayList<>(alivePlayers);
        Collections.reverse(reversedOrder);
        gameController.setPlayerOrder(reversedOrder);

        int newCurrentIndex = mirrorIndex(gameController.getCurrentPlayerIndex(), playerCount);
        gameController.setCurrentPlayerIndex(newCurrentIndex);
        gameController.setNextPlayerIndex((newCurrentIndex + 1) % playerCount);

        return Optional.empty();
    }

    private int mirrorIndex(int currentIndex, int playerCount) {
        return playerCount - 1 - currentIndex;
    }
}
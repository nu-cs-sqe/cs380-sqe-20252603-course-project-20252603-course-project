package domain.action;

import domain.model.Card;
import domain.model.GameState;
import domain.model.Player;

import java.util.List;

public class SeeTheFutureAction implements CardAction {

    public void execute(GameState gameState) {
        List<Card> topCards = gameState.peekTopOfDeck(3);
        Player currentPlayer = gameState.getCurrentPlayer();
        currentPlayer.storePeek(topCards);
    }
}

package domain;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DrawFromBottomCardControllerTests {

    @Test
    public void executeCardAction_DrawFromEmptyDeck_IllegalStateException() {
        Game game = Game.createGame(2);
        GameController gc = new GameController(game);
        Player player1 = game.getTotalPlayers().get(0);

        for (int i = 0; i < 34; i++) {
            game.getDeck().takeTopCard();
        }

        assertEquals(0, game.getDeck().getCards().size());

        DrawFromBottomCardController controller = new DrawFromBottomCardController();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.executeCardAction(gc, player1, Optional.empty());
        });

        assertEquals("no cards left in deck", exception.getMessage());
    }

    @Test
    public void executeCardAction_DrawFromDeckOneCard_CardMovedToPlayer() {
        Game game = Game.createGame(2);
        GameController gc = new GameController(game);
        Player player1 = game.getTotalPlayers().get(0);

        assertEquals(new ArrayList<Card>(), player1.getHand());

        for (int i = 0; i < 33; i++) {
            game.getDeck().takeTopCard();
        }

        assertEquals(1, game.getDeck().getCards().size());
        Card remainingCard = game.getDeck().getCards().get(0);

        DrawFromBottomCardController controller = new DrawFromBottomCardController();

        controller.executeCardAction(gc, player1, Optional.empty());

        ArrayList<Card> expectedHand = new ArrayList<Card>();
        expectedHand.add(remainingCard);
        ArrayList<Card> expectedDeckCards = new ArrayList<Card>();

        assertEquals(expectedDeckCards, game.getDeck().getCards());
        assertEquals(expectedHand, player1.getHand());

    }

    @Test
    public void executeCardAction_DrawFromDeckFifteenCards_CardMovedToPlayer() {
        Game game = Game.createGame(2);
        GameController gc = new GameController(game);
        Player player1 = game.getTotalPlayers().get(0);

        assertEquals(new ArrayList<Card>(), player1.getHand());

        for (int i = 0; i < 19; i++) {
            game.getDeck().takeTopCard();
        }

        ArrayList<Card> gameDeckCards = game.getDeck().getCards();
        assertEquals(15, gameDeckCards.size());
        Card lastCard = gameDeckCards.get(gameDeckCards.size() - 1);

        DrawFromBottomCardController controller = new DrawFromBottomCardController();

        controller.executeCardAction(gc, player1, Optional.empty());

        ArrayList<Card> expectedHand = new ArrayList<Card>();
        expectedHand.add(lastCard);
        ArrayList<Card> expectedDeckCards = gameDeckCards;
        expectedDeckCards.remove(gameDeckCards.size() - 1);

        assertEquals(expectedDeckCards, game.getDeck().getCards());
        assertEquals(expectedHand, player1.getHand());

    }

}

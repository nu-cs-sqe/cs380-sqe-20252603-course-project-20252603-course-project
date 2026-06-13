package domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeeTheFutureCardControllerTests {
    @Test
    public void executeCardAction_FourCardsInDeck_ReturnsListOfTopThree() {
        Game game = new Game(2);
        game.setup();

        while (game.getDeck().count() > 4) {
            game.getDeck().takeTopCard();
        }

        assertEquals(4, game.getDeck().count());

        int initialDeckSize = game.getDeck().count();
        Player user = game.getAlivePlayers().get(0);
        SeeTheFutureCardController controller = new SeeTheFutureCardController();
        GameController gc = new GameController(game);

        Optional<List<Card>> result = controller.executeCardAction(gc, user, Optional.empty());

        assertTrue(result.isPresent());

        List<Card> topThree = result.get();
        assertEquals(3, topThree.size());

        assertEquals(initialDeckSize, game.getDeck().count());

        assertEquals(game.getDeck().getCards().get(0).getType(), topThree.get(0).getType());
        assertEquals(game.getDeck().getCards().get(1).getType(), topThree.get(1).getType());
        assertEquals(game.getDeck().getCards().get(2).getType(), topThree.get(2).getType());
    }

    @Test
    public void executeCardAction_ThreeCardsInDeck_ReturnsListOfTopThree() {
        Game game = new Game(2);
        game.setup();

        while (game.getDeck().count() > 3) {
            game.getDeck().takeTopCard();
        }

        assertEquals(3, game.getDeck().count());

        int initialDeckSize = game.getDeck().count();
        Player user = game.getAlivePlayers().get(0);
        SeeTheFutureCardController controller = new SeeTheFutureCardController();
        GameController gc = new GameController(game);

        Optional<List<Card>> result = controller.executeCardAction(gc, user, Optional.empty());

        assertTrue(result.isPresent());

        List<Card> topThree = result.get();
        assertEquals(3, topThree.size());

        assertEquals(initialDeckSize, game.getDeck().count());

        assertEquals(game.getDeck().getCards().get(0).getType(), topThree.get(0).getType());
        assertEquals(game.getDeck().getCards().get(1).getType(), topThree.get(1).getType());
        assertEquals(game.getDeck().getCards().get(2).getType(), topThree.get(2).getType());
    }

    @Test
    public void executeCardAction_TwoCardsInDeck_ReturnsListOfTopTwo() {
        Game game = new Game(2);
        game.setup();

        while (game.getDeck().count() > 2) {
            game.getDeck().takeTopCard();
        }

        assertEquals(2, game.getDeck().count());

        int initialDeckSize = game.getDeck().count();
        Player user = game.getAlivePlayers().get(0);
        SeeTheFutureCardController controller = new SeeTheFutureCardController();
        GameController gc = new GameController(game);

        Optional<List<Card>> result = controller.executeCardAction(gc, user, Optional.empty());

        assertTrue(result.isPresent());

        List<Card> topTwo = result.get();
        assertEquals(2, topTwo.size());

        assertEquals(initialDeckSize, game.getDeck().count());

        assertEquals(game.getDeck().getCards().get(0).getType(), topTwo.get(0).getType());
        assertEquals(game.getDeck().getCards().get(1).getType(), topTwo.get(1).getType());
    }

    @Test
    public void executeCardAction_OneCardInDeck_ReturnsListOfTopOne() {
        Game game = new Game(2);
        game.setup();

        while (game.getDeck().count() > 1) {
            game.getDeck().takeTopCard();
        }

        assertEquals(1, game.getDeck().count());

        int initialDeckSize = game.getDeck().count();
        Player user = game.getAlivePlayers().get(0);
        SeeTheFutureCardController controller = new SeeTheFutureCardController();
        GameController gc = new GameController(game);

        Optional<List<Card>> result = controller.executeCardAction(gc, user, Optional.empty());

        assertTrue(result.isPresent());

        List<Card> topOne = result.get();
        assertEquals(1, topOne.size());

        assertEquals(initialDeckSize, game.getDeck().count());

        assertEquals(game.getDeck().getCards().get(0).getType(), topOne.get(0).getType());
    }

    @Test
    public void executeCardAction_emptyDeck_ReturnsEmptyList() {
        Game game = new Game(2);
        game.setup();

        while (game.getDeck().count() > 0) {
            game.getDeck().takeTopCard();
        }

        assertEquals(0, game.getDeck().count());

        int initialDeckSize = game.getDeck().count();
        Player user = game.getAlivePlayers().get(0);
        SeeTheFutureCardController controller = new SeeTheFutureCardController();
        GameController gc = new GameController(game);

        Optional<List<Card>> result = controller.executeCardAction(gc, user, Optional.empty());

        assertTrue(result.isPresent());

        List<Card> returnedCards = result.get();

        assertTrue(returnedCards.isEmpty());

        assertEquals(initialDeckSize, game.getDeck().count());
    }
}
package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.SeeTheFutureCardControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeeTheFutureCardControllerTests {
    @Test
    public void executeCardAction_FourCardsInDeck_CallsDisplayWithTopThree() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);
        SeeTheFutureCardControllerView mockView =
                EasyMock.createMock(SeeTheFutureCardControllerView.class);

        Card c1 = Card.createCard(CardType.SKIP);
        Card c2 = Card.createCard(CardType.ATTACK);
        Card c3 = Card.createCard(CardType.SHUFFLE);
        Card c4 = Card.createCard(CardType.NOPE);
        ArrayList<Card> deckCards = new ArrayList<>(List.of(c1, c2, c3, c4));

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockDeck.getCards()).andReturn(deckCards);
        mockView.displayTopCards(List.of(c1, c2, c3));
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockDeck, mockUser, mockView);

        SeeTheFutureCardController controller = new SeeTheFutureCardController(mockView);
        controller.executeCardAction(mockGc, mockUser, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockDeck, mockUser, mockView);
    }

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

    @Test
    public void executeCardAction_TwoCardsInDeck_CallsDisplayWithTopTwo() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);
        SeeTheFutureCardControllerView mockView =
                EasyMock.createMock(SeeTheFutureCardControllerView.class);

        Card c1 = Card.createCard(CardType.SKIP);
        Card c2 = Card.createCard(CardType.ATTACK);
        ArrayList<Card> deckCards = new ArrayList<>(List.of(c1, c2));

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockDeck.getCards()).andReturn(deckCards);
        mockView.displayTopCards(List.of(c1, c2));
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockDeck, mockUser, mockView);

        SeeTheFutureCardController controller = new SeeTheFutureCardController(mockView);
        controller.executeCardAction(mockGc, mockUser, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockDeck, mockUser, mockView);
    }
}
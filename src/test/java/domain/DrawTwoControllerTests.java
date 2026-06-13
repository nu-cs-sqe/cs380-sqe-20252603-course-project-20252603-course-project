package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DrawTwoControllerTests {
    @Test
    public void executeCardAction_emptyDeckEmptyHand_IllegalArgumentException() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockDeck.count()).andReturn(0);

        EasyMock.replay(mockGame, mockController, mockUser, mockDeck);

        DrawTwoController controller = new DrawTwoController();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                controller.executeCardAction(mockController, mockUser, Optional.empty())
        );

        assertEquals("deck needs at least two cards to play draw two card",
                exception.getMessage());

        EasyMock.verify(mockGame, mockController, mockUser, mockDeck);
    }

    @Test
    public void executeCardAction_oneCardDeckEmptyHand_IllegalArgumentException() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockDeck.count()).andReturn(1);

        EasyMock.replay(mockGame, mockController, mockUser, mockDeck);

        DrawTwoController controller = new DrawTwoController();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                controller.executeCardAction(mockController, mockUser, Optional.empty())
        );

        assertEquals("deck needs at least two cards to play draw two card",
                exception.getMessage());

        EasyMock.verify(mockGame, mockController, mockUser, mockDeck);
    }

    @Test
    public void executeCardAction_twoCardDeckEmptyHand_cardsDrawn() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);

        Card skip = new Card(CardType.SKIP);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockDeck.count()).andReturn(2);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(skip);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(explodingKitten);
        mockInitiator.addCard(skip);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(explodingKitten);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockInitiator, mockDeck);

        DrawTwoController controller = new DrawTwoController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockInitiator, Optional.empty());

        assertTrue(result.isEmpty());

        EasyMock.verify(mockGame, mockController, mockInitiator, mockDeck);
    }

    @Test
    public void executeCardAction_duplicateCardsDeckEmptyHand_cardsDrawn() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);

        Card skip1 = new Card(CardType.SKIP);
        Card skip2 = new Card(CardType.SKIP);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockDeck.count()).andReturn(3);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(skip1);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(skip2);

        mockInitiator.addCard(skip1);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(skip2);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockInitiator, mockDeck);

        DrawTwoController controller = new DrawTwoController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockInitiator, Optional.empty());

        assertTrue(result.isEmpty());

        EasyMock.verify(mockGame, mockController, mockInitiator, mockDeck);
    }

    @Test
    public void executeCardAction_twoCardDeckOneCardHand_cardsDrawn() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);

        Card skip = new Card(CardType.SKIP);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockDeck.count()).andReturn(2);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(skip);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(explodingKitten);

        mockInitiator.addCard(skip);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(explodingKitten);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockInitiator, mockDeck);

        DrawTwoController controller = new DrawTwoController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockInitiator, Optional.empty());

        assertTrue(result.isEmpty());

        EasyMock.verify(mockGame, mockController, mockInitiator, mockDeck);
    }

    @Test
    public void executeCardAction_twoCardDeckTwoCardHand_cardsDrawn() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);

        Card skip = new Card(CardType.SKIP);
        Card explodingKitten = new Card(CardType.EXPLODING_KITTEN);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockDeck.count()).andReturn(2);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(skip);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(explodingKitten);

        mockInitiator.addCard(skip);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(explodingKitten);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockInitiator, mockDeck);

        DrawTwoController controller = new DrawTwoController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockInitiator, Optional.empty());

        assertTrue(result.isEmpty());

        EasyMock.verify(mockGame, mockController, mockInitiator, mockDeck);
    }

    @Test
    public void executeCardAction_fullDeckEmptyHand_cardsDrawn() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);

        Card skip = new Card(CardType.SKIP);
        Card defuse = new Card(CardType.DEFUSE);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockDeck.count()).andReturn(28);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(skip);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(defuse);
        mockInitiator.addCard(skip);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(defuse);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockInitiator, mockDeck);

        DrawTwoController controller = new DrawTwoController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockInitiator, Optional.empty());

        assertTrue(result.isEmpty());

        EasyMock.verify(mockGame, mockController, mockInitiator, mockDeck);
    }
}
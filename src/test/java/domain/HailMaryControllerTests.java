package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HailMaryControllerTests {
    @Test
    public void executeCardAction_EmptyHand_ThrowsException() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);

        EasyMock.expect(mockUser.getHandSize()).andReturn(0);

        EasyMock.replay(mockController, mockUser);

        HailMaryController controller = new HailMaryController();
        assertThrows(IllegalStateException.class, () ->
                controller.executeCardAction(mockController, mockUser, Optional.empty()));

        EasyMock.verify(mockController, mockUser);
    }

    @Test
    public void executeCardAction_DeckSmallerThanHand_ThrowsException() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);

        EasyMock.expect(mockUser.getHandSize()).andReturn(2);
        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockDeck.count()).andReturn(1);

        EasyMock.replay(mockController, mockGame, mockDeck, mockUser);

        HailMaryController controller = new HailMaryController();
        assertThrows(IllegalStateException.class, () ->
                controller.executeCardAction(mockController, mockUser, Optional.empty()));

        EasyMock.verify(mockController, mockGame, mockDeck, mockUser);
    }

    @Test
    public void executeCardAction_DeckExactSizeAsHand_ReplacesHand() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);
        Card mockDrawnCard1 = EasyMock.createMock(Card.class);
        Card mockDrawnCard2 = EasyMock.createMock(Card.class);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(mockCard1);
        hand.add(mockCard2);

        EasyMock.expect(mockUser.getHandSize()).andReturn(2);
        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockDeck.count()).andReturn(2);
        EasyMock.expect(mockUser.getHand()).andReturn(hand);
        mockUser.removeCard(mockCard1);
        mockUser.removeCard(mockCard2);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(mockDrawnCard1);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(mockDrawnCard2);
        mockUser.addCard(mockDrawnCard1);
        mockUser.addCard(mockDrawnCard2);

        EasyMock.replay(mockController, mockGame, mockDeck, mockUser,
                mockCard1, mockCard2, mockDrawnCard1, mockDrawnCard2);

        HailMaryController controller = new HailMaryController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockUser, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, mockDeck, mockUser,
                mockCard1, mockCard2, mockDrawnCard1, mockDrawnCard2);
    }

    @Test
    public void executeCardAction_DeckLargerThanHand_ReplacesHand() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);
        Card mockDrawnCard1 = EasyMock.createMock(Card.class);
        Card mockDrawnCard2 = EasyMock.createMock(Card.class);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(mockCard1);
        hand.add(mockCard2);

        EasyMock.expect(mockUser.getHandSize()).andReturn(2);
        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockDeck.count()).andReturn(3);
        EasyMock.expect(mockUser.getHand()).andReturn(hand);
        mockUser.removeCard(mockCard1);
        mockUser.removeCard(mockCard2);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(mockDrawnCard1);
        EasyMock.expect(mockDeck.takeTopCard()).andReturn(mockDrawnCard2);
        mockUser.addCard(mockDrawnCard1);
        mockUser.addCard(mockDrawnCard2);

        EasyMock.replay(mockController, mockGame, mockDeck, mockUser,
                mockCard1, mockCard2, mockDrawnCard1, mockDrawnCard2);

        HailMaryController controller = new HailMaryController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockUser, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, mockDeck, mockUser,
                mockCard1, mockCard2, mockDrawnCard1, mockDrawnCard2);
    }
}

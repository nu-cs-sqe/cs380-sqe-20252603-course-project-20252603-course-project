package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class SwapTopAndBottomCardControllerTests {
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void executeCardAction_InvalidDeckSize_ThrowsException(int invalidDeckSize) {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockDeck.count()).andReturn(invalidDeckSize);

        EasyMock.replay(mockGc, mockGame, mockDeck, mockUser);

        SwapTopAndBottomCardController controller = new SwapTopAndBottomCardController();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controller.executeCardAction(mockGc, mockUser, Optional.empty());
        });

        assertEquals("not enough cards to swap", exception.getMessage());
        EasyMock.verify(mockGc, mockGame, mockDeck, mockUser);
    }

    @Test
    void executeCardAction_TwoCardDeck_SwapsCards() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);

        Card mockTopCard = EasyMock.createMock(Card.class);
        Card mockBottomCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockDeck.count()).andReturn(2);

        EasyMock.expect(mockDeck.takeTopCard()).andReturn(mockTopCard);

        EasyMock.expect(mockDeck.count()).andReturn(1);
        ArrayList<Card> mockCardList = new ArrayList<>();
        mockCardList.add(mockBottomCard);
        EasyMock.expect(mockDeck.getCards()).andReturn(mockCardList);

        mockDeck.discard(mockBottomCard);
        EasyMock.expectLastCall().once();

        mockDeck.insert(mockBottomCard, 0);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockDeck.count()).andReturn(0);
        mockDeck.insert(mockTopCard, 0);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockDeck, mockUser, mockTopCard, mockBottomCard);

        SwapTopAndBottomCardController controller = new SwapTopAndBottomCardController();
        controller.executeCardAction(mockGc, mockUser, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockDeck, mockUser, mockTopCard, mockBottomCard);
    }

    @Test
    void executeCardAction_ThreeCardDeck_SwapsEndsLeavesMiddle() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);

        Card mockTopCard = EasyMock.createMock(Card.class);
        Card mockMiddleCard = EasyMock.createMock(Card.class);
        Card mockBottomCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();

        EasyMock.expect(mockDeck.count()).andReturn(3);

        EasyMock.expect(mockDeck.takeTopCard()).andReturn(mockTopCard);

        EasyMock.expect(mockDeck.count()).andReturn(2);
        ArrayList<Card> mockCardList = new ArrayList<>();
        mockCardList.add(mockMiddleCard);
        mockCardList.add(mockBottomCard);
        EasyMock.expect(mockDeck.getCards()).andReturn(mockCardList);

        mockDeck.discard(mockBottomCard);
        EasyMock.expectLastCall().once();
        mockDeck.insert(mockBottomCard, 0);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockDeck.count()).andReturn(1);
        mockDeck.insert(mockTopCard, 1);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockDeck, mockUser,
                mockTopCard, mockMiddleCard, mockBottomCard);

        SwapTopAndBottomCardController controller = new SwapTopAndBottomCardController();
        controller.executeCardAction(mockGc, mockUser, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockDeck, mockUser, mockTopCard, mockBottomCard);
    }

    @Test
    void executeCardAction_FiveCardDeck_SwapsEndsLeavesMiddles() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Player mockUser = EasyMock.createMock(Player.class);

        Card mockTopCard = EasyMock.createMock(Card.class);
        Card mockMiddle3 = EasyMock.createMock(Card.class);
        Card mockMiddle2 = EasyMock.createMock(Card.class);
        Card mockMiddle1 = EasyMock.createMock(Card.class);
        Card mockBottomCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();

        EasyMock.expect(mockDeck.count()).andReturn(5);

        EasyMock.expect(mockDeck.takeTopCard()).andReturn(mockTopCard);

        EasyMock.expect(mockDeck.count()).andReturn(4);
        ArrayList<Card> mockCardList = new ArrayList<>();
        mockCardList.add(mockMiddle3);
        mockCardList.add(mockMiddle2);
        mockCardList.add(mockMiddle1);
        mockCardList.add(mockBottomCard);
        EasyMock.expect(mockDeck.getCards()).andReturn(mockCardList);

        mockDeck.discard(mockBottomCard);
        EasyMock.expectLastCall().once();
        mockDeck.insert(mockBottomCard, 0);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockDeck.count()).andReturn(3);
        mockDeck.insert(mockTopCard, 3);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockDeck, mockUser, mockTopCard,
                mockMiddle3, mockMiddle2, mockMiddle1, mockBottomCard);

        SwapTopAndBottomCardController controller = new SwapTopAndBottomCardController();
        controller.executeCardAction(mockGc, mockUser, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockDeck, mockUser, mockTopCard,
                mockMiddle3, mockMiddle2, mockMiddle1, mockBottomCard);
    }
}

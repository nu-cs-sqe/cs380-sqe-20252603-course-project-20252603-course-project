package domain;

import org.junit.jupiter.api.Test;
import ui.BuryCardControllerView;

import java.util.Optional;

import static org.easymock.EasyMock.*;

public class BuryCardControllerTests {

    @Test
    public void testExecuteCardAction_InvalidInputsAndInsertInMiddle() {
        BuryCardControllerView mockView = createStrictMock(BuryCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Deck mockDeck = createMock(Deck.class);
        Player mockInitiator = createMock(Player.class);

        Card mockDrawnCard = createMock(Card.class);

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGame.getDeck()).andReturn(mockDeck);

        expect(mockDeck.takeTopCard()).andReturn(mockDrawnCard);
        mockView.displayDrawnCard(mockDrawnCard);

        expect(mockView.getIndexChoice(mockDeck)).andReturn("One");
        mockView.displayInvalidIndex("One");

        expect(mockView.getIndexChoice(mockDeck)).andReturn("-1");
        mockDeck.insert(mockDrawnCard, -1);
        expectLastCall().andThrow(new IndexOutOfBoundsException());
        mockView.displayInvalidIndex("-1");

        expect(mockView.getIndexChoice(mockDeck)).andReturn("3");
        mockDeck.insert(mockDrawnCard, 3);
        expectLastCall().andThrow(new IndexOutOfBoundsException());
        mockView.displayInvalidIndex("3");

        expect(mockView.getIndexChoice(mockDeck)).andReturn("1");
        mockDeck.insert(mockDrawnCard, 1);
        expectLastCall().once();
        mockView.displayValidInsert(mockDrawnCard, 1);

        replay(mockView, mockGameController, mockGame,
                mockDeck, mockInitiator, mockDrawnCard);

        BuryCardController controller = new BuryCardController(mockView);
        controller.executeCardAction(mockGameController,
                mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame,
                mockDeck, mockInitiator, mockDrawnCard);
    }

    @Test
    public void testExecuteCardAction_InsertIndexIsTopOfDeck() {
        BuryCardControllerView mockView = createStrictMock(BuryCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Deck mockDeck = createMock(Deck.class);
        Player mockInitiator = createMock(Player.class);

        Card mockDrawnCard = createMock(Card.class);

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGame.getDeck()).andReturn(mockDeck);

        expect(mockDeck.takeTopCard()).andReturn(mockDrawnCard);
        mockView.displayDrawnCard(mockDrawnCard);

        expect(mockView.getIndexChoice(mockDeck)).andReturn("0");

        mockDeck.insert(mockDrawnCard, 0);
        expectLastCall().once();

        mockView.displayValidInsert(mockDrawnCard, 0);

        replay(mockView, mockGameController, mockGame,
                mockDeck, mockInitiator, mockDrawnCard);

        BuryCardController controller = new BuryCardController(mockView);
        controller.executeCardAction(mockGameController,
                mockInitiator,
                Optional.empty());

        verify(mockView, mockGameController, mockGame,
                mockDeck, mockInitiator, mockDrawnCard);
    }
}
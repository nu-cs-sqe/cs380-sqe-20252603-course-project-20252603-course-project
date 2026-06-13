package domain;

import org.junit.jupiter.api.Test;
import ui.BuryCardControllerView;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuryCardControllerTests {

    @Test
    public void testExecuteCardAction_InvalidInputsAndInsertInMiddle() {
        BuryCardControllerView mockView = createStrictMock(BuryCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Deck mockDeck = createMock(Deck.class);
        Player mockInitiator = createMock(Player.class);

        Card expectedDrawnCard = Card.createCard(CardType.CAT_CARD_1);

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGame.getDeck()).andReturn(mockDeck);

        expect(mockDeck.takeTopCard()).andReturn(expectedDrawnCard);
        mockView.displayDrawnCard(expectedDrawnCard);

        expect(mockView.getIndexChoice(mockDeck)).andReturn("One");
        mockView.displayInvalidIndex("One");

        expect(mockView.getIndexChoice(mockDeck)).andReturn("-1");
        mockDeck.insert(expectedDrawnCard, -1);
        expectLastCall().andThrow(new IndexOutOfBoundsException());
        mockView.displayInvalidIndex("-1");

        expect(mockView.getIndexChoice(mockDeck)).andReturn("3");
        mockDeck.insert(expectedDrawnCard, 3);
        expectLastCall().andThrow(new IndexOutOfBoundsException());
        mockView.displayInvalidIndex("3");

        expect(mockView.getIndexChoice(mockDeck)).andReturn("1");
        mockDeck.insert(expectedDrawnCard, 1);
        expectLastCall().once();
        mockView.displayValidInsert(expectedDrawnCard, 1);

        replay(mockView, mockGameController, mockGame,
                mockDeck, mockInitiator);

        BuryCardController controller = new BuryCardController(mockView);
        controller.executeCardAction(mockGameController,
                mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame,
                mockDeck, mockInitiator);
    }
}
package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShuffleCardControllerTests {
    @Test
    public void executeCardAction_OnEmptyDeck(){
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck deck = EasyMock.createMock(Deck.class);
        Player initiator = EasyMock.createMock(Player.class);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getDeck()).andReturn(deck);
        deck.shuffle();

        EasyMock.replay(mockController, mockGame, deck, initiator);

        ShuffleCardController controller = new ShuffleCardController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, initiator, Optional.empty()
        );

        assertTrue(result.isEmpty());

        EasyMock.verify(mockController, mockGame, deck, initiator);
    }
}

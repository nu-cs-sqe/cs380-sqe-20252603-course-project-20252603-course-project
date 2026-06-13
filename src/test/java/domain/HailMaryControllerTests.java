package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

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
}

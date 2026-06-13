package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HotPotatoControllerTests {
    @Test
    public void executeCardAction_EmptyHand_ThrowsException() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);

        EasyMock.expect(mockUser.getHandSize()).andReturn(0);

        EasyMock.replay(mockController, mockUser);

        HotPotatoController controller = new HotPotatoController();
        assertThrows(IllegalStateException.class, () ->
                controller.executeCardAction(mockController, mockUser, Optional.empty()));

        EasyMock.verify(mockController, mockUser);
    }

    @Test
    public void executeCardAction_OneCardHand_PassesCardToNextPlayer() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player mockNextPlayer = EasyMock.createMock(Player.class);
        Card mockCard = EasyMock.createMock(Card.class);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(mockCard);
        ArrayList<Player> players = new ArrayList<>();
        players.add(mockNextPlayer);

        EasyMock.expect(mockUser.getHandSize()).andReturn(1);
        EasyMock.expect(mockUser.getHand()).andReturn(hand);
        mockUser.removeCard(mockCard);
        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockController.getNextPlayerIndex()).andReturn(0);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(players);
        mockNextPlayer.addCard(mockCard);

        EasyMock.replay(mockController, mockGame, mockUser, mockNextPlayer, mockCard);

        HotPotatoController controller = new HotPotatoController();
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockUser, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, mockUser, mockNextPlayer, mockCard);
    }

    @Test
    public void executeCardAction_MultiCardHand_PassesCardToNextPlayer() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player mockNextPlayer = EasyMock.createMock(Player.class);
        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(mockCard1);
        hand.add(mockCard2);
        ArrayList<Player> players = new ArrayList<>();
        players.add(mockNextPlayer);

        EasyMock.expect(mockUser.getHandSize()).andReturn(2);
        EasyMock.expect(mockUser.getHand()).andReturn(hand);
        mockUser.removeCard(mockCard2);
        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockController.getNextPlayerIndex()).andReturn(0);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(players);
        mockNextPlayer.addCard(mockCard2);

        EasyMock.replay(mockController, mockGame, mockUser, mockNextPlayer, mockCard1, mockCard2);

        HotPotatoController controller = new HotPotatoController(new Random(0));
        Optional<List<Card>> result = controller.executeCardAction(
                mockController, mockUser, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, mockUser, mockNextPlayer, mockCard1, mockCard2);
    }
}

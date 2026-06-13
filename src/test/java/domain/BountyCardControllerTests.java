package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BountyCardControllerTests {

    @Test
    public void executeCardAction_OneOtherPlayerZeroCards_ReturnsEmpty() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other = EasyMock.createMock(Player.class);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other));
        EasyMock.expect(other.getHandSize()).andReturn(0);

        EasyMock.replay(mockController, mockGame, user, other);

        BountyCardController controller = new BountyCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other);
    }

    @Test
    public void executeCardAction_OneOtherPlayerOneCard_StealsCard() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other = EasyMock.createMock(Player.class);
        Card card = new Card(CardType.CAT_CARD_1);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other));
        EasyMock.expect(other.getHandSize()).andReturn(1);
        EasyMock.expect(other.getHand()).andReturn(new ArrayList<>(List.of(card)));
        other.removeCard(card);
        user.addCard(card);

        EasyMock.replay(mockController, mockGame, user, other);

        BountyCardController controller = new BountyCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other);
    }

    @Test
    public void executeCardAction_OneOtherPlayerManyCards_StealsOneCard() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other = EasyMock.createMock(Player.class);

        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            hand.add(new Card(CardType.CAT_CARD_1));
        }

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other));
        EasyMock.expect(other.getHandSize()).andReturn(4);
        EasyMock.expect(other.getHand()).andReturn(hand);
        other.removeCard(EasyMock.isA(Card.class));
        user.addCard(EasyMock.isA(Card.class));

        EasyMock.replay(mockController, mockGame, user, other);

        BountyCardController controller = new BountyCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other);
    }

    @Test
    public void executeCardAction_MultipleOtherPlayersAllZeroCards_ReturnsEmpty() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other1 = EasyMock.createMock(Player.class);
        Player other2 = EasyMock.createMock(Player.class);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other1, other2));
        EasyMock.expect(other1.getHandSize()).andReturn(0);
        EasyMock.expect(other2.getHandSize()).andReturn(0);

        EasyMock.replay(mockController, mockGame, user, other1, other2);

        BountyCardController controller = new BountyCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other1, other2);
    }

    @Test
    public void executeCardAction_MultipleOtherPlayersSomeZeroSomeOneCard_StealsFromNonEmpty() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other1 = EasyMock.createMock(Player.class);
        Player other2 = EasyMock.createMock(Player.class);
        Card card = new Card(CardType.CAT_CARD_1);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other1, other2));
        EasyMock.expect(other1.getHandSize()).andReturn(0);
        EasyMock.expect(other2.getHandSize()).andReturn(1);
        EasyMock.expect(other2.getHand()).andReturn(new ArrayList<>(List.of(card)));
        other2.removeCard(card);
        user.addCard(card);

        EasyMock.replay(mockController, mockGame, user, other1, other2);

        BountyCardController controller = new BountyCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other1, other2);
    }

    @Test
    public void executeCardAction_MultipleOtherPlayersAllWithCards_StealsFromEach() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other1 = EasyMock.createMock(Player.class);
        Player other2 = EasyMock.createMock(Player.class);

        ArrayList<Card> hand1 = new ArrayList<>();
        hand1.add(new Card(CardType.CAT_CARD_1));
        hand1.add(new Card(CardType.CAT_CARD_1));

        ArrayList<Card> hand2 = new ArrayList<>();
        hand2.add(new Card(CardType.CAT_CARD_2));
        hand2.add(new Card(CardType.CAT_CARD_2));
        hand2.add(new Card(CardType.CAT_CARD_2));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other1, other2));
        EasyMock.expect(other1.getHandSize()).andReturn(2);
        EasyMock.expect(other1.getHand()).andReturn(hand1);
        other1.removeCard(EasyMock.isA(Card.class));
        user.addCard(EasyMock.isA(Card.class));
        EasyMock.expect(other2.getHandSize()).andReturn(3);
        EasyMock.expect(other2.getHand()).andReturn(hand2);
        other2.removeCard(EasyMock.isA(Card.class));
        user.addCard(EasyMock.isA(Card.class));

        EasyMock.replay(mockController, mockGame, user, other1, other2);

        BountyCardController controller = new BountyCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other1, other2);
    }
}

package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BountyCardControllerTests {

    @Test
    public void executeCardAction_OneOtherPlayerZeroCards_ReturnsEmpty() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other = EasyMock.createMock(Player.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other));
        EasyMock.expect(other.getHandSize()).andReturn(0);

        EasyMock.replay(mockController, mockGame, user, other, mockRandom);

        BountyCardController controller = new BountyCardController(mockRandom);
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other, mockRandom);
    }

    @Test
    public void executeCardAction_OneOtherPlayerOneCard_StealsCard() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other = EasyMock.createMock(Player.class);
        Random mockRandom = EasyMock.createMock(Random.class);
        Card card = new Card(CardType.TACOCAT);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other));
        EasyMock.expect(other.getHandSize()).andReturn(1);
        EasyMock.expect(other.getHand()).andReturn(new ArrayList<>(List.of(card)));

        EasyMock.expect(mockRandom.nextInt(1)).andReturn(0).once();

        other.removeCard(card);
        user.addCard(card);

        EasyMock.replay(mockController, mockGame, user, other, mockRandom);

        BountyCardController controller = new BountyCardController(mockRandom);
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other, mockRandom);
    }

    @Test
    public void executeCardAction_OneOtherPlayerManyCards_StealsOneCard() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other = EasyMock.createMock(Player.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            hand.add(new Card(CardType.TACOCAT));
        }

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other));
        EasyMock.expect(other.getHandSize()).andReturn(4);
        EasyMock.expect(other.getHand()).andReturn(hand);

        EasyMock.expect(mockRandom.nextInt(4)).andReturn(2).once();

        Card specificCardStolen = hand.get(2);
        other.removeCard(specificCardStolen);
        user.addCard(specificCardStolen);

        EasyMock.replay(mockController, mockGame, user, other, mockRandom);

        BountyCardController controller = new BountyCardController(mockRandom);
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other, mockRandom);
    }

    @Test
    public void executeCardAction_MultipleOtherPlayersAllZeroCards_ReturnsEmpty() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other1 = EasyMock.createMock(Player.class);
        Player other2 = EasyMock.createMock(Player.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other1, other2));
        EasyMock.expect(other1.getHandSize()).andReturn(0);
        EasyMock.expect(other2.getHandSize()).andReturn(0);

        EasyMock.replay(mockController, mockGame, user, other1, other2, mockRandom);

        BountyCardController controller = new BountyCardController(mockRandom);
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other1, other2, mockRandom);
    }

    @Test
    public void executeCardAction_MultipleOtherPlayersSomeZeroSomeOneCard_StealsFromNonEmpty() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other1 = EasyMock.createMock(Player.class);
        Player other2 = EasyMock.createMock(Player.class);
        Random mockRandom = EasyMock.createMock(Random.class);
        Card card = new Card(CardType.TACOCAT);

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other1, other2));
        EasyMock.expect(other1.getHandSize()).andReturn(0);
        EasyMock.expect(other2.getHandSize()).andReturn(1);
        EasyMock.expect(other2.getHand()).andReturn(new ArrayList<>(List.of(card)));

        EasyMock.expect(mockRandom.nextInt(1)).andReturn(0).once();

        other2.removeCard(card);
        user.addCard(card);

        EasyMock.replay(mockController, mockGame, user, other1, other2, mockRandom);

        BountyCardController controller = new BountyCardController(mockRandom);
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other1, other2, mockRandom);
    }

    @Test
    public void executeCardAction_MultipleOtherPlayersAllWithCards_StealsFromEach() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player user = EasyMock.createMock(Player.class);
        Player other1 = EasyMock.createMock(Player.class);
        Player other2 = EasyMock.createMock(Player.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        ArrayList<Card> hand1 = new ArrayList<>();
        hand1.add(new Card(CardType.TACOCAT));
        hand1.add(new Card(CardType.TACOCAT));

        ArrayList<Card> hand2 = new ArrayList<>();
        hand2.add(new Card(CardType.CATERMELLON));
        hand2.add(new Card(CardType.CATERMELLON));
        hand2.add(new Card(CardType.CATERMELLON));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(user, other1, other2));

        EasyMock.expect(other1.getHandSize()).andReturn(2);
        EasyMock.expect(other1.getHand()).andReturn(hand1);
        EasyMock.expect(mockRandom.nextInt(2)).andReturn(1).once();
        Card stolenFromOther1 = hand1.get(1);
        other1.removeCard(stolenFromOther1);
        user.addCard(stolenFromOther1);

        EasyMock.expect(other2.getHandSize()).andReturn(3);
        EasyMock.expect(other2.getHand()).andReturn(hand2);
        EasyMock.expect(mockRandom.nextInt(3)).andReturn(0).once();
        Card stolenFromOther2 = hand2.get(0);
        other2.removeCard(stolenFromOther2);
        user.addCard(stolenFromOther2);

        EasyMock.replay(mockController, mockGame, user, other1, other2, mockRandom);

        BountyCardController controller = new BountyCardController(mockRandom);
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, user, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockGame, user, other1, other2, mockRandom);
    }
}
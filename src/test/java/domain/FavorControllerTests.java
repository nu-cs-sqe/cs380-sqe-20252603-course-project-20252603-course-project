package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.FavorCardControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FavorControllerTests {

    @Test
    void executeCardAction_targetOneCard_cardGiven() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        FavorCardControllerView mockInput = EasyMock.createMock(FavorCardControllerView.class);

        Card attack = new Card(CardType.ATTACK);
        ArrayList<Card> targetHand = new ArrayList<>(List.of(attack));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(targetHand).anyTimes();
        EasyMock.expect(mockInput.getCardToGive(targetHand)).andReturn(attack);
        mockTarget.removeCard(attack);
        EasyMock.expectLastCall().once();
        mockUser.addCard(attack);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockUser, mockTarget, mockInput);

        FavorCardController controller = new FavorCardController(mockInput);
        controller.executeCardAction(mockController, mockUser, Optional.of(mockTarget));

        EasyMock.verify(mockGame, mockController, mockUser, mockTarget, mockInput);
    }

    @Test
    void executeCardAction_targetTwoCards_cardGiven() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        FavorCardControllerView mockInput = EasyMock.createMock(FavorCardControllerView.class);

        Card attack = new Card(CardType.ATTACK);
        Card skip = new Card(CardType.SKIP);
        ArrayList<Card> targetHand = new ArrayList<>(List.of(attack, skip));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(targetHand).anyTimes();
        EasyMock.expect(mockInput.getCardToGive(targetHand)).andReturn(attack);
        mockTarget.removeCard(attack);
        EasyMock.expectLastCall().once();
        mockUser.addCard(attack);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockUser, mockTarget, mockInput);

        FavorCardController controller = new FavorCardController(mockInput);
        controller.executeCardAction(mockController, mockUser, Optional.of(mockTarget));

        EasyMock.verify(mockGame, mockController, mockUser, mockTarget, mockInput);
    }

    @Test
    void executeCardAction_targetDuplicateCards_cardGiven() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        FavorCardControllerView mockInput = EasyMock.createMock(FavorCardControllerView.class);

        Card attack1 = new Card(CardType.ATTACK);
        Card attack2 = new Card(CardType.ATTACK);
        ArrayList<Card> targetHand = new ArrayList<>(List.of(attack1, attack2));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(targetHand).anyTimes();
        EasyMock.expect(mockInput.getCardToGive(targetHand)).andReturn(attack1);
        mockTarget.removeCard(attack1);
        EasyMock.expectLastCall().once();
        mockUser.addCard(attack1);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockUser, mockTarget, mockInput);

        FavorCardController controller = new FavorCardController(mockInput);
        controller.executeCardAction(mockController, mockUser, Optional.of(mockTarget));

        EasyMock.verify(mockGame, mockController, mockUser, mockTarget, mockInput);
    }

    @Test
    void executeCardAction_userDuplicateCards_cardGiven() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        FavorCardControllerView mockInput = EasyMock.createMock(FavorCardControllerView.class);

        Card userAttack = new Card(CardType.ATTACK);
        Card targetAttack = new Card(CardType.ATTACK);
        ArrayList<Card> userHand = new ArrayList<>(List.of(userAttack));
        ArrayList<Card> targetHand = new ArrayList<>(List.of(targetAttack));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockUser.getHand()).andReturn(userHand).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(targetHand).anyTimes();
        EasyMock.expect(mockInput.getCardToGive(targetHand)).andReturn(targetAttack);
        mockTarget.removeCard(targetAttack);
        EasyMock.expectLastCall().once();
        mockUser.addCard(targetAttack);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockUser, mockTarget, mockInput);

        FavorCardController controller = new FavorCardController(mockInput);
        controller.executeCardAction(mockController, mockUser, Optional.of(mockTarget));

        EasyMock.verify(mockGame, mockController, mockUser, mockTarget, mockInput);
    }

    @Test
    void executeCardAction_targetFirstCard_cardGiven() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        FavorCardControllerView mockInput = EasyMock.createMock(FavorCardControllerView.class);

        Card skip = new Card(CardType.SKIP);
        Card attack = new Card(CardType.ATTACK);
        Card defuse = new Card(CardType.DEFUSE);
        ArrayList<Card> targetHand = new ArrayList<>(List.of(skip, attack, defuse));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(targetHand).anyTimes();
        EasyMock.expect(mockInput.getCardToGive(targetHand)).andReturn(skip);
        mockTarget.removeCard(skip);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(skip);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockInitiator, mockTarget, mockInput);

        FavorCardController controller = new FavorCardController(mockInput);
        controller.executeCardAction(mockController, mockInitiator, Optional.of(mockTarget));

        EasyMock.verify(mockGame, mockController, mockInitiator, mockTarget, mockInput);
    }

    @Test
    void executeCardAction_targetLastCard_cardGiven() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        FavorCardControllerView mockInput = EasyMock.createMock(FavorCardControllerView.class);

        Card skip = new Card(CardType.SKIP);
        Card attack = new Card(CardType.ATTACK);
        Card defuse = new Card(CardType.DEFUSE);
        ArrayList<Card> targetHand = new ArrayList<>(List.of(skip, attack, defuse));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(targetHand).anyTimes();
        EasyMock.expect(mockInput.getCardToGive(targetHand)).andReturn(defuse);
        mockTarget.removeCard(defuse);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(defuse);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockInitiator, mockTarget, mockInput);

        FavorCardController controller = new FavorCardController(mockInput);
        controller.executeCardAction(mockController, mockInitiator, Optional.of(mockTarget));

        EasyMock.verify(mockGame, mockController, mockInitiator, mockTarget, mockInput);
    }

    @Test
    void executeCardAction_userTwoCards_cardGiven() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        FavorCardControllerView mockInput = EasyMock.createMock(FavorCardControllerView.class);

        Card defuse = new Card(CardType.DEFUSE);
        Card skip = new Card(CardType.SKIP);
        Card attack = new Card(CardType.ATTACK);
        ArrayList<Card> userHand = new ArrayList<>(List.of(defuse, skip));
        ArrayList<Card> targetHand = new ArrayList<>(List.of(attack));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockInitiator.getHand()).andReturn(userHand).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(targetHand).anyTimes();
        EasyMock.expect(mockInput.getCardToGive(targetHand)).andReturn(attack);
        mockTarget.removeCard(attack);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(attack);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGame, mockController, mockInitiator, mockTarget, mockInput);

        FavorCardController controller = new FavorCardController(mockInput);
        controller.executeCardAction(mockController, mockInitiator, Optional.of(mockTarget));

        EasyMock.verify(mockGame, mockController, mockInitiator, mockTarget, mockInput);
    }

    @Test
    void executeCardAction_cardNotInTarget_illegalArgumentException() {
        Game mockGame = EasyMock.createMock(Game.class);
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        FavorCardControllerView mockInput = EasyMock.createMock(FavorCardControllerView.class);

        Card attack = new Card(CardType.ATTACK);
        Card defuse = new Card(CardType.DEFUSE);
        ArrayList<Card> targetHand = new ArrayList<>(List.of(attack));

        EasyMock.expect(mockController.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(targetHand).anyTimes();
        EasyMock.expect(mockInput.getCardToGive(targetHand)).andReturn(defuse);
        mockTarget.removeCard(defuse);
        IllegalArgumentException exception_msg =
                new IllegalArgumentException("card to remove not in hand");
        EasyMock.expectLastCall().andThrow(exception_msg);

        EasyMock.replay(mockGame, mockController, mockInitiator, mockTarget, mockInput);

        FavorCardController controller = new FavorCardController(mockInput);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.executeCardAction(mockController, mockInitiator, Optional.of(mockTarget));
        });

        assertEquals("card to remove not in hand", exception.getMessage());

        EasyMock.verify(mockGame, mockController, mockInitiator, mockTarget, mockInput);
    }
}
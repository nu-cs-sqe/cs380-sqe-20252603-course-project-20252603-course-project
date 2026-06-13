package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelfishRobinHoodCardControllerTests {
    @Test
    void executeCardAction_TargetHasFewerCards_NoSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)).anyTimes();

        EasyMock.expect(mockInitiator.getHandSize()).andReturn(3).anyTimes();
        EasyMock.expect(mockTarget.getHandSize()).andReturn(2).anyTimes();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget);

        SelfishRobinHoodCardController controller = new SelfishRobinHoodCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget);
    }

    @Test
    void executeCardAction_TargetHasEqualCards_NoSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)).anyTimes();

        EasyMock.expect(mockInitiator.getHandSize()).andReturn(3).anyTimes();
        EasyMock.expect(mockTarget.getHandSize()).andReturn(3).anyTimes();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget);

        SelfishRobinHoodCardController controller = new SelfishRobinHoodCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget);
    }

    @Test
    void executeCardAction_TargetHasOneMoreCard_StealsOneCard() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        Card mockStolenCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(List.of(
                mockInitiator, mockTarget)).anyTimes();

        EasyMock.expect(mockInitiator.getHandSize()).andReturn(3).anyTimes();
        EasyMock.expect(mockTarget.getHandSize()).andReturn(4).anyTimes();

        EasyMock.expect(mockTarget.getHand()).andReturn(
                new ArrayList<>(List.of(mockStolenCard))).anyTimes();

        mockTarget.removeCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        mockInitiator.addCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget, mockStolenCard);

        SelfishRobinHoodCardController controller = new SelfishRobinHoodCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget, mockStolenCard);
    }

    @Test
    void executeCardAction_TargetHasManyMoreCards_StealsOneCard() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        Card mockStolenCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)).anyTimes();

        EasyMock.expect(mockInitiator.getHandSize()).andReturn(3).anyTimes();
        EasyMock.expect(mockTarget.getHandSize()).andReturn(7).anyTimes();

        EasyMock.expect(mockTarget.getHand()).andReturn(
                new ArrayList<>(List.of(mockStolenCard))).anyTimes();

        mockTarget.removeCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        mockInitiator.addCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget, mockStolenCard);

        SelfishRobinHoodCardController controller = new SelfishRobinHoodCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget, mockStolenCard);
    }

    @Test
    void executeCardAction_MultiPlayerInitiatorRichest_NoSteals() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, p1, p2, p3, p4)).anyTimes();

        EasyMock.expect(mockInitiator.getHandSize()).andReturn(5).anyTimes();

        EasyMock.expect(p1.getHandSize()).andReturn(2).anyTimes();
        EasyMock.expect(p2.getHandSize()).andReturn(2).anyTimes();
        EasyMock.expect(p3.getHandSize()).andReturn(2).anyTimes();
        EasyMock.expect(p4.getHandSize()).andReturn(2).anyTimes();

        EasyMock.replay(mockGc, mockGame, mockInitiator, p1, p2, p3, p4);

        SelfishRobinHoodCardController controller = new SelfishRobinHoodCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, p1, p2, p3, p4);
    }

    @Test
    void executeCardAction_MultiPlayerInitiatorPoorest_MaximumSteals() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);
        Card mockStolenCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        List<Player> board = List.of(mockInitiator, p1, p2, p3, p4);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(board).anyTimes();

        EasyMock.expect(mockInitiator.getHandSize()).andReturn(1).anyTimes();

        for (int i = 1; i <= 4; i++) {
            Player currentMock = board.get(i);
            EasyMock.expect(currentMock.getHandSize()).andReturn(5).anyTimes();
            EasyMock.expect(currentMock.getHand()).andReturn(
                    new ArrayList<>(List.of(mockStolenCard))).anyTimes();

            currentMock.removeCard(mockStolenCard);
            EasyMock.expectLastCall().once();

            mockInitiator.addCard(mockStolenCard);
            EasyMock.expectLastCall().once();
        }

        EasyMock.replay(mockGc, mockGame, mockInitiator, p1, p2, p3, p4, mockStolenCard);

        SelfishRobinHoodCardController controller = new SelfishRobinHoodCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, p1, p2, p3, p4, mockStolenCard);
    }

    @Test
    void executeCardAction_MultiPlayerMixedWealth_PartialSteals() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockP2Poorer = EasyMock.createMock(Player.class);
        Player mockP3Equal = EasyMock.createMock(Player.class);
        Player mockP4Richer = EasyMock.createMock(Player.class);
        Player mockP5MuchRicher = EasyMock.createMock(Player.class);
        Card mockStolenCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockP2Poorer, mockP3Equal, mockP4Richer,
                        mockP5MuchRicher)).anyTimes();

        EasyMock.expect(mockInitiator.getHandSize()).andReturn(3).anyTimes();

        EasyMock.expect(mockP2Poorer.getHandSize()).andReturn(1).anyTimes();
        EasyMock.expect(mockP3Equal.getHandSize()).andReturn(3).anyTimes();

        EasyMock.expect(mockP4Richer.getHandSize()).andReturn(4).anyTimes();
        EasyMock.expect(mockP4Richer.getHand()).andReturn(
                new ArrayList<>(List.of(mockStolenCard))).anyTimes();
        mockP4Richer.removeCard(mockStolenCard);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockP5MuchRicher.getHandSize()).andReturn(7).anyTimes();
        EasyMock.expect(mockP5MuchRicher.getHand()).andReturn(
                new ArrayList<>(List.of(mockStolenCard))).anyTimes();
        mockP5MuchRicher.removeCard(mockStolenCard);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator,
                mockP2Poorer, mockP3Equal, mockP4Richer, mockP5MuchRicher, mockStolenCard);

        SelfishRobinHoodCardController controller = new SelfishRobinHoodCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator,
                mockP2Poorer, mockP3Equal, mockP4Richer, mockP5MuchRicher, mockStolenCard);
    }

    @Test
    void executeCardAction_MultiPlayerSnapshotBoundary_StealsFromBoth() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        Player mockPlayer3 = EasyMock.createMock(Player.class);
        Card mockStolenCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockPlayer2, mockPlayer3)).anyTimes();

        EasyMock.expect(mockInitiator.getHandSize()).andReturn(3).anyTimes();

        EasyMock.expect(mockPlayer2.getHandSize()).andReturn(4).anyTimes();
        EasyMock.expect(mockPlayer2.getHand()).andReturn(
                new ArrayList<>(List.of(mockStolenCard))).anyTimes();
        mockPlayer2.removeCard(mockStolenCard);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockPlayer3.getHandSize()).andReturn(4).anyTimes();
        EasyMock.expect(mockPlayer3.getHand()).andReturn(
                new ArrayList<>(List.of(mockStolenCard))).anyTimes();
        mockPlayer3.removeCard(mockStolenCard);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockPlayer2, mockPlayer3, mockStolenCard);

        SelfishRobinHoodCardController controller = new SelfishRobinHoodCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, mockPlayer2, mockPlayer3, mockStolenCard);
    }
}

package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.CatCardControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CatCardControllerTests {

    @Test
    void executeCardAction_targetMissing_noSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.replay(mockGc, mockInitiator, mockView, mockRandom);

        CatCardController controller = new CatCardController(2, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.empty()
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockInitiator, mockView, mockRandom);
    }

    @Test
    void executeCardAction_selfTargeting_noSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.replay(mockGc, mockInitiator, mockView, mockRandom);

        CatCardController controller = new CatCardController(2, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockInitiator)
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockInitiator, mockView, mockRandom);
    }

    @Test
    void executeCardAction_targetNotAlive_noSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockDeadTarget = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator)
        ).anyTimes();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockDeadTarget, mockView, mockRandom);

        CatCardController controller = new CatCardController(2, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockDeadTarget)
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockGame, mockInitiator, mockDeadTarget, mockView, mockRandom);
    }

    @Test
    void executeCardAction_twoCardsTargetEmptyHand_noSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)
        ).anyTimes();
        EasyMock.expect(mockTarget.getHandSize()).andReturn(0).anyTimes();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget, mockView, mockRandom);

        CatCardController controller = new CatCardController(2, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockTarget)
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget, mockView, mockRandom);
    }

    @Test
    void executeCardAction_twoCardsTargetOneCard_stealsOnlyCard() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        Card mockStolenCard = EasyMock.createMock(Card.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)
        ).anyTimes();
        EasyMock.expect(mockTarget.getHandSize()).andReturn(1).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(
                new ArrayList<>(List.of(mockStolenCard))
        ).anyTimes();

        EasyMock.expect(mockRandom.nextDouble()).andReturn(0.0).once();

        mockTarget.removeCard(mockStolenCard);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget,
                mockStolenCard, mockView, mockRandom);

        CatCardController controller = new CatCardController(2, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockTarget)
        );

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(mockStolenCard, result.get().get(0));
        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget,
                mockStolenCard, mockView, mockRandom);
    }

    @Test
    void executeCardAction_twoCardsNominal_stealsRandomCard() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);
        Card mockCard3 = EasyMock.createMock(Card.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)
        ).anyTimes();
        EasyMock.expect(mockTarget.getHandSize()).andReturn(3).anyTimes();
        EasyMock.expect(mockTarget.getHand()).andReturn(
                new ArrayList<>(List.of(mockCard1, mockCard2, mockCard3))
        ).anyTimes();

        EasyMock.expect(mockRandom.nextDouble()).andReturn(0.5).once();

        mockTarget.removeCard(mockCard2);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(mockCard2);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget,
                mockCard1, mockCard2, mockCard3, mockView, mockRandom);

        CatCardController controller = new CatCardController(2, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockTarget)
        );

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(mockCard2, result.get().get(0));
        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget,
                mockCard1, mockCard2, mockCard3, mockView, mockRandom);
    }

    @Test
    void executeCardAction_threeCardsMissingRequestedCard_noSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)
        ).anyTimes();
        EasyMock.expect(mockView.getRequestedCardType()).andReturn(Optional.empty()).once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget, mockView, mockRandom);

        CatCardController controller = new CatCardController(3, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockTarget)
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget, mockView, mockRandom);
    }

    @Test
    void executeCardAction_threeCardsTargetDoesNotHaveCard_whiff() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)
        ).anyTimes();
        EasyMock.expect(mockView.getRequestedCardType()).andReturn(
                Optional.of(CardType.ATTACK)
        ).once();
        EasyMock.expect(mockTarget.hasCard(CardType.ATTACK)).andReturn(false).once();

        EasyMock.replay(mockGc, mockGame, mockInitiator,
                mockTarget, mockView, mockRandom);

        CatCardController controller = new CatCardController(3, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockTarget)
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockGame, mockInitiator,
                mockTarget, mockView, mockRandom);
    }

    @Test
    void executeCardAction_threeCardsTargetHasCard_stealsChosenCard() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockTarget = EasyMock.createMock(Player.class);
        Card mockStolenCard = EasyMock.createMock(Card.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator, mockTarget)
        ).anyTimes();
        EasyMock.expect(mockView.getRequestedCardType()).andReturn(
                Optional.of(CardType.ATTACK)
        ).once();
        EasyMock.expect(mockTarget.hasCard(CardType.ATTACK)).andReturn(true).once();
        EasyMock.expect(mockTarget.getHand()).andReturn(
                new ArrayList<>(List.of(mockStolenCard))
        ).once();
        EasyMock.expect(mockStolenCard.getType()).andReturn(CardType.ATTACK).anyTimes();
        mockTarget.removeCard(mockStolenCard);
        EasyMock.expectLastCall().once();
        mockInitiator.addCard(mockStolenCard);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockTarget,
                mockStolenCard, mockView, mockRandom);

        CatCardController controller = new CatCardController(3, mockView, mockRandom);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockTarget)
        );

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(mockStolenCard, result.get().get(0));
        EasyMock.verify(mockGc, mockGame, mockInitiator, mockTarget,
                mockStolenCard, mockView, mockRandom);
    }
}
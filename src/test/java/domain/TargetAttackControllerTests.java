package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.TargetAttackControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TargetAttackControllerTests {
    @Test
    public void executeCardAction_ValidTargetIndex_ReturnsEmptyAndAssignsTurns() {
        String validIndex = "1";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockP1 = EasyMock.createMock(Player.class);
        Player mockP2 = EasyMock.createMock(Player.class);
        TargetAttackControllerView mockView = EasyMock.createMock(
                TargetAttackControllerView.class);

        ArrayList<Player> alivePlayers = new ArrayList<>();
        alivePlayers.add(mockP1);
        alivePlayers.add(mockP2);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);

        mockView.displayAlivePlayers(alivePlayers, mockP1);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(validIndex).once();

        EasyMock.expect(mockP2.isAlive()).andReturn(true);

        mockGameController.setNextPlayerTurnsLeft(2);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockP1,
                mockP2, mockView);

        TargetAttackController controller = new TargetAttackController(mockView);
        Optional<List<Card>> result = controller.executeCardAction(mockGameController,
                mockP1,
                Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockP1,
                mockP2, mockView);
        assertTrue(result.isEmpty(),
                "TargetAttackController should return Optional.empty()");
    }

    @Test
    public void executeCardAction_TargetIsInitiator_RetriesUntilValid() {
        String selfIndex = "0";
        String validIndex = "1";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockP1 = EasyMock.createMock(Player.class);
        Player mockP2 = EasyMock.createMock(Player.class);
        TargetAttackControllerView mockView = EasyMock.createMock(
                TargetAttackControllerView.class);

        ArrayList<Player> alivePlayers = new ArrayList<>();
        alivePlayers.add(mockP1);
        alivePlayers.add(mockP2);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);

        mockView.displayAlivePlayers(alivePlayers, mockP1);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(selfIndex).once();
        mockView.displayInvalidTarget("Target and initiator must be different players.");
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(validIndex).once();
        EasyMock.expect(mockP1.isAlive()).andReturn(true).anyTimes();
        EasyMock.expect(mockP2.isAlive()).andReturn(true).anyTimes();
        mockGameController.setNextPlayerTurnsLeft(2);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockP1,
                mockP2, mockView);

        TargetAttackController controller = new TargetAttackController(mockView);
        Optional<List<Card>> result = controller.executeCardAction(mockGameController,
                mockP1,
                Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockP1,
                mockP2, mockView);
        assertTrue(result.isEmpty());
    }

    @Test
    public void executeCardAction_TargetOutOfBoundsNegative_RetriesUntilValid() {
        String negativeIndex = "-1";
        String validIndex = "1";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockP1 = EasyMock.createMock(Player.class);
        Player mockP2 = EasyMock.createMock(Player.class);
        TargetAttackControllerView mockView = EasyMock.createMock(
                TargetAttackControllerView.class);

        ArrayList<Player> alivePlayers = new ArrayList<>();
        alivePlayers.add(mockP1);
        alivePlayers.add(mockP2);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);

        mockView.displayAlivePlayers(alivePlayers, mockP1);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(negativeIndex).once();
        mockView.displayInvalidIndex(negativeIndex);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(validIndex).once();
        EasyMock.expect(mockP2.isAlive()).andReturn(true).anyTimes();
        mockGameController.setNextPlayerTurnsLeft(2);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockP1,
                mockP2, mockView);

        TargetAttackController controller = new TargetAttackController(mockView);
        Optional<List<Card>> result = controller.executeCardAction(mockGameController,
                mockP1,
                Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockP1,
                mockP2, mockView);
        assertTrue(result.isEmpty());
    }

    @Test
    public void executeCardAction_TargetOutOfBoundsTooLarge_RetriesUntilValid() {
        String tooLargeIndex = "2";
        String validIndex = "1";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockP1 = EasyMock.createMock(Player.class);
        Player mockP2 = EasyMock.createMock(Player.class);
        TargetAttackControllerView mockView = EasyMock.createMock(
                TargetAttackControllerView.class);

        ArrayList<Player> alivePlayers = new ArrayList<>();
        alivePlayers.add(mockP1);
        alivePlayers.add(mockP2);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);

        mockView.displayAlivePlayers(alivePlayers, mockP1);
        EasyMock.expectLastCall().once();

        // 1st Iteration: Index 2 exceeds bounds (size is 2, max valid index is 1)
        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(tooLargeIndex).once();
        mockView.displayInvalidIndex(tooLargeIndex);
        EasyMock.expectLastCall().once();

        // 2nd Iteration: Valid selection
        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(validIndex).once();
        EasyMock.expect(mockP2.isAlive()).andReturn(true).anyTimes();
        mockGameController.setNextPlayerTurnsLeft(2);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockP1,
                mockP2, mockView);

        TargetAttackController controller = new TargetAttackController(mockView);
        Optional<List<Card>> result = controller.executeCardAction(mockGameController,
                mockP1,
                Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockP1,
                mockP2, mockView);
        assertTrue(result.isEmpty(), "TargetAttackController should return Optional.empty()");
    }

    @Test
    public void executeCardAction_InputFormatInvalid_RetriesUntilValid() {
        String invalidInput = "abc";
        String validIndex = "1";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockP1 = EasyMock.createMock(Player.class);
        Player mockP2 = EasyMock.createMock(Player.class);
        TargetAttackControllerView mockView = EasyMock.createMock(TargetAttackControllerView.class);

        ArrayList<Player> alivePlayers = new ArrayList<>();
        alivePlayers.add(mockP1);
        alivePlayers.add(mockP2);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);

        mockView.displayAlivePlayers(alivePlayers, mockP1);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(invalidInput).once();
        mockView.displayInvalidIndex(invalidInput);
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockView.getTargetPlayerIndex()).andReturn(validIndex).once();
        EasyMock.expect(mockP2.isAlive()).andReturn(true).anyTimes();
        mockGameController.setNextPlayerTurnsLeft(2);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockP1, mockP2, mockView);

        TargetAttackController controller = new TargetAttackController(mockView);
        Optional<List<Card>> result = controller.executeCardAction(mockGameController, mockP1, Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockP1, mockP2, mockView);
        assertTrue(result.isEmpty(), "TargetAttackController should return Optional.empty()");
    }
}

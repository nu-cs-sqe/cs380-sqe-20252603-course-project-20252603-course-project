package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackCardControllerTests {

    @Test
    void executeCardAction_OneTurnLeft_EndsCurrentAndGivesNextThree() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);

        EasyMock.expect(mockController.getCurrentPlayerTurnsLeft()).andReturn(1);
        mockController.setNextPlayerTurnsLeft(3);
        EasyMock.expectLastCall();
        mockController.setCurrentPlayerTurnsLeft(0);
        EasyMock.expectLastCall();

        EasyMock.replay(mockController, mockUser);

        AttackCardController controller = new AttackCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, mockUser, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockUser);
    }

    @Test
    void executeCardAction_ThreeTurnsLeft_ChainedAttack_GivesNextFive() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);

        EasyMock.expect(mockController.getCurrentPlayerTurnsLeft()).andReturn(3);
        mockController.setNextPlayerTurnsLeft(5);
        EasyMock.expectLastCall();
        mockController.setCurrentPlayerTurnsLeft(0);
        EasyMock.expectLastCall();

        EasyMock.replay(mockController, mockUser);

        AttackCardController controller = new AttackCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, mockUser, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockUser);
    }
}

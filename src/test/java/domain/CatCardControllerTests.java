package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.CatCardControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CatCardControllerTests {

    @Test
    void executeCardAction_targetMissing_noSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);

        EasyMock.replay(mockGc, mockInitiator, mockView);

        CatCardController controller = new CatCardController(2, mockView);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.empty()
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockInitiator, mockView);
    }

    @Test
    void executeCardAction_selfTargeting_noSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);

        EasyMock.replay(mockGc, mockInitiator, mockView);

        CatCardController controller = new CatCardController(2, mockView);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockInitiator)
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockInitiator, mockView);
    }

    @Test
    void executeCardAction_targetNotAlive_noSteal() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player mockDeadTarget = EasyMock.createMock(Player.class);
        CatCardControllerView mockView = EasyMock.createMock(CatCardControllerView.class);

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(
                List.of(mockInitiator)
        ).anyTimes();

        EasyMock.replay(mockGc, mockGame, mockInitiator, mockDeadTarget, mockView);

        CatCardController controller = new CatCardController(2, mockView);
        Optional<List<Card>> result = controller.executeCardAction(
                mockGc, mockInitiator, Optional.of(mockDeadTarget)
        );

        assertEquals(Optional.empty(), result);
        EasyMock.verify(mockGc, mockGame, mockInitiator, mockDeadTarget, mockView);
    }
}
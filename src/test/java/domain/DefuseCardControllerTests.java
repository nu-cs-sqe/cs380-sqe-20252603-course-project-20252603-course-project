package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefuseCardControllerTests {
    @Test
    public void executeCardAction_defusePlayed_throwIllegalStateException() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);

        EasyMock.replay(mockController, mockUser);

        DefuseCardController controller = new DefuseCardController();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controller.executeCardAction(mockController, mockUser, Optional.empty());
        });

        assertEquals("defuse cards can not be played directly", exception.getMessage());

        EasyMock.verify(mockController, mockUser);
    }
}
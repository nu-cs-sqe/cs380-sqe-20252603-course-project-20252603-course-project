package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
}

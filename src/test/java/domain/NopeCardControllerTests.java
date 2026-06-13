package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NopeCardControllerTests {

    @Test
    void executeCardAction_anyInput_returnsEmpty() {
        GameController mockController = EasyMock.createMock(GameController.class);
        Player mockUser = EasyMock.createMock(Player.class);

        EasyMock.replay(mockController, mockUser);

        NopeCardController controller = new NopeCardController();
        Optional<List<Card>> result =
                controller.executeCardAction(mockController, mockUser, Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(mockController, mockUser);
    }
}

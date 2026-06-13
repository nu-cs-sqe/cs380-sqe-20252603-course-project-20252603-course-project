package domain;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SkipCardControllerTests {

    @Test
    public void executeCardAction_NoTarget_ReturnsEmpty() {
        GameController gameController = EasyMock.createMock(GameController.class);
        Player user = EasyMock.createMock(Player.class);
        SkipCardController controller = new SkipCardController();

        EasyMock.replay(gameController, user);

        Optional<List<Card>> result = controller.executeCardAction(gameController,
                user,
                Optional.empty());

        assertTrue(result.isEmpty());
        EasyMock.verify(gameController, user);
    }
}
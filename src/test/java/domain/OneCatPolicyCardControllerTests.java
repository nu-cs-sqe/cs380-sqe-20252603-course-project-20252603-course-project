package domain;

import org.junit.jupiter.api.Test;
import ui.OneCatPolicyCardControllerView;

import java.util.ArrayList;
import java.util.Optional;

import static org.easymock.EasyMock.*;

public class OneCatPolicyCardControllerTests {

    @Test
    public void executeCardAction_NextPlayerHasZeroCatCards_AddsNoTurns() {
        OneCatPolicyCardControllerView mockView = createStrictMock(OneCatPolicyCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Player mockInitiator = createMock(Player.class);
        Player mockNextPlayer = createMock(Player.class);

        // Build Hand: No cat cards
        Card nonCat1 = createMock(Card.class);
        Card nonCat2 = createMock(Card.class);

        expect(nonCat1.getType()).andReturn(CardType.DEFUSE).anyTimes();
        expect(nonCat2.getType()).andReturn(CardType.SKIP).anyTimes();

        ArrayList<Card> nextPlayerHand = new ArrayList<>(java.util.Arrays.asList(nonCat1, nonCat2));

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGameController.getNextPlayerIndex()).andReturn(1);

        expect(mockGame.getAlivePlayers()).andReturn(java.util.Arrays.asList(mockInitiator, mockNextPlayer));

        expect(mockNextPlayer.getHand()).andReturn(nextPlayerHand);

        // Expect the view to display that no turns were added
        mockView.displayNoTurnsAdded();

        replay(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, nonCat1, nonCat2);

        OneCatPolicyCardController controller = new OneCatPolicyCardController(mockView);
        controller.executeCardAction(mockGameController, mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, nonCat1, nonCat2);
    }
}
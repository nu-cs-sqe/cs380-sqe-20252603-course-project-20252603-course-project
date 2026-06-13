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

        Card nonCat1 = createMock(Card.class);
        Card nonCat2 = createMock(Card.class);

        expect(nonCat1.getType()).andReturn(CardType.DEFUSE).anyTimes();
        expect(nonCat2.getType()).andReturn(CardType.SKIP).anyTimes();

        ArrayList<Card> nextPlayerHand = new ArrayList<>(java.util.Arrays.asList(nonCat1, nonCat2));

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGameController.getNextPlayerIndex()).andReturn(1);

        expect(mockGame.getAlivePlayers()).andReturn(java.util.Arrays.asList(mockInitiator, mockNextPlayer));

        expect(mockNextPlayer.getHand()).andReturn(nextPlayerHand);

        mockView.displayNoTurnsAdded();

        replay(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, nonCat1, nonCat2);

        OneCatPolicyCardController controller = new OneCatPolicyCardController(mockView);
        controller.executeCardAction(mockGameController, mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, nonCat1, nonCat2);
    }

    @Test
    public void executeCardAction_NextPlayerHasOneCatCard_AddsNoTurns() {
        OneCatPolicyCardControllerView mockView = createStrictMock(OneCatPolicyCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Player mockInitiator = createMock(Player.class);
        Player mockNextPlayer = createMock(Player.class);

        Card cat1 = createMock(Card.class);
        Card nonCat = createMock(Card.class);

        expect(cat1.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(nonCat.getType()).andReturn(CardType.DEFUSE).anyTimes();

        ArrayList<Card> nextPlayerHand = new ArrayList<>(java.util.Arrays.asList(cat1, nonCat));

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGameController.getNextPlayerIndex()).andReturn(1);

        expect(mockGame.getAlivePlayers()).andReturn(java.util.Arrays.asList(mockInitiator, mockNextPlayer));

        expect(mockNextPlayer.getHand()).andReturn(nextPlayerHand);

        mockView.displayNoTurnsAdded();

        replay(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1, nonCat);

        OneCatPolicyCardController controller = new OneCatPolicyCardController(mockView);
        controller.executeCardAction(mockGameController, mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1, nonCat);
    }

    @Test
    public void executeCardAction_NextPlayerHasMaxUniqueCatCards_AddsNoTurns() {
        OneCatPolicyCardControllerView mockView = createStrictMock(OneCatPolicyCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Player mockInitiator = createMock(Player.class);
        Player mockNextPlayer = createMock(Player.class);

        Card cat1 = createMock(Card.class);
        Card cat2 = createMock(Card.class);
        Card cat3 = createMock(Card.class);
        Card cat4 = createMock(Card.class);

        expect(cat1.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat2.getType()).andReturn(CardType.CAT_CARD_2).anyTimes();
        expect(cat3.getType()).andReturn(CardType.CAT_CARD_3).anyTimes();
        expect(cat4.getType()).andReturn(CardType.CAT_CARD_4).anyTimes();

        ArrayList<Card> nextPlayerHand = new ArrayList<>(java.util.Arrays.asList(cat1, cat2, cat3, cat4));

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGameController.getNextPlayerIndex()).andReturn(1);

        expect(mockGame.getAlivePlayers()).andReturn(java.util.Arrays.asList(mockInitiator, mockNextPlayer));

        expect(mockNextPlayer.getHand()).andReturn(nextPlayerHand);

        mockView.displayNoTurnsAdded();

        replay(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1, cat2, cat3, cat4);

        OneCatPolicyCardController controller = new OneCatPolicyCardController(mockView);
        controller.executeCardAction(mockGameController, mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1, cat2, cat3, cat4);
    }

    @Test
    public void executeCardAction_NextPlayerHasExactlyTwoSameCatCards_AddsOneTurn() {
        OneCatPolicyCardControllerView mockView = createStrictMock(OneCatPolicyCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Player mockInitiator = createMock(Player.class);
        Player mockNextPlayer = createMock(Player.class);

        Card cat1A = createMock(Card.class);
        Card cat1B = createMock(Card.class);

        expect(cat1A.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat1B.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();

        ArrayList<Card> nextPlayerHand = new ArrayList<>(java.util.Arrays.asList(cat1A, cat1B));

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGameController.getNextPlayerIndex()).andReturn(1);

        expect(mockGame.getAlivePlayers()).andReturn(java.util.Arrays.asList(mockInitiator, mockNextPlayer));

        expect(mockNextPlayer.getHand()).andReturn(nextPlayerHand);

        expect(mockGameController.getNextPlayerTurnsLeft()).andReturn(1);
        mockGameController.setNextPlayerTurnsLeft(2);

        mockView.displayTurnsAdded(1);

        replay(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1A, cat1B);

        OneCatPolicyCardController controller = new OneCatPolicyCardController(mockView);
        controller.executeCardAction(mockGameController, mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1A, cat1B);
    }

    @Test
    public void executeCardAction_NextPlayerHasThreeSameCatCards_AddsOneTurn() {
        OneCatPolicyCardControllerView mockView = createStrictMock(OneCatPolicyCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Player mockInitiator = createMock(Player.class);
        Player mockNextPlayer = createMock(Player.class);

        Card cat1A = createMock(Card.class);
        Card cat1B = createMock(Card.class);
        Card cat1C = createMock(Card.class);

        expect(cat1A.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat1B.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat1C.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();

        ArrayList<Card> nextPlayerHand = new ArrayList<>(java.util.Arrays.asList(cat1A, cat1B, cat1C));

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGameController.getNextPlayerIndex()).andReturn(1);

        expect(mockGame.getAlivePlayers()).andReturn(java.util.Arrays.asList(mockInitiator, mockNextPlayer));

        expect(mockNextPlayer.getHand()).andReturn(nextPlayerHand);

        expect(mockGameController.getNextPlayerTurnsLeft()).andReturn(1);
        mockGameController.setNextPlayerTurnsLeft(2);

        mockView.displayTurnsAdded(1);

        replay(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1A, cat1B, cat1C);

        OneCatPolicyCardController controller = new OneCatPolicyCardController(mockView);
        controller.executeCardAction(mockGameController, mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1A, cat1B, cat1C);
    }

    @Test
    public void executeCardAction_NextPlayerHasMultipleDistinctCatGroups_AddsMultipleTurns() {
        OneCatPolicyCardControllerView mockView = createStrictMock(OneCatPolicyCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Player mockInitiator = createMock(Player.class);
        Player mockNextPlayer = createMock(Player.class);

        Card cat1A = createMock(Card.class);
        Card cat1B = createMock(Card.class);
        Card cat2A = createMock(Card.class);
        Card cat2B = createMock(Card.class);
        Card cat2C = createMock(Card.class);

        expect(cat1A.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat1B.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat2A.getType()).andReturn(CardType.CAT_CARD_2).anyTimes();
        expect(cat2B.getType()).andReturn(CardType.CAT_CARD_2).anyTimes();
        expect(cat2C.getType()).andReturn(CardType.CAT_CARD_2).anyTimes();

        ArrayList<Card> nextPlayerHand = new ArrayList<>(java.util.Arrays.asList(
                cat1A, cat1B, cat2A, cat2B, cat2C
        ));

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGameController.getNextPlayerIndex()).andReturn(1);

        expect(mockGame.getAlivePlayers()).andReturn(java.util.Arrays.asList(mockInitiator, mockNextPlayer));

        expect(mockNextPlayer.getHand()).andReturn(nextPlayerHand);

        expect(mockGameController.getNextPlayerTurnsLeft()).andReturn(1);
        mockGameController.setNextPlayerTurnsLeft(3);

        mockView.displayTurnsAdded(2);

        replay(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer,
                cat1A, cat1B, cat2A, cat2B, cat2C);

        OneCatPolicyCardController controller = new OneCatPolicyCardController(mockView);
        controller.executeCardAction(mockGameController, mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer,
                cat1A, cat1B, cat2A, cat2B, cat2C);
    }

    @Test
    public void executeCardAction_NextPlayerHasFourSameCatCards_AddsOneTurn() {
        OneCatPolicyCardControllerView mockView = createStrictMock(OneCatPolicyCardControllerView.class);
        GameController mockGameController = createMock(GameController.class);
        Game mockGame = createMock(Game.class);
        Player mockInitiator = createMock(Player.class);
        Player mockNextPlayer = createMock(Player.class);

        Card cat1A = createMock(Card.class);
        Card cat1B = createMock(Card.class);
        Card cat1C = createMock(Card.class);
        Card cat1D = createMock(Card.class);

        expect(cat1A.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat1B.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat1C.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(cat1D.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();

        ArrayList<Card> nextPlayerHand = new ArrayList<>(java.util.Arrays.asList(cat1A, cat1B, cat1C, cat1D));

        expect(mockGameController.getGame()).andReturn(mockGame);
        expect(mockGameController.getNextPlayerIndex()).andReturn(1);

        expect(mockGame.getAlivePlayers()).andReturn(java.util.Arrays.asList(mockInitiator, mockNextPlayer));

        expect(mockNextPlayer.getHand()).andReturn(nextPlayerHand);

        expect(mockGameController.getNextPlayerTurnsLeft()).andReturn(1);
        mockGameController.setNextPlayerTurnsLeft(2);

        mockView.displayTurnsAdded(1);

        replay(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1A, cat1B, cat1C, cat1D);

        OneCatPolicyCardController controller = new OneCatPolicyCardController(mockView);
        controller.executeCardAction(mockGameController, mockInitiator, Optional.empty());

        verify(mockView, mockGameController, mockGame, mockInitiator, mockNextPlayer, cat1A, cat1B, cat1C, cat1D);
    }
}
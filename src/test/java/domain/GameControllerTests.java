package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ui.GameControllerView;


public class GameControllerTests {
    @Test
    void constructor_Valid_Game_MakesGameController() {
        Game myGame = new Game(4);
        GameController controller = new GameController(myGame);

        assertSame(myGame, controller.getGame());
    }

    @Test
    void setCurrentPlayerIndex_InvalidIndexBelowMinimum_ThrowsIllegalArgumentException() {
        Game game = new Game(3);
        GameController controller = new GameController(game);
        int invalidIndex = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setCurrentPlayerIndex(invalidIndex);
        });

        String expectedMessage = "Invalid player index: " + invalidIndex;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void setCurrentPlayerIndex_InvalidIndexAboveMaximum_ThrowsIllegalArgumentException() {
        Game game = new Game(3);
        GameController controller = new GameController(game);
        int invalidIndex = 3;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setCurrentPlayerIndex(invalidIndex);
        });

        String expectedMessage = "Invalid player index: " + invalidIndex;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void setCurrentPlayerIndex_ValidIndexAtMinimum_SetsCurrentPlayerIndexAsInput() {
        Game game = new Game(3);
        GameController controller = new GameController(game);
        int validMinIndex = 0;

        controller.setCurrentPlayerIndex(validMinIndex);

        assertEquals(validMinIndex, controller.getCurrentPlayerIndex());
    }

    @Test
    void setCurrentPlayerIndex_ValidIndexAtMaximum_SetsCurrentPlayerIndexAsInput() {
        Game game = new Game(3);
        GameController controller = new GameController(game);
        int validMaxIndex = controller.getGame().getAlivePlayerCount() - 1;

        controller.setCurrentPlayerIndex(validMaxIndex);

        assertEquals(validMaxIndex, controller.getCurrentPlayerIndex());
    }

    @Test
    void setNextPlayerIndex_InvalidNegativeIndex_ThrowsIllegalArgumentException() {
        Game game = new Game(5);
        GameController controller = new GameController(game);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setNextPlayerIndex(-1);
        });

        assertEquals("invalid next index", exception.getMessage());
    }

    @Test
    void setNextPlayerIndex_LowerBound_SetsNextPlayerIndex() {
        Game game = new Game(5);
        GameController controller = new GameController(game);

        controller.setNextPlayerIndex(0);

        assertEquals(0, controller.getNextPlayerIndex());
    }

    @Test
    void setNextPlayerIndex_MiddleOfList_SetsNextPlayerIndex() {
        Game game = new Game(5);
        GameController controller = new GameController(game);

        controller.setNextPlayerIndex(2);

        assertEquals(2, controller.getNextPlayerIndex());
    }

    @Test
    void setNextPlayerIndex_UpperBound_SetsNextPlayerIndex() {
        Game game = new Game(5);
        GameController controller = new GameController(game);

        controller.setNextPlayerIndex(4);

        assertEquals(4, controller.getNextPlayerIndex());
    }

    @Test
    void setNextPlayerIndex_InvalidAboveUpperBound_ThrowsIllegalArgumentException() {
        Game game = new Game(5);
        GameController controller = new GameController(game);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setNextPlayerIndex(5);
        });
        assertEquals("invalid next index", exception.getMessage());
    }

    @Test
    void setCurrentPlayerTurnsLeft_NegativeTurnsLeft_IllegalArgumentException() {
        int newCurrentPlayerTurnsLeft = -1;

        Game mockGame = mock(Game.class);
        GameController controller = new GameController(mockGame);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setCurrentPlayerTurnsLeft(newCurrentPlayerTurnsLeft);
        });
        assertEquals("invalid turn count", exception.getMessage());
    }

    @Test
    void setCurrentPlayerTurnsLeft_ZeroTurnsLeft_SetsCurrentPlayerTurnsLeft() {
        int newCurrentPlayerTurnsLeft = 0;

        Game mockGame = mock(Game.class);
        GameController controller = new GameController(mockGame);

        controller.setCurrentPlayerTurnsLeft(newCurrentPlayerTurnsLeft);

        assertEquals(newCurrentPlayerTurnsLeft, controller.getCurrentPlayerTurnsLeft());
    }

    @Test
    void setCurrentPlayerTurnsLeft_FourTurnsLeft_SetsCurrentPlayerTurnsLeft() {
        int newCurrentPlayerTurnsLeft = 4;

        Game mockGame = mock(Game.class);
        GameController controller = new GameController(mockGame);

        controller.setCurrentPlayerTurnsLeft(newCurrentPlayerTurnsLeft);

        assertEquals(newCurrentPlayerTurnsLeft, controller.getCurrentPlayerTurnsLeft());
    }

    @Test
    void setNextPlayerTurnsLeft_NegativeTurnsLeft_IllegalArgumentException() {
        int newNextPlayerTurnsLeft = -1;

        Game mockGame = mock(Game.class);
        GameController controller = new GameController(mockGame);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setNextPlayerTurnsLeft(newNextPlayerTurnsLeft);
        });
        assertEquals("invalid turn count", exception.getMessage());
    }

    @Test
    void setNextPlayerTurnsLeft_ZeroTurnsLeft_SetsNextPlayerTurnsLeft() {
        int newNextPlayerTurnsLeft = 0;

        Game mockGame = mock(Game.class);
        GameController controller = new GameController(mockGame);

        controller.setNextPlayerTurnsLeft(newNextPlayerTurnsLeft);

        assertEquals(newNextPlayerTurnsLeft, controller.getNextPlayerTurnsLeft());
    }

    @Test
    void setNextPlayerTurnsLeft_SevenTurnsLeft_SetsNextPlayerTurnsLeft() {
        int newNextPlayerTurnsLeft = 7;

        Game mockGame = mock(Game.class);
        GameController controller = new GameController(mockGame);

        controller.setNextPlayerTurnsLeft(newNextPlayerTurnsLeft);

        assertEquals(newNextPlayerTurnsLeft, controller.getNextPlayerTurnsLeft());
    }

    @Test
    void setPlayerOrder_IllegallyEmptyList_ThrowsIllegalArgumentException() {
        Game game = new Game(5);
        GameController controller = new GameController(game);
        List<Player> emptyOrder = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setPlayerOrder(emptyOrder);
        });

        assertEquals("list size doesn’t match alivePlayer", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 5})
    void setPlayerOrder_ValidBounds_UpdatesOrder(int playerCount) {
        Game game = Game.createGame(playerCount);
        GameController controller = new GameController(game);

        List<Player> newOrder = new ArrayList<>(game.getAlivePlayers());
        java.util.Collections.reverse(newOrder);

        controller.setPlayerOrder(newOrder);

        assertEquals(newOrder, game.getAlivePlayers());
    }

    @Test
    void setPlayerOrder_ShorterList_ThrowsException() {
        Game game = Game.createGame(4);
        GameController controller = new GameController(game);
        List<Player> original = game.getAlivePlayers();

        List<Player> shortList = List.of(
                original.get(0), original.get(1), original.get(2)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setPlayerOrder(shortList);
        });
        assertEquals("list size doesn’t match alivePlayer", exception.getMessage());
    }

    @Test
    void setPlayerOrder_LongerList_ThrowsException() {
        Game game = Game.createGame(2);
        GameController controller = new GameController(game);
        List<Player> original = game.getAlivePlayers();

        List<Player> longList = List.of(
                original.get(0),
                original.get(1),
                Player.createPlayer("Extra 1"),
                Player.createPlayer("Extra 2")
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.setPlayerOrder(longList);
        });
        assertEquals("list size doesn’t match alivePlayer", exception.getMessage());
    }

    @Test
    void getControllerType_InvalidTestType_IllegalArgumentException() {
        Game game = new Game(2);
        GameController controller = new GameController(game);

        Card card = new Card(CardType.TEST_TYPE);

        assertThrows(IllegalArgumentException.class, () -> {
            controller.getControllerType(card);
        });
    }

    @Test
    void isTargetValid_CatCardAndValidTarget_ReturnsTrue() {
        GameController controller = new GameController(null);
        Player initiator = EasyMock.createMock(Player.class);
        Player target = EasyMock.createMock(Player.class);

        EasyMock.replay(initiator, target);

        assertTrue(controller.isTargetValid(CardType.CAT_CARD_1, initiator, target));

        EasyMock.verify(initiator, target);
    }

    @Test
    void isTargetValid_NonCatCardAndValidTarget_ReturnsFalse() {
        GameController controller = new GameController(null);
        Player initiator = EasyMock.createMock(Player.class);
        Player target = EasyMock.createMock(Player.class);

        EasyMock.replay(initiator, target);

        assertFalse(controller.isTargetValid(CardType.SKIP, initiator, target));

        EasyMock.verify(initiator, target);
    }

    @Test
    void isTargetValid_CatCardTargetIsSelf_ReturnsFalse() {
        GameController controller = new GameController(null);
        Player initiator = EasyMock.createMock(Player.class);

        EasyMock.replay(initiator);

        assertFalse(controller.isTargetValid(CardType.CAT_CARD_1, initiator, initiator));

        EasyMock.verify(initiator);
    }

    @Test
    void cardsAllMatchingCatCards_EmptyList_ThrowsIndexOutOfBoundsException() {
        GameController controller = new GameController(null);
        ArrayList<Card> cards = new ArrayList<>();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            Boolean result = controller.cardsAllMatchingCatCards(cards);
        });
    }


    @Test
    void cardsAllMatchingCatCards_SingleCatCard_ReturnsTrue() {
        GameController controller = new GameController(null);
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        EasyMock.replay(mockCard);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(mockCard);

        assertTrue(controller.cardsAllMatchingCatCards(cards));

        EasyMock.verify(mockCard);
    }

    @Test
    void cardsAllMatchingCatCards_SingleNonCatCard_ReturnsFalse() {
        GameController controller = new GameController(null);
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCard.getType()).andReturn(CardType.SKIP).anyTimes();
        EasyMock.replay(mockCard);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(mockCard);

        assertFalse(controller.cardsAllMatchingCatCards(cards));

        EasyMock.verify(mockCard);
    }

    @Test
    void cardsAllMatchingCatCards_AllMatchingCatCards_ReturnsTrue() {
        GameController controller = new GameController(null);
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        EasyMock.replay(mockCard);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(mockCard);
        cards.add(mockCard);
        cards.add(mockCard);

        assertTrue(controller.cardsAllMatchingCatCards(cards));

        EasyMock.verify(mockCard);
    }

    @Test
    void cardsAllMatchingCatCards_AllNonCatCards_ReturnsFalse() {
        GameController controller = new GameController(null);
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCard.getType()).andReturn(CardType.SKIP).anyTimes();
        EasyMock.replay(mockCard);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(mockCard);
        cards.add(mockCard);
        cards.add(mockCard);

        assertFalse(controller.cardsAllMatchingCatCards(cards));

        EasyMock.verify(mockCard);
    }

    @Test
    void cardsAllMatchingCatCards_MismatchAfterMatch_ReturnsFalse() {
        GameController controller = new GameController(null);
        Card mockCatCard = EasyMock.createMock(Card.class);
        Card mockSkipCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCatCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        EasyMock.expect(mockSkipCard.getType()).andReturn(CardType.SKIP).anyTimes();
        EasyMock.replay(mockCatCard, mockSkipCard);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(mockCatCard);
        cards.add(mockCatCard);
        cards.add(mockSkipCard);

        assertFalse(controller.cardsAllMatchingCatCards(cards));

        EasyMock.verify(mockCatCard, mockSkipCard);
    }

    @Test
    void cardsAllMatchingCatCards_MatchAfterMismatch_ReturnsFalse() {
        GameController controller = new GameController(null);
        Card mockSkipCard = EasyMock.createMock(Card.class);
        Card mockCatCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockSkipCard.getType()).andReturn(CardType.SKIP).anyTimes();
        EasyMock.expect(mockCatCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        EasyMock.replay(mockSkipCard, mockCatCard);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(mockSkipCard);
        cards.add(mockCatCard);
        cards.add(mockCatCard);

        assertFalse(controller.cardsAllMatchingCatCards(cards));

        EasyMock.verify(mockSkipCard, mockCatCard);
    }

    @Test
    void cardsAllMatchingCatCards_MismatchAtLast_ReturnsFalse() {
        GameController controller = new GameController(null);
        Card mockCatCard1 = EasyMock.createMock(Card.class);
        Card mockCatCard2 = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCatCard1.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        EasyMock.expect(mockCatCard2.getType()).andReturn(CardType.CAT_CARD_2).anyTimes();
        EasyMock.replay(mockCatCard1, mockCatCard2);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(mockCatCard1);
        cards.add(mockCatCard1);
        cards.add(mockCatCard2);

        assertFalse(controller.cardsAllMatchingCatCards(cards));

        EasyMock.verify(mockCatCard1, mockCatCard2);
    }

    @Test
    void playerHasCards_EmptyCards_IllegalArgumentException() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.playerHasCards(player, cards);
        });

        assertEquals("cards list cannot be empty", exception.getMessage());
    }

    @Test
    void playerHasCards_OneCardInHand_ReturnTrue() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.CAT_CARD_1));
        player.addCard(new Card(CardType.SKIP));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));

        assertTrue(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_OneCardNotInHand_ReturnFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.CAT_CARD_1));
        player.addCard(new Card(CardType.SKIP));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_2));

        assertFalse(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_OneCardButHandEmpty_ReturnFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_2));

        assertFalse(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_TwoSameCardsButOnlyHaveOneInHand_ReturnFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.CAT_CARD_1));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_1));

        assertFalse(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_TwoSameCardsAndHaveBothInHand_ReturnTrue() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.CAT_CARD_2));
        player.addCard(new Card(CardType.CAT_CARD_2));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_2));
        cards.add(new Card(CardType.CAT_CARD_2));

        assertTrue(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_TwoDifferentCardsButOnlyHaveOneInHand_ReturnFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.CAT_CARD_2));
        player.addCard(new Card(CardType.SHUFFLE));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.SKIP));
        cards.add(new Card(CardType.CAT_CARD_2));

        assertFalse(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_TwoDifferentCardsButHaveNeitherInHand_ReturnFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.SHUFFLE));
        player.addCard(new Card(CardType.SEE_THE_FUTURE));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_2));
        cards.add(new Card(CardType.SKIP));

        assertFalse(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_TwoDifferentCardsAndHaveBothInHand_ReturnTrue() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.CAT_CARD_4));
        player.addCard(new Card(CardType.SEE_THE_FUTURE));
        player.addCard(new Card(CardType.SHUFFLE));
        player.addCard(new Card(CardType.SKIP));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.SKIP));
        cards.add(new Card(CardType.CAT_CARD_4));

        assertTrue(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_ThreeDifferentCardsButHaveOnlyOneInHand_ReturnFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.CAT_CARD_3));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_3));
        cards.add(new Card(CardType.CAT_CARD_2));
        cards.add(new Card(CardType.CAT_CARD_4));

        assertFalse(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void playerHasCards_ThreeSameCardsAndHaveAllThreeInHand_ReturnTrue() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player = game.getAlivePlayers().get(0);

        player.addCard(new Card(CardType.CAT_CARD_4));
        player.addCard(new Card(CardType.ATTACK));
        player.addCard(new Card(CardType.CAT_CARD_4));
        player.addCard(new Card(CardType.CAT_CARD_4));
        player.addCard(new Card(CardType.SHUFFLE));
        player.addCard(new Card(CardType.SKIP));


        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_4));
        cards.add(new Card(CardType.CAT_CARD_4));
        cards.add(new Card(CardType.CAT_CARD_4));

        assertTrue(controller.playerHasCards(player, cards));
        ;
    }

    @Test
    void isValidMove_EmptyCards_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();

        assertFalse(controller.isValidMove(cards, player1, Optional.empty()));
    }

    @Test
    void isValidMove_ValidSingleCard_ReturnsTrue() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);
        player1.addCard(new Card(CardType.SKIP));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.SKIP));

        assertTrue(controller.isValidMove(cards, player1, Optional.empty()));
    }

    @Test
    void isValidMove_InvalidSingleCatCard_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));

        assertFalse(controller.isValidMove(cards, player1, Optional.empty()));
    }

    @Test
    void isValidMove_ValidPairOfCatCards_ReturnsTrue() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);
        Player player2 = game.getAlivePlayers().get(1);

        player1.addCard(new Card(CardType.CAT_CARD_1));
        player1.addCard(new Card(CardType.CAT_CARD_1));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_1));

        assertTrue(controller.isValidMove(cards, player1, Optional.of(player2)));
    }

    @Test
    void isValidMove_ValidPairOfCatCardsButNoTarget_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_1));

        assertFalse(controller.isValidMove(cards, player1, Optional.empty()));
    }

    @Test
    void isValidMove_ValidPairOfCatCardsButTargetingSelf_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_1));

        assertFalse(controller.isValidMove(cards, player1, Optional.of(player1)));
    }

    @Test
    void isValidMove_InvalidPairOfCatCards_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);
        Player player2 = game.getAlivePlayers().get(1);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_2));

        assertFalse(controller.isValidMove(cards, player1, Optional.of(player2)));
    }

    @Test
    void isValidMove_InvalidPairOfNonCatCards_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);
        Player player2 = game.getAlivePlayers().get(1);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.SKIP));
        cards.add(new Card(CardType.SKIP));

        assertFalse(controller.isValidMove(cards, player1, Optional.of(player2)));
    }

    @Test
    void isValidMove_ValidTripleOfCatCards_ReturnsTrue() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);
        Player player2 = game.getAlivePlayers().get(1);

        player1.addCard(new Card(CardType.CAT_CARD_1));
        player1.addCard(new Card(CardType.CAT_CARD_1));
        player1.addCard(new Card(CardType.CAT_CARD_1));

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_1));

        assertTrue(controller.isValidMove(cards, player1, Optional.of(player2)));
    }

    @Test
    void isValidMove_ValidTripleOfCatCardsButNoTarget_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_1));

        assertFalse(controller.isValidMove(cards, player1, Optional.empty()));
    }

    @Test
    void isValidMove_InvalidTripleOfCatCards_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_3));
        cards.add(new Card(CardType.CAT_CARD_2));

        assertFalse(controller.isValidMove(cards, player1, Optional.empty()));
    }

    @Test
    void isValidMove_InvalidTripleOfNonCatCards_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.SKIP));
        cards.add(new Card(CardType.SKIP));
        cards.add(new Card(CardType.SKIP));

        assertFalse(controller.isValidMove(cards, player1, Optional.empty()));
    }

    @Test
    void isValidMove_TooManyCards_ReturnsFalse() {
        Game game = new Game(2);
        GameController controller = new GameController(game);
        Player player1 = game.getAlivePlayers().get(0);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(CardType.CAT_CARD_1));
        cards.add(new Card(CardType.CAT_CARD_3));
        cards.add(new Card(CardType.CAT_CARD_2));
        cards.add(new Card(CardType.SHUFFLE));

        assertFalse(controller.isValidMove(cards, player1, Optional.empty()));
    }

    @Test
    void advanceTurn_CurrentIndex1NextTurns1_CurrentIndex2NextIndex3TurnsMovedFromNextToCurrent() {
        Game mockGame = mock(Game.class);

        expect(mockGame.getAlivePlayerCount()).andReturn(4).anyTimes();
        replay(mockGame);

        GameController controller = new GameController(mockGame);

        controller.setCurrentPlayerIndex(1);
        controller.setNextPlayerIndex(2);
        controller.setCurrentPlayerTurnsLeft(0);
        controller.setNextPlayerTurnsLeft(1);

        controller.advanceTurn();

        assertEquals(2, controller.getCurrentPlayerIndex(),
                "Current player should advance to index 2");
        assertEquals(3, controller.getNextPlayerIndex(),
                "Next player should advance to index 3");
        assertEquals(1, controller.getCurrentPlayerTurnsLeft(),
                "Current player moves less should now be 1");
        assertEquals(1, controller.getNextPlayerTurnsLeft(),
                "Next player moves less should be reset to 1");

        verify(mockGame);
    }

    @Test
    void advanceTurn_CurrentIndex2NextTurns2_CurrentIndex3NextIndex0TurnsMovedFromNextToCurrent() {
        Game mockGame = mock(Game.class);

        expect(mockGame.getAlivePlayerCount()).andReturn(4).anyTimes();
        replay(mockGame);

        GameController controller = new GameController(mockGame);

        controller.setCurrentPlayerIndex(2);
        controller.setNextPlayerIndex(3);
        controller.setCurrentPlayerTurnsLeft(0);
        controller.setNextPlayerTurnsLeft(2);

        controller.advanceTurn();

        assertEquals(3, controller.getCurrentPlayerIndex(),
                "Current player should advance to index 3");
        assertEquals(0, controller.getNextPlayerIndex(),
                "Next player should advance to index 0");
        assertEquals(2, controller.getCurrentPlayerTurnsLeft(),
                "Current player moves less should now be 2");
        assertEquals(1, controller.getNextPlayerTurnsLeft(),
                "Next player moves less should be reset to 1");

        verify(mockGame);
    }

    @Test
    void advanceTurn_CurrentIndex3NextTurns3_CurrentIndex0NextIndex1TurnsMovedFromNextToCurrent() {
        Game mockGame = mock(Game.class);

        expect(mockGame.getAlivePlayerCount()).andReturn(4).anyTimes();
        replay(mockGame);

        GameController controller = new GameController(mockGame);

        controller.setCurrentPlayerIndex(3);
        controller.setNextPlayerIndex(0);
        controller.setCurrentPlayerTurnsLeft(0);
        controller.setNextPlayerTurnsLeft(3);

        controller.advanceTurn();

        assertEquals(0, controller.getCurrentPlayerIndex(),
                "Current player should advance to index 0");
        assertEquals(1, controller.getNextPlayerIndex(),
                "Next player should advance to index 1");
        assertEquals(3, controller.getCurrentPlayerTurnsLeft(),
                "Current player moves less should now be 2");
        assertEquals(1, controller.getNextPlayerTurnsLeft(),
                "Next player moves less should be reset to 1");

        verify(mockGame);
    }

    @Test
    void advanceTurn_MinPlayerCountNextTurns1_CurrentIndex0NextIndex1TurnsMovedFromNextToCurrent() {
        Game mockGame = mock(Game.class);

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        replay(mockGame);

        GameController controller = new GameController(mockGame);

        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(0);
        controller.setNextPlayerTurnsLeft(1);

        controller.advanceTurn();

        assertEquals(1, controller.getCurrentPlayerIndex(),
                "Current player should advance to index 1");
        assertEquals(0, controller.getNextPlayerIndex(),
                "Next player should advance to index 0");
        assertEquals(1, controller.getCurrentPlayerTurnsLeft(),
                "Current player moves less should now be 1");
        assertEquals(1, controller.getNextPlayerTurnsLeft(),
                "Next player moves less should be reset to 1");

        verify(mockGame);
    }

    @Test
    void advanceTurn_MaxPlayersNextTurns10_CurrentIndex4NextIndex0TurnsMovedFromNextToCurrent() {
        Game mockGame = mock(Game.class);

        expect(mockGame.getAlivePlayerCount()).andReturn(5).anyTimes();
        replay(mockGame);

        GameController controller = new GameController(mockGame);

        controller.setCurrentPlayerIndex(4);
        controller.setNextPlayerIndex(0);

        controller.advanceTurn();

        assertEquals(0, controller.getCurrentPlayerIndex(),
                "Current player should advance to index 1");
        assertEquals(1, controller.getNextPlayerIndex(),
                "Next player should advance to index 0");

        verify(mockGame);
    }

    @Test
    void takeTurn_emptyInput_InvalidOutputFunction() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "";
        ArrayList<Player> alivePlayers = new ArrayList<Player>();

        Game mockGame = mock(Game.class);
        Player mockPlayer1 = mock(Player.class);
        GameControllerView mockControllerView = EasyMock.createMock(GameControllerView.class);

        alivePlayers.add(mockPlayer1);

        expect(mockGame.getAlivePlayerCount()).andReturn(2);
        expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);
        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer1);
        expectLastCall();
        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);
        mockControllerView.displayInvalidChoice(mockUserChoice);
        expectLastCall();

        replay(mockGame, mockControllerView, mockPlayer1);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.takeTurn(mockControllerView);

        verify(mockGame, mockControllerView, mockPlayer1);
    }

    @Test
    void takeTurn_NonemptyInvalidInput_InvalidOutputFunction() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "skip";
        ArrayList<Player> alivePlayers = new ArrayList<Player>();

        Game mockGame = mock(Game.class);
        Player mockPlayer1 = mock(Player.class);
        GameControllerView mockControllerView = EasyMock.createMock(GameControllerView.class);

        alivePlayers.add(mockPlayer1);

        expect(mockGame.getAlivePlayerCount()).andReturn(2);
        expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);
        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer1);
        expectLastCall();
        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);
        mockControllerView.displayInvalidChoice(mockUserChoice);
        expectLastCall();

        replay(mockGame, mockControllerView, mockPlayer1);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.takeTurn(mockControllerView);

        verify(mockGame, mockControllerView, mockPlayer1);


    }

    @Test
    void takeTurn_InputIsD_DrawCardAndReduceTurnsLeft() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "d";
        ArrayList<Player> alivePlayers = new ArrayList<Player>();

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Deck mockDeck = mock(Deck.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        alivePlayers.add(mockPlayer);

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);

        expect(mockGame.getDeck()).andReturn(mockDeck);
        mockGame.draw(mockPlayer, mockDeck);
        expect(mockPlayer.getHand()).andReturn(new ArrayList<>()).anyTimes();
        expectLastCall();

        replay(mockGame, mockPlayer, mockDeck, mockControllerView);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(1);

        controller.takeTurn(mockControllerView);

        assertEquals(0, controller.getCurrentPlayerTurnsLeft());

        verify(mockGame, mockPlayer, mockDeck, mockControllerView);
    }

    @Test
    void takeTurn_InputIsSingleCatCard_CallsDisplayInvalidMove() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "1";
        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        ArrayList<Card> hand = new ArrayList<Card>();

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Card mockSkipCard = mock(Card.class);
        Card mockCatCard = mock(Card.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        alivePlayers.add(mockPlayer);
        hand.add(mockSkipCard);
        hand.add(mockCatCard);

        expect(mockGame.getAlivePlayerCount()).andReturn(2);
        expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);

        expect(mockPlayer.getHand()).andReturn(hand).anyTimes();
        expect(mockSkipCard.getType()).andReturn(CardType.SKIP).anyTimes();
        expect(mockCatCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();

        mockControllerView.displayInvalidMove(isA(ArrayList.class));
        expectLastCall();

        replay(mockGame, mockPlayer, mockSkipCard, mockCatCard, mockControllerView);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(1);

        controller.takeTurn(mockControllerView);

        assertEquals(1, controller.getCurrentPlayerTurnsLeft());
        verify(mockGame, mockPlayer, mockSkipCard, mockCatCard, mockControllerView);
    }

    @Test
    void takeTurn_InputIsOutOfBounds_CallsDisplayInvalidIndex() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "5";
        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        ArrayList<Card> hand = new ArrayList<Card>();

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Card mockSkipCard = mock(Card.class);
        Card mockCatCard = mock(Card.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        alivePlayers.add(mockPlayer);
        hand.add(mockSkipCard);
        hand.add(mockCatCard);

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);

        expect(mockPlayer.getHand()).andReturn(hand);

        mockControllerView.displayInvalidIndex(mockUserChoice);
        expectLastCall();

        replay(mockGame, mockPlayer, mockSkipCard, mockCatCard, mockControllerView);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(1);

        controller.takeTurn(mockControllerView);

        assertEquals(1, controller.getCurrentPlayerTurnsLeft());
        verify(mockGame, mockPlayer, mockSkipCard, mockCatCard, mockControllerView);
    }

    @Test
    void takeTurn_ValidNonCatCard_ExecutesActionAndRemovesCardWithoutReducingTurns() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "0";

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Card mockSeeTheFutureCard = mock(Card.class);
        Card mockCatCard = mock(Card.class);
        CardController mockCardController = mock(CardController.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        ArrayList<Player> realAlivePlayers = new ArrayList<>();
        realAlivePlayers.add(mockPlayer);

        ArrayList<Card> realHand = new ArrayList<>();
        realHand.add(mockSeeTheFutureCard);
        realHand.add(mockCatCard);

        GameController controller = new GameController(mockGame) {
            @Override
            public CardController getControllerType(Card card) {
                return mockCardController;
            }
        };

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(realAlivePlayers);

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);

        expect(mockPlayer.getHand()).andReturn(realHand).anyTimes();

        expect(mockSeeTheFutureCard.getType()).andReturn(CardType.SEE_THE_FUTURE).anyTimes();
        expect(mockCatCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();

        expect(mockCardController.executeCardAction(eq(controller),
                eq(mockPlayer),
                eq(Optional.empty())))
                .andReturn(Optional.empty());

        mockPlayer.removeCard(mockSeeTheFutureCard);
        expectLastCall();

        replay(mockGame, mockPlayer, mockSeeTheFutureCard, mockCatCard,
                mockCardController, mockControllerView);

        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(1);

        controller.takeTurn(mockControllerView);

        assertEquals(1, controller.getCurrentPlayerTurnsLeft());
        verify(mockGame, mockPlayer, mockSeeTheFutureCard, mockCatCard,
                mockCardController, mockControllerView);
    }

    @Test
    void takeTurn_ValidStateAlteringCard_ExecutesActionAndUpdatesState() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "1"; // User selects DRAW_FROM_BOTTOM at index 1

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Deck mockDeck = mock(Deck.class);
        CardController mockCardController = mock(CardController.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        Card mockCatCard = mock(Card.class);
        Card mockDrawBottomCard = mock(Card.class);
        Card mockSkipCard = mock(Card.class);
        Card mockDrawnCard = mock(Card.class);

        ArrayList<Player> realAlivePlayers = new ArrayList<>();
        realAlivePlayers.add(mockPlayer);

        ArrayList<Card> realHand = new ArrayList<>();
        realHand.add(mockCatCard);
        realHand.add(mockDrawBottomCard);
        realHand.add(mockSkipCard);

        GameController controller = new GameController(mockGame) {
            @Override
            public CardController getControllerType(Card card) {
                return mockCardController;
            }
        };

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(realAlivePlayers).anyTimes();
        expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);
        expect(mockPlayer.getHand()).andReturn(realHand).anyTimes();

        expect(mockCatCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(mockDrawBottomCard.getType()).andReturn(CardType.DRAW_FROM_BOTTOM).anyTimes();
        expect(mockSkipCard.getType()).andReturn(CardType.SKIP).anyTimes();

        expect(mockCardController.executeCardAction(eq(controller),
                eq(mockPlayer),
                eq(Optional.empty())))
                .andAnswer(() -> {
                    realHand.add(mockDrawnCard);
                    return Optional.empty();
                });

        mockPlayer.removeCard(mockDrawBottomCard);
        expectLastCall().andAnswer(() -> {
            realHand.remove(mockDrawBottomCard);
            return null;
        });

        replay(mockGame, mockPlayer, mockDeck, mockCardController, mockControllerView,
                mockCatCard, mockDrawBottomCard, mockSkipCard, mockDrawnCard);

        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(1);

        controller.takeTurn(mockControllerView);

        assertEquals(1, controller.getCurrentPlayerTurnsLeft(), "Turns left should remain 1");

        assertEquals(3, realHand.size(), "Hand size should remain 3");
        assertTrue(realHand.contains(mockDrawnCard),
                "The hand should contain the newly drawn card");
        assertTrue(!realHand.contains(mockDrawBottomCard), "The played card should be removed");

        verify(mockGame, mockPlayer, mockDeck, mockCardController, mockControllerView,
                mockCatCard, mockDrawBottomCard, mockSkipCard, mockDrawnCard);
    }

    @Test
    void takeTurn_ValidTurnEndingCard_ExecutesActionReducesTurnsAndRemovesCard() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "2";

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        CardController mockCardController = mock(CardController.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        Card mockCatCard = mock(Card.class);
        Card mockDrawBottomCard = mock(Card.class);
        Card mockSkipCard = mock(Card.class);

        ArrayList<Player> realAlivePlayers = new ArrayList<>();
        realAlivePlayers.add(mockPlayer);

        ArrayList<Card> realHand = new ArrayList<>();
        realHand.add(mockCatCard);
        realHand.add(mockDrawBottomCard);
        realHand.add(mockSkipCard);

        GameController controller = new GameController(mockGame) {
            @Override
            public CardController getControllerType(Card card) {
                return mockCardController;
            }
        };

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(realAlivePlayers).anyTimes();

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);
        expect(mockPlayer.getHand()).andReturn(realHand).anyTimes();

        expect(mockCatCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();
        expect(mockDrawBottomCard.getType()).andReturn(CardType.DRAW_FROM_BOTTOM).anyTimes();
        expect(mockSkipCard.getType()).andReturn(CardType.SKIP).anyTimes();

        expect(mockCardController.executeCardAction(eq(controller),
                eq(mockPlayer),
                eq(Optional.empty())))
                .andAnswer(() -> {
                    int currentTurns = controller.getCurrentPlayerTurnsLeft();
                    controller.setCurrentPlayerTurnsLeft(currentTurns - 1);

                    return Optional.empty();
                });

        mockPlayer.removeCard(mockSkipCard);
        expectLastCall().andAnswer(() -> {
            realHand.remove(mockSkipCard);
            return null;
        });

        replay(mockGame, mockPlayer, mockCardController, mockControllerView,
                mockCatCard, mockDrawBottomCard, mockSkipCard);

        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(2);

        controller.takeTurn(mockControllerView);

        assertEquals(1, controller.getCurrentPlayerTurnsLeft());

        assertEquals(2, realHand.size());
        assertFalse(realHand.contains(mockSkipCard));

        verify(mockGame, mockPlayer, mockCardController, mockControllerView,
                mockCatCard, mockDrawBottomCard, mockSkipCard);
    }

    @Test
    void takeTurn_InputIsCatAndNonCatCard_CallsDisplayInvalidMove() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "0,1";
        String mockTargetChoice = "1";

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Player mockTargetPlayer = mock(Player.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        Card mockSkipCard = mock(Card.class);
        Card mockCatCard = mock(Card.class);

        ArrayList<Player> realAlivePlayers = new ArrayList<>();
        realAlivePlayers.add(mockPlayer);
        realAlivePlayers.add(mockTargetPlayer);

        ArrayList<Card> realHand = new ArrayList<>();
        realHand.add(mockSkipCard);
        realHand.add(mockCatCard);

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(realAlivePlayers).anyTimes();

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);

        expect(mockControllerView.getTargetPlayerIndex(anyObject(ArrayList.class),
                eq(mockPlayer)))
                .andReturn(mockTargetChoice);

        expect(mockPlayer.getHand()).andReturn(realHand).anyTimes();

        expect(mockSkipCard.getType()).andReturn(CardType.SKIP).anyTimes();
        expect(mockCatCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();

        mockControllerView.displayInvalidMove(anyObject());
        expectLastCall();

        replay(mockGame, mockPlayer, mockTargetPlayer, mockControllerView,
                mockSkipCard, mockCatCard);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(1);

        controller.takeTurn(mockControllerView);

        assertEquals(1, controller.getCurrentPlayerTurnsLeft(),
                "Turns should not decrease on an invalid move");
        verify(mockGame, mockPlayer, mockTargetPlayer, mockControllerView,
                mockSkipCard, mockCatCard);
    }

    @Test
    void takeTurn_InputHasDuplicateCard_CallsDisplayDuplicateCardInMove() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "1,1";

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        Card mockSkipCard = mock(Card.class);
        Card mockCatCard = mock(Card.class);

        ArrayList<Player> realAlivePlayers = new ArrayList<>();
        realAlivePlayers.add(mockPlayer);

        ArrayList<Card> realHand = new ArrayList<>();
        realHand.add(mockSkipCard);
        realHand.add(mockCatCard);

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(realAlivePlayers).anyTimes();

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);

        expect(mockPlayer.getHand()).andReturn(realHand).anyTimes();

        mockControllerView.displayDuplicateCardInMove(mockUserChoice);
        expectLastCall();

        replay(mockGame, mockPlayer, mockControllerView, mockSkipCard, mockCatCard);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(1);

        controller.takeTurn(mockControllerView);

        assertEquals(1, controller.getCurrentPlayerTurnsLeft());
        verify(mockGame, mockPlayer, mockControllerView, mockSkipCard, mockCatCard);
    }

    @Test
    void takeTurn_ValidCatCardPair_ExecutesActionRemovesCardsAndDoesNotReduceTurns() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "0,1";
        String mockTargetChoice = "1";

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Player mockTargetPlayer = mock(Player.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        Card mockSkipCard = mock(Card.class);
        Card mockCatCard = mock(Card.class);


        ArrayList<Player> realAlivePlayers = new ArrayList<>();
        realAlivePlayers.add(mockPlayer);
        realAlivePlayers.add(mockTargetPlayer);

        ArrayList<Card> realHand = new ArrayList<>();
        realHand.add(mockSkipCard);
        realHand.add(mockCatCard);

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(realAlivePlayers).anyTimes();

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);

        expect(mockControllerView.getTargetPlayerIndex(eq(realAlivePlayers),
                eq(mockPlayer)))
                .andReturn(mockTargetChoice);

        expect(mockPlayer.getHand()).andReturn(realHand).anyTimes();

        expect(mockSkipCard.getType()).andReturn(CardType.SKIP).anyTimes();
        expect(mockCatCard.getType()).andReturn(CardType.CAT_CARD_1).anyTimes();

        mockControllerView.displayInvalidMove(anyObject());
        expectLastCall();

        replay(mockGame, mockPlayer, mockTargetPlayer, mockControllerView,
                mockSkipCard, mockCatCard);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(1);

        controller.takeTurn(mockControllerView);

        assertEquals(1, controller.getCurrentPlayerTurnsLeft(),
                "Turns should not decrease on an invalid move");
        verify(mockGame, mockPlayer, mockTargetPlayer, mockControllerView,
                mockSkipCard, mockCatCard);
    }

    @Test
    void takeTurn_ValidCatCardTriple_ExecutesActionAndStealsCardWithoutReducingTurns() {
        int currentPlayerIndex = 0;
        String mockUserChoice = "0,3,4";
        String mockTargetChoice = "1";

        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Player mockTargetPlayer = mock(Player.class);
        CardController mockCardController = mock(CardController.class);
        GameControllerView mockControllerView = mock(GameControllerView.class);

        Card mockCatCard1 = mock(Card.class);
        Card mockDrawBottomCard = mock(Card.class);
        Card mockSkipCard = mock(Card.class);
        Card mockCatCard2 = mock(Card.class);
        Card mockCatCard3 = mock(Card.class);
        Card mockStolenCard = mock(Card.class);

        ArrayList<Player> realAlivePlayers = new ArrayList<>();
        realAlivePlayers.add(mockPlayer);
        realAlivePlayers.add(mockTargetPlayer);

        ArrayList<Card> realHand = new ArrayList<>();
        realHand.add(mockCatCard1);
        realHand.add(mockDrawBottomCard);
        realHand.add(mockSkipCard);
        realHand.add(mockCatCard2);
        realHand.add(mockCatCard3);

        GameController controller = new GameController(mockGame) {
            @Override
            public CardController getControllerType(Card card) {
                return mockCardController;
            }
        };

        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        expect(mockGame.getAlivePlayers()).andReturn(realAlivePlayers).anyTimes();

        mockControllerView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();

        expect(mockControllerView.getCardChoiceOrDraw()).andReturn(mockUserChoice);

        expect(mockControllerView.getTargetPlayerIndex(eq(realAlivePlayers),
                eq(mockPlayer)))
                .andReturn(mockTargetChoice);

        expect(mockPlayer.getHand()).andReturn(realHand).anyTimes();

        expect(mockCatCard1.getType()).andReturn(CardType.CAT_CARD_3).anyTimes();
        expect(mockDrawBottomCard.getType()).andReturn(CardType.DRAW_FROM_BOTTOM).anyTimes();
        expect(mockSkipCard.getType()).andReturn(CardType.SKIP).anyTimes();
        expect(mockCatCard2.getType()).andReturn(CardType.CAT_CARD_3).anyTimes();
        expect(mockCatCard3.getType()).andReturn(CardType.CAT_CARD_3).anyTimes();

        expect(mockCardController.executeCardAction(eq(controller),
                eq(mockPlayer),
                eq(Optional.of(mockTargetPlayer))))
                .andAnswer(() -> {
                    realHand.add(mockStolenCard);
                    return Optional.empty();
                });

        mockPlayer.removeCard(mockCatCard1);
        expectLastCall().andAnswer(() -> { realHand.remove(mockCatCard1); return null; });

        mockPlayer.removeCard(mockCatCard2);
        expectLastCall().andAnswer(() -> { realHand.remove(mockCatCard2); return null; });

        mockPlayer.removeCard(mockCatCard3);
        expectLastCall().andAnswer(() -> { realHand.remove(mockCatCard3); return null; });

        replay(mockGame, mockPlayer, mockTargetPlayer, mockCardController, mockControllerView,
                mockCatCard1, mockDrawBottomCard, mockSkipCard, mockCatCard2, mockCatCard3);

        controller.setCurrentPlayerIndex(currentPlayerIndex);
        controller.setCurrentPlayerTurnsLeft(2);

        controller.takeTurn(mockControllerView);

        assertEquals(2, controller.getCurrentPlayerTurnsLeft(),
                "Turns left should remain 2");

        assertEquals(3, realHand.size(),
                "Hand size should be 3 after playing a triple and stealing one");
        assertFalse(realHand.contains(mockCatCard1), "First cat card should be removed");
        assertFalse(realHand.contains(mockCatCard2), "Second cat card should be removed");
        assertFalse(realHand.contains(mockCatCard3), "Third cat card should be removed");
        assertTrue(realHand.contains(mockStolenCard), "Hand should contain the stolen card");

        verify(mockGame, mockPlayer, mockTargetPlayer, mockCardController, mockControllerView,
                mockCatCard1, mockDrawBottomCard, mockSkipCard, mockCatCard2, mockCatCard3);
    }

    @Test
    void playerHasCardOfType_EmptyHand_ReturnsFalse() {
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockPlayer = EasyMock.createMock(Player.class);
        GameController controller = new GameController(mockGame);

        EasyMock.expect(mockPlayer.getHand()).andReturn(new ArrayList<>());
        EasyMock.replay(mockGame, mockPlayer);

        assertFalse(controller.playerHasCardOfType(mockPlayer, CardType.EXPLODING_KITTEN));
        EasyMock.verify(mockGame, mockPlayer);
    }
    @Test
    void playerHasCardOfType_OneCardMatchingType_ReturnsTrue() {
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockPlayer = EasyMock.createMock(Player.class);
        GameController controller = new GameController(mockGame);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(Card.createCard(CardType.EXPLODING_KITTEN));
        EasyMock.expect(mockPlayer.getHand()).andReturn(hand);
        EasyMock.replay(mockGame, mockPlayer);

        assertTrue(controller.playerHasCardOfType(mockPlayer, CardType.EXPLODING_KITTEN));
        EasyMock.verify(mockGame, mockPlayer);
    }

    @Test
    void playerHasCardOfType_OneCardNotMatchingType_ReturnsFalse() {
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockPlayer = EasyMock.createMock(Player.class);
        GameController controller = new GameController(mockGame);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(Card.createCard(CardType.DEFUSE));
        EasyMock.expect(mockPlayer.getHand()).andReturn(hand);
        EasyMock.replay(mockGame, mockPlayer);

        assertFalse(controller.playerHasCardOfType(mockPlayer, CardType.EXPLODING_KITTEN));
        EasyMock.verify(mockGame, mockPlayer);
    }

    @Test
    void playerHasCardOfType_MultipleCardsNoneMatchType_ReturnsFalse() {
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockPlayer = EasyMock.createMock(Player.class);
        GameController controller = new GameController(mockGame);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(Card.createCard(CardType.SKIP));
        hand.add(Card.createCard(CardType.DEFUSE));
        hand.add(Card.createCard(CardType.CAT_CARD_1));
        EasyMock.expect(mockPlayer.getHand()).andReturn(hand);
        EasyMock.replay(mockGame, mockPlayer);

        assertFalse(controller.playerHasCardOfType(mockPlayer, CardType.EXPLODING_KITTEN));
        EasyMock.verify(mockGame, mockPlayer);
    }

    @Test
    void playerHasCardOfType_MultipleCardsOneMatchesType_ReturnsTrue() {
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockPlayer = EasyMock.createMock(Player.class);
        GameController controller = new GameController(mockGame);

        ArrayList<Card> hand = new ArrayList<>();
        hand.add(Card.createCard(CardType.SKIP));
        hand.add(Card.createCard(CardType.EXPLODING_KITTEN));
        hand.add(Card.createCard(CardType.CAT_CARD_1));
        EasyMock.expect(mockPlayer.getHand()).andReturn(hand);
        EasyMock.replay(mockGame, mockPlayer);

        assertTrue(controller.playerHasCardOfType(mockPlayer, CardType.EXPLODING_KITTEN));
        EasyMock.verify(mockGame, mockPlayer);
    }

    @Test
    void runGame_TwoPlayersFirstPlayerDrawsKittenNoDefuse_SecondPlayerWins() {
        Game game = new Game(2);
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 0);

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.setNextPlayerTurnsLeft(1);

        Player player0 = game.getAlivePlayers().get(0);
        Player player1 = game.getAlivePlayers().get(1);

        GameControllerView mockView = EasyMock.createMock(GameControllerView.class);
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");

        EasyMock.replay(mockView);

        controller.runGame(mockView);

        assertEquals(1, game.getAlivePlayerCount());
        assertSame(player1, game.getAlivePlayers().get(0));

        EasyMock.verify(mockView);
    }

    @Test
    void runGame_TwoPlayersFirstPlayerDrawsNormalCardThenSecondPlayerDrawsKitten_FirstPlayerWins() {
        Game game = new Game(2);
        game.getDeck().insert(Card.createCard(CardType.SKIP), 0);
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 1);

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.setNextPlayerTurnsLeft(1);

        Player player0 = game.getAlivePlayers().get(0);
        Player player1 = game.getAlivePlayers().get(1);

        GameControllerView mockView = EasyMock.createMock(GameControllerView.class);
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        mockView.displayCurrentPlayerAndCardsInHand(player1);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");

        EasyMock.replay(mockView);

        controller.runGame(mockView);

        assertEquals(1, game.getAlivePlayerCount());
        assertSame(player0, game.getAlivePlayers().get(0));

        EasyMock.verify(mockView);
    }

    @Test
    void runGame_TwoPlayersFirstPlayerHasTwoTurnsDrawsKittenOnFirst_SecondPlayerWins() {
        Game game = new Game(2);
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 0);

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(2);
        controller.setNextPlayerTurnsLeft(1);

        Player player0 = game.getAlivePlayers().get(0);
        Player player1 = game.getAlivePlayers().get(1);

        GameControllerView mockView = EasyMock.createMock(GameControllerView.class);
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");

        EasyMock.replay(mockView);

        controller.runGame(mockView);

        assertEquals(1, game.getAlivePlayerCount());
        assertSame(player1, game.getAlivePlayers().get(0));

        EasyMock.verify(mockView);
    }

    @Test
    void runGame_ThreePlayersSequentialEliminations_CorrectSurvivor() {
        Game game = new Game(3);
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 0);
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 1);

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(2);
        controller.setNextPlayerIndex(0);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.setNextPlayerTurnsLeft(1);

        Player player0 = game.getAlivePlayers().get(0);
        Player player1 = game.getAlivePlayers().get(1);
        Player player2 = game.getAlivePlayers().get(2);

        GameControllerView mockView = EasyMock.createMock(GameControllerView.class);
        mockView.displayCurrentPlayerAndCardsInHand(player2);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");

        EasyMock.replay(mockView);

        controller.runGame(mockView);

        assertEquals(1, game.getAlivePlayerCount());
        assertSame(player1, game.getAlivePlayers().get(0));

        EasyMock.verify(mockView);
    }

    @Test
    void runGame_NonLastPlayerEliminated_NextPlayerIndexAdjusts() {
        Game game = new Game(3);
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 0);
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 1);

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.setNextPlayerTurnsLeft(1);

        Player player0 = game.getAlivePlayers().get(0);
        Player player1 = game.getAlivePlayers().get(1);
        Player player2 = game.getAlivePlayers().get(2);

        GameControllerView mockView = EasyMock.createMock(GameControllerView.class);
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        mockView.displayCurrentPlayerAndCardsInHand(player1);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");

        EasyMock.replay(mockView);

        controller.runGame(mockView);

        assertEquals(1, game.getAlivePlayerCount());
        assertSame(player2, game.getAlivePlayers().get(0));

        EasyMock.verify(mockView);
    }
    @Test
    void takeTurn_DrawsExplodingKitten_NoDefuse_PlayerKilled() {
        Game mockGame = mock(Game.class);
        Player mockPlayer = mock(Player.class);
        Deck mockDeck = mock(Deck.class);
        GameControllerView mockView = mock(GameControllerView.class);

        ArrayList<Player> alivePlayers = new ArrayList<>();
        alivePlayers.add(mockPlayer);

        expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);
        expect(mockGame.getAlivePlayerCount()).andReturn(2).anyTimes();
        mockView.displayCurrentPlayerAndCardsInHand(mockPlayer);
        expectLastCall();
        expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        expect(mockGame.getDeck()).andReturn(mockDeck);
        mockGame.draw(mockPlayer, mockDeck);
        expectLastCall();
        ArrayList<Card> kittenHand = new ArrayList<>(List.of(new Card(CardType.EXPLODING_KITTEN)));
        expect(mockPlayer.getHand()).andReturn(kittenHand).anyTimes();
        expect(mockPlayer.hasDefuse()).andReturn(false).anyTimes();
        mockGame.removeAlivePlayer(mockPlayer);
        expectLastCall().once();

        replay(mockGame, mockPlayer, mockDeck, mockView);

        GameController controller = new GameController(mockGame);
        controller.setCurrentPlayerIndex(0);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.takeTurn(mockView);

        assertEquals(0, controller.getCurrentPlayerTurnsLeft());
        verify(mockGame, mockPlayer, mockDeck, mockView);
    }

    @Test
    void runGame_TwoPlayersFirstPlayerHasDefuse_FirstPlayerSurvives() {
        Game game = new Game(2);
        Player player0 = game.getAlivePlayers().get(0);
        Player player1 = game.getAlivePlayers().get(1);

        player0.addCard(Card.createCard(CardType.DEFUSE));
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 0);

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.setNextPlayerTurnsLeft(1);

        GameControllerView mockView = EasyMock.createMock(GameControllerView.class);
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        mockView.displayCurrentPlayerAndCardsInHand(player1);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        EasyMock.replay(mockView);

        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream("0\n".getBytes(StandardCharsets.UTF_8)));
        try {
            controller.runGame(mockView);
        } finally {
            System.setIn(originalIn);
        }

        assertEquals(1, game.getAlivePlayerCount());
        assertSame(player0, game.getAlivePlayers().get(0));
        EasyMock.verify(mockView);
    }

    @Test
    void runGame_FivePlayersFirstPlayerDrawsKittenNoDefuse_OuterLoopContinuesUntilOneRemains() {
        Game game = new Game(5);
        Player player0 = game.getAlivePlayers().get(0);
        Player player1 = game.getAlivePlayers().get(1);
        Player player2 = game.getAlivePlayers().get(2);
        Player player3 = game.getAlivePlayers().get(3);
        Player player4 = game.getAlivePlayers().get(4);

        for (int i = 0; i < 4; i++) {
            game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 0);
        }

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.setNextPlayerTurnsLeft(1);

        GameControllerView mockView = EasyMock.createMock(GameControllerView.class);
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        mockView.displayCurrentPlayerAndCardsInHand(player1);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        mockView.displayCurrentPlayerAndCardsInHand(player2);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        mockView.displayCurrentPlayerAndCardsInHand(player3);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        EasyMock.replay(mockView);

        controller.runGame(mockView);

        assertEquals(1, game.getAlivePlayerCount());
        assertSame(player4, game.getAlivePlayers().get(0));
        EasyMock.verify(mockView);
    }

    @Test
    void runGame_PlayerPlaysCardThenDraws_InnerLoopCallsTakeTurnTwice() {
        Game game = new Game(2);
        Player player0 = game.getAlivePlayers().get(0);
        Player player1 = game.getAlivePlayers().get(1);

        player0.addCard(Card.createCard(CardType.SEE_THE_FUTURE));
        game.getDeck().insert(Card.createCard(CardType.EXPLODING_KITTEN), 1);

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.setNextPlayerTurnsLeft(1);

        GameControllerView mockView = EasyMock.createMock(GameControllerView.class);
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("0");
        mockView.displayCurrentPlayerAndCardsInHand(player0);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        mockView.displayCurrentPlayerAndCardsInHand(player1);
        EasyMock.expectLastCall();
        EasyMock.expect(mockView.getCardChoiceOrDraw()).andReturn("d");
        EasyMock.replay(mockView);

        controller.runGame(mockView);

        assertEquals(1, game.getAlivePlayerCount());
        assertSame(player0, game.getAlivePlayers().get(0));
        EasyMock.verify(mockView);
    }

}

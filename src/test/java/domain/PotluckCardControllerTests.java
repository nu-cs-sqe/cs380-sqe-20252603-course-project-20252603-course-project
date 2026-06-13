package domain;

import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import ui.PotluckCardControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PotluckCardControllerTests {
    @Test
    public void executeCardAction_InvalidInputAndTwoPlayers_InvalidOutputThenCardsRemoved() {
        String userChoice = "DRAW_FROM_BOTTOM";
        String userChoice2 = "10";
        String userChoice3 = "1";
        String userChoice4 = "0";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockCurrentPlayer = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        Card mockSkipCard = EasyMock.createMock(Card.class);
        Card mockDrawFromBottomCard = EasyMock.createMock(Card.class);
        Card mockCatCard = EasyMock.createMock(Card.class);
        PotluckCardControllerView mockInput = EasyMock.createMock(PotluckCardControllerView.class);

        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        alivePlayers.add(mockCurrentPlayer);
        alivePlayers.add(mockPlayer2);

        ArrayList<Card> currentPlayerHand = new ArrayList<Card>();
        currentPlayerHand.add(mockSkipCard);
        currentPlayerHand.add(mockDrawFromBottomCard);

        ArrayList<Card> player2Hand = new ArrayList<Card>();
        player2Hand.add(mockCatCard);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);
        EasyMock.expect(mockGame.getAlivePlayerCount()).andReturn(2);
        EasyMock.expect(mockGameController.getCurrentPlayerIndex()).andReturn(0);

        EasyMock.expect(mockCurrentPlayer.getHand()).andReturn(currentPlayerHand).times(3);
        EasyMock.expect(mockPlayer2.getHand()).andReturn(player2Hand);
        EasyMock.expect(mockCurrentPlayer.getHandSize()).andReturn(2);
        EasyMock.expect(mockPlayer2.getHandSize()).andReturn(1);

        mockInput.displayPlayerAndCardsInHand(mockCurrentPlayer);
        EasyMock.expectLastCall().times(3);

        mockInput.displayPlayerAndCardsInHand(mockPlayer2);
        EasyMock.expectLastCall();

        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice2).once();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice3).once();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice4).once();

        mockInput.displayInvalidIndex(userChoice);
        EasyMock.expectLastCall();

        mockInput.displayInvalidIndex(userChoice2);
        EasyMock.expectLastCall();

        mockCurrentPlayer.removeCard(mockDrawFromBottomCard);
        EasyMock.expectLastCall().once();

        mockDeck.insert(mockDrawFromBottomCard, 0);
        EasyMock.expectLastCall().once();

        mockPlayer2.removeCard(mockCatCard);
        EasyMock.expectLastCall().once();

        mockDeck.insert(mockCatCard, 0);
        EasyMock.expectLastCall().once();

        mockInput.displayValidCard(mockDrawFromBottomCard);
        EasyMock.expectLastCall();

        mockInput.displayValidCard(mockCatCard);
        EasyMock.expectLastCall();

        EasyMock.replay(mockGameController, mockGame, mockCurrentPlayer,
                mockPlayer2, mockInput, mockDeck, mockSkipCard,
                mockDrawFromBottomCard, mockCatCard);

        PotluckCardController controller = new PotluckCardController(mockInput);
        controller.executeCardAction(mockGameController, mockCurrentPlayer, Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockCurrentPlayer,
                mockPlayer2, mockInput, mockDeck, mockSkipCard,
                mockDrawFromBottomCard, mockCatCard);

    }

    @Test
    public void executeCardAction_AllPlayersHaveCards_CardsRemovedAndAddedToDeck() {
        String userChoice = "0";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        PotluckCardControllerView mockInput = EasyMock.createMock(PotluckCardControllerView.class);

        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        Player mockPlayer3 = EasyMock.createMock(Player.class);
        Player mockPlayer4 = EasyMock.createMock(Player.class);
        Player mockPlayer5 = EasyMock.createMock(Player.class);

        Card mockSkipCard1 = EasyMock.createMock(Card.class);
        Card mockDrawFromBottomCard = EasyMock.createMock(Card.class);
        Card mockSkipCard2 = EasyMock.createMock(Card.class);
        Card mockCatCard3 = EasyMock.createMock(Card.class);
        Card mockSeeTheFutureCard = EasyMock.createMock(Card.class);

        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        alivePlayers.add(mockPlayer1);
        alivePlayers.add(mockPlayer2);
        alivePlayers.add(mockPlayer3);
        alivePlayers.add(mockPlayer4);
        alivePlayers.add(mockPlayer5);

        ArrayList<Card> player1Hand = new ArrayList<Card>();
        player1Hand.add(mockSkipCard1);

        ArrayList<Card> player2Hand = new ArrayList<Card>();
        player2Hand.add(mockDrawFromBottomCard);

        ArrayList<Card> player3Hand = new ArrayList<Card>();
        player3Hand.add(mockSkipCard2);

        ArrayList<Card> player4Hand = new ArrayList<Card>();
        player4Hand.add(mockCatCard3);

        ArrayList<Card> player5Hand = new ArrayList<Card>();
        player5Hand.add(mockSeeTheFutureCard);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);
        EasyMock.expect(mockGame.getAlivePlayerCount()).andReturn(5);
        EasyMock.expect(mockGameController.getCurrentPlayerIndex()).andReturn(0);

        EasyMock.expect(mockPlayer1.getHandSize()).andReturn(1);
        EasyMock.expect(mockPlayer1.getHand()).andReturn(player1Hand);
        mockInput.displayPlayerAndCardsInHand(mockPlayer1);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();
        mockPlayer1.removeCard(mockSkipCard1);
        EasyMock.expectLastCall().once();
        mockDeck.insert(mockSkipCard1, 0);
        EasyMock.expectLastCall().once();
        mockInput.displayValidCard(mockSkipCard1);
        EasyMock.expectLastCall();

        EasyMock.expect(mockPlayer2.getHandSize()).andReturn(1);
        EasyMock.expect(mockPlayer2.getHand()).andReturn(player2Hand);
        mockInput.displayPlayerAndCardsInHand(mockPlayer2);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();
        mockDeck.insert(mockDrawFromBottomCard, 0);
        EasyMock.expectLastCall().once();
        mockPlayer2.removeCard(mockDrawFromBottomCard);
        EasyMock.expectLastCall().once();
        mockInput.displayValidCard(mockDrawFromBottomCard);
        EasyMock.expectLastCall();

        EasyMock.expect(mockPlayer3.getHandSize()).andReturn(1);
        EasyMock.expect(mockPlayer3.getHand()).andReturn(player3Hand);
        mockInput.displayPlayerAndCardsInHand(mockPlayer3);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();
        mockPlayer3.removeCard(mockSkipCard2);
        EasyMock.expectLastCall().once();
        mockDeck.insert(mockSkipCard2, 0);
        EasyMock.expectLastCall().once();
        mockInput.displayValidCard(mockSkipCard2);
        EasyMock.expectLastCall();

        EasyMock.expect(mockPlayer4.getHandSize()).andReturn(1);
        EasyMock.expect(mockPlayer4.getHand()).andReturn(player4Hand);
        mockInput.displayPlayerAndCardsInHand(mockPlayer4);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();
        mockPlayer4.removeCard(mockCatCard3);
        EasyMock.expectLastCall().once();
        mockDeck.insert(mockCatCard3, 0);
        EasyMock.expectLastCall().once();
        mockInput.displayValidCard(mockCatCard3);
        EasyMock.expectLastCall();

        EasyMock.expect(mockPlayer5.getHandSize()).andReturn(1);
        EasyMock.expect(mockPlayer5.getHand()).andReturn(player5Hand);
        mockInput.displayPlayerAndCardsInHand(mockPlayer5);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();
        mockPlayer5.removeCard(mockSeeTheFutureCard);
        EasyMock.expectLastCall().once();
        mockDeck.insert(mockSeeTheFutureCard, 0);
        EasyMock.expectLastCall().once();
        mockInput.displayValidCard(mockSeeTheFutureCard);
        EasyMock.expectLastCall();

        EasyMock.replay(mockGameController, mockGame, mockDeck, mockInput,
                mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5,
                mockSkipCard1, mockDrawFromBottomCard, mockSkipCard2,
                mockCatCard3, mockSeeTheFutureCard);

        PotluckCardController controller = new PotluckCardController(mockInput);
        controller.executeCardAction(mockGameController,
                mockPlayer1,
                Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockDeck, mockInput,
                mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4, mockPlayer5,
                mockSkipCard1, mockDrawFromBottomCard, mockSkipCard2,
                mockCatCard3, mockSeeTheFutureCard);

    }

    @Test
    public void executeCardAction_ActivePlayerHasNoCards_SkipsActivePlayerAndProcessesOthers() {
        String userChoice = "0";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        PotluckCardControllerView mockInput = EasyMock.createMock(PotluckCardControllerView.class);

        Player mockCurrentPlayer = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        Player mockPlayer3 = EasyMock.createMock(Player.class);

        Card mockShuffleCard = EasyMock.createMock(Card.class);
        Card mockSeeTheFutureCard = EasyMock.createMock(Card.class);

        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        alivePlayers.add(mockCurrentPlayer);
        alivePlayers.add(mockPlayer2);
        alivePlayers.add(mockPlayer3);

        ArrayList<Card> player2Hand = new ArrayList<Card>();
        player2Hand.add(mockShuffleCard);

        ArrayList<Card> player3Hand = new ArrayList<Card>();
        player3Hand.add(mockSeeTheFutureCard);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);
        EasyMock.expect(mockGame.getAlivePlayerCount()).andReturn(3);
        EasyMock.expect(mockGameController.getCurrentPlayerIndex()).andReturn(0);

        EasyMock.expect(mockCurrentPlayer.getHandSize()).andReturn(0);
        mockInput.displayNoCardsAvailable(mockCurrentPlayer);
        EasyMock.expectLastCall();

        EasyMock.expect(mockPlayer2.getHandSize()).andReturn(1);
        EasyMock.expect(mockPlayer2.getHand()).andReturn(player2Hand);
        mockInput.displayPlayerAndCardsInHand(mockPlayer2);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();

        mockPlayer2.removeCard(mockShuffleCard);
        EasyMock.expectLastCall().once();

        mockDeck.insert(mockShuffleCard, 0);
        EasyMock.expectLastCall().once();
        mockInput.displayValidCard(mockShuffleCard);
        EasyMock.expectLastCall();

        EasyMock.expect(mockPlayer3.getHandSize()).andReturn(1);
        EasyMock.expect(mockPlayer3.getHand()).andReturn(player3Hand);
        mockInput.displayPlayerAndCardsInHand(mockPlayer3);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();

        mockPlayer3.removeCard(mockSeeTheFutureCard);
        EasyMock.expectLastCall().once();

        mockDeck.insert(mockSeeTheFutureCard, 0);
        EasyMock.expectLastCall().once();
        mockInput.displayValidCard(mockSeeTheFutureCard);
        EasyMock.expectLastCall();

        EasyMock.replay(mockGameController, mockGame, mockDeck, mockInput,
                mockCurrentPlayer, mockPlayer2, mockPlayer3,
                mockShuffleCard, mockSeeTheFutureCard);

        PotluckCardController controller = new PotluckCardController(mockInput);
        controller.executeCardAction(mockGameController,
                mockCurrentPlayer,
                Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockDeck, mockInput,
                mockCurrentPlayer, mockPlayer2, mockPlayer3,
                mockShuffleCard, mockSeeTheFutureCard);
    }

    @Test
    public void executeCardAction_OpponentHasNoCards_OpponentSkippedAndCurrentPlayerProcessed() {
        String userChoice = "0";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        PotluckCardControllerView mockInput = EasyMock.createMock(PotluckCardControllerView.class);

        Player mockCurrentPlayer = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);

        Card mockCatCard2 = EasyMock.createMock(Card.class);

        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        alivePlayers.add(mockCurrentPlayer);
        alivePlayers.add(mockPlayer2);

        ArrayList<Card> currentPlayerHand = new ArrayList<Card>();
        currentPlayerHand.add(mockCatCard2);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);
        EasyMock.expect(mockGame.getAlivePlayerCount()).andReturn(2);
        EasyMock.expect(mockGameController.getCurrentPlayerIndex()).andReturn(0);

        EasyMock.expect(mockCurrentPlayer.getHandSize()).andReturn(1);
        EasyMock.expect(mockCurrentPlayer.getHand()).andReturn(currentPlayerHand);
        mockInput.displayPlayerAndCardsInHand(mockCurrentPlayer);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();

        mockCurrentPlayer.removeCard(mockCatCard2);
        EasyMock.expectLastCall().once();

        mockDeck.insert(mockCatCard2, 0);
        EasyMock.expectLastCall().once();

        mockInput.displayValidCard(mockCatCard2);
        EasyMock.expectLastCall();

        EasyMock.expect(mockPlayer2.getHandSize()).andReturn(0);
        mockInput.displayNoCardsAvailable(mockPlayer2);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockGameController, mockGame, mockDeck, mockInput,
                mockCurrentPlayer, mockPlayer2, mockCatCard2);

        PotluckCardController controller = new PotluckCardController(mockInput);
        controller.executeCardAction(mockGameController,
                mockCurrentPlayer,
                Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockDeck, mockInput,
                mockCurrentPlayer, mockPlayer2, mockCatCard2);

    }

    @Test
    public void executeCardAction_MultiplePlayersPlaySameCardType_CardsRemovedAndAddedToDeck() {
        String userChoice = "0";

        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        PotluckCardControllerView mockInput = EasyMock.createMock(PotluckCardControllerView.class);

        Player mockCurrentPlayer = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        Card mockSkipCard1 = EasyMock.createMock(Card.class);
        Card mockSkipCard2 = EasyMock.createMock(Card.class);

        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        alivePlayers.add(mockCurrentPlayer);
        alivePlayers.add(mockPlayer2);

        ArrayList<Card> currentPlayerHand = new ArrayList<Card>();
        currentPlayerHand.add(mockSkipCard1);

        ArrayList<Card> player2Hand = new ArrayList<Card>();
        player2Hand.add(mockSkipCard2);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck);
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(alivePlayers);
        EasyMock.expect(mockGame.getAlivePlayerCount()).andReturn(2);
        EasyMock.expect(mockGameController.getCurrentPlayerIndex()).andReturn(0);

        EasyMock.expect(mockCurrentPlayer.getHandSize()).andReturn(1);
        EasyMock.expect(mockCurrentPlayer.getHand()).andReturn(currentPlayerHand);
        mockInput.displayPlayerAndCardsInHand(mockCurrentPlayer);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();

        mockCurrentPlayer.removeCard(mockSkipCard1);
        EasyMock.expectLastCall().once();

        mockDeck.insert(mockSkipCard1, 0);
        EasyMock.expectLastCall().once();

        mockInput.displayValidCard(mockSkipCard1);
        EasyMock.expectLastCall();

        EasyMock.expect(mockPlayer2.getHandSize()).andReturn(1);
        EasyMock.expect(mockPlayer2.getHand()).andReturn(player2Hand);
        mockInput.displayPlayerAndCardsInHand(mockPlayer2);
        EasyMock.expectLastCall();
        EasyMock.expect(mockInput.getCardChoice()).andReturn(userChoice).once();

        mockPlayer2.removeCard(mockSkipCard2);
        EasyMock.expectLastCall().once();

        mockDeck.insert(mockSkipCard2, 0);
        EasyMock.expectLastCall().once();

        mockInput.displayValidCard(mockSkipCard2);
        EasyMock.expectLastCall();

        EasyMock.replay(mockGameController, mockGame, mockDeck, mockInput,
                mockCurrentPlayer, mockPlayer2, mockSkipCard1, mockSkipCard2);

        PotluckCardController controller = new PotluckCardController(mockInput);
        Optional<List<Card>> result = controller.executeCardAction(mockGameController, mockCurrentPlayer, Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockDeck, mockInput,
                mockCurrentPlayer, mockPlayer2, mockSkipCard1, mockSkipCard2);

        assertTrue(result.isEmpty(), "PotluckCardController should return Optional.empty()");
    }
}


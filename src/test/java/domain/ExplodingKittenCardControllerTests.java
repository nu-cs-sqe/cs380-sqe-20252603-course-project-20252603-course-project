package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import ui.ExplodingKittenCardControllerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExplodingKittenCardControllerTests {

    @Test
    void executeCardAction_noDefuseEmptyHand_playerKilled() {
        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        ExplodingKittenCardControllerView mockInput = EasyMock.createMock(
                ExplodingKittenCardControllerView.class);

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockUser.hasDefuse()).andReturn(false).anyTimes();
        mockUser.kill();
        EasyMock.expectLastCall().once();
        mockGame.removeAlivePlayer(mockUser);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockUser, mockDeck);

        ExplodingKittenCardController controller = new ExplodingKittenCardController(mockInput);
        controller.executeCardAction(mockGameController, mockUser, Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockUser, mockDeck);
    }

    @Test
    public void executeCardAction_oneDefuseOneCard_playerLivesEmptyHand() {
        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        ExplodingKittenCardControllerView mockInput = EasyMock.createMock(
                ExplodingKittenCardControllerView.class);

        Card defuse = new Card(CardType.DEFUSE);
        int deckSize = 5;

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockUser.hasDefuse()).andReturn(true).anyTimes();
        ArrayList<Card> hand = new ArrayList<>(List.of(defuse));
        EasyMock.expect(mockUser.getHand()).andReturn(hand).anyTimes();
        mockUser.removeCard(defuse);
        EasyMock.expectLastCall().once();
        EasyMock.expect(mockDeck.count()).andReturn(deckSize).anyTimes();
        EasyMock.expect(mockInput.getInsertPosition(deckSize)).andReturn(0);
        mockDeck.insert(EasyMock.isA(Card.class), EasyMock.eq(0));
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockUser, mockDeck, mockInput);

        ExplodingKittenCardController controller = new ExplodingKittenCardController(mockInput);
        controller.executeCardAction(mockGameController, mockUser, Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockUser, mockDeck, mockInput);
    }

    @Test
    public void executeCardAction_twoDefuses_playerLivesOneDefuse() {
        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        ExplodingKittenCardControllerView mockInput = EasyMock.createMock(
                ExplodingKittenCardControllerView.class);

        Card defuse1 = new Card(CardType.DEFUSE);
        Card defuse2 = new Card(CardType.DEFUSE);
        int deckSize = 5;

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockUser.hasDefuse()).andReturn(true).anyTimes();
        ArrayList<Card> hand = new ArrayList<>(List.of(defuse1, defuse2));
        EasyMock.expect(mockUser.getHand()).andReturn(hand).anyTimes();
        mockUser.removeCard(defuse1);
        EasyMock.expectLastCall().once();
        EasyMock.expect(mockDeck.count()).andReturn(deckSize).anyTimes();
        EasyMock.expect(mockInput.getInsertPosition(deckSize)).andReturn(0);
        mockDeck.insert(EasyMock.anyObject(Card.class), EasyMock.eq(0));
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockUser, mockDeck, mockInput);

        ExplodingKittenCardController controller = new ExplodingKittenCardController(mockInput);
        controller.executeCardAction(mockGameController, mockUser, Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockUser, mockDeck, mockInput);
    }

    @Test
    public void executeCardAction_lastCardDefuseTwoCards_playerLivesOneCard() {
        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        ExplodingKittenCardControllerView mockInput = EasyMock.createMock(
                ExplodingKittenCardControllerView.class);

        Card attack = new Card(CardType.ATTACK);
        Card defuse = new Card(CardType.DEFUSE);
        int deckSize = 5;

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockUser.hasDefuse()).andReturn(true).anyTimes();
        ArrayList<Card> hand = new ArrayList<>(List.of(attack, defuse));
        EasyMock.expect(mockUser.getHand()).andReturn(hand).anyTimes();
        mockUser.removeCard(defuse);
        EasyMock.expectLastCall().once();
        EasyMock.expect(mockDeck.count()).andReturn(deckSize).anyTimes();
        EasyMock.expect(mockInput.getInsertPosition(deckSize)).andReturn(0);
        mockDeck.insert(EasyMock.anyObject(Card.class), EasyMock.eq(0));
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockUser, mockDeck, mockInput);

        ExplodingKittenCardController controller = new ExplodingKittenCardController(mockInput);
        controller.executeCardAction(mockGameController, mockUser, Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockUser, mockDeck, mockInput);
    }

    @Test
    public void executeCardAction_firstCardDefuseThreeCards_playerLivesOneCard() {
        GameController mockGameController = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Deck mockDeck = EasyMock.createMock(Deck.class);
        ExplodingKittenCardControllerView mockInput = EasyMock.createMock(
                ExplodingKittenCardControllerView.class);

        Card defuse = new Card(CardType.DEFUSE);
        Card attack = new Card(CardType.ATTACK);
        Card skip = new Card(CardType.SKIP);
        int deckSize = 5;

        EasyMock.expect(mockGameController.getGame()).andReturn(mockGame);
        EasyMock.expect(mockGame.getDeck()).andReturn(mockDeck).anyTimes();
        EasyMock.expect(mockUser.hasDefuse()).andReturn(true).anyTimes();
        ArrayList<Card> hand = new ArrayList<>(List.of(defuse, attack, skip));
        EasyMock.expect(mockUser.getHand()).andReturn(hand).anyTimes();
        mockUser.removeCard(defuse);
        EasyMock.expectLastCall().once();
        EasyMock.expect(mockDeck.count()).andReturn(deckSize).anyTimes();
        EasyMock.expect(mockInput.getInsertPosition(deckSize)).andReturn(0);
        mockDeck.insert(EasyMock.anyObject(Card.class), EasyMock.eq(0));
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGameController, mockGame, mockUser, mockDeck, mockInput);

        ExplodingKittenCardController controller = new ExplodingKittenCardController(mockInput);
        controller.executeCardAction(mockGameController, mockUser, Optional.empty());

        EasyMock.verify(mockGameController, mockGame, mockUser, mockDeck, mockInput);
    }
}

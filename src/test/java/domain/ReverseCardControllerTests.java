package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReverseCardControllerTests {

    @Test
    void executeCardAction_TwoPlayers_ReversesOrderAndUpdatesIndices() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);

        List<Player> originalList = new ArrayList<>(List.of(p1, p2));
        List<Player> expectedReversedList = new ArrayList<>(List.of(p2, p1));

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(originalList);

        EasyMock.expect(mockGc.getCurrentPlayerIndex()).andReturn(0);

        mockGc.setPlayerOrder(expectedReversedList);
        EasyMock.expectLastCall().once();

        mockGc.setCurrentPlayerIndex(1);
        EasyMock.expectLastCall().once();

        mockGc.setNextPlayerIndex(0);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockUser, p1, p2);

        ReverseCardController controller = new ReverseCardController();
        controller.executeCardAction(mockGc, mockUser, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockUser, p1, p2);
    }

    @Test
    void executeCardAction_ThreePlayers_ReversesOrderAndUpdatesIndices() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);

        List<Player> originalList = new ArrayList<>(List.of(p1, p2, p3));
        List<Player> expectedReversedList = new ArrayList<>(List.of(p3, p2, p1));

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(originalList);

        EasyMock.expect(mockGc.getCurrentPlayerIndex()).andReturn(1);

        mockGc.setPlayerOrder(expectedReversedList);
        EasyMock.expectLastCall().once();

        mockGc.setCurrentPlayerIndex(1);
        EasyMock.expectLastCall().once();

        mockGc.setNextPlayerIndex(2);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockUser, p1, p2, p3);

        ReverseCardController controller = new ReverseCardController();
        controller.executeCardAction(mockGc, mockUser, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockUser, p1, p2, p3);
    }

    @Test
    void executeCardAction_FivePlayers_ReversesOrderAndUpdatesIndices() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockUser = EasyMock.createMock(Player.class);
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);
        Player p5 = EasyMock.createMock(Player.class);

        List<Player> originalList = new ArrayList<>(List.of(p1, p2, p3, p4, p5));
        List<Player> expectedReversedList = new ArrayList<>(List.of(p5, p4, p3, p2, p1));

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(originalList);

        EasyMock.expect(mockGc.getCurrentPlayerIndex()).andReturn(2);

        mockGc.setPlayerOrder(expectedReversedList);
        EasyMock.expectLastCall().once();

        mockGc.setCurrentPlayerIndex(2);
        EasyMock.expectLastCall().once();

        mockGc.setNextPlayerIndex(3);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockUser, p1, p2, p3, p4, p5);

        ReverseCardController controller = new ReverseCardController();
        controller.executeCardAction(mockGc, mockUser, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockUser, p1, p2, p3, p4, p5);
    }

    @Test
    void executeCardAction_FivePlayers_MaxIndex_UpdatesIndices() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player p0 = EasyMock.createMock(Player.class);
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);
        Player p3 = EasyMock.createMock(Player.class);
        Player p4 = EasyMock.createMock(Player.class);

        List<Player> originalList = new ArrayList<>(List.of(p0, p1, p2, p3, p4));
        List<Player> expectedReversedList = new ArrayList<>(List.of(p4, p3, p2, p1, p0));

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(originalList).anyTimes();

        EasyMock.expect(mockGc.getCurrentPlayerIndex()).andReturn(4).anyTimes();

        mockGc.setPlayerOrder(expectedReversedList);
        EasyMock.expectLastCall().once();

        mockGc.setCurrentPlayerIndex(0);
        EasyMock.expectLastCall().once();

        mockGc.setNextPlayerIndex(1);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, p0, p1, p2, p3, p4);

        ReverseCardController controller = new ReverseCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, p0, p1, p2, p3, p4);
    }

    @Test
    void executeCardAction_ThreePlayers_MinIndex_ReversesObjects() {
        GameController mockGc = EasyMock.createMock(GameController.class);
        Game mockGame = EasyMock.createMock(Game.class);
        Player mockInitiator = EasyMock.createMock(Player.class);
        Player p0 = EasyMock.createMock(Player.class);
        Player p1 = EasyMock.createMock(Player.class);
        Player p2 = EasyMock.createMock(Player.class);

        List<Player> originalList = new ArrayList<>(List.of(p0, p1, p2));
        List<Player> expectedReversedList = new ArrayList<>(List.of(p2, p1, p0));

        EasyMock.expect(mockGc.getGame()).andReturn(mockGame).anyTimes();
        EasyMock.expect(mockGame.getAlivePlayers()).andReturn(originalList).anyTimes();

        EasyMock.expect(mockGc.getCurrentPlayerIndex()).andReturn(0).anyTimes();

        mockGc.setPlayerOrder(expectedReversedList);
        EasyMock.expectLastCall().once();

        mockGc.setCurrentPlayerIndex(2);
        EasyMock.expectLastCall().once();

        mockGc.setNextPlayerIndex(0);
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockGc, mockGame, mockInitiator, p0, p1, p2);

        ReverseCardController controller = new ReverseCardController();
        controller.executeCardAction(mockGc, mockInitiator, Optional.empty());

        EasyMock.verify(mockGc, mockGame, mockInitiator, p0, p1, p2);
    }
}
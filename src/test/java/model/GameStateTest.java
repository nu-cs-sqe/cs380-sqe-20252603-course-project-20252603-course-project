package model;

import java.util.List;

import org.easymock.EasyMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GameStateTest {

    @Test
    void constructor_initializesCollectionsAsEmptyLists() {
        GameState gameState = new GameState();

        assertNotNull(gameState.getPlayers());
        assertNotNull(gameState.getTerritories());
        assertNotNull(gameState.getTurnOrder());
        assertTrue(gameState.getPlayers().isEmpty());
        assertTrue(gameState.getTerritories().isEmpty());
        assertTrue(gameState.getTurnOrder().isEmpty());
    }

    @Test
    void settersAndGetters_updateAllFields() {
        GameState gameState = new GameState();

        Player currentPlayer = EasyMock.createMock(Player.class);
        Player playerTwo = EasyMock.createMock(Player.class);
        Territory territory = EasyMock.createMock(Territory.class);
        List<Player> players = List.of(currentPlayer, playerTwo);
        List<Territory> territories = List.of(territory);
        List<Player> turnOrder = List.of(playerTwo, currentPlayer);

        EasyMock.replay(currentPlayer, playerTwo, territory);

        gameState.setPlayers(players);
        gameState.setTerritories(territories);
        gameState.setTurnOrder(turnOrder);
        gameState.setCurrentPlayer(currentPlayer);
        gameState.setCurrentPhase(GamePhase.ATTACK);

        assertEquals(2, gameState.getPlayers().size());
        assertEquals(1, gameState.getTerritories().size());
        assertEquals(2, gameState.getTurnOrder().size());
        assertEquals(players, gameState.getPlayers());
        assertEquals(territories, gameState.getTerritories());
        assertEquals(turnOrder, gameState.getTurnOrder());
        assertSame(currentPlayer, gameState.getCurrentPlayer());
        assertEquals(GamePhase.ATTACK, gameState.getCurrentPhase());

        EasyMock.verify(currentPlayer, playerTwo, territory);
    }
}

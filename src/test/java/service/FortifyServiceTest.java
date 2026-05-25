package service;


import model.GamePhase;
import model.GameState;
import model.Player;
import model.Continent;
import service.ReinforcementService;
import java.util.ArrayList;
import java.util.List;
import service.PlayerColor;
import model.Territory;
import org.easymock.EasyMock;
import service.FortifyService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FortifyServiceTest {

    @Test
    void T1_shouldAllowFortifyBetweenConnectedOwnedTerritories() {
        FortifyService FS = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories = new ArrayList<>();
        Player player1 = new Player(1, "A", PlayerColor.RED, 1, controlled_territories);
        Territory t1 = new Territory("Western United States", player1, 5, Continent.NORTH_AMERICA);
        Territory t2 = new Territory("Eastern United States", player1, 1, Continent.NORTH_AMERICA);
        controlled_territories.add(t1);
        controlled_territories.add(t2);
        player1.setControlledTerritories(controlled_territories);
        assertTrue(FS.areConnectedThroughOwnedTerritories(player1, t1, t2, gameState));
        assertTrue(FS.canFortify(player1, t1, t2, 2, gameState));
    }

    @Test
    void T2_shouldRejectFortifyWhenSourceIsNotOwnedByPlayer() {
        FortifyService FS = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        Player player1 = new Player(1, "A", PlayerColor.RED, 1, controlled_territories1);
        Player player2 = new Player(2, "B", PlayerColor.BLUE, 1, controlled_territories2);
        Territory t1 = new Territory("Western United States", player2, 5, Continent.NORTH_AMERICA);
        Territory t2 = new Territory("Eastern United States", player1, 1, Continent.NORTH_AMERICA);
        controlled_territories2.add(t1);
        controlled_territories1.add(t2);
        player1.setControlledTerritories(controlled_territories1);
        player2.setControlledTerritories(controlled_territories2);
        assertTrue(FS.areConnectedThroughOwnedTerritories(player1, t1, t2, gameState));
        assertFalse(FS.canFortify(player1, t1, t2, 2, gameState));
    }

    @Test
    void T3_shouldRejectFortifyWhenDestinationIsNotOwnedByPlayer(){
        FortifyService FS = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        Player player1 = new Player(1, "A", PlayerColor.RED, 1, controlled_territories1);
        Player player2 = new Player(2, "B", PlayerColor.BLUE, 1, controlled_territories2);
        Territory t1 = new Territory("Western United States", player1, 5, Continent.NORTH_AMERICA);
        Territory t2 = new Territory("Eastern United States", player2, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(t1);
        controlled_territories2.add(t2);
        player1.setControlledTerritories(controlled_territories1);
        player2.setControlledTerritories(controlled_territories2);
        assertTrue(FS.areConnectedThroughOwnedTerritories(player1, t1, t2, gameState));
        assertFalse(FS.canFortify(player1, t1, t2, 2, gameState));
    }

    @Test
    void T4_shouldRejectFortifyWhenTerritoriesAreNotConnected(){
        FortifyService FS = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        Player player1 = new Player(1, "A", PlayerColor.RED, 1, controlled_territories1);
        Player player2 = new Player(2, "B", PlayerColor.BLUE, 1, controlled_territories2);
        Territory t1 = new Territory("Western United States", player1, 5, Continent.NORTH_AMERICA);
        Territory t2 = new Territory("Iceland", player2, 1, Continent.EUROPE);
        controlled_territories1.add(t1);
        controlled_territories2.add(t2);
        player1.setControlledTerritories(controlled_territories1);
        player2.setControlledTerritories(controlled_territories2);
        assertFalse(FS.areConnectedThroughOwnedTerritories(player1, t1, t2, gameState));
    }

    @Test
    void T5_shouldRejectFortifyWhenSourceHasOnlyOneArmy(){
        FortifyService FS = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories = new ArrayList<>();
        Player player1 = new Player(1, "A", PlayerColor.RED, 1, controlled_territories);
        Territory t1 = new Territory("Western United States", player1, 1, Continent.NORTH_AMERICA);
        Territory t2 = new Territory("Eastern United States", player1, 0, Continent.NORTH_AMERICA);
        controlled_territories.add(t1);
        controlled_territories.add(t2);
        player1.setControlledTerritories(controlled_territories);
        assertTrue(FS.areConnectedThroughOwnedTerritories(player1, t1, t2, gameState));
        assertFalse(FS.canFortify(player1, t1, t2, 1, gameState));
    }

    @Test
    void T6_shouldRejectFortifyWhenMoveWouldLeaveSourceEmpty(){
        FortifyService FS = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories = new ArrayList<>();
        Player player1 = new Player(1, "A", PlayerColor.RED, 1, controlled_territories);
        Territory t1 = new Territory("Western United States", player1, 5, Continent.NORTH_AMERICA);
        Territory t2 = new Territory("Eastern United States", player1, 1, Continent.NORTH_AMERICA);
        controlled_territories.add(t1);
        controlled_territories.add(t2);
        player1.setControlledTerritories(controlled_territories);
        assertTrue(FS.areConnectedThroughOwnedTerritories(player1, t1, t2, gameState));
        assertFalse(FS.canFortify(player1, t1, t2, 5, gameState));
    }
}

package service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import model.Continent;
import model.GameState;
import model.Player;
import model.Territory;

public class FortifyServiceTest {

    @Test
    void T1_shouldAllowFortifyBetweenConnectedOwnedTerritories() {
        FortifyService fortifyService = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories
        );
        Territory t1 = new Territory(
                "Western United States",
                player1,
                5,
                Continent.NORTH_AMERICA
        );
        Territory t2 = new Territory(
                "Eastern United States",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories.add(t1);
        controlledTerritories.add(t2);
        player1.setControlledTerritories(controlledTerritories);
        assertTrue(
                fortifyService.areConnectedThroughOwnedTerritories(
                        player1,
                        t1,
                        t2,
                        gameState
                )
        );
        assertTrue(fortifyService.canFortify(player1, t1, t2, 2, gameState));
    }

    @Test
    void T2_shouldRejectFortifyWhenSourceIsNotOwnedByPlayer() {
        FortifyService fortifyService = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories1
        );
        Player player2 = new Player(
                2,
                "B",
                PlayerColor.BLUE,
                1,
                controlledTerritories2
        );
        Territory t1 = new Territory(
                "Western United States",
                player2,
                5,
                Continent.NORTH_AMERICA
        );
        Territory t2 = new Territory(
                "Eastern United States",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories2.add(t1);
        controlledTerritories1.add(t2);
        player1.setControlledTerritories(controlledTerritories1);
        player2.setControlledTerritories(controlledTerritories2);
        assertTrue(
                fortifyService.areConnectedThroughOwnedTerritories(
                        player1,
                        t1,
                        t2,
                        gameState
                )
        );
        assertFalse(fortifyService.canFortify(player1, t1, t2, 2, gameState));
    }

    @Test
    void T3_shouldRejectFortifyWhenDestinationIsNotOwnedByPlayer() {
        FortifyService fortifyService = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories1
        );
        Player player2 = new Player(
                2,
                "B",
                PlayerColor.BLUE,
                1,
                controlledTerritories2
        );
        Territory t1 = new Territory(
                "Western United States",
                player1,
                5,
                Continent.NORTH_AMERICA
        );
        Territory t2 = new Territory(
                "Eastern United States",
                player2,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(t1);
        controlledTerritories2.add(t2);
        player1.setControlledTerritories(controlledTerritories1);
        player2.setControlledTerritories(controlledTerritories2);
        assertTrue(
                fortifyService.areConnectedThroughOwnedTerritories(
                        player1,
                        t1,
                        t2,
                        gameState
                )
        );
        assertFalse(fortifyService.canFortify(player1, t1, t2, 2, gameState));
    }

    @Test
    void T4_shouldRejectFortifyWhenTerritoriesAreNotConnected() {
        FortifyService fortifyService = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories1
        );
        Player player2 = new Player(
                2,
                "B",
                PlayerColor.BLUE,
                1,
                controlledTerritories2
        );
        Territory t1 = new Territory(
                "Western United States",
                player1,
                5,
                Continent.NORTH_AMERICA
        );
        Territory t2 = new Territory(
                "Iceland",
                player2,
                1,
                Continent.EUROPE
        );
        controlledTerritories1.add(t1);
        controlledTerritories2.add(t2);
        player1.setControlledTerritories(controlledTerritories1);
        player2.setControlledTerritories(controlledTerritories2);
        assertFalse(
                fortifyService.areConnectedThroughOwnedTerritories(
                        player1,
                        t1,
                        t2,
                        gameState
                )
        );
    }

    @Test
    void T5_shouldRejectFortifyWhenSourceHasOnlyOneArmy() {
        FortifyService fortifyService = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories
        );
        Territory t1 = new Territory(
                "Western United States",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        Territory t2 = new Territory(
                "Eastern United States",
                player1,
                0,
                Continent.NORTH_AMERICA
        );
        controlledTerritories.add(t1);
        controlledTerritories.add(t2);
        player1.setControlledTerritories(controlledTerritories);
        assertFalse(fortifyService.canFortify(player1, t1, t2, 1, gameState));
    }

    @Test
    void T6_shouldRejectFortifyWhenMoveWouldLeaveSourceEmpty() {
        FortifyService fortifyService = new FortifyService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories
        );
        Territory t1 = new Territory(
                "Western United States",
                player1,
                5,
                Continent.NORTH_AMERICA
        );
        Territory t2 = new Territory(
                "Eastern United States",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories.add(t1);
        controlledTerritories.add(t2);
        player1.setControlledTerritories(controlledTerritories);
        assertFalse(fortifyService.canFortify(player1, t1, t2, 5, gameState));
    }
}

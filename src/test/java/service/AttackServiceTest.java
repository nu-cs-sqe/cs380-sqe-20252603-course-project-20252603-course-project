package service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import model.Continent;
import model.Player;
import model.Territory;

public class AttackServiceTest {

    @Test
    void T1_shouldAllowAttackIfTerritoriesAreAdjacentAndOwnedByDifferentPlayers() {
        TerritoryAdjacencyService adjacencyService =
        new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player attacker = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories1
        );
        Player defender = new Player(
                2,
                "B",
                PlayerColor.BLUE,
                1,
                controlledTerritories2
        );
        Territory atackingTerritory = new Territory(
                "Western United States",
                attacker,
                5,
                Continent.NORTH_AMERICA
        );
        Territory defendingTerritory = new Territory(
                "Eastern United States",
                defender,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(atackingTerritory);
        controlledTerritories2.add(defendingTerritory);
        attacker.setControlledTerritories(controlledTerritories1);
        defender.setControlledTerritories(controlledTerritories2);
        assertTrue(attackService.canAttack(
                attacker,
                atackingTerritory,
                defendingTerritory)
        );
    }

    @Test
    void T2_shouldRejectAttackIfAttackingTerritoryIsNotOwnedByCurrentPlayer() {
        TerritoryAdjacencyService adjacencyService =
        new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        List<Territory> controlledTerritories3 = new ArrayList<>();
        Player attacker = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories1
        );
        Player defender = new Player(
                2,
                "B",
                PlayerColor.BLUE,
                1,
                controlledTerritories2
        );
        Player otherPlayer = new Player(
                3,
                "C",
                PlayerColor.GREEN,
                1,
                controlledTerritories3
        );
        Territory atackingTerritory = new Territory(
                "Western United States",
                otherPlayer,
                5,
                Continent.NORTH_AMERICA
        );
        Territory defendingTerritory = new Territory(
                "Eastern United States",
                defender,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories2.add(defendingTerritory);
        controlledTerritories3.add(atackingTerritory);
        attacker.setControlledTerritories(controlledTerritories1);
        defender.setControlledTerritories(controlledTerritories2);
        otherPlayer.setControlledTerritories(controlledTerritories3);
        assertFalse(attackService.canAttack(
                attacker,
                atackingTerritory,
                defendingTerritory)
        );
    }

    @Test
    void T3_shouldRejectAttackIfDefendingTerritoryIsOwnedByCurrentPlayer() {
        TerritoryAdjacencyService adjacencyService =
        new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlledTerritories1 = new ArrayList<>();
        Player attacker = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories1
        );
        Territory atackingTerritory = new Territory(
                "Western United States",
                attacker,
                5,
                Continent.NORTH_AMERICA
        );
        Territory defendingTerritory = new Territory(
                "Eastern United States",
                attacker,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(atackingTerritory);
        controlledTerritories1.add(defendingTerritory);
        attacker.setControlledTerritories(controlledTerritories1);
        assertFalse(attackService.canAttack(
                attacker,
                atackingTerritory,
                defendingTerritory)
        );
    }

    @Test
    void T4_shouldRejectAttackIfTerritoriesAreNotAdjacent() {
        TerritoryAdjacencyService adjacencyService =
        new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player attacker = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories1
        );
        Player defender = new Player(
                2,
                "B",
                PlayerColor.BLUE,
                1,
                controlledTerritories2
        );
        Territory atackingTerritory = new Territory(
                "Alberta",
                attacker,
                5,
                Continent.NORTH_AMERICA
        );
        Territory defendingTerritory = new Territory(
                "Eastern United States",
                defender,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(atackingTerritory);
        controlledTerritories2.add(defendingTerritory);
        attacker.setControlledTerritories(controlledTerritories1);
        defender.setControlledTerritories(controlledTerritories2);
        assertFalse(attackService.canAttack(
                attacker, 
                atackingTerritory, 
                defendingTerritory
        ));
    }

    @Test
    void T5_shouldRejectAttackIfAttackingTerritoryHasOnlyOneArmy() {
        TerritoryAdjacencyService adjacencyService =
        new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player attacker = new Player(
                1,
                "A",
                PlayerColor.RED,
                1,
                controlledTerritories1
        );
        Player defender = new Player(
                2,
                "B",
                PlayerColor.BLUE,
                1,
                controlledTerritories2
        );
        Territory atackingTerritory = new Territory(
                "Western United States",
                attacker,
                1,
                Continent.NORTH_AMERICA
        );
        Territory defendingTerritory = new Territory(
                "Eastern United States",
                defender,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(atackingTerritory);
        controlledTerritories2.add(defendingTerritory);
        attacker.setControlledTerritories(controlledTerritories1);
        defender.setControlledTerritories(controlledTerritories2);
        assertFalse(attackService.canAttack(
                attacker, 
                atackingTerritory, 
                defendingTerritory
        ));
    }

}

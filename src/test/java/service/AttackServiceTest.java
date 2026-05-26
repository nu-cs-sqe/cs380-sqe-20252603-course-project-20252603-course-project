package service;

import model.Continent;
import model.GameState;
import model.Player;
import model.Territory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackServiceTest {

    @Test
    void T1_shouldAllowAttackWhenTerritoriesAreAdjacentAndOwnedByDifferentPlayers(){
        TerritoryAdjacencyService adjacencyService = new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        Player attacker = new Player(1, "A", PlayerColor.RED, 1, controlled_territories1);
        Player defender = new Player(2, "B", PlayerColor.BLUE, 1, controlled_territories2);
        Territory atackingTerritory = new Territory("Western United States", attacker, 5, Continent.NORTH_AMERICA);
        Territory defendingTerritory = new Territory("Eastern United States", defender, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(atackingTerritory);
        controlled_territories2.add(defendingTerritory);
        attacker.setControlledTerritories(controlled_territories1);
        defender.setControlledTerritories(controlled_territories2);
        assertTrue(attackService.canAttack(attacker, atackingTerritory, defendingTerritory));
    }

    @Test
    void T2_shouldRejectAttackWhenAttackingTerritoryIsNotOwnedByCurrentPlayer(){
        TerritoryAdjacencyService adjacencyService = new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        List<Territory> controlled_territories3 = new ArrayList<>();
        Player attacker = new Player(1, "A", PlayerColor.RED, 1, controlled_territories1);
        Player defender = new Player(2, "B", PlayerColor.BLUE, 1, controlled_territories2);
        Player otherPlayer = new Player(3, "C", PlayerColor.GREEN, 1, controlled_territories3);
        Territory atackingTerritory = new Territory("Western United States", otherPlayer, 5, Continent.NORTH_AMERICA);
        Territory defendingTerritory = new Territory("Eastern United States", defender, 1, Continent.NORTH_AMERICA);
        controlled_territories2.add(defendingTerritory);
        controlled_territories3.add(atackingTerritory);
        attacker.setControlledTerritories(controlled_territories1);
        defender.setControlledTerritories(controlled_territories2);
        otherPlayer.setControlledTerritories(controlled_territories3);
        assertFalse(attackService.canAttack(attacker, atackingTerritory, defendingTerritory));
    }

    @Test
    void T3_shouldRejectAttackWhenDefendingTerritoryIsOwnedByCurrentPlayer(){
        TerritoryAdjacencyService adjacencyService = new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlled_territories1 = new ArrayList<>();
        Player attacker = new Player(1, "A", PlayerColor.RED, 1, controlled_territories1);
        Territory atackingTerritory = new Territory("Western United States", attacker, 5, Continent.NORTH_AMERICA);
        Territory defendingTerritory = new Territory("Eastern United States", attacker, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(atackingTerritory);
        controlled_territories1.add(defendingTerritory);
        attacker.setControlledTerritories(controlled_territories1);
        assertFalse(attackService.canAttack(attacker, atackingTerritory, defendingTerritory));
    }

    @Test
    void T4_shouldRejectAttackWhenTerritoriesAreNotAdjacent(){
        TerritoryAdjacencyService adjacencyService = new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        Player attacker = new Player(1, "A", PlayerColor.RED, 1, controlled_territories1);
        Player defender = new Player(2, "B", PlayerColor.BLUE, 1, controlled_territories2);
        Territory atackingTerritory = new Territory("Alberta", attacker, 5, Continent.NORTH_AMERICA);
        Territory defendingTerritory = new Territory("Eastern United States", defender, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(atackingTerritory);
        controlled_territories2.add(defendingTerritory);
        attacker.setControlledTerritories(controlled_territories1);
        defender.setControlledTerritories(controlled_territories2);
        assertFalse(attackService.canAttack(attacker, atackingTerritory, defendingTerritory));
    }

    @Test
    void T5_shouldRejectAttackWhenAttackingTerritoryHasOnlyOneArmy(){
        TerritoryAdjacencyService adjacencyService = new TerritoryAdjacencyService();
        AttackService attackService = new AttackService(adjacencyService);
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        Player attacker = new Player(1, "A", PlayerColor.RED, 1, controlled_territories1);
        Player defender = new Player(2, "B", PlayerColor.BLUE, 1, controlled_territories2);
        Territory atackingTerritory = new Territory("Western United States", attacker, 1, Continent.NORTH_AMERICA);
        Territory defendingTerritory = new Territory("Eastern United States", defender, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(atackingTerritory);
        controlled_territories2.add(defendingTerritory);
        attacker.setControlledTerritories(controlled_territories1);
        defender.setControlledTerritories(controlled_territories2);
        assertFalse(attackService.canAttack(attacker, atackingTerritory, defendingTerritory));
    }

}

package service;


import model.GamePhase;
import model.GameState;
import model.Player;
import model.Continent;
import service.ReinforcementService;
import java.util.ArrayList;
import java.util.List;
import model.Territory;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ReinforcementServiceTest {

    @Test
    void shouldAllowPlayerToPlaceReinforcementsOnOwnedTerritory() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 4, controlled_territories1);
        Territory t1 = new Territory("Indonesia", player1, 3, Continent.AUSTRALIA);
        Territory t2 = new Territory("Greenland", player1, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(t1);
        controlled_territories1.add(t2);
        player1.setControlledTerritories(controlled_territories1);
        int armies_we_will_place = 4;
        assertDoesNotThrow(() -> rs.placeReinforcements(player1, t1, armies_we_will_place, gameState));
    }

    @Test
    void shouldRejectPlacementOnTerritoryNotOwnedByPlayer() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 4, controlled_territories1);
        Player player2 = new Player(2, "B", "Blue", 4, controlled_territories2);
        Territory t1 = new Territory("Indonesia", player1, 3, Continent.AUSTRALIA);
        Territory t2 = new Territory("Greenland", player1, 1, Continent.NORTH_AMERICA);
        Territory t3 = new Territory("Peru", player2, 1, Continent.SOUTH_AMERICA);
        controlled_territories1.add(t1);
        controlled_territories1.add(t2);
        controlled_territories2.add(t3);
        player1.setControlledTerritories(controlled_territories1);
        player2.setControlledTerritories(controlled_territories2);
        int armies_we_will_place = 4;
        assertThrows(IllegalArgumentException.class, () -> {
            rs.placeReinforcements(player1, t3, armies_we_will_place, gameState);
        }, "Your army value or territory is not valid, please try again.");
    }

    @Test
    void shouldRejectPlacementWhenArmiesExceedRemainingReinforcements() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 4, controlled_territories1);
        Territory t1 = new Territory("Indonesia", player1, 3, Continent.AUSTRALIA);
        Territory t2 = new Territory("Greenland", player1, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(t1);
        controlled_territories1.add(t2);
        player1.setControlledTerritories(controlled_territories1);
        int armies_we_will_place = 5;
        assertThrows(IllegalArgumentException.class, () -> {
            rs.placeReinforcements(player1, t2, armies_we_will_place, gameState);
        }, "Your army value or territory is not valid, please try again.");
    }

    @Test
    void shouldDecreaseRemainingReinforcementsAfterValidPlacement() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 4, controlled_territories1);
        Territory t1 = new Territory("Indonesia", player1, 3, Continent.AUSTRALIA);
        Territory t2 = new Territory("Greenland", player1, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(t1);
        controlled_territories1.add(t2);
        player1.setControlledTerritories(controlled_territories1);
        int armies_we_will_place = 3;
        rs.placeReinforcements(player1, t1, armies_we_will_place, gameState);
        int remaining_army_count_post_place = player1.getRemainingArmiesToPlace();
        assertEquals(1, remaining_army_count_post_place);
    }
    void shouldReturnZeroBonusWhenPlayerControlsNoFullContinent() {
        // player has 5/6 in AFRICA continent, one in Asia, no bonus
        ReinforcementService rs = new ReinforcementService();

        GameState gameState = new GameState();
        List<Territory> controlled_territories = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 0, controlled_territories);
        Territory t1 = new Territory("Egypt", player1, 0, Continent.AFRICA);
        Territory t2 = new Territory("Congo", player1, 0, Continent.AFRICA);
        Territory t3 = new Territory("Madagascar", player1, 0, Continent.AFRICA);
        Territory t4 = new Territory("East Africa", player1, 0, Continent.AFRICA);
        Territory t5 = new Territory("South Africa", player1, 0, Continent.AFRICA);
        Territory t6 = new Territory("Japan", player1, 0, Continent.ASIA);
        controlled_territories.add(t1);
        controlled_territories.add(t2);
        controlled_territories.add(t3);
        controlled_territories.add(t4);
        controlled_territories.add(t5);
        controlled_territories.add(t6);
        player1.setControlledTerritories(controlled_territories);
        int actual_bonus = rs.calculateContinentBonus(player1, gameState);
        assertEquals(0, actual_bonus);
    }

    @Test
    void shouldReturnCorrectBonusWhenPlayerControlsOneContinent() {
        // player has control over one full continent plus one in every other continent
        // Africa
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 0, controlled_territories);
        Territory t1 = new Territory("Egypt", player1, 0, Continent.AFRICA);
        Territory t2 = new Territory("Congo", player1, 0, Continent.AFRICA);
        Territory t3 = new Territory("Madagascar", player1, 0, Continent.AFRICA);
        Territory t4 = new Territory("East Africa", player1, 0, Continent.AFRICA);
        Territory t5 = new Territory("South Africa", player1, 0, Continent.AFRICA);
        Territory t6 = new Territory("Japan", player1, 0, Continent.ASIA);
    }
    void shouldReturnMinimumReinforcementWhenPlayerOwnsFewTerritories() {
        // TC1
        ReinforcementService rs = new ReinforcementService();
        List<Territory> controlled_territories = new ArrayList<>();
        GameState gameState1 = new GameState();
        Player player1 = new Player(1, "A", "Red", 0, controlled_territories);
        Territory t1 = new Territory("Alaska", player1, 0, Continent.NORTH_AMERICA);
        Territory t2 = new Territory("Indonesia", player1, 0, Continent.AUSTRALIA);
        controlled_territories.add(t1);
        controlled_territories.add(t2);
        gameState1.setTerritories(controlled_territories);
        int actual_owned_terr = rs.calculateBaseReinforcements(player1, gameState1);
        assertEquals(3, actual_owned_terr);
    }

    @Test
    void shouldCalculateBaseReinforcementFromTerritoryCount() {
        // TC1
        ReinforcementService rs = new ReinforcementService();
        // include 9 territories

        List<Territory> controlled_territories = new ArrayList<>();
        GameState gameState = new GameState();
        Player player1 = new Player(1, "A", "Red", 0, controlled_territories);
        Territory t1 = new Territory("Alaska", player1, 0, Continent.NORTH_AMERICA);
        Territory t2 = new Territory("Alaska", player1, 0, Continent.NORTH_AMERICA);
        Territory t3 = new Territory("Central America", player1, 0, Continent.NORTH_AMERICA);
        Territory t4 = new Territory("Western United States", player1, 0, Continent.NORTH_AMERICA);
        Territory t5 = new Territory("Southern United States", player1, 0, Continent.NORTH_AMERICA);
        Territory t6 = new Territory("Quebec", player1, 0, Continent.NORTH_AMERICA);
        Territory t7 = new Territory("Ontario", player1, 0, Continent.NORTH_AMERICA);
        Territory t8 = new Territory("Greenland", player1, 0, Continent.NORTH_AMERICA);
        Territory t9 = new Territory("Peru", player1, 0, Continent.SOUTH_AMERICA);
        controlled_territories.add(t1);
        controlled_territories.add(t2);
        controlled_territories.add(t3);
        controlled_territories.add(t4);
        controlled_territories.add(t5);
        controlled_territories.add(t6);
        player1.setControlledTerritories(controlled_territories);
        int actual_bonus = rs.calculateContinentBonus(player1, gameState);
        assertEquals(0, actual_bonus);

        // Australia

        List<Territory> controlled_territories2 = new ArrayList<>();
        Player player2 = new Player(1, "A", "Red", 0, controlled_territories2);
        Territory t21 = new Territory("Western Australia", player1, 0, Continent.AUSTRALIA);
        Territory t22 = new Territory("Eastern Australia", player1, 0, Continent.AUSTRALIA);
        Territory t23 = new Territory("Indonesia", player1, 0, Continent.AUSTRALIA);
        Territory t24 = new Territory("New Guinea", player1, 0, Continent.AUSTRALIA);
        controlled_territories2.add(t21);
        controlled_territories2.add(t22);
        controlled_territories2.add(t23);
        controlled_territories2.add(t24);
        player2.setControlledTerritories(controlled_territories2);
        int actual_bonus2 = rs.calculateContinentBonus(player2, gameState);
        assertEquals(2, actual_bonus2);

        // South America
        List<Territory> controlled_territories3 = new ArrayList<>();
        Player player3 = new Player(1, "A", "Red", 0, controlled_territories3);
        Territory t31 = new Territory("Peru", player1, 0, Continent.SOUTH_AMERICA);
        Territory t32 = new Territory("Argentina", player1, 0, Continent.SOUTH_AMERICA);
        Territory t33 = new Territory("Venezuela", player1, 0, Continent.SOUTH_AMERICA);
        Territory t34 = new Territory("Brazil", player1, 0, Continent.SOUTH_AMERICA);
        controlled_territories3.add(t31);
        controlled_territories3.add(t32);
        controlled_territories3.add(t33);
        controlled_territories3.add(t34);
        player3.setControlledTerritories(controlled_territories3);
        int actual_bonus3 = rs.calculateContinentBonus(player3, gameState);
        assertEquals(2, actual_bonus3);

        // North America
        List<Territory> controlled_territories4 = new ArrayList<>();
        Player player4 = new Player(1, "A", "Red", 0, controlled_territories4);
        Territory t41 = new Territory("Alaska", player1, 0, Continent.NORTH_AMERICA);
        Territory t42 = new Territory("Northwest Territory", player1, 0, Continent.NORTH_AMERICA);
        Territory t43 = new Territory("Greenland", player1, 0, Continent.NORTH_AMERICA);
        Territory t44 = new Territory("Alberta", player1, 0, Continent.NORTH_AMERICA);
        Territory t45 = new Territory("Ontario", player1, 0, Continent.NORTH_AMERICA);
        Territory t46 = new Territory("Quebec", player1, 0, Continent.NORTH_AMERICA);
        Territory t47 = new Territory("Western United States", player1, 0, Continent.NORTH_AMERICA);
        Territory t48 = new Territory("Eastern United States", player1, 0, Continent.NORTH_AMERICA);
        Territory t49 = new Territory("Central America", player1, 0, Continent.NORTH_AMERICA);
        controlled_territories4.add(t41);
        controlled_territories4.add(t42);
        controlled_territories4.add(t43);
        controlled_territories4.add(t44);
        controlled_territories4.add(t45);
        controlled_territories4.add(t46);
        controlled_territories4.add(t47);
        controlled_territories4.add(t48);
        controlled_territories4.add(t49);
        player4.setControlledTerritories(controlled_territories4);
        int actual_bonus4 = rs.calculateContinentBonus(player4, gameState);
        assertEquals(5, actual_bonus4);

        // Europe
        List<Territory> controlled_territories5 = new ArrayList<>();
        Player player5 = new Player(1, "A", "Red", 0, controlled_territories5);
        Territory t51 = new Territory("Iceland", player1, 0, Continent.EUROPE);
        Territory t52 = new Territory("Great Britain", player1, 0, Continent.EUROPE);
        Territory t53 = new Territory("Western Europe", player1, 0, Continent.EUROPE);
        Territory t54 = new Territory("Eastern Europe", player1, 0, Continent.EUROPE);
        Territory t55 = new Territory("Ukraine", player1, 0, Continent.EUROPE);
        Territory t56 = new Territory("Southern Europe", player1, 0, Continent.EUROPE);
        Territory t57 = new Territory("Scandinavia", player1, 0, Continent.EUROPE);
        controlled_territories5.add(t51);
        controlled_territories5.add(t52);
        controlled_territories5.add(t53);
        controlled_territories5.add(t54);
        controlled_territories5.add(t55);
        controlled_territories5.add(t56);
        controlled_territories5.add(t57);
        player5.setControlledTerritories(controlled_territories5);
        int actual_bonus5 = rs.calculateContinentBonus(player5, gameState);
        assertEquals(5, actual_bonus5);

        // Asia
        List<Territory> controlled_territories6 = new ArrayList<>();
        Player player6 = new Player(1, "A", "Red", 0, controlled_territories6);
        Territory t61 = new Territory("Ural", player1, 0, Continent.ASIA);
        Territory t62 = new Territory("Siberia", player1, 0, Continent.ASIA);
        Territory t63 = new Territory("Yakutsk", player1, 0, Continent.ASIA);
        Territory t64 = new Territory("Kamchatka", player1, 0, Continent.ASIA);
        Territory t65 = new Territory("Irkutsk", player1, 0, Continent.ASIA);
        Territory t66 = new Territory("Afghanistan", player1, 0, Continent.ASIA);
        Territory t67 = new Territory("Mongolia", player1, 0, Continent.ASIA);
        Territory t68 = new Territory("Siam", player1, 0, Continent.ASIA);
        Territory t69 = new Territory("Western Europe", player1, 0, Continent.ASIA);
        Territory t610 = new Territory("Middle East", player1, 0, Continent.ASIA);
        Territory t611 = new Territory("Japan", player1, 0, Continent.ASIA);
        Territory t612 = new Territory("India", player1, 0, Continent.ASIA);
        Territory t613 = new Territory("China", player1, 0, Continent.ASIA);
        controlled_territories6.add(t61);
        controlled_territories6.add(t62);
        controlled_territories6.add(t63);
        controlled_territories6.add(t64);
        controlled_territories6.add(t65);
        controlled_territories6.add(t66);
        controlled_territories6.add(t67);
        controlled_territories6.add(t68);
        controlled_territories6.add(t69);
        controlled_territories6.add(t610);
        controlled_territories6.add(t611);
        controlled_territories6.add(t612);
        player6.setControlledTerritories(controlled_territories6);
        int actual_bonus6 = rs.calculateContinentBonus(player6, gameState);
        assertEquals(7, actual_bonus6);

    }

    @Test
    void shouldReturnCombinedBonusWhenPlayerControlsMultipleContinents() {
        // 1 player created, each controls 2 continents
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 0, controlled_territories);
        Territory t1 = new Territory("Egypt", player1, 0, Continent.AFRICA);
        Territory t2 = new Territory("Congo", player1, 0, Continent.AFRICA);
        Territory t3 = new Territory("Madagascar", player1, 0, Continent.AFRICA);
        Territory t4 = new Territory("East Africa", player1, 0, Continent.AFRICA);
        Territory t5 = new Territory("South Africa", player1, 0, Continent.AFRICA);
        Territory t6 = new Territory("Japan", player1, 0, Continent.AFRICA);
        Territory t21 = new Territory("Western Australia", player1, 0, Continent.AUSTRALIA);
        Territory t22 = new Territory("Eastern Australia", player1, 0, Continent.AUSTRALIA);
        Territory t23 = new Territory("Indonesia", player1, 0, Continent.AUSTRALIA);
        Territory t24 = new Territory("New Guinea", player1, 0, Continent.AUSTRALIA);
        controlled_territories.add(t1);
        controlled_territories.add(t2);
        controlled_territories.add(t3);
        controlled_territories.add(t4);
        controlled_territories.add(t5);
        controlled_territories.add(t6);
        controlled_territories.add(t21);
        controlled_territories.add(t22);
        controlled_territories.add(t23);
        controlled_territories.add(t24);
        player1.setControlledTerritories(controlled_territories);

        int actual_bonus = rs.calculateContinentBonus(player1, gameState);
        assertEquals(5, actual_bonus);

    }

    @Test
    void shouldUpdateTerritoryArmyCountAfterValidPlacement() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 4, controlled_territories1);
        Territory t1 = new Territory("Indonesia", player1, 3, Continent.AUSTRALIA);
        Territory t2 = new Territory("Greenland", player1, 1, Continent.NORTH_AMERICA);
        controlled_territories1.add(t1);
        controlled_territories1.add(t2);
        player1.setControlledTerritories(controlled_territories1);
        int armies_we_will_place = 2;
        rs.placeReinforcements(player1, t1, armies_we_will_place, gameState);
        int new_army_count_post_place = t1.getArmyCount();
        assertEquals(5, new_army_count_post_place);
    }
    void shouldNotGrantBonusWhenOneTerritoryInContinentIsOwnedByAnotherPlayer() {
        // one continent, 2 players, all but one go to one player
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlled_territories1 = new ArrayList<>();
        List<Territory> controlled_territories2 = new ArrayList<>();
        Player player1 = new Player(1, "A", "Red", 0, controlled_territories1);
        Player player2 = new Player(1, "A", "Red", 0, controlled_territories2);
        Territory t21 = new Territory("Western Australia", player1, 0, Continent.AUSTRALIA);
        Territory t22 = new Territory("Eastern Australia", player1, 0, Continent.AUSTRALIA);
        Territory t23 = new Territory("Indonesia", player1, 0, Continent.AUSTRALIA);
        Territory t24 = new Territory("New Guinea", player1, 0, Continent.AUSTRALIA);
        controlled_territories1.add(t21);
        controlled_territories1.add(t22);
        controlled_territories1.add(t23);
        controlled_territories2.add(t24);
        player1.setControlledTerritories(controlled_territories1);
        player2.setControlledTerritories(controlled_territories2);
        int actual_bonus2 = rs.calculateContinentBonus(player1, gameState);
        assertEquals(0, actual_bonus2);
    }
    @ParameterizedTest
    @CsvSource({"1,3",
            "9,3",
            "11,3",
            "12,4",
            "14,4",
            "15,5",
            "17,5",
            "18,6",
            "20,6",
            "21,7",
            "23,7",
            "24,8",
            "26,8",
            "27,9",
            "29,9",
            "30,10",
            "32,10",
            "33,11",
            "35,11",
            "36,12",
            "38,12",
            "39,13",
            "41,13",
            "42,14"})
    void shouldCalculateCorrectReinforcementAtBoundaryValues(int len, int expected) {
        ReinforcementService rs = new ReinforcementService();

        // TC1: 1 -- > 3 armies
        List<Territory> controlled_territories = new ArrayList<>();
        GameState gameState1 = new GameState();
        for (int i = 0; i < 42; i++) {
            Territory terr = new Territory();
            controlled_territories.add(terr);
        }
        List<Territory> first_group = controlled_territories.subList(0, len);
        Player player1 = new Player(1, "A", "Red", 0, first_group);
        int actual_owned_terr = rs.calculateBaseReinforcements(player1, gameState1);
        assertEquals(expected, actual_owned_terr);

    }

}

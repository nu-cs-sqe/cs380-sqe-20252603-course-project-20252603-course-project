package service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import model.Continent;
import model.GameState;
import model.Player;
import model.Territory;

public class ReinforcementServiceTest {

    @Test
    void shouldAllowPlayerToPlaceReinforcementsOnOwnedTerritory() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                4,
                controlledTerritories1
        );
        Territory t1 = new Territory(
                "Indonesia",
                player1,
                3,
                Continent.AUSTRALIA
        );
        Territory t2 = new Territory(
                "Greenland",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(t1);
        controlledTerritories1.add(t2);
        player1.setControlledTerritories(controlledTerritories1);
        int armiesToPlace = 4;
        assertDoesNotThrow(() -> rs.placeReinforcements(
                player1,
                t1,
                armiesToPlace,
                gameState
        )
        );
    }

    @Test
    void shouldRejectPlacementOnTerritoryNotOwnedByPlayer() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                4,
                controlledTerritories1
        );
        Player player2 = new Player(
                2, "B",
                PlayerColor.BLUE,
                4,
                controlledTerritories2
        );
        Territory t1 = new Territory(
                "Indonesia",
                player1,
                3,
                Continent.AUSTRALIA
        );
        Territory t2 = new Territory(
                "Greenland",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        Territory t3 = new Territory(
                "Peru",
                player2,
                1,
                Continent.SOUTH_AMERICA
        );
        controlledTerritories1.add(t1);
        controlledTerritories1.add(t2);
        controlledTerritories2.add(t3);
        player1.setControlledTerritories(controlledTerritories1);
        player2.setControlledTerritories(controlledTerritories2);
        int armiesToPlace = 4;
        assertThrows(IllegalArgumentException.class, () -> {
            rs.placeReinforcements(
                    player1,
                    t3,
                    armiesToPlace,
                    gameState
            );
        }, "Your army value or territory is not valid, "
                + "please try again."
        );
    }

    @Test
    void shouldRejectPlacementWhenArmiesExceedRemainingReinforcements() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                4,
                controlledTerritories1
        );
        Territory t1 = new Territory(
                "Indonesia",
                player1,
                3,
                Continent.AUSTRALIA
        );
        Territory t2 = new Territory(
                "Greenland",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(t1);
        controlledTerritories1.add(t2);
        player1.setControlledTerritories(controlledTerritories1);
        int armiesToPlace = 5;
        assertThrows(IllegalArgumentException.class, () -> {
            rs.placeReinforcements(player1, t2, armiesToPlace, gameState);
        }, "Your army value or territory is not valid, "
                + "please try again."
        );
    }

    @Test
    void shouldDecreaseRemainingReinforcementsAfterValidPlacement() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                4,
                controlledTerritories1
        );
        Territory t1 = new Territory(
                "Indonesia",
                player1,
                3,
                Continent.AUSTRALIA
        );
        Territory t2 = new Territory(
                "Greenland",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(t1);
        controlledTerritories1.add(t2);
        player1.setControlledTerritories(controlledTerritories1);
        int armiesToPlace = 3;
        rs.placeReinforcements(player1, t1, armiesToPlace, gameState);
        int remainingArmyCount = player1.getRemainingArmiesToPlace();
        assertEquals(1, remainingArmyCount);
    }

    @Test
    void shouldReturnZeroBonusWhenPlayerControlsNoFullContinent() {
        // player has 5/6 in AFRICA continent, one in Asia, no bonus
        ReinforcementService rs = new ReinforcementService();

        GameState gameState = new GameState();
        List<Territory> controlledTerritories = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories
        );
        Territory t1 = new Territory(
                "Egypt",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t2 = new Territory(
                "Congo",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t3 = new Territory(
                "Madagascar",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t4 = new Territory(
                "East Africa",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t5 = new Territory(
                "South Africa",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t6 = new Territory(
                "Japan",
                player1,
                0,
                Continent.ASIA
        );
        controlledTerritories.add(t1);
        controlledTerritories.add(t2);
        controlledTerritories.add(t3);
        controlledTerritories.add(t4);
        controlledTerritories.add(t5);
        controlledTerritories.add(t6);
        player1.setControlledTerritories(controlledTerritories);
        int actualBonus = rs.calculateContinentBonus(
                player1,
                gameState
        );
        assertEquals(0, actualBonus);
    }

    @Test
    void shouldReturnCorrectBonusWhenPlayerControlsOneContinent() {
        // player has control over one full continent
        // plus one in every other continent
        // Africa
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories
        );
        Territory t1 = new Territory(
                "Egypt",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t2 = new Territory(
                "Congo",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t3 = new Territory(
                "Madagascar",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t4 = new Territory(
                "East Africa",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t5 = new Territory(
                "South Africa",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t6 = new Territory(
                "North Africa",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t7 = new Territory(
                "Japan",
                player1,
                0,
                Continent.ASIA
        );
        controlledTerritories.add(t1);
        controlledTerritories.add(t2);
        controlledTerritories.add(t3);
        controlledTerritories.add(t4);
        controlledTerritories.add(t5);
        controlledTerritories.add(t6);
        controlledTerritories.add(t7);
        player1.setControlledTerritories(controlledTerritories);
        int actualBonus = rs.calculateContinentBonus(player1, gameState);
        assertEquals(3, actualBonus);
    }

    @Test
    void shouldReturnMinimumReinforcementWhenPlayerOwnsFewTerritories() {
        // TC1
        ReinforcementService rs = new ReinforcementService();
        List<Territory> controlledTerritories = new ArrayList<>();
        GameState gameState1 = new GameState();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories
        );
        Territory t1 = new Territory(
                "Alaska",
                player1,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t2 = new Territory(
                "Indonesia",
                player1,
                0,
                Continent.AUSTRALIA
        );
        controlledTerritories.add(t1);
        controlledTerritories.add(t2);
        gameState1.setTerritories(controlledTerritories);
        int actualOwnedTerr = rs.calculateBaseReinforcements(
                player1,
                gameState1
        );
        assertEquals(3, actualOwnedTerr);
    }

    @Test
    void shouldReturnZeroBonusForPartialNorthAmericaSet() {
        ReinforcementService rs = new ReinforcementService();
        // include 6 of 9 territories (partial ownership)

        List<Territory> controlledTerritories = new ArrayList<>();
        GameState gameState = new GameState();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories
        );
        Territory t1 = new Territory(
                "Alaska",
                player1,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t2 = new Territory(
                "Alaska",
                player1,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t3 = new Territory(
                "Central America",
                player1,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t4 = new Territory(
                "Western United States",
                player1,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t5 = new Territory(
                "Southern United States",
                player1,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t6 = new Territory(
                "Quebec",
                player1,
                0,
                Continent.NORTH_AMERICA
        );
        // other territories in continent not owned by player1,
        // so no bonus should be granted
//        Territory t7 = new Territory(
//                "Ontario",
//                player1,
//                0,
//                Continent.NORTH_AMERICA
//        );
//        Territory t8 = new Territory(
//                "Greenland",
//                player1,
//                0,
//                Continent.NORTH_AMERICA
//        );
//        Territory t9 = new Territory(
//                "Peru",
//                player1, 
//                0,
//                Continent.SOUTH_AMERICA
//        );

        controlledTerritories.add(t1);
        controlledTerritories.add(t2);
        controlledTerritories.add(t3);
        controlledTerritories.add(t4);
        controlledTerritories.add(t5);
        controlledTerritories.add(t6);
        player1.setControlledTerritories(controlledTerritories);

        int actualBonus = rs.calculateContinentBonus(player1, gameState);
        assertEquals(0, actualBonus);
    }

    @Test
    void shouldReturnAustraliaBonusWhenPlayerControlsAustralia() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player player2 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories2
        );
        Territory t21 = new Territory(
                "Western Australia",
                player2,
                0,
                Continent.AUSTRALIA
        );
        Territory t22 = new Territory(
                "Eastern Australia",
                player2,
                0,
                Continent.AUSTRALIA
        );
        Territory t23 = new Territory(
                "Indonesia",
                player2,
                0,
                Continent.AUSTRALIA
        );
        Territory t24 = new Territory(
                "New Guinea",
                player2,
                0,
                Continent.AUSTRALIA
        );
        controlledTerritories2.add(t21);
        controlledTerritories2.add(t22);
        controlledTerritories2.add(t23);
        controlledTerritories2.add(t24);
        player2.setControlledTerritories(controlledTerritories2);
        int actualBonus2 = rs.calculateContinentBonus(player2, gameState);
        assertEquals(2, actualBonus2);
    }

    @Test
    void shouldReturnSouthAmericaBonusWhenPlayerControlsSouthAmerica() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories3 = new ArrayList<>();
        Player player3 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories3
        );
        Territory t31 = new Territory(
                "Peru",
                player3,
                0,
                Continent.SOUTH_AMERICA
        );
        Territory t32 = new Territory(
                "Argentina",
                player3,
                0,
                Continent.SOUTH_AMERICA
        );
        Territory t33 = new Territory(
                "Venezuela",
                player3,
                0,
                Continent.SOUTH_AMERICA
        );
        Territory t34 = new Territory(
                "Brazil",
                player3,
                0,
                Continent.SOUTH_AMERICA
        );
        controlledTerritories3.add(t31);
        controlledTerritories3.add(t32);
        controlledTerritories3.add(t33);
        controlledTerritories3.add(t34);
        player3.setControlledTerritories(controlledTerritories3);
        int actualBonus3 = rs.calculateContinentBonus(player3, gameState);
        assertEquals(2, actualBonus3);
    }

    @Test
    void shouldReturnNorthAmericaBonusWhenPlayerControlsNorthAmerica() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories4 = new ArrayList<>();
        Player player4 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories4
        );
        Territory t41 = new Territory(
                "Alaska",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t42 = new Territory(
                "Northwest Territory",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t43 = new Territory(
                "Greenland",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t44 = new Territory(
                "Alberta",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t45 = new Territory(
                "Ontario",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t46 = new Territory(
                "Quebec",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t47 = new Territory(
                "Western United States",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t48 = new Territory(
                "Eastern United States",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        Territory t49 = new Territory(
                "Central America",
                player4,
                0,
                Continent.NORTH_AMERICA
        );
        controlledTerritories4.add(t41);
        controlledTerritories4.add(t42);
        controlledTerritories4.add(t43);
        controlledTerritories4.add(t44);
        controlledTerritories4.add(t45);
        controlledTerritories4.add(t46);
        controlledTerritories4.add(t47);
        controlledTerritories4.add(t48);
        controlledTerritories4.add(t49);
        player4.setControlledTerritories(controlledTerritories4);
        int actualBonus4 = rs.calculateContinentBonus(player4, gameState);
        assertEquals(5, actualBonus4);
    }

    @Test
    void shouldReturnEuropeBonusWhenPlayerControlsEurope() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories5 = new ArrayList<>();
        Player player5 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories5
        );
        Territory t51 = new Territory(
                "Iceland",
                player5,
                0,
                Continent.EUROPE
        );
        Territory t52 = new Territory(
                "Great Britain",
                player5,
                0,
                Continent.EUROPE
        );
        Territory t53 = new Territory(
                "Western Europe",
                player5,
                0,
                Continent.EUROPE
        );
        Territory t54 = new Territory(
                "Eastern Europe",
                player5,
                0,
                Continent.EUROPE
        );
        Territory t55 = new Territory(
                "Ukraine",
                player5,
                0,
                Continent.EUROPE
        );
        Territory t56 = new Territory(
                "Southern Europe",
                player5,
                0,
                Continent.EUROPE
        );
        Territory t57 = new Territory(
                "Scandinavia",
                player5,
                0,
                Continent.EUROPE
        );
        controlledTerritories5.add(t51);
        controlledTerritories5.add(t52);
        controlledTerritories5.add(t53);
        controlledTerritories5.add(t54);
        controlledTerritories5.add(t55);
        controlledTerritories5.add(t56);
        controlledTerritories5.add(t57);
        player5.setControlledTerritories(controlledTerritories5);
        int actualBonus5 = rs.calculateContinentBonus(player5, gameState);
        assertEquals(5, actualBonus5);
    }

    @Test
    void shouldReturnAsiaBonusWhenPlayerControlsAsia() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories6 = new ArrayList<>();
        Player player6 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories6
        );
        Territory t61 = new Territory(
                "Ural",
                player6,
                0,
                Continent.ASIA
        );
        Territory t62 = new Territory(
                "Siberia",
                player6,
                0,
                Continent.ASIA
        );
        Territory t63 = new Territory(
                "Yakutsk",
                player6,
                0,
                Continent.ASIA
        );
        Territory t64 = new Territory(
                "Kamchatka",
                player6,
                0,
                Continent.ASIA
        );
        Territory t65 = new Territory(
                "Irkutsk",
                player6,
                0,
                Continent.ASIA
        );
        Territory t66 = new Territory(
                "Afghanistan",
                player6,
                0,
                Continent.ASIA
        );
        Territory t67 = new Territory(
                "Mongolia",
                player6,
                0,
                Continent.ASIA
        );
        Territory t68 = new Territory(
                "Siam",
                player6,
                0,
                Continent.ASIA
        );
        Territory t69 = new Territory(
                "China",
                player6,
                0,
                Continent.ASIA
        );
        Territory t610 = new Territory(
                "Middle East",
                player6,
                0,
                Continent.ASIA
        );
        Territory t611 = new Territory(
                "Japan",
                player6,
                0,
                Continent.ASIA
        );
        Territory t612 = new Territory(
                "India",
                player6,
                0,
                Continent.ASIA
        );
        controlledTerritories6.add(t61);
        controlledTerritories6.add(t62);
        controlledTerritories6.add(t63);
        controlledTerritories6.add(t64);
        controlledTerritories6.add(t65);
        controlledTerritories6.add(t66);
        controlledTerritories6.add(t67);
        controlledTerritories6.add(t68);
        controlledTerritories6.add(t69);
        controlledTerritories6.add(t610);
        controlledTerritories6.add(t611);
        controlledTerritories6.add(t612);
        player6.setControlledTerritories(controlledTerritories6);
        int actualBonus6 = rs.calculateContinentBonus(player6, gameState);
        assertEquals(7, actualBonus6);
    }

    @Test
    void shouldReturnCombinedBonusWhenPlayerControlsMultipleContinents() {
        // 1 player created, each controls 2 continents
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories
        );
        Territory t1 = new Territory(
                "Egypt",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t2 = new Territory(
                "Congo",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t3 = new Territory(
                "Madagascar",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t4 = new Territory(
                "East Africa",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t5 = new Territory(
                "South Africa",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t6 = new Territory(
                "NorthAfrica",
                player1,
                0,
                Continent.AFRICA
        );
        Territory t21 = new Territory(
                "Western Australia",
                player1,
                0,
                Continent.AUSTRALIA
        );
        Territory t22 = new Territory(
                "Eastern Australia",
                player1,
                0,
                Continent.AUSTRALIA
        );
        Territory t23 = new Territory(
                "Indonesia",
                player1,
                0,
                Continent.AUSTRALIA
        );
        Territory t24 = new Territory(
                "New Guinea",
                player1,
                0,
                Continent.AUSTRALIA
        );
        controlledTerritories.add(t1);
        controlledTerritories.add(t2);
        controlledTerritories.add(t3);
        controlledTerritories.add(t4);
        controlledTerritories.add(t5);
        controlledTerritories.add(t6);
        controlledTerritories.add(t21);
        controlledTerritories.add(t22);
        controlledTerritories.add(t23);
        controlledTerritories.add(t24);
        player1.setControlledTerritories(controlledTerritories);

        int actualBonus = rs.calculateContinentBonus(player1, gameState);
        assertEquals(5, actualBonus);

    }

    @Test
    void shouldUpdateTerritoryArmyCountAfterValidPlacement() {
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                4,
                controlledTerritories1
        );
        Territory t1 = new Territory(
                "Indonesia",
                player1,
                3,
                Continent.AUSTRALIA
        );
        Territory t2 = new Territory(
                "Greenland",
                player1,
                1,
                Continent.NORTH_AMERICA
        );
        controlledTerritories1.add(t1);
        controlledTerritories1.add(t2);
        player1.setControlledTerritories(controlledTerritories1);
        int armiesToPlace = 2;
        rs.placeReinforcements(player1, t1, armiesToPlace, gameState);
        int newArmyCountPostPlace = t1.getArmyCount();
        assertEquals(5, newArmyCountPostPlace);
    }

    @Test
    void shouldNotGrantBonusWhenOneTerritoryInContinentOwnedByAnotherPlayer() {
        // one continent, 2 players, all but one go to one player
        ReinforcementService rs = new ReinforcementService();
        GameState gameState = new GameState();
        List<Territory> controlledTerritories1 = new ArrayList<>();
        List<Territory> controlledTerritories2 = new ArrayList<>();
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories1
        );
        Player player2 = new Player(
                2,
                "A",
                PlayerColor.RED,
                0,
                controlledTerritories2
        );
        Territory t21 = new Territory(
                "Western Australia",
                player1,
                0,
                Continent.AUSTRALIA
        );
        Territory t22 = new Territory(
                "Eastern Australia",
                player1,
                0,
                Continent.AUSTRALIA
        );
        Territory t23 = new Territory(
                "Indonesia",
                player1,
                0,
                Continent.AUSTRALIA
        );
        Territory t24 = new Territory(
                "New Guinea",
                player1,
                0,
                Continent.AUSTRALIA
        );
        controlledTerritories1.add(t21);
        controlledTerritories1.add(t22);
        controlledTerritories1.add(t23);
        controlledTerritories2.add(t24);
        player1.setControlledTerritories(controlledTerritories1);
        player2.setControlledTerritories(controlledTerritories2);
        int actualBonus2 = rs.calculateContinentBonus(player1, gameState);
        assertEquals(0, actualBonus2);
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
    final void shouldCalculateCorrectReinforcementAtBoundaryValues(
            final int len,
            final int expected
    ) {
        ReinforcementService rs = new ReinforcementService();

        // TC1: 1 -- > 3 armies
        List<Territory> controlledTerritories = new ArrayList<>();
        GameState gameState1 = new GameState();
        for (int i = 0; i < 42; i++) {
            Territory terr = new Territory();
            controlledTerritories.add(terr);
        }
        List<Territory> firstGroup = controlledTerritories.subList(0, len);
        Player player1 = new Player(
                1,
                "A",
                PlayerColor.RED,
                0,
                firstGroup
        );
        int actualOwnedTerr = rs.calculateBaseReinforcements(
                player1,
                gameState1
        );
        assertEquals(expected, actualOwnedTerr);

    }
}

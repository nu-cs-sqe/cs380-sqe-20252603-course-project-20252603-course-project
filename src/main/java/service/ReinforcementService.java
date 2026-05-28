package service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.TerritoryCatalog;
import model.Continent;
import model.GameState;
import model.Player;
import model.Territory;

public class ReinforcementService {

    private static final Map<Continent, Integer> CONTINENT_BONUSES = Map.of(
            Continent.ASIA, 7,
            Continent.EUROPE, 5,
            Continent.NORTH_AMERICA, 5,
            Continent.SOUTH_AMERICA, 2,
            Continent.AUSTRALIA, 2,
            Continent.AFRICA, 3
    );
    private static final int MIN_REINFORCEMENT = 3;
    private static final int REINFORCEMENTDIVIDER = 3;

    /**
     * Validates and performs placement of reinforcements, throws Illegal
     * Argument Exception is placement is invalid
     */
    void placeReinforcements(
            final Player player,
            final Territory territory,
            final int armies,
            final GameState gameState
    ) {
        boolean result = canPlaceReinforcements(player,
                territory,
                armies,
                gameState
        );
        if (result) {
            List<Territory> territories = player.getControlledTerritories();
            int territoryIndex = territories.indexOf(territory);
            Territory trFound = territories.get(territoryIndex);
            int currentArmiesUnplaced = player.getRemainingArmiesToPlace();
            int newRemainingArmies = currentArmiesUnplaced - armies;
            int currentTerritoriesPlaced = trFound.getArmyCount();
            int newTerritoryArmy = armies + currentTerritoriesPlaced;
            trFound.setArmyCount(newTerritoryArmy);
            player.setRemainingArmiesToPlace(newRemainingArmies);
        } else {
            throw new IllegalArgumentException(
                    "Your army value or territory is not valid, "
                    + "please try again."
            );
        }

    }

    /**
     * Determines if placement is valid: must not exceed remaining armies and
     * territory is owned by player
     */
    boolean canPlaceReinforcements(
            final Player player,
            final Territory territory,
            final int armies,
            final GameState gameState) {
        // get all territories owned by player
        List<Territory> territories = player.getControlledTerritories();
        int territoryIndex = territories.indexOf(territory);

        // if the territory is not found (index is -1)
        if (territoryIndex == -1) {
            return false;
        }
        // if the armies we want to place is
        // greater than the remaining armies of the player
        if (player.getRemainingArmiesToPlace() < armies) {
            return false;
        }
        return true;
    }

    /**
     * Calculates Continent Bonus: accumulates bonus for every continent a
     * player controls entirety
     */
    int calculateContinentBonus(
            final Player player,
            final GameState gameState
    ) {
        int totalBonuses = 0;
        for (Continent continent : Continent.values()) {
            if (controlsContinent(player, continent, gameState)) {
                totalBonuses += CONTINENT_BONUSES.get(continent);
            }
        }
        return totalBonuses;
    }

    /**
     * Determines if a player controls territories of an entire continent
     */
    boolean controlsContinent(
            final Player player,
            final Continent continent,
            final GameState gameState) {
        // get all controlled territories
        List<Territory> territories = player.getControlledTerritories();
        // filter controlled territories by the input continent,
        // and convert stream to a list
        List<Territory> newTerritories = territories.stream().filter(
                territory -> territory.getContinent() == continent
        ).collect(Collectors.toList());
        // get all territories in the provided continent
        List<String> continentTerritories
                = TerritoryCatalog.TERRITORIES_BY_CONTINENT.get(continent);
        return newTerritories.size() == continentTerritories.size();
    }

    /**
     * calculates reinforcements a player received based on current territories
     * owned
     */
    int calculateBaseReinforcements(
            final Player player,
            final GameState gameState
    ) {
        // get controlled territories
        int ownerTerritories = player.getControlledTerritoryCount();
        // take the territories divide by 3,
        // minimally, we should get three troops
        int baseReinforcements = Math.max(
                MIN_REINFORCEMENT,
                ownerTerritories / REINFORCEMENTDIVIDER
        );
        return baseReinforcements;
    }

}

package service;

import model.GameState;
import model.Player;
import model.GameState;
import model.Territory;
import model.Continent;
import domain.TerritoryCatalog;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class ReinforcementService {

    private static final Map<Continent, Integer> CONTINENT_BONUSES = Map.of(
            Continent.ASIA, 7,
            Continent.EUROPE, 5,
            Continent.NORTH_AMERICA, 5,
            Continent.SOUTH_AMERICA, 2,
            Continent.AUSTRALIA, 2,
            Continent.AFRICA, 3
    );
  
    /**
     * Validates and performs placement of reinforcements, throws Illegal Argument Exception is placement is invalid
     */
    void placeReinforcements(Player player, Territory territory, int armies, GameState gameState){
        boolean result = canPlaceReinforcements(player, territory, armies, gameState);
        if (result) {
            List<Territory> territories = player.getControlledTerritories();
            int territory_index = territories.indexOf(territory);
            Territory tr_found = territories.get(territory_index);
            int current_armies_unplaced = player.getRemainingArmiesToPlace();
            int new_remaining_armies = current_armies_unplaced - armies;
            int current_territories_placed = tr_found.getArmyCount();
            int new_territory_army = armies + current_territories_placed;
            tr_found.setArmyCount(new_territory_army);
            player.setRemainingArmiesToPlace(new_remaining_armies);
        }
        else {
            throw new IllegalArgumentException("Your army value or territory is not valid, please try again.");
        }

    }

    /**
     * Determines if placement is valid: must not exceed remaining armies and territory is owned by player
     */
    boolean canPlaceReinforcements(Player player, Territory territory, int armies, GameState gameState){
        // get all territories owned by player
        List<Territory> territories = player.getControlledTerritories();
        int territory_index = territories.indexOf(territory);

        // if the territory is not found (index is -1)
        if (territory_index == -1){
            return false;
        }
        // if the armies we want to place is greater than the remaining armies of the player
        if (player.getRemainingArmiesToPlace() < armies) {
            return false;
        }
        return true;
    }



    
    /**
     * Calculates Continent Bonus: accumulates bonus for every continent a player controls entirety
     */
    int calculateContinentBonus(Player player, GameState gameState){
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
    boolean controlsContinent(Player player, Continent continent, GameState gameState){
        // get all controlled territories
        List<Territory> territories = player.getControlledTerritories();
        // filter controlled territories by the input continent, and convert stream to a list
        List<Territory> new_territories = territories.stream().filter(territory -> territory.getContinent() == continent).collect(Collectors.toList());
        // get all territories in the provided continent
        List<String> continent_territories = TerritoryCatalog.TERRITORIES_BY_CONTINENT.get(continent);
        if (new_territories.size() == continent_territories.size()) {
            return true;
        }
        else{
            return false;
        }
    }




    /**
     * calculates reinforcements a player received based on current territories owned
     */
    int calculateBaseReinforcements(Player player, GameState gameState) {
        // get controlled territories
        int owner_territories = player.getControlledTerritoryCount();
        // take the territories divide by 3, minimally, we should get three troops
        int base_reinforcements = Math.max(3, owner_territories / 3);
        return base_reinforcements;
    }



}

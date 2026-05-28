package service;

import java.util.List;

import model.BattleResult;
import model.GameState;
import model.Player;
import model.Territory;

public final class TerritoryService {

    private TerritoryService() {
    }

    public static Territory findTerritoryByName(
            final GameState state,
            final String name
    ) {

        List<Territory> territories = state.getTerritories();

        for (Territory territory : territories) {
            if (territory.getName().equals(name)) {
                return territory;
            }
        }
        throw new IllegalArgumentException("territory not found: " + name);
    }

    public static boolean playerOwnsTerritory(
            final Player player,
            final GameState state,
            final String territoryName
    ) {

        Territory territory = findTerritoryByName(state, territoryName);
        if (territory.getOwner() == null) {
            return false;
        }
        return territory.getOwner().getId() == player.getId();
    }

    public static void applyBattleResult(
            final Territory from,
            final Territory to,
            final BattleResult result) {
        int attackerLosses = result.getAttackerLosses();
        int defenderLosses = result.getDefenderLosses();

        from.setArmyCount(from.getArmyCount() - attackerLosses);
        to.setArmyCount(to.getArmyCount() - defenderLosses);

    }

    public static void conquerTerritory(
            final Player attacker,
            final Territory from,
            final Territory to,
            final int armiesToMove,
            final GameState gameState) {
        // Validate input
        if (armiesToMove <= 0) {
            throw new IllegalArgumentException(
                    "armiesToMove must be greater than 0"
            );
        }

        if (from.getArmyCount() < armiesToMove + 1) {
            throw new IllegalArgumentException(
                    "attacking territory must keep at least 1 army behind"
            );
        }

        if (from.getOwner().getId() != attacker.getId()) {
            throw new IllegalArgumentException(
                    "attacker does not own the attacking territory"
            );
        }

        // Transfer ownership to the attacker
        Player defender = to.getOwner();
        to.setOwner(attacker);

        // Move armies from attacking territory to conquered territory
        from.setArmyCount(from.getArmyCount() - armiesToMove);
        to.setArmyCount(armiesToMove);

        // Update controlled territories
        attacker.addControlledTerritory(to);

        // If defender is null, the territory was unoccupied,
        // so we don't need to remove it
        // from the defender's controlled territories
        if (defender != null) {
            defender.removeControlledTerritory(to);
        }
    }
}

package service;

import model.GameState;
import model.Player;
import model.Territory;

public class FortifyService {

    public final boolean canFortify(
            final Player player,
            final Territory source,
            final Territory destination,
            final int armiesToMove,
            final GameState gameState) {
        if (source.getArmyCount() < armiesToMove
                || source.getArmyCount() <= 1
                || source.getArmyCount() == armiesToMove) {
            return false;
        }
        if (!source.getOwner().equals(player)) {
            return false;
        }
        if (!destination.getOwner().equals(player)) {
            return false;
        }
        return true;
    }

    public final boolean areConnectedThroughOwnedTerritories(
            final Player player,
            final Territory source,
            final Territory destination,
            final GameState gameState) {
        TerritoryAdjacencyService tas = new TerritoryAdjacencyService();
        return tas.areAdjacent(source, destination);
    }
}

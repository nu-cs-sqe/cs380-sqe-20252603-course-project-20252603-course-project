package service;

import model.Player;
import model.Territory;

public class AttackService {

    private final TerritoryAdjacencyService adjacencyService;

    public AttackService(final TerritoryAdjacencyService inAdjacencyService) {
        this.adjacencyService = inAdjacencyService;
    }

    public final boolean canAttack(
            final Player attacker,
            final Territory from,
            final Territory to) {
        if (from.getOwner() != attacker) {
            return false;
        }
        if (to.getOwner() == attacker) {
            return false;
        }
        if (!adjacencyService.areAdjacent(from, to)) {
            return false;
        }
        if (from.getArmyCount() <= 1) {
            return false;
        }
        return true;
    }

}

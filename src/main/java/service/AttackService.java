package service;

import model.BattleResult;
import model.Player;
import model.Territory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttackService {

    private final TerritoryAdjacencyService adjacencyService;
    private final DiceService diceService;
    private final Integer maxNumAttack = 3;
    private final Integer maxNumDefend = 2;

    public AttackService(
            final TerritoryAdjacencyService inAdjacencyService,
            final DiceService inDiceService
    ) {
        this.adjacencyService = inAdjacencyService;
        this.diceService = inDiceService;
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

    public final BattleResult resolveBattleRound(
            final Player attacker,
            final Territory from,
            final Territory to,
            final int attackerDice,
            final int defenderDice) {
        if (!canAttack(attacker, from, to)) {
            throw new IllegalArgumentException(
                    "Cannot proceed with the attack!"
            );
        }
        if (attackerDice > from.getArmyCount() - 1) {
            throw new IllegalArgumentException(
                    "The attacker has attempted to roll more dice "
                    + "than permitted based on territory holdings."
            );
        }
        if (attackerDice > maxNumAttack) {
            throw new IllegalArgumentException(
                    "The attacker may role at most " + maxNumAttack
                    + " dice. " + attackerDice
                    + " dice requested."
            );
        }
        if (defenderDice > to.getArmyCount()) {
            throw new IllegalArgumentException(
                    "The defender has attempted to roll more dice "
                    + "than permitted based on territory holdings."
            );
        }
        if (defenderDice > maxNumDefend) {
            throw new IllegalArgumentException(
                    "The defender may role at most " + maxNumDefend
                    + " dice. " + defenderDice
                    + " dice requested.");
        }
        List<Integer> attackerRolls = new ArrayList<>(
                sortDescending(diceService.multiRollDice(attackerDice))
        );
        List<Integer> defenderRolls = new ArrayList<>(
                sortDescending(diceService.multiRollDice(defenderDice))
        );
        int attackerLosses = 0;
        int defenderLosses = 0;
        for (int i = 0; i < Math.min(attackerDice, defenderDice); i++) {
            if (attackerRolls.get(i) > defenderRolls.get(i)) {
                defenderLosses += 1;
            } else {
                attackerLosses += 1;
            }
        }
        if (attackerLosses > 2) {
            throw new IllegalArgumentException(
                    "The attacker can never lose "
                    + "more than 2 armies in a single role. "
                    + "Armies requested to lose: " + attackerLosses
            );
        }
        BattleResult battleResult = new BattleResult(
                attackerLosses,
                defenderLosses
        );
        return battleResult;
    }

    public final List<Integer> sortDescending(
            final List<Integer> listOfRolls
    ) {
        List<Integer> descendingResult = new ArrayList<>(listOfRolls);
        descendingResult.sort(Collections.reverseOrder());
        return descendingResult;
    }

}

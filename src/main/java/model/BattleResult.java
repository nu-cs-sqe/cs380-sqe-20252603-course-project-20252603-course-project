package model;

public class BattleResult {

    private final int attackerLosses;
    private final int defenderLosses;

    public BattleResult(
            final int newAttackerLosses,
            final int newDefenderLosses
    ) {
        if (newAttackerLosses < 0 || newDefenderLosses < 0) {
            throw new IllegalArgumentException("Losses cannot be negative");
        }
        this.attackerLosses = newAttackerLosses;
        this.defenderLosses = newDefenderLosses;
    }

    public final int getAttackerLosses() {
        return attackerLosses;
    }

    public final int getDefenderLosses() {
        return defenderLosses;
    }
}

package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceService {

    private final Random rand;
    private final int maxRoll = 6;

    DiceService(final Random inRand) {
        this.rand = inRand;
    }

    public final int rollDice() {
        return rand.nextInt(maxRoll) + 1;
    }

    public final List<Integer> multiRollDice(
            final int numberOfRolls
    ) {
        if (numberOfRolls < 1) {
            throw new IllegalArgumentException(
                    "Number of Rolls must be at least 1. "
                    + "Received a request to roll: " + numberOfRolls
                    + " times.");
        }
        List<Integer> rollResults = new ArrayList<>();
        for (int i = 0; i < numberOfRolls; i++) {
            rollResults.add(rollDice());
        }
        return rollResults;
    }

}

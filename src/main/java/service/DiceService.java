package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceService {

    private final Random rand;

    DiceService(Random rand){
        this.rand = rand;
    }

    public int rollDice(){
        return rand.nextInt(6) + 1;
    }

    public List<Integer> multiRollDice(int numberOfRolls){
        if(numberOfRolls < 1){
            throw new IllegalArgumentException("Number of Rolls must be at least 1. Received a request to roll: " + numberOfRolls + " times.");
        }
        List<Integer> rollResults = new ArrayList<>();
        for(int i = 0; i < numberOfRolls; i++){
            rollResults.add(rollDice());
        }
        return rollResults;
    }

}

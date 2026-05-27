package service;

import java.util.Random;

public class DiceService {

    private Random rand = new Random();

    DiceService(Random rand){
        this.rand = rand;
    }

    public int rollDice(){
        return rand.nextInt(6) + 1;
    }

}

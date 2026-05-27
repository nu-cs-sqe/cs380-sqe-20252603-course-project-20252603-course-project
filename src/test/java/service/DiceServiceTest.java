package service;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DiceServiceTest {

    @Test
    public void T1_expectReturnMax(){
        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(5);
        EasyMock.replay(rand);

        DiceService dice = new DiceService(rand);
        assertEquals(6, dice.rollDice());
    }

    @Test
    public void T2_expectReturnMin(){
        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andStubReturn(0);
        EasyMock.replay(rand);

        DiceService dice = new DiceService(rand);
        assertEquals(1, dice.rollDice());
    }

}

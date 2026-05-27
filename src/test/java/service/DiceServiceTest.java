package service;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void T3_multiRoll2Rolls(){
        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(6)).andReturn(0);
        EasyMock.expect(rand.nextInt(6)).andReturn(2);
        EasyMock.expect(rand.nextInt(6)).andReturn(5);
        EasyMock.replay(rand);

        DiceService dice = new DiceService(rand);
        List<Integer> rollResults = new ArrayList<>();
        rollResults.add(1);
        rollResults.add(3);
        rollResults.add(6);
        assertEquals(rollResults, dice.multiRollDice(3));
    }

    @Test
    public void T4_multiRollLessThan1(){
        Random rand = EasyMock.createMock(Random.class);

        DiceService dice = new DiceService(rand);
        List<Integer> rollResults = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> dice.multiRollDice(0));
    }

}

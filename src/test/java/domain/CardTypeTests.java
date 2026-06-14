package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CardTypeTests {
    @Test
    public void CatCard1_ReturnsTrue(){
        CardType type = CardType.TACOCAT;
        assertTrue(type.canHaveTarget());
    }

    @Test
    public void CAT_CARD_2_ReturnsTrue(){
        CardType type = CardType.CAT_CARD_2;
        assertTrue(type.canHaveTarget());
    }

    @Test
    public void CAT_CARD_3_ReturnsTrue(){
        CardType type = CardType.CAT_CARD_3;
        assertTrue(type.canHaveTarget());
    }

    @Test
    public void CAT_CARD_4_ReturnsTrue(){
        CardType type = CardType.CAT_CARD_4;
        assertTrue(type.canHaveTarget());
    }

    @Test
    public void  NonCatCard_ReturnsFalse(){
        CardType type = CardType.SKIP;
        assertFalse(type.canHaveTarget());
    }
}

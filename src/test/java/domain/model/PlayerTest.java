package domain.model;

import domain.action.DefuseAction;
import domain.action.SkipAction;
import domain.enums.CardName;
import domain.enums.CardType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private static Card defuseCard() {
        return new Card(CardType.DEFUSE, CardName.DEFUSE, new DefuseAction());
    }

    private static Card skipCard() {
        return new Card(CardType.SKIP, CardName.SKIP, new SkipAction());
    }

    @Test
    void constructor_SetsIdAndName() {
        Player player = new Player("p1", "Alice");
        assertEquals("p1", player.getId());
        assertEquals("Alice", player.getName());
    }

    @Test
    void constructor_InitializesEmptyHand() {
        Player player = new Player("p1", "Alice");
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    void constructor_InitializesEmptyPeekBuffer() {
        Player player = new Player("p1", "Alice");
        assertTrue(player.getPeekCards().isEmpty());
    }

    @Test
    void addCard_EmptyHand_AddsCard() {
        Player player = new Player("p1", "Alice");
        Card card = defuseCard();
        player.addCard(card);
        assertEquals(1, player.getHand().size());
        assertSame(card, player.getHand().get(0));
    }

    @Test
    void addCard_NonEmptyHand_AppendsCard() {
        Player player = new Player("p1", "Alice");
        Card first = defuseCard();
        Card second = skipCard();
        player.addCard(first);
        player.addCard(second);
        assertEquals(2, player.getHand().size());
        assertSame(first, player.getHand().get(0));
        assertSame(second, player.getHand().get(1));
    }

    @Test
    void getHand_IsUnmodifiable() {
        Player player = new Player("p1", "Alice");
        assertThrows(UnsupportedOperationException.class, () -> player.getHand().add(defuseCard()));
    }

    @Test
    void getPeekCards_IsUnmodifiable() {
        Player player = new Player("p1", "Alice");
        assertThrows(UnsupportedOperationException.class, () -> player.getPeekCards().add(defuseCard()));
    }
}

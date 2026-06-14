package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTests {

    @Test
    void defaultConstructor(){
        Deck deck = new Deck();

        ArrayList<Card> cards = deck.getCards();

        Map<CardType, Integer> card_counts = new HashMap<>(Map.of(
                // Confirm features w/team
                CardType.ATTACK, 0,
                CardType.SKIP, 0,
                CardType.SEE_THE_FUTURE, 0,
                CardType.SHUFFLE, 0,
                CardType.NOPE, 0,
                CardType.TACOCAT, 0,
                CardType.CATERMELLON, 0,
                CardType.BEARD_CAT, 0,
                CardType.CAT_CARD_4, 0
        )
        );

        for (Card curr : cards) {
            CardType type = curr.getType();
            card_counts.put(type, card_counts.get(type) + 1);
        }

        assertEquals(3, card_counts.get(CardType.ATTACK));
        assertEquals(3, card_counts.get(CardType.SKIP));
        assertEquals(4, card_counts.get(CardType.SEE_THE_FUTURE));
        assertEquals(4, card_counts.get(CardType.SHUFFLE));
        assertEquals(4, card_counts.get(CardType.NOPE));
        assertEquals(4, card_counts.get(CardType.TACOCAT));
        assertEquals(4, card_counts.get(CardType.CATERMELLON));
        assertEquals(4, card_counts.get(CardType.BEARD_CAT));
        assertEquals(4, card_counts.get(CardType.CAT_CARD_4));
        assertEquals(34, cards.size());
    }

    @Test
    void emptyConstructor(){
        Deck deck = new Deck(0);
        ArrayList<Card> cards = deck.getCards();

        assertEquals(0, cards.size());
    }

    @Test
    void oneCardConstructor(){
        Deck deck = new Deck(1);
        ArrayList<Card> cards = deck.getCards();

        assertEquals(1, cards.size());
    }


    @Test
    void shuffleOnEmptyDeck(){
        Deck deck = new Deck(0);
        ArrayList<Card> initialCards = deck.getCards();
        int initialSize = deck.count();

        deck.shuffle();
        ArrayList<Card> shuffled_cards = deck.getCards();
        int shuffled_size = deck.count();

        assertEquals(0, initialSize);
        assertEquals(0, shuffled_size);
        assertEquals(initialCards, shuffled_cards);
    }

    @Test
    void shuffleOnDeckWithOneCard(){
        Deck deck = new Deck(1);
        ArrayList<Card> initialCards = deck.getCards();
        int initialSize = deck.count();

        deck.shuffle();
        ArrayList<Card> shuffled_cards = deck.getCards();
        int shuffled_size = deck.count();

        assertEquals(1, initialSize);
        assertEquals(1, shuffled_size);
        assertEquals(initialCards, shuffled_cards);
    }

    @Test
    void shuffleOnDefaultDeck(){
        Deck deck = new Deck();
        ArrayList<Card> initialCards = deck.getCards();
        int initialSize = deck.count();

        deck.shuffle();
        ArrayList<Card> shuffled_cards = deck.getCards();
        int shuffled_size = deck.count();

        assertEquals(34, initialSize);
        assertEquals(34, shuffled_size);
        assertTrue(shuffled_cards.containsAll(initialCards));
    }

    @Test
    void insertAtIndexNegativeOne(){
        Deck deck = new Deck();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            deck.insert(Card.createCard(CardType.TEST_TYPE), -1);
        });
    }

    @Test
    void insertAtIndexZero(){
        Deck deck = new Deck();
        int initialSize = deck.count();

        Card card = Card.createCard(CardType.TEST_TYPE);
        deck.insert(card, 0);
        ArrayList<Card> cards = deck.getCards();

        assertEquals(initialSize + 1, deck.count());
        assertSame(card, cards.get(0));
    }

    @Test
    void insertAtIndexOne(){
        Deck deck = new Deck();
        int initialSize = deck.count();

        Card card = Card.createCard(CardType.TEST_TYPE);
        deck.insert(card, 1);
        ArrayList<Card> cards = deck.getCards();

        assertEquals(initialSize + 1, deck.count());
        assertSame(card, cards.get(1));
    }

    @Test
    void insertAtIndexN(){
        Deck deck = new Deck();
        int initialSize = deck.count();

        Card card = Card.createCard(CardType.TEST_TYPE);
        deck.insert(card, initialSize);
        ArrayList<Card> cards = deck.getCards();

        assertEquals(initialSize + 1, deck.count());
        assertSame(card, cards.get(initialSize));
    }

    @Test
    void insertAtIndexNMinusOne(){
        Deck deck = new Deck();
        int initialSize = deck.count();

        Card card = Card.createCard(CardType.TEST_TYPE);
        deck.insert(card, initialSize - 1);
        ArrayList<Card> cards = deck.getCards();

        assertEquals(initialSize + 1, deck.count());
        assertSame(card, cards.get(initialSize - 1));
    }

    @Test
    void insertIntoEmptyDeck(){
        Deck deck = new Deck(0);

        Card card = Card.createCard(CardType.TEST_TYPE);
        deck.insert(card, 0);
        ArrayList<Card> cards = deck.getCards();

        assertEquals(1, deck.count());
        assertSame(card, cards.get(0));
    }

    @Test
    void discardOnlyCard() {
        Deck deck = new Deck(1);
        int initialSize = deck.count();

        Card cardToDiscard = deck.getCards().get(0);
        deck.discard(cardToDiscard);

        ArrayList<Card> cards = deck.getCards();
        assertEquals(initialSize - 1, deck.count());
        assertFalse(cards.contains(cardToDiscard));
    }

    @Test
    void discardFromEmptyDeck() {
        Deck deck = new Deck(0);
        Card cardToDiscard= Card.createCard(CardType.TEST_TYPE);

        assertThrows(IllegalArgumentException.class, () -> {
            deck.discard(cardToDiscard);
        });
        assertEquals(0, deck.count());
    }

    @Test
    void discardFirstCard() {
        Deck deck = new Deck();
        int initialSize = deck.count();

        Card cardToDiscard = deck.getCards().get(0);
        deck.discard(cardToDiscard);

        ArrayList<Card> cards = deck.getCards();
        assertEquals(initialSize - 1, deck.count());
        assertFalse(cards.contains(cardToDiscard));
    }

    @Test
    void discardLastCard() {
        Deck deck = new Deck();
        int initialSize = deck.count();

        Card cardToDiscard = deck.getCards().get(initialSize - 1);
        deck.discard(cardToDiscard);

        ArrayList<Card> cards = deck.getCards();
        assertEquals(initialSize - 1, deck.count());
        assertFalse(cards.contains(cardToDiscard));
    }

    @Test
    void discardCardNotInDeck(){
        Deck deck = new Deck();
        int initialSize = deck.count();

        Card cardToDiscard = Card.createCard(CardType.TEST_TYPE);

        assertThrows(IllegalArgumentException.class, () -> {
            deck.discard(cardToDiscard);
        });
        assertEquals(initialSize, deck.count());
    }

    @Test
    void takeTopCardOnOneCardDeck(){
        Deck deck = new Deck(1);
        ArrayList<Card> cards = deck.getCards();
        Card topCard = deck.takeTopCard();

        assertEquals(0, deck.count());
        assertEquals(cards.get(0), topCard);
    }

    @Test
    void takeTopCardOnMultipleCardDeck(){
        Deck deck = new Deck();
        ArrayList<Card> cards = deck.getCards();
        int originalSize = deck.count();
        Card topCard = deck.takeTopCard();

        assertEquals(originalSize - 1, deck.count());
        assertEquals(cards.get(0), topCard);
    }

    @Test
    void takeTopCardOnEmptyDeck(){
        Deck deck = new Deck(0);
        assertThrows(IllegalArgumentException.class, () -> {
            deck.takeTopCard();
        });
    }

    @Test
    void getCardsOnMultipleCardDeck(){
        Deck deck = new Deck();
        int initialSize = deck.count();

        ArrayList<Card> cards = deck.getCards();

        assertEquals(initialSize, cards.size());
        assertEquals(initialSize, deck.count());
    }

    @Test
    void getCardsOnOneCardDeck(){
        Deck deck = new Deck(1);

        ArrayList<Card> cards = deck.getCards();

        assertEquals(1, cards.size());
        assertEquals(1, deck.count());
    }

    @Test
    void getCardsOnEmptyDeck(){
        Deck deck = new Deck(0);

        ArrayList<Card> cards = deck.getCards();

        assertEquals(0, cards.size());
        assertEquals(0, deck.count());
    }

    @Test
    void countDeckWithOneCard(){
        Deck deck = new Deck(1);
        assertEquals(1, deck.count());
    }

    @Test
    void countEmptyDeck(){
        Deck deck = new Deck(0);
        assertEquals(0, deck.count());
    }

    @Test
    void countDeckWithNCards(){
        Deck deck = new Deck();
        assertEquals(34, deck.count());
    }
}
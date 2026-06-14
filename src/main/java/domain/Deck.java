package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Deck {
    private final List<Card> cards;
    private static final Map<CardType, Integer> card_counts = Map.of(
            // Confirm features w/team
            CardType.ATTACK, 3,
            CardType.SKIP, 3,
            CardType.SEE_THE_FUTURE, 4,
            CardType.SHUFFLE, 4,
            CardType.NOPE, 4,
            CardType.TACOCAT, 4,
            CardType.CATERMELLON, 4,
            CardType.BEARD_CAT, 4,
            CardType.HAIRY_POTATO_CAT, 4
    );

    public Deck(){
        this.cards = new ArrayList<>();

        for (Map.Entry<CardType, Integer> entry : card_counts.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                cards.add(Card.createCard(entry.getKey()));
            }
        }
    }

    public Deck(int num_cards){
        this.cards = new ArrayList<>();

        for (int i = 0; i < num_cards; i++){
            cards.add(Card.createCard(CardType.TACOCAT));
        }
    }

    public void shuffle(){
        Collections.shuffle(this.cards);
    }

    public void insert(Card card, int location){
        this.cards.add(location, card);
    }

    public void discard(Card card){
        if (!this.cards.contains(card)){
            throw new IllegalArgumentException("Card is not in the deck");
        }
        this.cards.remove(card);
    }

    public Card takeTopCard(){
        if (this.cards.size() == 0){
            throw new IllegalArgumentException("Cannot take a top card when no cards in deck.");
        }
        Card topCard = this.cards.get(0);
        this.discard(topCard);
        return topCard;
    }

    ArrayList<Card> getCards(){ // change name
        return new ArrayList<>(cards);
    }

    public int count(){
        return this.cards.size();
    }
}
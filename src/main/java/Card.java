public class Card {
    private final CardType type;

    public Card(CardType type) {
        if (type == null) {
                throw new IllegalArgumentException("need a card type!");
            }
        this.type = type;
    }

    public CardType getType() {
        return type;
    }
}

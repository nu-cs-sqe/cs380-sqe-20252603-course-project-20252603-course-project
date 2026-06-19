import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String playerName;
    private final List<Card> cards;
    private boolean isAlive;

    public Player(String playerName) {
        if (playerName == null) {
            throw new IllegalArgumentException("player name cannot be null");
        }
        if (playerName.isEmpty()) {
            throw new IllegalArgumentException("player name cannot be empty");
        }
        this.playerName = playerName;
        this.cards = new ArrayList<>();
        this.isAlive = true;
    }

    public String getPlayerName() { return playerName; }
    public List<Card> getCards() { return cards; }
    public boolean isAlive() { return isAlive; }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("card cannot be null");
        }

        cards.add(card);
    }

    public boolean hasDefuse() {
        for (Card card : cards) {
            if (card.getType() == CardType.DEFUSE) {
                return true;
            }
        }
        return false;
    }
}

package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Game {
    private final Deck deck;
    private final List<Player> totalPlayers;
    private final List<Player> alivePlayers;

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 5;
    private static final int NORMAL_CARDS_PER_PLAYER = 6; // + 1 DEFUSE

    Game(int playerCount) {
        this.deck = new Deck();

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            players.add(Player.createPlayer("Player " + i));
        }
        this.alivePlayers = new ArrayList<>(players);
        this.totalPlayers = new ArrayList<>(players);
    }

    public static Game createGame(int playerCount) {
        if (playerCount < MIN_PLAYERS) {
            throw new IllegalArgumentException("Cannot initiate game with less than 2 players");
        }
        if (playerCount > MAX_PLAYERS) {
            throw new IllegalArgumentException("Cannot initiate game with more than 5 players");
        }
        return new Game(playerCount);
    }

    public void setup() {
        int cardsNeeded = totalPlayers.size() * NORMAL_CARDS_PER_PLAYER;
        if (deck.count() < cardsNeeded) {
            throw new IllegalStateException("Not enough cards in deck to deal starting hands");
        }

        deck.shuffle();
        for (Player player : totalPlayers) {
            player.addCard(Card.createCard(CardType.DEFUSE));
            for (int i = 0; i < NORMAL_CARDS_PER_PLAYER; i++) {
                this.draw(player, this.deck);
            }
        }

        int kittensNeeded = totalPlayers.size() - 1;
        for (int i = 0; i < kittensNeeded; i++) {
            deck.insert(Card.createCard(CardType.EXPLODING_KITTEN), 0);
        }
        deck.shuffle();
    }

    public void draw(Player player, Deck deck) {
        Card cardToDraw = deck.takeTopCard();
        player.addCard(cardToDraw);
    }

    public void removeAlivePlayer(Player player) {
        if (!alivePlayers.contains(player)) {
            throw new IllegalArgumentException("Player is not in the alive players list");
        }
        player.kill();
        alivePlayers.remove(player);
    }

    public void addAlivePlayer(Player player) {
        if (!totalPlayers.contains(player)) {
            throw new IllegalArgumentException("Player not in game");
        }
        if (alivePlayers.contains(player)) {
            throw new IllegalArgumentException("Player already alive");
        }
        player.revive();
        alivePlayers.add(player);
    }

    Deck getDeck() {
        return this.deck;
    }

    public List<Player> getTotalPlayers() {
        return Collections.unmodifiableList(this.totalPlayers);
    }

    public List<Player> getAlivePlayers() {
        return Collections.unmodifiableList(this.alivePlayers);
    }

    public int getTotalPlayerCount() {
        return totalPlayers.size();
    }

    public int getAlivePlayerCount() {
        return alivePlayers.size();
    }

    void setAlivePlayersOrder(List<Player> newOrder) {
        if (newOrder.size() != this.alivePlayers.size()) {
            throw new IllegalArgumentException(
                    "New order must contain exact number of alive players");
        }

        this.alivePlayers.clear();
        this.alivePlayers.addAll(newOrder);
    }

    @Override
    protected final void finalize() {
    }
}



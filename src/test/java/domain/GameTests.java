package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {
    @ParameterizedTest
    @ValueSource(ints = {1, 6})
    void createGame_InvalidPlayerCount_ThrowsException(int invalidCount) {
        assertThrows(IllegalArgumentException.class, () -> {
            Game.createGame(invalidCount);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 5})
    void createGame_ValidPlayerCount_MakesGame(int validCount) {
        Game game = Game.createGame(validCount);
        assertEquals(validCount, game.getTotalPlayerCount());
        assertEquals(validCount, game.getAlivePlayerCount());
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1",
            "5, 4"
    })
    void setup_ValidNumPlayers_DealsCorrectCardsAndKittens(
            int playerCount, int expectedKittens) {
        Game game = Game.createGame(playerCount);
        game.setup();

        for (Player player : game.getTotalPlayers()) {
            assertEquals(7, player.getHandSize());
        }

        long actualKittens = game.getDeck().getCards().stream()
                .filter(card -> card.getType() == CardType.EXPLODING_KITTEN)
                .count();

        assertEquals(expectedKittens, actualKittens);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 20})
    void draw_FromValidDeck_UpdatesHandAndDeck(int deckSize) {
        Game game = Game.createGame(5);
        Deck deck = new Deck(deckSize);
        Card topCard = deck.getCards().get(0);
        Player player = Player.createPlayer("Test Name");

        game.draw(player, deck);

        assertTrue(player.hasCard(topCard.getType()));
        assertEquals(deckSize - 1, deck.count());
    }

    @Test
    void draw_FromEmptyDeck(){
        Game game = Game.createGame(5);
        Deck deck = new Deck(0);
        Player player = Player.createPlayer("Test Name");

        assertThrows(IllegalArgumentException.class, () -> {
            game.draw(player, deck);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 5})
    void getTotalPlayerCount_ValidBounds_ReturnsCorrectCount(int playerCount) {
        Game game = Game.createGame(playerCount);
        assertEquals(playerCount, game.getTotalPlayerCount());
    }

    @ParameterizedTest
    @CsvSource({
            "2, 0, 2",
            "2, 1, 1",
            "5, 4, 1"
    })
    void getAlivePlayerCount_AfterEliminations_ReturnsCount(
            int initialPlayers, int eliminations, int expectedRemaining) {
        Game game = Game.createGame(initialPlayers);

        for (int i = 0; i < eliminations; i++) {
            game.removeAlivePlayer(game.getAlivePlayers().get(0));
        }

        assertEquals(expectedRemaining, game.getAlivePlayerCount());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 5})
    void getTotalPlayers_ValidBounds_ReturnsCorrectListSize(int count) {
        Game game = Game.createGame(count);
        assertEquals(count, game.getTotalPlayers().size());
    }

    @ParameterizedTest
    @CsvSource({
            "5, 0, 5",
            "5, 1, 4"
    })
    void getAlivePlayers_AfterEliminations_ReturnsCorrectListSize(
            int initialPlayers, int eliminations, int expectedRemaining) {
        Game game = Game.createGame(initialPlayers);

        for (int i = 0; i < eliminations; i++) {
            game.removeAlivePlayer(game.getAlivePlayers().get(0));
        }

        List<Player> alivePlayers = game.getAlivePlayers();
        assertEquals(expectedRemaining, alivePlayers.size());
    }

    @Test
    void getAlivePlayers_ReturnsUnmodifiableList() {
        Game game = Game.createGame(5);
        List<Player> players = game.getAlivePlayers();

        assertThrows(UnsupportedOperationException.class, players::clear);
    }

    @Test
    void removeAlivePlayer_ManyPlayers_RemovesPlayer() {
        Game game = Game.createGame(5);
        Player playerToRemove = game.getAlivePlayers().get(0);
        game.removeAlivePlayer(playerToRemove);
        assertEquals(4, game.getAlivePlayerCount());
        assertFalse(game.getAlivePlayers().contains(playerToRemove));
    }

    @Test
    void removeAlivePlayer_OnePlayer_EmptiesList() {
        Game game = Game.createGame(2);
        game.removeAlivePlayer(game.getAlivePlayers().get(0));
        game.removeAlivePlayer(game.getAlivePlayers().get(0));
        assertEquals(0, game.getAlivePlayerCount());
    }

    @Test
    void removeAlivePlayer_EmptyList_ThrowsException() {
        Game game = Game.createGame(2);
        game.removeAlivePlayer(game.getAlivePlayers().get(0));
        game.removeAlivePlayer(game.getAlivePlayers().get(0));
        Player extraPlayer = Player.createPlayer("Extra");
        assertThrows(IllegalArgumentException.class, () -> {
            game.removeAlivePlayer(extraPlayer);
        });
    }

    @Test
    void removeAlivePlayer_PlayerNotInAliveList_ThrowsException() {
        Game game = Game.createGame(2);
        Player playerToRemove = game.getAlivePlayers().get(0);
        game.removeAlivePlayer(playerToRemove);
        assertThrows(IllegalArgumentException.class, () -> {
            game.removeAlivePlayer(playerToRemove);
        });
    }

    @Test
    void addAlivePlayer_NoAlivePlayers_AddsPlayer() {
        Game game = Game.createGame(2);
        Player playerToAdd = game.getAlivePlayers().get(0);
        game.removeAlivePlayer(game.getAlivePlayers().get(0));
        game.removeAlivePlayer(game.getAlivePlayers().get(0));
        game.addAlivePlayer(playerToAdd);
        assertEquals(1, game.getAlivePlayerCount());
    }

    @Test
    void addAlivePlayer_SomeAlivePlayers_AddsPlayer() {
        Game game = Game.createGame(5);
        Player playerToAdd = game.getAlivePlayers().get(0);
        game.removeAlivePlayer(playerToAdd);
        game.addAlivePlayer(playerToAdd);
        assertEquals(5, game.getAlivePlayerCount());
    }

    @Test
    void addAlivePlayer_PlayerNotInGame_ThrowsException() {
        Game game = Game.createGame(2);
        Player extraPlayer = Player.createPlayer("Extra");
        assertThrows(IllegalArgumentException.class, () -> {
            game.addAlivePlayer(extraPlayer);
        });
    }

    @Test
    void addAlivePlayer_PlayerAlreadyAlive_ThrowsException() {
        Game game = Game.createGame(2);
        Player alivePlayer = game.getAlivePlayers().get(0);
        assertThrows(IllegalArgumentException.class, () -> {
            game.addAlivePlayer(alivePlayer);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 5})
    void setAlivePlayersOrder_ValidBounds_UpdatesOrder(int playerCount) {
        Game game = Game.createGame(playerCount);

        List<Player> newOrder = new ArrayList<>(game.getAlivePlayers());
        java.util.Collections.reverse(newOrder);

        game.setAlivePlayersOrder(newOrder);
        assertEquals(newOrder, game.getAlivePlayers());
    }

    @Test
    void setAlivePlayersOrder_ShorterList_ThrowsException() {
        Game game = Game.createGame(4);
        List<Player> original = game.getAlivePlayers();

        List<Player> shortList = List.of(
                original.get(0), original.get(1), original.get(2)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            game.setAlivePlayersOrder(shortList);
        });
    }

    @Test
    void setAlivePlayersOrder_LongerList_ThrowsException() {
        Game game = Game.createGame(4);
        List<Player> original = game.getAlivePlayers();

        List<Player> longList = List.of(original.get(0), original.get(1),
                original.get(2), original.get(3), Player.createPlayer("Extra")
        );

        assertThrows(IllegalArgumentException.class, () -> {
            game.setAlivePlayersOrder(longList);
        });
    }
}
package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.GamePhase;
import model.GameState;
import model.Player;

public class GameSetupService {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    private List<Player> players = new ArrayList<>();

    public final void validatePlayerCount(final int playerCount) {
        if (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS) {
            throw new IllegalArgumentException(
                    "Player count must be between " + MIN_PLAYERS
                    + " and " + MAX_PLAYERS
                    + ", but got: " + playerCount
            );
        }
    }

    public final void validateUniqueColors(final List<PlayerColor> colors) {
        Set<PlayerColor> uniqueColors = new HashSet<>(colors);
        if (uniqueColors.size() != colors.size()) {
            throw new IllegalArgumentException(
                    "Not all colors are unique. Expected " + uniqueColors.size()
                    + " unique colors, but received "
                    + colors.size() + ".");
        }
    }

    public final List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public final void createPlayers(
            final List<String> names,
            final List<PlayerColor> colors
    ) {
        if (names.size() != colors.size()) {
            throw new IllegalArgumentException(
                    "Size of name list (" + names.size()
                    + ") and color list (" + colors.size()
                    + ") differ.");
        }
        int numberOfPlayers = names.size();
        validatePlayerCount(numberOfPlayers);
        validateUniqueColors(colors);
        players.clear();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(i,
                    names.get(i),
                    colors.get(i),
                    0, new ArrayList<>()));
        }
    }

    public final void initializeTurnOrder(final GameState gameState) {
        List<Player> playersTurnOrder = gameState.getPlayers();
        gameState.setTurnOrder(playersTurnOrder);
    }

    public final void startFirstTurn(final GameState gameState) {
        List<Player> playersTurnOrder = gameState.getTurnOrder();
        Player firstPlayer = playersTurnOrder.get(0);
        gameState.setCurrentPlayer(firstPlayer);
    }

    public final GameState createNewGame(
            final List<String> names,
            final List<PlayerColor> colors
    ) {

        GameState gameState = new GameState();
        createPlayers(names, colors);
        gameState.setPlayers(getPlayers());
        TerritoryAssignmentService tas = new TerritoryAssignmentService();
        tas.assignTerritories(gameState);

        return gameState;
    }

    public final GameState setupOrchestration(
            final List<String> preNames,
            final List<PlayerColor> colors
    ) {
        GameState gameState = createNewGame(preNames, colors);
        initializeTurnOrder(gameState);
        startFirstTurn(gameState);
        gameState.setCurrentPhase(GamePhase.REINFORCEMENT);
        return gameState;

    }
}

package model;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<Player> players;
    private List<Territory> territories;
    private List<Player> turnOrder;
    private Player currentPlayer;
    private GamePhase currentPhase;

    public GameState() {
        this.players = new ArrayList<>();
        this.territories = new ArrayList<>();
        this.turnOrder = new ArrayList<>();
    }

    public final List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public final void setPlayers(final List<Player> newPlayers) {
        this.players = List.copyOf(newPlayers);
    }

    public final List<Territory> getTerritories() {
        return List.copyOf(territories);
    }

    public final void setTerritories(final List<Territory> newTerritories) {
        this.territories = List.copyOf(newTerritories);
    }

    public final List<Player> getTurnOrder() {
        return List.copyOf(turnOrder);
    }

    public final void setTurnOrder(final List<Player> newTurnOrder) {
        this.turnOrder = List.copyOf(newTurnOrder);
    }

    public final Player getCurrentPlayer() {
        return currentPlayer;
    }

    public final void setCurrentPlayer(final Player newCurrentPlayer) {
        this.currentPlayer = newCurrentPlayer;
    }

    public final GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public final void setCurrentPhase(final GamePhase newCurrentPhase) {
        this.currentPhase = newCurrentPhase;
    }
}

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

	public List<Player> getPlayers() {
		return List.copyOf(players);
	}

	public void setPlayers(List<Player> players) {
		this.players = List.copyOf(players);
	}

	public List<Territory> getTerritories() {
		return List.copyOf(territories);
	}

	public void setTerritories(List<Territory> territories) {
		this.territories = List.copyOf(territories);
	}

	public List<Player> getTurnOrder() {
		return List.copyOf(turnOrder);
	}

	public void setTurnOrder(List<Player> turnOrder) {
		this.turnOrder = List.copyOf(turnOrder);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public GamePhase getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(GamePhase currentPhase) {
		this.currentPhase = currentPhase;
	}
}

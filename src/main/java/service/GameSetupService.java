package service;


import java.util.List;
import model.Player;
import model.Territory;
import model.GameState;
import model.GamePhase;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.Set;

import static domain.GameConstants.TOTAL_TERRITORIES;

public class GameSetupService{

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    private List<Player> Players = new ArrayList<>();

    // void initializeTurnOrder(GameState gameState)
    public void initializeTurnOrder(GameState gameState){
        List<Player> players_TO = gameState.getPlayers();
        // Collections.shuffle(players_TO);
        gameState.setTurnOrder(players_TO);
    public void validatePlayerCount(int playerCount) {
        if (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS){
            throw new IllegalArgumentException(
                    "Player count must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS + ", but got: " + playerCount
            );
        }
    }

    // void startFirstTurn(GameState gameState)
    public void startFirstTurn(GameState gameState){
        List<Player> players_TO = gameState.getTurnOrder();
        Player first_pl = players_TO.get(0);
        gameState.setCurrentPlayer(first_pl);
    }

    // GameState createNewGame(List<String> names, List<PlayerColor> colors)

    public GameState createNewGame(List<String> names, List<String> colors){
        // create a new game state
        GameState gameState = new GameState();
        // set the phase to setup
        // call player generation
        // call territory generation
        // call territory distribution
    public List<Player> getPlayers(){
        return Players;
    }

    public void createPlayers(List<String> names, List<PlayerColor> colors) {
        if (names.size() != colors.size()){
            throw new IllegalArgumentException("Size of name list (" + names.size() + ") and color list (" + colors.size() + ") differ.");
        }
        int numberOfPlayers = names.size();
        validatePlayerCount(numberOfPlayers);
        validateUniqueColors(colors);
        Players.clear();
        for(int i = 0; i < numberOfPlayers; i++){
            Players.add(new Player(i, names.get(i), colors.get(i), 0, new ArrayList<>()));
        }
    }

}

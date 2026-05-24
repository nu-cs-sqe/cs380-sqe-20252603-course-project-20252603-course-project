package service;

import model.GamePhase;
import model.GameState;
import model.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameSetupService {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    private List<Player> Players = new ArrayList<>();

    public void validatePlayerCount(int playerCount) {
        if (playerCount < MIN_PLAYERS || playerCount > MAX_PLAYERS){
            throw new IllegalArgumentException(
                    "Player count must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS + ", but got: " + playerCount
            );
        }
    }

    public void validateUniqueColors(List<PlayerColor> colors){
        Set<PlayerColor> uniqueColors = new HashSet<>(colors);
        if (uniqueColors.size() != colors.size()){
            throw new IllegalArgumentException("Not all colors are unique. Expected " + uniqueColors.size() + " unique colors, but received " + colors.size() + ".");
        }
    }

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

    public void initializeTurnOrder(GameState gameState){
        List<Player> players_TO = gameState.getPlayers();
        // Collections.shuffle(players_TO);
        gameState.setTurnOrder(players_TO);
    }

    public void startFirstTurn(GameState gameState){
        List<Player> players_TO = gameState.getTurnOrder();
        Player first_pl = players_TO.get(0);
        gameState.setCurrentPlayer(first_pl);
    }
    public GameState createNewGame(List<String> names, List<PlayerColor> colors){
        // create a new game state
        GameState gameState = new GameState();
        // call player generation
        createPlayers(names, colors);
        gameState.setPlayers(getPlayers());
        // call territory generation
        TerritoryAssignmentService TAS = new TerritoryAssignmentService();
        TAS.assignTerritories(gameState);

        return gameState;
    }
    public GameState orchestration(List<String> pre_names, List<PlayerColor> colors){
        GameState gameState = createNewGame(pre_names, colors);
        initializeTurnOrder(gameState);
        startFirstTurn(gameState);
        gameState.setCurrentPhase(GamePhase.REINFORCEMENT);
        return gameState;

    }
    }




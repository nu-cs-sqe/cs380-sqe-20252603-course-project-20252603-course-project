package service;

import model.Player;
import model.Territory;
import model.GameState;


public class FortifyService {


    public boolean canFortify(Player player, Territory source, Territory destination, int armiesToMove, GameState gameState){
        if (source.getArmyCount() < armiesToMove || source.getArmyCount() <= 1 || source.getArmyCount() == armiesToMove){
            return false;
        }
        if (!source.getOwner().equals(player)){
            return false;
        }
        if (!destination.getOwner().equals(player)){
            return false;
        }
        return true;
    }
    
    public boolean areConnectedThroughOwnedTerritories(Player player, Territory source, Territory destination, GameState gameState){
        TerritoryAdjacencyService TAS = new TerritoryAdjacencyService();
        return TAS.areAdjacent(source, destination);
    }
}

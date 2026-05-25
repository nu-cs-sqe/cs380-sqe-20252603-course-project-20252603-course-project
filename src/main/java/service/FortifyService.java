package service;

import model.Player;
import model.Territory;
import model.GameState;


public class FortifyService {


    public boolean canFortify(Player player, Territory source, Territory destination, int armiesToMove, GameState gameState){
        if (source.getArmyCount() < armiesToMove){
            System.out.println("cond 1 early return");
            return false;
        }
        if (!source.getOwner().equals(player)){
            return false;
        }
        if (!destination.getOwner().equals(player)){
            System.out.println("cond 3 early return");
            return false;
        }
        return true;
    }
    public boolean areConnectedThroughOwnedTerritories(Player player, Territory source, Territory destination, GameState gameState){
        TerritoryAdjacencyService TAS = new TerritoryAdjacencyService();
        if (TAS.areAdjacent(source, destination)){
            return true;
        }
        return false;
    }


}

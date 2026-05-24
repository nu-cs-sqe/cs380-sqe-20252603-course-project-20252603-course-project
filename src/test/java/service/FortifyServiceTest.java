package service;


import model.GamePhase;
import model.GameState;
import model.Player;
import model.Continent;
import service.ReinforcementService;
import java.util.ArrayList;
import java.util.List;
import model.Territory;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FortifyServiceTest {

    @Test
    void shouldAllowFortifyBetweenConnectedOwnedTerritories() {
        GameState gameState = new GameState();
        Player player1 = new Player(1, );
        Territory t1 = new Territory("Western United States");
    }

    @Test
    void shouldRejectFortifyWhenSourceIsNotOwnedByPlayer() {

    }

    @Test
    void shouldRejectFortifyWhenDestinationIsNotOwnedByPlayer(){

    }

    @Test
    void shouldRejectFortifyWhenTerritoriesAreNotConnected(){

    }

    @Test
    void shouldRejectFortifyWhenSourceHasOnlyOneArmy(){

    }

    @Test
    void shouldRejectFortifyWhenMoveWouldLeaveSourceEmpty(){
        
    }
}

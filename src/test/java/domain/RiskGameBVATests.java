package domain;

import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class RiskGameBVATests {

    private Map<PlayerColor, String> threePlayerMap() {
        Map<PlayerColor, String> map = new LinkedHashMap<>();
        map.put(PlayerColor.RED, "Alice");
        map.put(PlayerColor.BLUE, "Bob");
        map.put(PlayerColor.GREEN, "Carol");
        return map;
    }

    private Random stubbedRandom(int returnValue) {
        Random rand = EasyMock.createMock(Random.class);
        EasyMock.expect(rand.nextInt(EasyMock.anyInt())).andStubReturn(returnValue);
        EasyMock.replay(rand);
        return rand;
    }

    // BVA 1 — playerInfo null (below the valid-input boundary)
    @Test
    public void Constructor_NullPlayerInfo_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new RiskGame(null));
    }

    // BVA 2 — territoriesClaimed = 41 (TOTAL_TERRITORIES - 1): phase must still be SCRAMBLE
    @Test
    public void ClaimTerritory_FortyFirstClaim_PhaseRemainsScramble() {
        RiskGame game = new RiskGame(threePlayerMap(), stubbedRandom(0));
        WorldMap mockMap = EasyMock.createMock(WorldMap.class);
        mockMap.claim(EasyMock.anyObject(), EasyMock.anyObject());
        EasyMock.expectLastCall().times(41);
        mockMap.addArmies(EasyMock.anyObject(), EasyMock.anyInt());
        EasyMock.expectLastCall().times(41);
        EasyMock.replay(mockMap);
        game.provideWorldMap(mockMap);
        TerritoryName[] territories = TerritoryName.values();
        for (int i = 0; i < 41; i++) {
            game.claimTerritory(territories[i]);
        }
        assertEquals(GamePhase.SCRAMBLE, game.getPhase());
        EasyMock.verify(mockMap);
    }

    // BVA 3 — currentPlayerIndex wraps from last player back to first after a claim
    // With stubbedRandom(0) the order is RED(0) → BLUE(1) → GREEN(2) → RED(0)
    @Test
    public void ClaimTerritory_LastPlayerInListClaims_CurrentPlayerWrapsToFirst() {
        RiskGame game = new RiskGame(threePlayerMap(), stubbedRandom(0));
        WorldMap mockMap = EasyMock.createMock(WorldMap.class);
        mockMap.claim(EasyMock.anyObject(), EasyMock.anyObject());
        EasyMock.expectLastCall().times(3);
        mockMap.addArmies(EasyMock.anyObject(), EasyMock.anyInt());
        EasyMock.expectLastCall().times(3);
        EasyMock.replay(mockMap);
        game.provideWorldMap(mockMap);
        TerritoryName[] territories = TerritoryName.values();
        game.claimTerritory(territories[0]); // RED's turn → advances to BLUE
        game.claimTerritory(territories[1]); // BLUE's turn → advances to GREEN
        game.claimTerritory(territories[2]); // GREEN's turn (last) → must wrap to RED
        assertEquals(PlayerColor.RED, game.getCurrentPlayerColor());
        EasyMock.verify(mockMap);
    }

    // BVA 4 — armiesToPlace = 1 (minimum valid value): placeArmy must succeed and leave 0 armies
    @Test
    public void PlaceArmy_PlayerWithOneArmyRemaining_PlacesSuccessfully() {
        RiskGame game = new RiskGame(threePlayerMap(), stubbedRandom(0));
        Player redPlayer = new Player(PlayerColor.RED, "Alice", 1);
        Player bluePlayer = new Player(PlayerColor.BLUE, "Bob", 0);
        Player greenPlayer = new Player(PlayerColor.GREEN, "Carol", 0);
        game.providePlayers(List.of(redPlayer, bluePlayer, greenPlayer));
        game.setPhase(GamePhase.SETUP);
        game.setCurrentPlayer(PlayerColor.RED);
        WorldMap mockMap = EasyMock.createMock(WorldMap.class);
        EasyMock.expect(mockMap.isOwnedBy(TerritoryName.ALASKA, PlayerColor.RED))
                .andStubReturn(true);
        mockMap.addArmies(TerritoryName.ALASKA, 1);
        EasyMock.expectLastCall();
        EasyMock.replay(mockMap);
        game.provideWorldMap(mockMap);
        assertDoesNotThrow(() -> game.placeArmy(TerritoryName.ALASKA));
        assertEquals(0, redPlayer.getArmiesToPlace());
        EasyMock.verify(mockMap);
    }

    // BVA 5 — isSetupComplete boundary: exactly one player still has 1 army → must return false
    @Test
    public void IsSetupComplete_ExactlyOnePlayerHasOneArmyLeft_ReturnsFalse() {
        RiskGame game = new RiskGame(threePlayerMap(), stubbedRandom(0));
        Player redPlayer = new Player(PlayerColor.RED, "Alice", 1);
        Player bluePlayer = new Player(PlayerColor.BLUE, "Bob", 0);
        Player greenPlayer = new Player(PlayerColor.GREEN, "Carol", 0);
        game.providePlayers(List.of(redPlayer, bluePlayer, greenPlayer));
        assertFalse(game.isSetupComplete());
    }
}

package service;

import model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameSetupServiceTest {

    @Test
    void TC1_ValidateMinimumSizeGame() {
        GameSetupService service = new GameSetupService();
        int playerCount = 2;
        assertDoesNotThrow(() -> service.validatePlayerCount(playerCount));
    }

    @Test
    void TC2_ValidateBelowMinimumSizeGame() {
        GameSetupService service = new GameSetupService();
        int playerCount = 1;
        assertThrows(IllegalArgumentException.class,
                () -> service.validatePlayerCount(playerCount));
    }

    @Test
    void TC3_ValidateMaximumSizeGame() {
        GameSetupService service = new GameSetupService();
        int playerCount = 6;
        assertDoesNotThrow(() -> service.validatePlayerCount(playerCount));
    }

    @Test
    void TC4_ValidateAboveMaximumSizeGame() {
        GameSetupService service = new GameSetupService();
        int playerCount = 7;
        assertThrows(IllegalArgumentException.class,
                () -> service.validatePlayerCount(playerCount));
    }

    @Test
    void TC5_ValidateINT_MAX() {
        GameSetupService service = new GameSetupService();
        int playerCount = Integer.MAX_VALUE;
        assertThrows(IllegalArgumentException.class,
                () -> service.validatePlayerCount(playerCount));
    }

    @Test
    void TC6_ValidateINT_MIN() {
        GameSetupService service = new GameSetupService();
        int playerCount = Integer.MIN_VALUE;
        assertThrows(IllegalArgumentException.class,
                () -> service.validatePlayerCount(playerCount));
    }

    @Test
    void TC7_ValidateInRangeOfValidGame() {
        GameSetupService service = new GameSetupService();
        int playerCount = 4;
        assertDoesNotThrow(() -> service.validatePlayerCount(playerCount));
    }

    @Test
    void TC1_CreatePlayersBasic(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of("P1", "P2", "P3"));
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW));
        assertDoesNotThrow(() -> service.createPlayers(names, colors));
        List<Player> players = service.getPlayers();
        assertEquals(3, players.size());
        assertEquals("P1", players.get(0).getName());
        assertEquals("P2", players.get(1).getName());
        assertEquals("P3", players.get(2).getName());
        assertEquals(PlayerColor.BLUE, players.get(0).getColor());
        assertEquals(PlayerColor.GREEN, players.get(1).getColor());
        assertEquals(PlayerColor.YELLOW, players.get(2).getColor());
    }

    @Test
    void TC2_CreatePlayersUnequalListLengthSmallerNames(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of("P1", "P2"));
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW));
        assertThrows(IllegalArgumentException.class,
                () -> service.createPlayers(names, colors));
    }

    @Test
    void TC3_CreatePlayersUnequalListLengthSmallerColors(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of("P1", "P2", "P3"));
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN));
        assertThrows(IllegalArgumentException.class,
                () -> service.createPlayers(names, colors));
    }

    @Test
    void TC4_CreatePlayersBothListsEmpty(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of());
        List<PlayerColor> colors = new ArrayList<>(List.of());
        assertThrows(IllegalArgumentException.class,
                () -> service.createPlayers(names, colors));
    }

    @Test
    void TC5_CreatePlayersBothListsOversized(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of("P1", "P2", "P3", "P4", "P5", "P6", "P7"));
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.BLACK, PlayerColor.GREEN, PlayerColor.RED, PlayerColor.GREEN));
        assertThrows(IllegalArgumentException.class,
                () -> service.createPlayers(names, colors));
    }

    @Test
    void TC6_CreatePlayersBasicRepeatNames(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of("P1", "P1", "P3"));
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW));
        assertDoesNotThrow(() -> service.createPlayers(names, colors));
        List<Player> players = service.getPlayers();
        assertEquals(3, players.size());
        assertEquals("P1", players.get(0).getName());
        assertEquals("P1", players.get(1).getName());
        assertEquals("P3", players.get(2).getName());
        assertEquals(PlayerColor.BLUE, players.get(0).getColor());
        assertEquals(PlayerColor.GREEN, players.get(1).getColor());
        assertEquals(PlayerColor.YELLOW, players.get(2).getColor());
    }

    @Test
    void TC7_CreatePlayersBasicRepeatColors(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of("P1", "P1", "P3"));
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.BLUE, PlayerColor.YELLOW));
        assertThrows(IllegalArgumentException.class,
                () -> service.createPlayers(names, colors));
    }

    @Test
    void TC8_CreatePlayersBasicMaxLen(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of("John", "Mike", "Tom", "rus", "321", "SAM!"));
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED, PlayerColor.BLACK, PlayerColor.WHITE));
        assertDoesNotThrow(() -> service.createPlayers(names, colors));
        List<Player> players = service.getPlayers();
        assertEquals(6, players.size());
        assertEquals("John", players.get(0).getName());
        assertEquals("Mike", players.get(1).getName());
        assertEquals("Tom", players.get(2).getName());
        assertEquals("rus", players.get(3).getName());
        assertEquals("321", players.get(4).getName());
        assertEquals("SAM!", players.get(5).getName());
        assertEquals(PlayerColor.BLUE, players.get(0).getColor());
        assertEquals(PlayerColor.GREEN, players.get(1).getColor());
        assertEquals(PlayerColor.YELLOW, players.get(2).getColor());
        assertEquals(PlayerColor.RED, players.get(3).getColor());
        assertEquals(PlayerColor.BLACK, players.get(4).getColor());
        assertEquals(PlayerColor.WHITE, players.get(5).getColor());
    }

    @Test
    void TC9_CreatePlayersBasicMinLen(){
        GameSetupService service = new GameSetupService();
        List<String> names = new ArrayList<>(List.of("John", "Mike"));
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN));
        assertDoesNotThrow(() -> service.createPlayers(names, colors));
        List<Player> players = service.getPlayers();
        assertEquals(2, players.size());
        assertEquals("John", players.get(0).getName());
        assertEquals("Mike", players.get(1).getName());
        assertEquals(PlayerColor.BLUE, players.get(0).getColor());
        assertEquals(PlayerColor.GREEN, players.get(1).getColor());
    }

    @Test
    void TC1_ValidateUniqueColorsNoRepeats(){
        GameSetupService service = new GameSetupService();
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN));
        assertDoesNotThrow(() -> service.validateUniqueColors(colors));
    }

    @Test
    void TC2_ValidateUniqueColorsRepeats(){
        GameSetupService service = new GameSetupService();
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.BLUE));
        assertThrows(IllegalArgumentException.class, () -> service.validateUniqueColors(colors));
    }

    @Test
    void TC3_ValidateUniqueColorsEmpty(){
        GameSetupService service = new GameSetupService();
        List<PlayerColor> colors = new ArrayList<>(List.of());
        assertDoesNotThrow(() -> service.validateUniqueColors(colors));
    }

    @Test
    void TC4_ValidateUniqueColorsManyRepeats(){
        GameSetupService service = new GameSetupService();
        List<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.BLACK, PlayerColor.GREEN, PlayerColor.RED, PlayerColor.GREEN));
        assertThrows(IllegalArgumentException.class, () -> service.validateUniqueColors(colors));
    }

    @Test
    void TC3_ValidateMaximumSizeGame() {
        GameSetupService service = new GameSetupService();
        int playerCount = 6;
        assertTrue(service.validatePlayerCount(playerCount));
    }

    @Test
    void TC4_ValidateAboveMaximumSizeGame() {
        GameSetupService service = new GameSetupService();
        int playerCount = 7;
        assertFalse(service.validatePlayerCount(playerCount));
    }

    @Test
    void TC5_ValidateINT_MAX() {
        GameSetupService service = new GameSetupService();
        int playerCount = Integer.MAX_VALUE;
        assertFalse(service.validatePlayerCount(playerCount));
    }

    @Test
    void TC6_ValidateINT_MIN() {
        GameSetupService service = new GameSetupService();
        int playerCount = Integer.MIN_VALUE;
        assertFalse(service.validatePlayerCount(playerCount));
    }


}

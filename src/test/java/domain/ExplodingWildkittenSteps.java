package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExplodingWildkittenSteps {

    private Game game;
    private GameController gameController;
    private Player currentPlayer;

    @Given("a game with {int} players")
    public void aGameWithPlayers(int playerCount) {
        game = Game.createGame(playerCount);
        gameController = new GameController(game);
        currentPlayer = game.getAlivePlayers().get(0);
    }

    @And("the current player has no Defuse card")
    public void theCurrentPlayerHasNoDefuseCard() {
        // Fresh game players start with empty hands by default
    }

    @And("the current player has a Defuse card")
    public void theCurrentPlayerHasADefuseCard() {
        currentPlayer.addCard(Card.createCard(CardType.DEFUSE));
    }

    @When("the current player draws an Exploding Wildkitten card")
    public void theCurrentPlayerDrawsAnExplodingWildkittenCard() {
        // Provide insert-position input before constructing the controller,
        // so the Scanner inside ExplodingKittenCardControllerView binds to it.
        System.setIn(new ByteArrayInputStream("0\n".getBytes(StandardCharsets.UTF_8)));
        ExplodingKittenCardController controller = new ExplodingKittenCardController();
        currentPlayer.addCard(Card.createCard(CardType.EXPLODING_KITTEN));
        controller.executeCardAction(gameController, currentPlayer, Optional.empty());
    }

    @Then("the current player is eliminated from the game")
    public void theCurrentPlayerIsEliminatedFromTheGame() {
        assertFalse(currentPlayer.isAlive());
        assertFalse(game.getAlivePlayers().contains(currentPlayer));
    }

    @Then("the current player is not eliminated from the game")
    public void theCurrentPlayerIsNotEliminatedFromTheGame() {
        assertTrue(currentPlayer.isAlive());
        assertTrue(game.getAlivePlayers().contains(currentPlayer));
    }

    @And("the Exploding Wildkitten card is returned to the deck")
    public void theExplodingWildkittenCardIsReturnedToTheDeck() {
        boolean deckContainsEk = game.getDeck().getCards().stream()
                .anyMatch(c -> c.getType() == CardType.EXPLODING_KITTEN);
        assertTrue(deckContainsEk);
    }
}

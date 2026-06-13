Feature: Exploding Wildkitten card behavior

  Scenario: Drawing an Exploding Wildkitten with no Defuse eliminates the player
    Given a game with 2 players
    And the current player has no Defuse card
    When the current player draws an Exploding Wildkitten card
    Then the current player is eliminated from the game

# BVA Analysis for HailMaryController class

### Method under test: `executeCardAction()`

- **TC1: initiator has an empty hand** ( ☑️ )
    - **State of the system**: initiator = player1 (0 cards), deck has any number of cards.
    - **Expected output**: Throws `IllegalStateException`. Game state is unchanged.

- **TC2: deck has fewer cards than hand size** ( ☑️ )
    - **State of the system**: initiator = player1 (2 cards), deck has 1 card.
    - **Expected output**: Throws `IllegalStateException`. Game state is unchanged.

- **TC3: deck has exactly as many cards as hand size** ( ☑️ )
    - **State of the system**: initiator = player1 (2 cards), deck has exactly 2 cards.
    - **Expected output**: `Optional.empty()` is returned. Player1's hand is replaced with 2 new cards drawn from the deck.

- **TC4: deck has more cards than hand size** ( :x: )
    - **State of the system**: initiator = player1 (2 cards), deck has 3+ cards.
    - **Expected output**: `Optional.empty()` is returned. Player1's hand is replaced with 2 new cards drawn from the deck.

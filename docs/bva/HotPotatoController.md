# BVA Analysis for HotPotatoController

### Method under test: executeCardAction()

- **TC1: initiator has an empty hand** ( ☑️ )
  - **State of the system**: initiator = player1 (0 cards), nextPlayer = player2
  - **Expected output**: IllegalStateException is thrown. Game state is unchanged.

- **TC2: initiator has exactly 1 card** ( ☑️ )
  - **State of the system**: initiator = player1 (1 card), nextPlayer = player2
  - **Expected output**: Optional.empty() is returned. The card is removed from player1's hand and added to player2's hand.

- **TC3: initiator has 2+ cards** ( ☑️ )
  - **State of the system**: initiator = player1 (2+ cards), nextPlayer = player2
  - **Expected output**: Optional.empty() is returned. One card is removed from player1's hand and added to player2's hand.

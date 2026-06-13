# BVA Analysis for BuryCardController class

### Method under test: `executeCardAction()`
- **TC1: Insert Index is Not Numeric** 
    - **State of the system**: `userChoice` = "One" 
    - **Expected output**: console output function for invalid index is called

- **TC2: Insert Index is Too Small**
    - **State of the system**: Game has exactly 3 alive players. Current index is 1.
    - **Expected output**: Turn order is reversed. Current index is updated to 1. Next index is updated to 2.

- **TC3: Insert Index is Top of Deck** 
    - **State of the system**: Game has exactly 3 alive players. Current index is 1.
    - **Expected output**: Turn order is reversed. Current index is updated to 1. Next index is updated to 2.

- **TC4: Insert Index is Bottom of Deck** 
    - **State of the system**: Game has 5 alive players. Current index is 2.
    - **Expected output**: Turn order is reversed. Current index is updated to 2. Next index is updated to 3.

- **TC5: Insert Index is Too Big**
    - **State of the system**: Game has 5 alive players. Current index is 2.
    - **Expected output**: Turn order is reversed. Current index is updated to 2. Next index is updated to 3.

- **TC6: Deck is Empty** 
    - **State of the system**: Game has 5 alive players. Current index is 4 (the last player in the original array).
    - **Expected output**: The player order is completely reversed. Current index is correctly mapped to 0. The `nextPlayerIndex` increments and wraps to 1.

- **TC7: Deck Only Has One Card** 
    - **State of the system**: Game has 3 alive players: [P0, P1, P2]. Current index is 0 (the first player in the original array).
    - **Expected output**: The player object order is reversed to exactly [P2, P1, P0]. Current index is mapped to 2. The `nextPlayerIndex` math successfully wraps around the array length and becomes 0.
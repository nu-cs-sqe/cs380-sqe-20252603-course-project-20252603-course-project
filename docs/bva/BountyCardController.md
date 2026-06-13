### Method under test: `executeCardAction()`
- **TC1: one other player with 0 cards** (☑️)
    - **State of the system**: game has 2 players, user has 0 cards, other player has 0 cards
    - **Expected output**: `Optional.empty()`, user hand unchanged

- **TC2: one other player with 1 card** (☑️)
    - **State of the system**: game has 2 players, user has 0 cards, other player has 1 card
    - **Expected output**: user has 1 card, other player has 0 cards

- **TC3: one other player with many cards** (☑️)
    - **State of the system**: game has 2 players, user has 0 cards, other player has 4 cards
    - **Expected output**: user has 1 card, other player has 3 cards

- **TC4: multiple other players all with 0 cards** (☑️)
    - **State of the system**: game has 3 players, user has 0 cards, both other players have 0 cards
    - **Expected output**: `Optional.empty()`, all hands unchanged

- **TC5: multiple other players, some with 0 cards and some with 1 card** (☑️)
    - **State of the system**: game has 3 players, user has 0 cards, player2 has 0 cards, player3 has 1 card
    - **Expected output**: user has 1 card, player2 unchanged, player3 has 0 cards

- **TC6: multiple other players all with cards** (☑️)
    - **State of the system**: game has 3 players, user has 0 cards, player2 has 2 cards, player3 has 3 cards
    - **Expected output**: user has 2 cards, player2 has 1 card, player3 has 2 cards

- **TC7: multiple other players, one with exactly 1 card and one with many** (☑️)
    - **State of the system**: game has 3 players, user has 0 cards, player2 has 1 card, player3 has 3 cards
    - **Expected output**: user has 2 cards, player2 has 0 cards, player3 has 2 cards
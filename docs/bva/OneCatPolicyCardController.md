# Boundary Value Analysis: One Cat Policy Card Controller

### Method under test: `executeCardAction()`

- **TC1: Next player has zero cat cards** ( :white-check-mark: )
  - **State of the system**: `nextPlayer`'s hand contains only non-cat cards (e.g., Defuse, Skip, Attack),
    `nextPlayerTurnsLeft` = 1
  - **Expected output**: `displayPolicyAvoided` is called, `nextPlayerTurnsLeft` remains 1

- **TC2: Next player has exactly one cat card (Boundary - 1)** ( :white-check-mark: )
  - **State of the system**: `nextPlayer`'s hand contains exactly 1 `CAT_CARD_1` (alongside any non-cat cards),
      `nextPlayerTurnsLeft` = 1
  - **Expected output**: `displayPolicyAvoided` is called, `nextPlayerTurnsLeft` remains 1

- **TC3: Next player has maximum unique cat cards below the boundary**
  - **State of the system**: `nextPlayer`'s hand contains 1x `CAT_CARD_1`, 1x `CAT_CARD_2`, 1x `CAT_CARD_3`, and 1x
      `CAT_CARD_4`, `nextPlayerTurnsLeft` = 1
  - **Expected output**: `displayPolicyAvoided` is called, `nextPlayerTurnsLeft` remains 1

- **TC4: Next player has exactly two of the same cat card (Boundary)**
  - **State of the system**: `nextPlayer`'s hand contains exactly 2x `CAT_CARD_1`, `nextPlayerTurnsLeft` = 1
  - **Expected output**: `displayPolicyEnforced` is called, `nextPlayerTurnsLeft` becomes 2

- **TC5: Next player has three of the same cat card (Boundary + 1)**
  - **State of the system**: `nextPlayer`'s hand contains exactly 3x `CAT_CARD_1`, `nextPlayerTurnsLeft` = 1
  - **Expected output**: `displayPolicyEnforced` is called, `nextPlayerTurnsLeft` becomes 2 (the controller correctly
      short-circuits at the 2nd card and increments turns by 1, regardless of having a 3rd)

- **TC6: Multiple distinct cat card groups**
  - **State of the system**: `nextPlayer`'s hand contains 2x `CAT_CARD_1` and 3x `CAT_CARD_2`, `nextPlayerTurnsLeft` =
      1
  - **Expected output**: `displayPolicyEnforced` is called, `nextPlayerTurnsLeft` becomes 3 (1 base + 2 penalty turns)

- **TC7: Four of the same cat card (Upper constraint on a single type)**
  - **State of the system**: `nextPlayer`'s hand contains 4x `CAT_CARD_1`, `nextPlayerTurnsLeft` = 1
  - **Expected output**: `displayPolicyEnforced` is called, `nextPlayerTurnsLeft` becomes 2 (1 base + 1 penalty turn,
      bypassing extra penalty for 3rd/4th card of the same type)
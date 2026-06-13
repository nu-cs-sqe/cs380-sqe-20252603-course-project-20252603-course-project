# BVA Analysis for SwapTopAndBottomCardController class

### Method under test: `executeCardAction()`
- **TC1: Deck With No Cards (Lower Bound Invalid)** ( :white_check_mark: )
    - **State of the system**: Deck contains 0 cards.
    - **Expected output**: Throws `IllegalStateException` ("not enough cards to swap").

- **TC2: Deck With One Card (Lower Bound Invalid)** ( :white_check_mark: )
    - **State of the system**: Deck contains exactly 1 card.
    - **Expected output**: Throws `IllegalStateException` ("not enough cards to swap").

- **TC3: Deck With Exactly Two Cards (Minimum Valid Boundary)** ( :white_check_mark: )
    - **State of the system**: Deck contains exactly 2 cards [Card A, Card B].
    - **Expected output**: Deck is updated to [Card B, Card A].

- **TC4: Deck With Exactly Three Cards (Minimum + 1 Boundary)** ( :white_check_mark: )
    - **State of the system**: Deck contains exactly 3 cards [Card A, Card B, Card C].
    - **Expected output**: Deck is updated to [Card C, Card B, Card A]. Middle card is untouched.

- **TC5: Deck With Many Cards (Standard Valid State)** ( :white_check_mark: )
    - **State of the system**: Deck contains 5 cards.
    - **Expected output**: Top and bottom swap. All middle cards remain untouched.
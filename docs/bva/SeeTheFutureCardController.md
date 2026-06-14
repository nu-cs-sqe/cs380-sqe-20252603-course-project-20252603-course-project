# BVA Analysis for SeeTheFutureCardController class

### Method under test: executeCardAction

- **TC1: Deck With Four cards (one more than the boundary) ( :white_check_mark: )
    - **State of the system**: Deck contains 4 cards
    - **Expected output**: A new list containing exactly the top 3 cards from the deck is returned. The original Deck remains completely unchanged (size and order).

- **TC2: Deck With Exactly Three Cards ( :white_check_mark: )
    - **State of the system**: Deck contains exactly 3 cards
    - **Expected output**: A new list containing all 3 cards is returned. The original Deck remains completely unchanged.
  
- **TC3: Deck With Two Cards ( :white_check_mark: )
    - **State of the system**: Deck contains exactly 2 cards
    - **Expected output**: A new list containing all 2 cards is returned. The original Deck remains completely unchanged.

- **TC4: Deck With One Card ( :white_check_mark: )
    - **State of the system**: Deck contains exactly 1 cards
    - **Expected output**: A new list containing the only card is returned. The original Deck remains completely unchanged.

- **TC5: Deck With No Cards ( :white_check_mark: )
    - **State of the system**: Deck contains no cards at all
    - **Expected output**: An empty new list is returned

- **TC6: Deck With Four Cards — view displays top three ( :white-check-mark: )
    - **State of the system**: Deck contains 4 cards
    - **Expected output**: `view.displayTopCards` is called with the top 3 cards

- **TC7: Deck With Two Cards — view displays top two ( :white-check-mark: )
    - **State of the system**: Deck contains 2 cards
    - **Expected output**: `view.displayTopCards` is called with those 2 cards

- **TC8: Deck With No Cards — view displays empty list ( ☐ )
    - **State of the system**: Deck contains no cards
    - **Expected output**: `view.displayTopCards` is called with an empty list

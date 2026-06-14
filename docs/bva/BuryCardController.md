# BVA Analysis for BuryCardController class

### Method under test: `executeCardAction()`
- **TC1: Invalid Inputs and Insert in Middle of Deck** ( :white-check-mark: )
    - **State of the system**: Deck has initially 3 cards, try to input userChoice = "One", "-1", "3", and finally accept "1"
      - **Expected output**: invalid index called 3 times, top card is inserted after the first card

- **TC2: Insert Index is Top of Deck** ( :white-check-mark: )
    - **State of the system**: Deck has 10 cards, `userChoice` = "0", 
    - **Expected output**: Top card is inserted before the first card

- **TC3: Insert Index is Bottom of Deck** ( :white-check-mark: )
    - **State of the system**: Deck has 15 cards, `userChoice` = "15",
      - **Expected output**: Top card is inserted after the last (15th) card

- **TC4: Deck is Empty** ( :white-check-mark: )
    - **State of the system**: Deck has 0 cards
    - **Expected output**: no cards in deck called.

- **TC5: Deck Only Has One Card** ( :white-check-mark: )
    - **State of the system**: Deck has 1 card, `userChoice` = "0",
    - **Expected output**: Two card is inserted before the first card
# BVA Analysis for Deck Class

### Method under test: constructor
- **TC1: Default Constructor** ( :white_check_mark: )
    - **State of the system**: 
    - **Expected output**: A new deck object with a list of 34 total cards with the following types
      * Attack: 3
      * Skip: 3
      * See the Future: 4
      * Shuffle: 4
      * Nope: 4
      * Cat Cards: 4 of each × 4 types = 16

- **TC2: Empty Constructor** ( :white_check_mark: )
  - **State of the system**: 
  - **Expected output**: A new deck object with no cards

- **TC3: One Card Constructor** ( :white_check_mark: )
  - **State of the system**:
  - **Expected output**: A new deck object with one CAT_CARD_1 card

### Method under test: `shuffle()`
- **TC4: Empty Deck** ( :white_check_mark: )
  - **State of the system**: Deck.cards is empty
  - **Expected Output**: Deck.cards is empty

- **TC5: Deck With One Card** ( :white_check_mark: )
    - **State of the system**: Deck.cards contains exactly 1 card
    - **Expected Output**: Deck.cards still contains the same 1 card

- **TC6: Default Deck** ( :white_check_mark: )
    - **State of the system**: Deck.cards contains 34 cards (post-constructor)
    - **Expected Output**: Deck.cards still contains the same 34 cards with no duplicates and no cards lost

### Method under test: `insert(card, location)`
- **TC7: Insert at index -1 ** ( :white_check_mark: )
  - **State of the system**: Deck has N cards; valid card object provided; location = -1
  - **Expected output**: Raises `IndexOutOfBoundsException`
  
- **TC8: Insert at index 0 ** ( :white_check_mark: )
    - **State of the system**: Deck has N cards; valid card object provided; location = 0
    - **Expected output**: Card is inserted at the front of the deck; deck size becomes N+1

- **TC9: Insert at index 1 ** ( :white_check_mark: )
    - **State of the system**: Deck has N cards; valid card object provided; location = 1
    - **Expected output**: Card is inserted at index 1; deck size becomes N+1

- **TC10: Insert at index N ** ( :white_check_mark: )
    - **State of the system**: Deck has N cards; valid card object provided; location = N 
    - **Expected output**: Card is appended at index N+1; deck size becomes N+1

- **TC11: Insert at index N-1 ** ( :white_check_mark: )
    - **State of the system**: Deck has N cards; valid card object provided; location = N-1
    - **Expected output**: Card is inserted at index N-1; deck size becomes N+1

- **TC12: Insert into an empty deck** ( :white_check_mark: )
    - **State of the system**: Deck has 0 cards; valid card object provided; location = 0
    - **Expected output**: Card is inserted as the only element; deck size becomes 1

### Method under test: `discard(card, location)`
- **TC13: Discard the only card in the deck** ( :white_check_mark: )
    - **State of the system**: Deck has exactly 1 card; card to discard is that card
    - **Expected output**: Card is removed; deck size becomes 0

- **TC14: Discard from an empty deck** ( :white_check_mark: )
    - **State of the system**: Deck has 0 cards; any card provided
    - **Expected output**: Throws an `IllegalArgumentException`; deck remains empty

- **TC15: Discard the first card in the deck** ( :white_check_mark: )
    - **State of the system**: Deck has N cards; card to discard is at index 0
    - **Expected output**: Card is removed from the front; deck size becomes N-1

- **TC16: Discard the last card in the deck** ( :white_check_mark: )
    - **State of the system**: Deck has N cards; card to discard is at index N-1
    - **Expected output**: Card is removed from the back; deck size becomes N-1

- **TC17: Discard a card not present in the deck** ( :white_check_mark: )
    - **State of the system**: Deck has N cards; provided card does not exist in the deck
    - **Expected output**: Raises a `ValueError`; deck remains unchanged

### Method under test: `takeTopCard()`
- **TC18: Deck Has One Card** ( :white_check_mark: )
  - **State of the system**: deck contains exactly 1 card
  - **Expected output**: that card is returned; deck is now empty

- **TC19: Deck Has Many Cards** ( :white_check_mark: )
  - **State of the system**: deck contains more than 1 card
  - **Expected output**: the first card is returned; deck size decreases by 1

  - **TC20: Deck Is Empty** ( :white_check_mark: )
    - **State of the system**: deck contains 0 cards
    - **Expected output**: throws an exception

### Method under test: `getCards()`
- **TC21: Get cards from a deck with multiple cards** (:white_check_mark:)
    - **State of the system**: Deck has N cards
    - **Expected output**: Returns a list of all N cards; deck remains unchanged

- **TC22: Get cards from a deck with exactly one card** ( :white_check_mark: )
    - **State of the system**: Deck has exactly 1 card
    - **Expected output**: Returns a list containing that 1 card; deck remains unchanged

- **TC23: Get cards from an empty deck** ( :white_check_mark: )
    - **State of the system**: Deck has 0 cards
    - **Expected output**: Returns an empty list; deck remains unchanged

### Method under test: `count()`
- **TC24: Count an empty deck** ( :white_check_mark: )
    - **State of the system**: Deck has 0 cards
    - **Expected output**: Returns 0

- **TC25: Count a deck with exactly one card** ( :white_check_mark: )
    - **State of the system**: Deck has exactly 1 card
    - **Expected output**: Returns 1

- **TC26: Count a deck with N cards** ( :white_check_mark: )
    - **State of the system**: Deck has exactly N cards
    - **Expected output**: Returns N
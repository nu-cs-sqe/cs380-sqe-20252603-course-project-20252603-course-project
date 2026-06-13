### Method under test: executeCardAction()

inputs: Game game, Player initiator, Optional<Player> target
- LIST: initiator.getHand()
- LIST: game.getDeck()

output: Optional.empty() (Optional<List<Card>>)
- LIST: initiator.getHand()
- LIST: game.getDeck()

- **TC1: executeCardAction_emptyDeckEmptyHand_IllegalArgumentException ** ( :white_check_mark: )
    - **State of the system**: hand: [], deck: []
    - **Expected output**: IllegalArgumentException

- **TC2: executeCardAction_oneCardDeckEmptyHand_IllegalArgumentException ** ( :white_check_mark: )
    - **State of the system**: hand: [], deck: [EXPLODING_KITTEN]
    - **Expected output**: IllegalArgumentException

- **TC3: executeCardAction_twoCardDeckEmptyHand_cardsDrawn ** ( :white_check_mark: )
    - **State of the system**: hand: [], deck: [SKIP, EXPLODING_KITTEN]
    - **Expected output**: hand: [SKIP, EXPLODING_KITTEN], deck: []

- **TC4: executeCardAction_duplicateCardsDeckEmptyHand_cardsDrawn ** ( :white_check_mark: )
    - **State of the system**: hand: [], deck: [SKIP, SKIP, ATTACK]
    - **Expected output**: hand: [SKIP, SKIP], deck: [ATTACK]

- **TC5: executeCardAction_twoCardDeckOneCardHand_cardsDrawn ** ( :white_check_mark: )
    - **State of the system**: hand: [DEFUSE], deck: [SKIP, EXPLODING_KITTEN]
    - **Expected output**: hand: [DEFUSE, SKIP, EXPLODING_KITTEN], deck: []

- **TC6: executeCardAction_twoCardDeckTwoCardHand_cardsDrawn ** ( :white_check_mark: )
    - **State of the system**: hand: [DEFUSE, DEFUSE], deck: [SKIP, EXPLODING_KITTEN]
    - **Expected output**: hand: [DEFUSE, DEFUSE, SKIP, EXPLODING_KITTEN], deck: []

- **TC7: executeCardAction_fullDeckEmptyHand_cardsDrawn ** ( :white_check_mark: )
    - **State of the system**: hand: [], deck: [SKIP, DEFUSE, ATTACK ... SKIP] (28 cards total for 2 players)
    - **Expected output**: hand: [SKIP, DEFUSE], deck: [ATTACK ... SKIP]

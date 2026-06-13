# BVA Analysis for PotluckCardController class

### Method under test: executeCardAction

- TC1: Exactly Two Players Remaining, and invalid inputs tested ( :white-check-mark: )
  - **State of the system**: current player inputs "DRAW_FROM_BOTTOM" then invalid index 10, then plays DRAW_FROM_BOTTOM, player 2 plays CAT_CARD_4
  - **Expected output**: Both players contribute 1 card. The draw pile size increases by exactly 2. The opponent's card becomes the new top card of the draw pile.

- TC2: All Players Have At Least One Card ( :white-check-mark: )
  - **State of the system**: all 5 active player has at least 1 card in hand and plays SKIP, DRAW_FROM_BOTTOM, SKIP, CAT_CARD_3, SEE_THE_FUTURE
  - **Expected output**: Each active player has respective cards removed from hand and added to draw pile. current player's card is bottom of the new stack, and each next player is on top of the previous player.

- TC3: Active Player Has No Cards ( :white-check-mark: )
  - **State of the system**: current player's hand is empty, player 2 chooses SHUFFLE, player 3' hand = [SEE_THE_FUTURE], chooses SEE_THE_FUTURE
  - **Expected output**: current player's hand = [], both other player has respective cards removed, deck pile has two new cards at the top

- TC4: Opponent Has No Cards  ( :white-check-mark: ) 
  - **State of the system**: current player chooses CAT_CARD_2, player 2's hand is empty
  - **Expected output**: current player has CAT_CARD_2 removed, player 2's hand is empty, draw pile has one card added

- TC5: Multiple players play the same card type
  - **State of the System**: current player plays SKIP, player 2 also plays SKIP
  - **Expected Output**: both SKIPs added to draw pile correctly
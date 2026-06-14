### Method under test: `executeCardAction(GameController, Player, Optional<Player>)`

- **TC1: executeCardAction_OneTurnLeft_EndsCurrentAndGivesNextThree** ( :white-check-mark: )
  - **State of the system**: `currentPlayerTurnsLeft = 1` (standard single turn)
  - **Expected output**: `currentPlayerTurnsLeft = 0`, `nextPlayerTurnsLeft = 3`

- **TC2: executeCardAction_ThreeTurnsLeft_ChainedAttack_GivesNextFive** ( :white-check-mark: )
  - **State of the system**: `currentPlayerTurnsLeft = 3` (player was already under attack with 3 turns)
  - **Expected output**: `currentPlayerTurnsLeft = 0`, `nextPlayerTurnsLeft = 5`

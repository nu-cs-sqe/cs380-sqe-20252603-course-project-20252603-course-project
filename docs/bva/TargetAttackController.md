# BVA Analysis for TargetAttackController class

### Method under test: `executeCardAction()`

### Test Cases

- **TC1: Valid target index selected** (  )
  - **State of the system**: `initiator` = player1, `alivePlayers` = [player1, player2, player3]. View prints players and returns index `1` (which maps to player2).
  - **Expected output**: Target is successfully resolved to player2. `Optional.empty()` is returned. `GameController.setNextPlayerTurnsLeft(2)` is called.

- **TC2: Target index points to the initiator** (  )
  - **State of the system**: `initiator` = player1, `alivePlayers` = [player1, player2, player3]. View returns index `0` (which maps to the initiator, player1).
  - **Expected output**: `IllegalArgumentException` is thrown indicating the initiator cannot target themselves. Game state is unchanged.

- **TC3: Target index is out of bounds (Negative)** (  )
  - **State of the system**: `initiator` = player1, `alivePlayers` = [player1, player2, player3]. View returns index `-1`.
  - **Expected output**: `IndexOutOfBoundsException` or `IllegalArgumentException` is thrown. Game state is unchanged.

- **TC4: Target index is out of bounds (Exceeds list size)** (  )
  - **State of the system**: `initiator` = player1, `alivePlayers` = [player1, player2, player3]. View returns index `3` (size is 3, maximum valid index is 2).
  - **Expected output**: `IndexOutOfBoundsException` or `IllegalArgumentException` is thrown. Game state is unchanged.

- **TC5: Input format is invalid / Non-integer** (  )
  - **State of the system**: `initiator` = player1, `alivePlayers` = [player1, player2]. View returns an unparseable string (e.g., `"abc"` or `""`).
  - **Expected output**: `NumberFormatException` is thrown (assuming string-to-integer parsing is handled inside the controller). Game state is unchanged.
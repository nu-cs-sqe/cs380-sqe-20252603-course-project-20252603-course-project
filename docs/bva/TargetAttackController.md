# BVA Analysis for TargetAttackController class

### Method under test: `executeCardAction()`

### Test Cases
- **TC1: Valid target index selected** ( :white-check-mark: )
  - **System State**: `initiator` = P1, `alivePlayers` = [P1, P2]. View returns `"1"`.
  - **Result**: Valid P2 selected. `GameController.setNextPlayerTurnsLeft(2)` called. Loop breaks.

- **TC2: Target is initiator** ( :white-check-mark: )
  - **System State**: `initiator` = P1, `alivePlayers` = [P1, P2]. View returns `"0"` then `"1"`.
  - **Result**: `displayInvalidTarget` called. Loop retries. Valid P2 selected. Loop breaks.

- **TC3: Target is out-of-bounds (Negative)** ( :white-check-mark: )
  - **System State**: `initiator` = P1, `alivePlayers` = [P1, P2]. View returns `"-1"` then `"1"`.
  - **Result**: `IndexOutOfBoundsException` caught. `displayInvalidIndex` called. Loop retries. Valid P2 selected. Loop breaks.

- **TC4: Target is out-of-bounds (Too large)**
  - **System State**: `initiator` = P1, `alivePlayers` = [P1, P2]. View returns `"2"` then `"1"`.
  - **Result**: `IndexOutOfBoundsException` caught. `displayInvalidIndex` called. Loop retries. Valid P2 selected. Loop breaks.

- **TC5: Input format invalid**
  - **System State**: `initiator` = P1, `alivePlayers` = [P1, P2]. View returns `"abc"` then `"1"`.
  - **Result**: `NumberFormatException` caught. `displayInvalidIndex` called. Loop retries. Valid P2 selected. Loop breaks.

- **TC6: Target is dead**
  - **System State**: `initiator` = P1, `alivePlayers` = [P1, P2]. P2 is dead. View returns `"1"` then index of a valid alive player.
  - **Result**: `displayInvalid*Expected output**: `NumberFormatException` is thrown (assuming string-to-integer parsing is handled inside the controller). Game state is unchanged.
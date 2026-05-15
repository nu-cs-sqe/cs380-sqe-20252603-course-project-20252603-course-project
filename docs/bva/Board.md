# BVA Analysis for `Board`

Test Cases 1–22 (covering `Board()`) are documented in a separate branch and will appear here once it merges. The analysis below covers the new methods added in `feature-board-gamestate-multiple-turns-of-the-game`, starting with `getSnapshot()`.

---

## Step 1-4 Summary (new methods: `getSnapshot`, `getCurrentGameState`, `switchTurn`, `updateWhiteKingLocation`, `updateBlackKingLocation`, `applyMoveIfKingSafe`, `castle`)

- Step 1, input equivalence classes:
  - `Location` params: valid on-board location (x ∈ [0,7], y ∈ [0,7]) vs null pointer.
  - `GameState`: `WHITE_TURN` / `BLACK_TURN`.
  - `hasMoved` on a piece: `false` (never moved) / `true` (already moved).
  - Path between king and rook: clear (0 blocking pieces) / occupied (≥1 blocking piece).
  - King check status: not in check / in check.
  - King transit square: not attacked / attacked.
  - King landing square: not attacked / attacked.
- Step 1, output equivalence classes:
  - Move accepted: board state changes, pieces repositioned.
  - Move rejected: board state unchanged — invariant for every precondition failure.
  - Return values: `GameState` (for `getCurrentGameState`) or `void`.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `Location` reference → `Pointers` (null vs non-null) + `Ordered` (int coordinate 0–7).
  - `GameState` → `Cases`.
  - `hasMoved` flag → `Booleans`.
  - Path occupancy, check status, transit/landing safety → `Booleans`.
- Step 3, concrete boundary values selected from the catalog:
  - `Location`: null (invalid pointer boundary), (0,0) minimum-coordinate corner, (7,7) maximum-coordinate corner, (4,4) interior.
  - int coordinate: 0 (min board), 7 (max board).
  - `GameState`: `WHITE_TURN` (first case), `BLACK_TURN` (last case).
  - `hasMoved`: `false` (valid castling precondition), `true` (invalid; triggers rejection).
- Step 4 strategy:
  - `getCurrentGameState` and `switchTurn`: each-choice across the two `GameState` cases.
  - `updateWhiteKingLocation` and `updateBlackKingLocation`: each-choice across null pointer, min corner, max corner, and interior.
  - `applyMoveIfKingSafe`: each-choice across null/non-null location inputs and the two check-outcome booleans (move leaves king in check vs does not), plus the special case where the king itself moves.
  - `castle`: each-choice for each independent precondition failure + one all-valid success case + null pointer cases per parameter.

---

## Method under test: `getSnapshot()`

Returns a copy of the board state. Not yet implemented; tests document the stub boundary.

|       | System under test                                             | Expected output                              | Implemented? |
|-------|---------------------------------------------------------------|----------------------------------------------|--------------|
| Test Case 23 | call `getSnapshot()` on any board                             | throws `UnsupportedOperationException`       | :white_check_mark: |

---

## Method under test: `getCurrentGameState()`

Returns the current `GameState`. The board starts at `WHITE_TURN` and alternates with each `switchTurn()` call.

|       | System under test                                             | Expected output      | Implemented? |
|-------|---------------------------------------------------------------|----------------------|--------------|
| Test Case 24 | newly constructed board (no `switchTurn()` calls)             | returns `WHITE_TURN` | :white_check_mark: |
| Test Case 25 | board after one call to `switchTurn()`                        | returns `BLACK_TURN` | :white_check_mark: |

---

## Method under test: `switchTurn()`

Alternates `currentGameState` between `WHITE_TURN` and `BLACK_TURN` after each move.

|       | System under test                                              | Expected output                                | Implemented? |
|-------|----------------------------------------------------------------|------------------------------------------------|--------------|
| Test Case 26 | `currentGameState = WHITE_TURN`; call `switchTurn()`          | `getCurrentGameState()` returns `BLACK_TURN`  | :white_check_mark: |
| Test Case 27 | `currentGameState = BLACK_TURN`; call `switchTurn()`          | `getCurrentGameState()` returns `WHITE_TURN`  | :white_check_mark: |

---

## Method under test: `updateWhiteKingLocation(Location location)`

Keeps `whiteKingLocation` accurate so check-detection runs against the correct square. Uses `Pointers` (null boundary) and `Ordered` (corner vs interior coordinates).

|       | System under test                                      | Expected output                                                                    | Implemented? |
|-------|--------------------------------------------------------|------------------------------------------------------------------------------------|--------------|
| Test Case 28 | `location = null`                                      | throws `IllegalArgumentException` with message `"location must not be null"`       | :white_check_mark: |
| Test Case 29 | `location = (0, 0)` (minimum-coordinate corner)        | `whiteKingLocation` is now `(0, 0)`                                                | :white_check_mark: |
| Test Case 30 | `location = (7, 7)` (maximum-coordinate corner)        | `whiteKingLocation` is now `(7, 7)`                                                | :white_check_mark: |
| Test Case 31 | `location = (4, 4)` (interior square)                  | `whiteKingLocation` is now `(4, 4)`                                                | :white_check_mark: |

---

## Method under test: `updateBlackKingLocation(Location location)`

Symmetric to `updateWhiteKingLocation`; keeps `blackKingLocation` accurate before check-detection runs.

|       | System under test                                      | Expected output                                                                    | Implemented? |
|-------|--------------------------------------------------------|------------------------------------------------------------------------------------|--------------|
| Test Case 32 | `location = null`                                      | throws `IllegalArgumentException` with message `"location must not be null"`       | :white_check_mark: |
| Test Case 33 | `location = (0, 0)` (minimum-coordinate corner)        | `blackKingLocation` is now `(0, 0)`                                                | :white_check_mark: |
| Test Case 34 | `location = (7, 7)` (maximum-coordinate corner)        | `blackKingLocation` is now `(7, 7)`                                                | :white_check_mark: |
| Test Case 35 | `location = (4, 4)` (interior square)                  | `blackKingLocation` is now `(4, 4)`                                                | :white_check_mark: |

---

## Method under test: `applyMoveIfKingSafe(Location from, Location to)`

Simulates a candidate move and rejects it if the current player's king would be in check afterward. Uses `Pointers` for both `Location` params and `Booleans` for the check-outcome boundary. Tests use EasyMock mocks to configure `canAttack()` behavior — the discriminating variable is the attack geometry, not the piece type.

|       | System under test                                                                                                                | Expected output                                                              | Implemented? |
|-------|----------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------|--------------|
| Test Case 36 | `from = null`; `to` is a valid location                                                                                          | throws `IllegalArgumentException` with message `"from must not be null"`     | :white_check_mark: |
| Test Case 37 | `from` is a valid location; `to = null`                                                                                          | throws `IllegalArgumentException` with message `"to must not be null"`       | :white_check_mark: |
| Test Case 38 | board where moving the piece at `from` to `to` does NOT leave own king in check (non-pinned piece moves freely)                  | move is allowed; returns `true`; board reflects the move                     | :white_check_mark: |
| Test Case 39 | board where moving the piece at `from` to `to` leaves own king exposed to check (pinned piece moves away from pin line; attacker configured to attack) | move is rejected; returns `false`; board state is unchanged | :white_check_mark: |
| Test Case 40 | `from` and `to` are both empty squares; own king location set                                                                    | returns `true`; destination remains null                                     | :white_check_mark: |
| Test Case 41 | white king moves to a square not attacked by any opponent (no attacker pieces)                                                   | move is allowed; `whiteKingLocation` updated to destination                  | :white_check_mark: |
| Test Case 42 | white king moves to a square attacked by an opponent piece (attacker mock configured to return `true`)                           | move is rejected; `whiteKingLocation` unchanged; board unchanged             | :white_check_mark: |
| Test Case 43 | black king moves to a safe square after `switchTurn()`; no attacker present                                                     | move is allowed; `blackKingLocation` updated to destination                  | :white_check_mark: |
| Test Case 44 | opponent piece mock configured to attack (`canAttack = true`) is on the board; own king at registered location                   | returns `false` (king in check)                                              | :white_check_mark: |
| Test Case 45 | opponent piece mock configured NOT to attack (`canAttack = false`) is on the board; own king at registered location              | returns `true`                                                               | :white_check_mark: |
| Test Case 46 | opponent piece mock configured `canAttack = false` (attack blocked by own-color piece between attacker and king)                 | returns `true`                                                               | :white_check_mark: |
| Test Case 47 | opposing king mock configured to attack the friendly king's square (`canAttack = true`)                                          | returns `false`                                                              | :white_check_mark: |
| Test Case 48 | opposing king mock configured NOT to attack (`canAttack = false`; ≥ 2 squares away)                                             | returns `true`                                                               | :white_check_mark: |

---

## Method under test: `castle(Location kingFrom, Location kingTo, Location rookFrom, Location rookTo)`

Validates all castling preconditions before executing atomically. Every precondition failure must leave the board unchanged. Strategy: each-choice per independent precondition, one white-side success case, one black-side success case, and a null pointer case for each `Location` parameter. Tests use EasyMock mocks for king and rook pieces to control `hasMoved()` and `getColor()` behavior.

|       | System under test                                                                                                                                                         | Expected output                                                                                          | Implemented? |
|-------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|--------------|
| Test Case 49 | `kingFrom = null`; all other args valid                                                                                                                                   | throws `IllegalArgumentException` with message `"kingFrom must not be null"`                             | :white_check_mark: |
| Test Case 50 | `kingTo = null`; all other args valid                                                                                                                                     | throws `IllegalArgumentException` with message `"kingTo must not be null"`                               | :white_check_mark: |
| Test Case 51 | `rookFrom = null`; all other args valid                                                                                                                                   | throws `IllegalArgumentException` with message `"rookFrom must not be null"`                             | :white_check_mark: |
| Test Case 52 | `rookTo = null`; all other args valid                                                                                                                                     | throws `IllegalArgumentException` with message `"rookTo must not be null"`                               | :white_check_mark: |
| Test Case 53 | king at `kingFrom` has `hasMoved = true`; rook has `hasMoved = false`; path clear; king not in check; transit and landing squares safe                                    | castle rejected; board unchanged                                                                         | :white_check_mark: |
| Test Case 54 | king has `hasMoved = false`; rook at `rookFrom` has `hasMoved = true`; path clear; king not in check; transit and landing squares safe                                    | castle rejected; board unchanged                                                                         | :white_check_mark: |
| Test Case 55 | king has `hasMoved = false`; rook has `hasMoved = false`; one piece occupies a square between `kingFrom` and `rookFrom`; king not in check; transit and landing squares safe | castle rejected; board unchanged                                                                      | :white_check_mark: |
| Test Case 56 | king has `hasMoved = false`; rook has `hasMoved = false`; path clear; king IS currently in check (attacker mock returns `true`); transit and landing squares safe         | castle rejected; board unchanged                                                                         | :white_check_mark: |
| Test Case 57 | king has `hasMoved = false`; rook has `hasMoved = false`; path clear; king not in check; king's transit square IS attacked (attacker mock returns `true`)                 | castle rejected; board unchanged                                                                         | :white_check_mark: |
| Test Case 58 | king has `hasMoved = false`; rook has `hasMoved = false`; path clear; king not in check; transit square safe; king's landing square IS attacked (attacker mock returns `true`) | castle rejected; board unchanged                                                                    | :white_check_mark: |
| Test Case 59 | all preconditions met (white side): `hasMoved = false` for both pieces; path clear; king not in check; transit and landing squares safe                                   | castle succeeds; king is at `kingTo`; rook is at `rookTo`; `whiteKingLocation` updated to `kingTo`; original squares empty | :white_check_mark: |
| Test Case 60 | all preconditions met (black side): same conditions as TC59 but for black king and rook                                                                                   | castle succeeds; black king at `kingTo`; black rook at `rookTo`; `blackKingLocation` updated             | :white_check_mark: |
| Test Case 61 | `kingFrom` points to an empty square (no piece); all other args valid; rook at `rookFrom` has `hasMoved = false`                                                          | castle rejected; returns `false`; board unchanged                                                        | :white_check_mark: |
| Test Case 62 | king at `kingFrom` has `hasMoved = false`; `rookFrom` points to an empty square (no piece); all other args valid                                                          | castle rejected; returns `false`; board unchanged                                                        | :white_check_mark: |

# BVA Analysis for `Board`

## Step 1-4 Summary

- Step 1, input equivalence classes:
  - `Board()` has no explicit parameters.
  - The constructor implicitly depends on successful allocation of an `8 x 8` `Piece[][]` and calls `initializeBoard()`.
  - `initializeBoard()` populates:
    - black major pieces on row `0`
    - black pawns on row `1`
    - empty middle rows `2..5`
    - white pawns on row `6`
    - white major pieces on row `7`
  - `getSnapshot()` has no parameters; behavior depends on internal board state:
    - occupied square (`Piece != null`)
    - empty square (`null`)
  - `movePiece(Location from, Location to)`: `Location` params → valid on-board (x ∈ [0,7], y ∈ [0,7]) vs null vs out-of-bounds; destination may be empty, same-color, or enemy.
  - `getCurrentGameState` / `switchTurn`: `GameState` ∈ { `WHITE_TURN`, `BLACK_TURN` }.
  - `updateWhiteKingLocation` / `updateBlackKingLocation`: `Location` param → null pointer or valid coordinate.
  - `applyMoveIfKingSafe(Location from, Location to)`: null pointer, check-outcome boolean (king safe vs in check), king itself moving.
  - `castle(Location kingFrom, Location kingTo, Location rookFrom, Location rookTo)`: null per parameter; `hasMoved` on king and rook; path occupancy; king check / transit / landing safety.
- Step 1, output equivalence classes:
  - `Board()` creates a valid initialized chess board.
  - `getSnapshot()` returns a non-null, 8×8, deep-copied `Piece[][]`.
  - `movePiece`: accepted (board mutates) or rejected (board unchanged); returns `boolean`.
  - `getCurrentGameState` returns the current `GameState`; `switchTurn` returns `void`.
  - `updateWhiteKingLocation` / `updateBlackKingLocation`: update stored location or throw `IllegalArgumentException`.
  - `applyMoveIfKingSafe`: move allowed (`true`, board changes) or rejected (`false`, board unchanged).
  - `castle`: castles atomically (`true`) or is rejected (`false`, board unchanged).
- Step 2, BVA catalog mapping:
  - Board dimensions → `Intervals` (min index `0`, max index `7`, middle `2..5`).
  - Piece placement categories → `Cases` (pawn, rook, knight, bishop, queen, king, empty).
  - Piece colors → `Cases` (`WHITE`, `BLACK`).
  - Snapshot object identity → `Pointers` (same reference vs distinct reference).
  - `Location` parameters → `Pointers` (null vs non-null) + `Ordered` (int coordinate 0–7).
  - `GameState` → `Cases`.
  - `hasMoved` flag, path occupancy, check / transit / landing safety → `Booleans`.
- Step 3, concrete boundary values:
  - Row boundaries: `0`, `1`, `2`, `5`, `6`, `7`.
  - Column boundaries: `0`, `1`, `3`, `4`, `6`, `7`.
  - Representative squares: corners `(0,0)`, `(0,7)`, `(7,0)`, `(7,7)`; center empty `(3,3)`, `(4,4)`; king/queen files `(0,3)`, `(0,4)`, `(7,3)`, `(7,4)`.
  - Snapshot identity: copied piece reference differs; copied piece state equals original.
  - `Location`: null; `(0,0)` min-corner; `(7,7)` max-corner; `(4,4)` interior.
  - `GameState`: `WHITE_TURN` (first case), `BLACK_TURN` (last case).
  - `hasMoved`: `false` (valid castling precondition), `true` (invalid).
- Step 4 strategy:
  - `Board()` / `getSnapshot()`: each-choice for representative piece placements; explicit edge coverage for corners and edge rows; dedicated deep-copy tests.
  - `movePiece`: each-choice across null, out-of-bounds, same-color, valid, capture, blocked, and same-square inputs.
  - `getCurrentGameState` / `switchTurn`: each-choice across the two `GameState` cases.
  - `updateWhiteKingLocation` / `updateBlackKingLocation`: each-choice across null pointer, min corner, max corner, and interior.
  - `applyMoveIfKingSafe`: each-choice across null/non-null location inputs, check-outcome booleans, and the special case where the king itself moves.
  - `castle`: each-choice for each independent precondition failure + one white-side success case + one black-side success case + null pointer cases per parameter.

---

## Method under test: `Board()`

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 1  | `new Board()` | creates non-null board instance | :white_check_mark: |
| Test Case 2  | board initialization | snapshot dimensions are `8 x 8` | :white_check_mark: |
| Test Case 3  | board initialization | black pawns exist at row `1`, cols `0..7` | :white_check_mark: |
| Test Case 4  | board initialization | white pawns exist at row `6`, cols `0..7` | :white_check_mark: |
| Test Case 5  | board initialization | black rooks exist at `(0,0)` and `(0,7)` | :white_check_mark: |
| Test Case 6  | board initialization | white rooks exist at `(7,0)` and `(7,7)` | :white_check_mark: |
| Test Case 7  | board initialization | black knights exist at `(0,1)` and `(0,6)` | :white_check_mark: |
| Test Case 8  | board initialization | white knights exist at `(7,1)` and `(7,6)` | :white_check_mark: |
| Test Case 9  | board initialization | black bishops exist at `(0,2)` and `(0,5)` | :white_check_mark: |
| Test Case 10 | board initialization | white bishops exist at `(7,2)` and `(7,5)` | :white_check_mark: |
| Test Case 11 | board initialization | black queen at `(0,3)` and black king at `(0,4)` | :white_check_mark: |
| Test Case 12 | board initialization | white queen at `(7,3)` and white king at `(7,4)` | :white_check_mark: |
| Test Case 13 | board initialization | rows `2..5` contain only `null` references | :white_check_mark: |

---

## Method under test: `getSnapshot()`

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 14 | initialized board; call `getSnapshot()` | returns non-null `Piece[][]` | :white_check_mark: |
| Test Case 15 | initialized board; call `getSnapshot()` | returned snapshot dimensions are `8 x 8` | :white_check_mark: |
| Test Case 16 | snapshot at `(0,0)` | contains `Rook(BLACK)` | :white_check_mark: |
| Test Case 17 | snapshot at `(7,4)` | contains `King(WHITE)` | :white_check_mark: |
| Test Case 18 | snapshot at `(3,3)` | contains `null` because middle squares are empty | :white_check_mark: |
| Test Case 19 | snapshot piece copy at `(0,0)` | snapshot rook is not the same reference as internal rook | :white_check_mark: |
| Test Case 20 | snapshot piece copy at `(7,1)` | copied knight preserves `PieceType = KNIGHT` and `PieceColor = WHITE` | :white_check_mark: |
| Test Case 21 | modify returned snapshot reference at `(0,0)` | original board state remains unchanged on future snapshots | :white_check_mark: |
| Test Case 22 | two successive calls to `getSnapshot()` | corresponding non-null pieces are distinct object references across snapshots | :white_check_mark: |

---

## Method under test: `movePiece(Location from, Location to)`

| Test Case | System under test | Expected output | Implemented? |
|-----------|-------------------|-----------------|--------------|
| Test Case 23 | valid move (piece + legal shape) | piece moved from → to, source becomes null | :white_check_mark: |
| Test Case 24 | invalid move shape | board state unchanged | :white_check_mark: |
| Test Case 25 | blocked path (rook/bishop/queen) | board state unchanged | :white_check_mark: |
| Test Case 26 | capture move | opponent piece removed, moved into target | :white_check_mark: |
| Test Case 27 | same-square move | rejected, no mutation | :white_check_mark: |
| Test Case 28 | null from | throws `IllegalArgumentException` | :white_check_mark: |
| Test Case 29 | null to | throws `IllegalArgumentException` | :white_check_mark: |
| Test Case 30 | from out-of-bounds | rejected, no mutation | :white_check_mark: |
| Test Case 31 | to out-of-bounds | rejected, no mutation | :white_check_mark: |
| Test Case 32 | same-color destination | rejected, no mutation | :white_check_mark: |
| Test Case 33 | valid move mutation invariant | exactly two squares updated | :white_check_mark: |
| Test Case 34 | invalid move invariant | zero board changes | :white_check_mark: |

---

## Method under test: `getCurrentGameState()`

Returns the current `GameState`. The board starts at `WHITE_TURN` and alternates with each `switchTurn()` call.

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 35 | newly constructed board (no `switchTurn()` calls) | returns `WHITE_TURN` | :white_check_mark: |
| Test Case 36 | board after one call to `switchTurn()` | returns `BLACK_TURN` | :white_check_mark: |

---

## Method under test: `switchTurn()`

Alternates `currentGameState` between `WHITE_TURN` and `BLACK_TURN` after each move.

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 37 | `currentGameState = WHITE_TURN`; call `switchTurn()` | `getCurrentGameState()` returns `BLACK_TURN` | :white_check_mark: |
| Test Case 38 | `currentGameState = BLACK_TURN`; call `switchTurn()` | `getCurrentGameState()` returns `WHITE_TURN` | :white_check_mark: |

---

## Method under test: `updateWhiteKingLocation(Location location)`

Keeps `whiteKingLocation` accurate so check-detection runs against the correct square.

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 39 | `location = null` | throws `IllegalArgumentException` with message `"location must not be null"` | :white_check_mark: |
| Test Case 40 | `location = (0, 0)` (minimum-coordinate corner) | `whiteKingLocation` is now `(0, 0)` | :white_check_mark: |
| Test Case 41 | `location = (7, 7)` (maximum-coordinate corner) | `whiteKingLocation` is now `(7, 7)` | :white_check_mark: |
| Test Case 42 | `location = (4, 4)` (interior square) | `whiteKingLocation` is now `(4, 4)` | :white_check_mark: |

---

## Method under test: `updateBlackKingLocation(Location location)`

Symmetric to `updateWhiteKingLocation`; keeps `blackKingLocation` accurate before check-detection runs.

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 43 | `location = null` | throws `IllegalArgumentException` with message `"location must not be null"` | :white_check_mark: |
| Test Case 44 | `location = (0, 0)` (minimum-coordinate corner) | `blackKingLocation` is now `(0, 0)` | :white_check_mark: |
| Test Case 45 | `location = (7, 7)` (maximum-coordinate corner) | `blackKingLocation` is now `(7, 7)` | :white_check_mark: |
| Test Case 46 | `location = (4, 4)` (interior square) | `blackKingLocation` is now `(4, 4)` | :white_check_mark: |

---

## Method under test: `applyMoveIfKingSafe(Location from, Location to)`

Simulates a candidate move and rejects it if the current player's king would be in check afterward. Uses `Pointers` for both `Location` params and `Booleans` for the check-outcome boundary. Tests use EasyMock mocks to configure `canAttack()` behavior.

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 47 | `from = null`; `to` is a valid location | throws `IllegalArgumentException` with message `"from must not be null"` | :white_check_mark: |
| Test Case 48 | `from` is a valid location; `to = null` | throws `IllegalArgumentException` with message `"to must not be null"` | :white_check_mark: |
| Test Case 49 | moving piece at `from` to `to` does NOT leave own king in check (non-pinned piece) | move is allowed; returns `true`; board reflects the move | :white_check_mark: |
| Test Case 50 | moving piece at `from` to `to` leaves own king exposed (pinned piece; attacker mock returns `true`) | move is rejected; returns `false`; board state is unchanged | :white_check_mark: |
| Test Case 51 | `from` and `to` are both empty squares; own king location set | returns `true`; destination remains null | :white_check_mark: |
| Test Case 52 | white king moves to a square not attacked by any opponent | move is allowed; `whiteKingLocation` updated to destination | :white_check_mark: |
| Test Case 53 | white king moves to a square attacked by an opponent piece (mock returns `true`) | move is rejected; `whiteKingLocation` unchanged; board unchanged | :white_check_mark: |
| Test Case 54 | black king moves to a safe square after `switchTurn()`; no attacker present | move is allowed; `blackKingLocation` updated to destination | :white_check_mark: |
| Test Case 55 | opponent piece mock configured `canAttack = true` is on the board; own king at registered location | returns `false` (king in check) | :white_check_mark: |
| Test Case 56 | opponent piece mock configured `canAttack = false` is on the board; own king at registered location | returns `true` | :white_check_mark: |
| Test Case 57 | opponent piece mock configured `canAttack = false` (attack blocked by own-color piece) | returns `true` | :white_check_mark: |
| Test Case 58 | opposing king mock configured to attack the friendly king's square (`canAttack = true`) | returns `false` | :white_check_mark: |
| Test Case 59 | opposing king mock configured NOT to attack (`canAttack = false`; ≥ 2 squares away) | returns `true` | :white_check_mark: |

---

## Method under test: `castle(Location kingFrom, Location kingTo, Location rookFrom, Location rookTo)`

Validates all castling preconditions before executing atomically. Every precondition failure must leave the board unchanged. Tests use EasyMock mocks for king and rook pieces to control `hasMoved()` and `getColor()` behavior.

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 60 | `kingFrom = null`; all other args valid | throws `IllegalArgumentException` with message `"kingFrom must not be null"` | :white_check_mark: |
| Test Case 61 | `kingTo = null`; all other args valid | throws `IllegalArgumentException` with message `"kingTo must not be null"` | :white_check_mark: |
| Test Case 62 | `rookFrom = null`; all other args valid | throws `IllegalArgumentException` with message `"rookFrom must not be null"` | :white_check_mark: |
| Test Case 63 | `rookTo = null`; all other args valid | throws `IllegalArgumentException` with message `"rookTo must not be null"` | :white_check_mark: |
| Test Case 64 | `kingFrom` points to an empty square (no piece) | castle rejected; returns `false`; board unchanged | :white_check_mark: |
| Test Case 65 | king present; `rookFrom` points to an empty square | castle rejected; returns `false`; board unchanged | :white_check_mark: |
| Test Case 66 | king at `kingFrom` has `hasMoved = true`; rook has `hasMoved = false` | castle rejected; board unchanged | :white_check_mark: |
| Test Case 67 | king has `hasMoved = false`; rook at `rookFrom` has `hasMoved = true` | castle rejected; board unchanged | :white_check_mark: |
| Test Case 68 | both pieces unmoved; one piece occupies a square between `kingFrom` and `rookFrom` | castle rejected; board unchanged | :white_check_mark: |
| Test Case 69 | both pieces unmoved; path clear; king IS currently in check (attacker mock returns `true`) | castle rejected; board unchanged | :white_check_mark: |
| Test Case 70 | both pieces unmoved; path clear; king not in check; king's transit square IS attacked | castle rejected; board unchanged | :white_check_mark: |
| Test Case 71 | both pieces unmoved; path clear; king not in check; transit square safe; landing square IS attacked | castle rejected; board unchanged | :white_check_mark: |
| Test Case 72 | all preconditions met (white side): both unmoved; path clear; king not in check; transit and landing safe | castle succeeds; king at `kingTo`; rook at `rookTo`; `whiteKingLocation` updated; original squares empty | :white_check_mark: |
| Test Case 73 | all preconditions met (black side): same conditions as TC72 for black | castle succeeds; black king at `kingTo`; black rook at `rookTo`; `blackKingLocation` updated | :white_check_mark: |
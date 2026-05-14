# BVA Analysis for `GameState`

`GameState` is a two-value enum. It has no custom public methods. BVA documents the valid enum constants; the null pointer boundary is exercised through `Board.getCurrentGameState()` and `Board.switchTurn()`.

## Step 1-4 Summary

- Step 1, input equivalence classes:
  - `GameState` has two valid enum cases: `WHITE_TURN` and `BLACK_TURN`. No other value can be set in Java.
- Step 1, output equivalence classes:
  - Any reference to a `GameState` is either a valid enum constant or the invalid null pointer boundary.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `GameState` constants → `Cases`.
  - A `GameState` reference used as a method parameter or return value also uses `Pointers` (null vs non-null).
- Step 3, concrete boundary values selected from the catalog:
  - First case: `WHITE_TURN`. Last case: `BLACK_TURN`. Null pointer boundary is `CAN'T SET` as a returned enum constant but is reachable as an uninitialized field; it is covered by Board-level tests.
- Step 4 strategy:
  - Each-choice — one test per case. Impossible non-enum values are omitted because they are `CAN'T SET` in Java.

---

### Method under test: `GameState` enum constants

|             | System under test                                       | Expected output                                         | Implemented? |
|-------------|---------------------------------------------------------|---------------------------------------------------------|--------------|
| Test Case 1 | `GameState.WHITE_TURN` referenced as a compile-time constant | enum constant is non-null; `name()` returns `"WHITE_TURN"` | :white_check_mark: |
| Test Case 2 | `GameState.BLACK_TURN` referenced as a compile-time constant | enum constant is non-null; `name()` returns `"BLACK_TURN"` | :white_check_mark: |

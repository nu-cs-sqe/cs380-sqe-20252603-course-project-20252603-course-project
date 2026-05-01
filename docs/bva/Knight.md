# BVA Analysis for Knight

`Knight` fixes the inherited `PieceType` to `KNIGHT`. The only constructor input boundary is the `PieceColor color` argument; `makeCopy()` has no parameters, so its boundaries come from the receiver's stored color state.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `PieceColor` is a Java reference type, `null` is also a settable pointer boundary for `color`.
  - `makeCopy()` has no parameters; its input classes are the existing `Knight` receiver states with `color = BLACK`, `color = WHITE`, or `color = null`.
- Step 1, output equivalence classes:
  - The constructor creates a `Knight` with `type = KNIGHT` and the supplied `color`, including `null`.
  - `makeCopy()` returns a distinct `Piece` object whose runtime state is a `Knight` with `type = KNIGHT` and the same `color` as the original.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - The `color` reference also uses `Pointers`: null pointer vs pointer to a true enum object.
  - The `makeCopy()` return value uses `Pointers`: pointer to a true object. A `null` return is not expected from this implementation.
  - There are no string outputs in `Knight`'s public constructor or `makeCopy()` method.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus settable pointer boundary `null`.
  - `PieceType` is not an input for `Knight`; it is hardcoded to the output case `KNIGHT`.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice because there is only one independent varying input/state variable, `color`.
  - Cover both enum cases and the null-pointer boundary for the constructor.
  - Reuse the same receiver color states for `makeCopy()` so every copy output class is covered.


### Method under test: `Knight(PieceColor color)`

|             | System under test                     | Expected output                                                     | Implemented? |
|-------------|---------------------------------------|---------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`     | knight stores `type = KNIGHT`; knight stores `color = BLACK`        | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`     | knight stores `type = KNIGHT`; knight stores `color = WHITE`        | :x: |
| Test Case 3 | constructor args: `color = null`      | knight constructs successfully; knight stores `type = KNIGHT`; knight stores `color = null` | :x: |


### Method under test: `makeCopy()`

|             | System under test                 | Expected output                                                                                         | Implemented? |
|-------------|-----------------------------------|---------------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | knight: `Knight(BLACK)`           | return a distinct `Piece`; copied piece has `type = KNIGHT`; copied piece has `color = BLACK`           | :x: |
| Test Case 5 | knight: `Knight(WHITE)`           | return a distinct `Piece`; copied piece has `type = KNIGHT`; copied piece has `color = WHITE`           | :x: |
| Test Case 6 | knight: `Knight(null)`            | return a distinct `Piece`; copied piece has `type = KNIGHT`; copied piece has `color = null`            | :x: |

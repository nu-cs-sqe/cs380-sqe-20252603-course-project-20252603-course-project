# BVA Analysis for Pawn

`Pawn` is concrete. Its constructor fixes the inherited `type` field to `PieceType.PAWN`, so the only constructor input boundary is the `PieceColor color` argument. The planned tests cover the public constructor and the public `makeCopy()` override declared in `Pawn`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `PieceColor` is a Java reference type, `null` is also a settable invalid boundary for `color`.
  - `makeCopy()` has no parameters, but its relevant input state is the pawn's stored `color`.
- Step 1, output equivalence classes:
  - `Pawn(PieceColor color)` returns a constructed `Pawn` with inherited `type = PAWN` and inherited `color` equal to the constructor argument.
  - `makeCopy()` returns a distinct `Pawn` whose inherited `type = PAWN` and inherited `color` match the original pawn.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - The `color` reference also uses the `Pointers` boundary: null pointer vs pointer to a true object.
  - No public method declared in `Pawn` returns a generated `String`, so string-length boundaries are not applicable here.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus settable pointer boundary `null`.
  - `PieceType` does not vary for `Pawn`; it is always fixed to `PAWN` by the constructor.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice for the single varying `PieceColor` case variable.
  - All-combinations is not needed because there is only one independent varying input/state boundary for this class.
  - Add a separate null-pointer case for `color`.
  - Reuse the same color boundaries as object state boundaries for `makeCopy()`.


### Method under test: `Pawn(PieceColor color)`

|             | System under test                    | Expected output                                                     | Implemented? |
|-------------|--------------------------------------|---------------------------------------------------------------------|--------------|
| Test Case 1 | constructor arg: `color = BLACK`     | pawn constructs successfully; pawn has `type = PAWN`; pawn has `color = BLACK` | :white_check_mark: |
| Test Case 2 | constructor arg: `color = WHITE`     | pawn constructs successfully; pawn has `type = PAWN`; pawn has `color = WHITE` | :white_check_mark: |
| Test Case 3 | constructor arg: `color = null`      | constructor throws `IllegalArgumentException`                                 | :white_check_mark: |


### Method under test: `makeCopy()`

|             | System under test             | Expected output                                                                                  | Implemented? |
|-------------|-------------------------------|--------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | pawn: `Pawn(BLACK)`           | return a distinct `Pawn`; copied pawn has `type = PAWN`; copied pawn has `color = BLACK`        | :white_check_mark: |
| Test Case 5 | pawn: `Pawn(WHITE)`           | return a distinct `Pawn`; copied pawn has `type = PAWN`; copied pawn has `color = WHITE`        | :white_check_mark: |
| Test Case 6 | pawn: `Pawn(null)`            | constructor throws `IllegalArgumentException`                                                    | :white_check_mark: |

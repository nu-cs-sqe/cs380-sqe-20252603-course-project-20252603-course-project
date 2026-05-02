# BVA Analysis for King

`King` is a concrete `Piece` subclass. Its constructor fixes `type` to `PieceType.KING`, so the only constructor input boundary is the supplied `PieceColor`. Inherited getters and `toString()` are covered by `Piece.md`; this file covers the public API declared in `King`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `PieceColor` is a Java reference type, `null` is also a settable pointer boundary for the constructor.
  - `makeCopy()` has no parameters, so its input boundary is the receiver state: a `King` whose stored color is `BLACK`, `WHITE`, or `null`.
- Step 1, output equivalence classes:
  - `King(PieceColor color)` constructs a `King` with `type = KING` and stored `color` equal to the constructor argument, including `null`.
  - `makeCopy()` returns a distinct `Piece` object that is a `King`, has `type = KING`, and has the same stored color as the original.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - The nullable `color` reference also uses the `Pointers` boundary: null pointer vs pointer to a true object.
  - The `makeCopy()` return value is also a reference, but the implementation should always return a pointer to a true object rather than `null`.
  - `King` does not generate strings, so string-length boundaries are not applicable here.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus settable pointer boundary `null`.
  - `PieceType.KING` is fixed by `King` and is not a variable input for this class.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice because there is only one independent input/state variable, `color`.
  - Include the null-pointer boundary separately for both construction and copied receiver state.


### Method under test: `King(PieceColor color)`

|             | System under test                           | Expected output                                                        | Implemented? |
|-------------|---------------------------------------------|------------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`           | king constructs successfully; stores `type = KING`; stores `color = BLACK` | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`           | king constructs successfully; stores `type = KING`; stores `color = WHITE` | :white_check_mark: |
| Test Case 3 | constructor args: `color = null`            | throws `IllegalArgumentException` | :white_check_mark: |


### Method under test: `makeCopy()`

|             | System under test                           | Expected output                                                                                       | Implemented? |
|-------------|---------------------------------------------|-------------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | king: `King(BLACK)`                         | return a distinct `Piece`; copied piece is a `King`; copied piece has `type = KING`; copied piece has `color = BLACK` | :white_check_mark: |
| Test Case 5 | king: `King(WHITE)`                         | return a distinct `Piece`; copied piece is a `King`; copied piece has `type = KING`; copied piece has `color = WHITE` | :white_check_mark: |
| Test Case 6 | king: `King(null)`                          | throws `IllegalArgumentException` | :white_check_mark: |

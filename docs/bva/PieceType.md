# BVA Analysis for PieceType

`PieceType` is a Java enum. The public behavior to test is the declared enum constants plus the compiler-generated enum methods `values()` and `valueOf(String)`. The private constructor argument `name` is implementation state only; there is no public accessor for it, and `PieceType` does not override `toString()`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - Direct enum constant access has no runtime input; the public cases are `PAWN`, `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN`, and `KING`.
  - `values()` has no input.
  - `valueOf(String name)` accepts exact enum identifier strings `PAWN`, `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN`, and `KING`.
  - `valueOf(String name)` also has invalid string inputs that do not exactly match an enum identifier, including private display strings such as `Pawn`, and the nullable reference boundary `null`.
- Step 1, output equivalence classes:
  - Direct enum constant access evaluates to the requested `PieceType` singleton.
  - `values()` returns a non-null array containing all six `PieceType` constants in declaration order.
  - `valueOf(String)` returns the matching `PieceType` singleton for exact identifier strings.
  - `valueOf(String)` throws `IllegalArgumentException` for non-matching non-null strings and `NullPointerException` for `null`.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceType` constants are `Cases`.
  - The `String name` input to `valueOf(String)` is a reference, so `null` uses the `Pointers` boundary.
  - Non-null `String name` inputs are tested by exact enum identifier state, not by arbitrary string-length boundaries.
  - The private constructor display `name` values are implementation state; because no public accessor exposes them, they are only relevant as non-matching `valueOf(String)` inputs.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceType`: first `PAWN`, second `ROOK`, interior `KNIGHT`, `BISHOP`, `QUEEN`, and last `KING`.
  - `valueOf(String)` valid strings: `"PAWN"`, `"ROOK"`, `"KNIGHT"`, `"BISHOP"`, `"QUEEN"`, and `"KING"`.
  - `valueOf(String)` invalid strings: `"Pawn"` to confirm private display names are not public enum identifiers, and `"pawn"` to confirm exact case-sensitive matching.
  - `valueOf(String)` pointer boundary: `null`.
  - Impossible non-string and non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice across the enum `Cases` so every declared `PieceType` appears at least once.
  - Use one aggregate `values()` case to verify the complete declaration-order boundary from first through last.
  - Add separate negative `valueOf(String)` cases for non-matching strings and the null-pointer boundary.


### Method under test: enum constants

|             | System under test             | Expected output                                                                 | Implemented? |
|-------------|-------------------------------|---------------------------------------------------------------------------------|--------------|
| Test Case 1 | reference `PieceType.PAWN`    | enum constant exists; evaluates to the `PAWN` singleton                         | :x: |
| Test Case 2 | reference `PieceType.ROOK`    | enum constant exists; evaluates to the `ROOK` singleton                         | :x: |
| Test Case 3 | reference `PieceType.KNIGHT`  | enum constant exists; evaluates to the `KNIGHT` singleton                       | :x: |
| Test Case 4 | reference `PieceType.BISHOP`  | enum constant exists; evaluates to the `BISHOP` singleton                       | :x: |
| Test Case 5 | reference `PieceType.QUEEN`   | enum constant exists; evaluates to the `QUEEN` singleton                        | :x: |
| Test Case 6 | reference `PieceType.KING`    | enum constant exists; evaluates to the `KING` singleton                         | :x: |


### Method under test: `values()`

|             | System under test       | Expected output                                                                                   | Implemented? |
|-------------|-------------------------|---------------------------------------------------------------------------------------------------|--------------|
| Test Case 7 | call `PieceType.values()` | return non-null array `[PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING]` in declaration order; length `6` | :x: |


### Method under test: `valueOf(String)`

|              | System under test                     | Expected output              | Implemented? |
|--------------|---------------------------------------|------------------------------|--------------|
| Test Case 8  | call `PieceType.valueOf("PAWN")`      | return `PieceType.PAWN`      | :x: |
| Test Case 9  | call `PieceType.valueOf("ROOK")`      | return `PieceType.ROOK`      | :x: |
| Test Case 10 | call `PieceType.valueOf("KNIGHT")`    | return `PieceType.KNIGHT`    | :x: |
| Test Case 11 | call `PieceType.valueOf("BISHOP")`    | return `PieceType.BISHOP`    | :x: |
| Test Case 12 | call `PieceType.valueOf("QUEEN")`     | return `PieceType.QUEEN`     | :x: |
| Test Case 13 | call `PieceType.valueOf("KING")`      | return `PieceType.KING`      | :x: |
| Test Case 14 | call `PieceType.valueOf("Pawn")`      | `IllegalArgumentException`   | :x: |
| Test Case 15 | call `PieceType.valueOf("pawn")`      | `IllegalArgumentException`   | :x: |
| Test Case 16 | call `PieceType.valueOf(null)`        | `NullPointerException`       | :x: |

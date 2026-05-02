# BVA Analysis for PieceColor

`PieceColor` is a Java enum with compiler-generated public enum behavior. The planned tests cover the declared constants plus the generated `values()` and `valueOf(String)` methods.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - The enum constants have no method input; the public cases are `BLACK` and `WHITE`.
  - `values()` has no method input.
  - `valueOf(String name)` accepts exact valid names `"BLACK"` and `"WHITE"`.
  - Because `name` is a Java reference, `null` is an invalid pointer boundary for Java's generated enum API; this does not make null a normal domain value.
  - Invalid generated strings are names tied to the enum input state, such as wrong case, extra whitespace, and a missing enum case; they are not string-length boundaries.
- Step 1, output equivalence classes:
  - Constant access returns the singleton enum objects `PieceColor.BLACK` and `PieceColor.WHITE`.
  - `values()` returns a new `PieceColor[]` containing exactly `BLACK` then `WHITE`.
  - `valueOf(String)` returns `BLACK` or `WHITE` for exact valid names.
  - Java's generated `valueOf(String)` throws `NullPointerException` with message `"Name is null"` for `null`.
  - Java's generated `valueOf(String)` throws `IllegalArgumentException` with message `"No enum constant domain.piece.PieceColor.<input>"` for non-null invalid names.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` enum constants and enum outputs use the `Cases` category.
  - The `valueOf(String name)` reference also uses the `Pointers` category for Java enum API behavior: null pointer vs pointer to a real `String` object.
  - Generated strings for invalid `valueOf` inputs are tied to the allowed enum names, not to minimum or maximum string length boundaries.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first case `BLACK`, second/last case `WHITE`.
  - `valueOf` valid names: `"BLACK"` and `"WHITE"`.
  - `valueOf` invalid pointer boundary: `null`, which is rejected by Java's enum API.
  - `valueOf` invalid generated names: `"black"` for wrong case, `" BLACK "` for extra whitespace, and `"RED"` for a missing enum case.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice because the enum has one independent case variable and one nullable `String` input for `valueOf`.
  - Include every enum case at least once for constants, `values()`, and valid `valueOf` behavior.
  - Add separate invalid pointer and invalid generated-name cases for Java's generated `valueOf` API.


### Method under test: enum constants

|             | System under test                    | Expected output                                            | Implemented? |
|-------------|--------------------------------------|------------------------------------------------------------|--------------|
| Test Case 1 | access constant `PieceColor.BLACK`   | returns singleton enum constant `BLACK`; name is `"BLACK"` | :white_check_mark: |
| Test Case 2 | access constant `PieceColor.WHITE`   | returns singleton enum constant `WHITE`; name is `"WHITE"` | :white_check_mark: |


### Method under test: `values()`

|             | System under test                         | Expected output                                                          | Implemented? |
|-------------|-------------------------------------------|--------------------------------------------------------------------------|--------------|
| Test Case 3 | call `PieceColor.values()`                | returns array of length 2: index 0 is `BLACK`, index 1 is `WHITE`        | :white_check_mark: |
| Test Case 4 | call `PieceColor.values()` twice          | returns distinct array objects; both arrays contain `BLACK` then `WHITE` | :white_check_mark: |


### Method under test: `valueOf(String name)`

|             | System under test                         | Expected output                | Implemented? |
|-------------|-------------------------------------------|--------------------------------|--------------|
| Test Case 5 | call `PieceColor.valueOf("BLACK")`        | return `BLACK`                 | :white_check_mark: |
| Test Case 6 | call `PieceColor.valueOf("WHITE")`        | return `WHITE`                 | :white_check_mark: |
| Test Case 7 | call `PieceColor.valueOf(null)`           | Java enum API throws `NullPointerException` with message `"Name is null"` | :x: |
| Test Case 8 | call `PieceColor.valueOf("black")`        | Java enum API throws `IllegalArgumentException` with message `"No enum constant domain.piece.PieceColor.black"` | :x: |
| Test Case 9 | call `PieceColor.valueOf(" BLACK ")`      | Java enum API throws `IllegalArgumentException` with message `"No enum constant domain.piece.PieceColor. BLACK "`; the final space is part of the message | :x: |
| Test Case 10 | call `PieceColor.valueOf("RED")`         | Java enum API throws `IllegalArgumentException` with message `"No enum constant domain.piece.PieceColor.RED"` | :x: |

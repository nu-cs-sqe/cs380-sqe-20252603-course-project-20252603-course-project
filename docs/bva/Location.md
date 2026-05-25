# BVA Analysis for Location

`Location` is a simple value object that stores a pair of `(x, y)` integer board coordinates as `final` fields. It performs no validation — any `int` is accepted and returned as-is. The real domain constraint (columns 0–7, rows 0–7 on a standard 8×8 chess board) comes from the chess context, not from `Location` itself. BVA boundaries are therefore drawn around that chess-board range.

## Step 1–3 Summary

- **Step 1, input equivalence classes:** For each of `x` and `y`: below-valid-range (< 0), within-valid-range (0..7), above-valid-range (> 7).
- **Step 1, output equivalence classes:** `getX()` and `getY()` are pure getters — the output class mirrors the stored value, so the same three partitions apply to each output.
- **Step 2, BVA catalog mapping:** Both `x` and `y` map to the **Bounded Integer** category with domain [0, 7].
- **Step 3, concrete boundary values selected:** −1 (below min), 0 (min), 1 (min+1), 6 (max−1), 7 (max), 8 (above max) — applied independently to each axis.
- **Step 4 strategy:** **Each-choice** — because `x` and `y` are independent inputs, there is no need to enumerate all pairs; one dedicated sweep per axis is sufficient. `y` is held at a neutral mid-range value (3) while sweeping `x`, and vice versa.

---

### Method under test: `getX()`

Each test constructs a `Location` with the listed `x` (and `y = 3`) and calls `getX()`.

|              | State of the System                  | Expected output | Implemented? |
|--------------|--------------------------------------|-----------------|--------------|
| Test Case 1  | `Location(0, 3)` — x at min (0)      | `0`             | :white_check_mark:          |
| Test Case 2  | `Location(7, 3)` — x at max (7)      | `7`             | :white_check_mark:          |
| Test Case 3  | `Location(1, 3)` — x at min+1 (1)   | `1`             | :white_check_mark:          |
| Test Case 4  | `Location(6, 3)` — x at max−1 (6)   | `6`             | :white_check_mark:          |
| Test Case 5  | `Location(-1, 3)` — x just below min | `-1`            | :white_check_mark:          |
| Test Case 6  | `Location(8, 3)` — x just above max  | `8`             | :white_check_mark:          |

---

### Method under test: `getY()`

Each test constructs a `Location` with the listed `y` (and `x = 3`) and calls `getY()`.

|               | State of the System                    | Expected output | Implemented? |
|---------------|----------------------------------------|-----------------|--------------|
| Test Case 7   | `Location(3, 0)` — y at min (0)        | `0`             | :white_check_mark:          |
| Test Case 8   | `Location(3, 7)` — y at max (7)        | `7`             | :white_check_mark:          |
| Test Case 9   | `Location(3, 1)` — y at min+1 (1)     | `1`             | :white_check_mark:          |
| Test Case 10  | `Location(3, 6)` — y at max−1 (6)     | `6`             | :white_check_mark:          |
| Test Case 11  | `Location(3, -1)` — y just below min   | `-1`            | :white_check_mark:          |
| Test Case 12  | `Location(3, 8)` — y just above max    | `8`             | :white_check_mark:          |

---

### Method under test: `isOnBoard()`

`isOnBoard()` returns `true` when both `x` and `y` are within [0, 7]; `false` otherwise. The boundary variables are `x` and `y`, treated independently via each-choice. `y` is held at 3 while sweeping `x`, and vice versa.

|               | State of the System                      | Expected output | Implemented? |
|---------------|------------------------------------------|-----------------|--------------|
| Test Case 13  | `Location(0, 3)` — x at min             | `true`          | :white_check_mark: |
| Test Case 14  | `Location(7, 3)` — x at max             | `true`          | :white_check_mark: |
| Test Case 15  | `Location(1, 3)` — x at min+1           | `true`          | :white_check_mark: |
| Test Case 16  | `Location(6, 3)` — x at max−1           | `true`          | :white_check_mark: |
| Test Case 17  | `Location(-1, 3)` — x just below min    | `false`         | :white_check_mark: |
| Test Case 18  | `Location(8, 3)` — x just above max     | `false`         | :white_check_mark: |
| Test Case 19  | `Location(3, 0)` — y at min             | `true`          | :white_check_mark: |
| Test Case 20  | `Location(3, 7)` — y at max             | `true`          | :white_check_mark: |
| Test Case 21  | `Location(3, 1)` — y at min+1           | `true`          | :white_check_mark: |
| Test Case 22  | `Location(3, 6)` — y at max−1           | `true`          | :white_check_mark: |
| Test Case 23  | `Location(3, -1)` — y just below min    | `false`         | :white_check_mark: |
| Test Case 24  | `Location(3, 8)` — y just above max     | `false`         | :white_check_mark: |

---

### Method under test: `getIntermediateSquares(Location to)`

`getIntermediateSquares` returns the squares strictly between `this` and `to` along the same rank, file, or diagonal. The path length (number of intermediate squares) is the key boundary variable: 0 (adjacent or same location) and ≥1 (skips one or more squares). Null `to` is also a boundary (`Pointers` category).

|               | State of the System                                    | Expected output                              | Implemented? |
|---------------|--------------------------------------------------------|----------------------------------------------|--------------|
| Test Case 25  | `from = Location(4,4)`, `to = Location(4,4)` — same square | empty list                              | :white_check_mark: |
| Test Case 26  | `from = Location(3,4)`, `to = Location(4,4)` — adjacent horizontal | empty list                    | :white_check_mark: |
| Test Case 27  | `from = Location(4,3)`, `to = Location(4,4)` — adjacent vertical | empty list                      | :white_check_mark: |
| Test Case 28  | `from = Location(3,3)`, `to = Location(4,4)` — adjacent diagonal | empty list                     | :white_check_mark: |
| Test Case 29  | `from = Location(0,4)`, `to = Location(3,4)` — horizontal, 2 intermediate | `[(1,4),(2,4)]`  | :white_check_mark: |
| Test Case 30  | `from = Location(4,0)`, `to = Location(4,3)` — vertical, 2 intermediate | `[(4,1),(4,2)]`   | :white_check_mark: |
| Test Case 31  | `from = Location(0,0)`, `to = Location(3,3)` — diagonal, 2 intermediate | `[(1,1),(2,2)]`  | :white_check_mark: |
| Test Case 32  | `to = null`                                            | `IllegalArgumentException("to must not be null")` | :white_check_mark: |

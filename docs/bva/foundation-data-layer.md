# BVA: Foundation Data Layer

Author: Xinyuan Liu
Classes: `Position`, `Piece`, `Player`, `Color`, `PieceType`

---

## 1. `Position.isValid()`

**Input**: `row` (int), `col` (int)
**Output**: `boolean` — true only if both are in [0, 7]

**Boundaries**:
- Row: min=0, max=7 → boundaries at -1, 0, 1, 6, 7, 8
- Col: min=0, max=7 → boundaries at -1, 0, 1, 6, 7, 8

| Test Case | row | col | Expected | Reason |
|-----------|-----|-----|----------|--------|
| BVA-P-01 | -1  |  4  | false    | row just below min |
| BVA-P-02 |  0  |  4  | true     | row at min |
| BVA-P-03 |  1  |  4  | true     | row just above min |
| BVA-P-04 |  6  |  4  | true     | row just below max |
| BVA-P-05 |  7  |  4  | true     | row at max |
| BVA-P-06 |  8  |  4  | false    | row just above max |
| BVA-P-07 |  4  | -1  | false    | col just below min |
| BVA-P-08 |  4  |  0  | true     | col at min |
| BVA-P-09 |  4  |  7  | true     | col at max |
| BVA-P-10 |  4  |  8  | false    | col just above max |
| BVA-P-11 |  0  |  0  | true     | both at min (corner a1) |
| BVA-P-12 |  7  |  7  | true     | both at max (corner h8) |
| BVA-P-13 | -1  | -1  | false    | both below min |
| BVA-P-14 |  8  |  8  | false    | both above max |

---

## 2. `Position.equals()`

**Logic**: Two positions are equal iff row and col are both equal.

| Test Case | pos1       | pos2       | Expected | Reason |
|-----------|------------|------------|----------|--------|
| BVA-PE-01 | (3, 4)     | (3, 4)     | true     | same row and col |
| BVA-PE-02 | (3, 4)     | (3, 5)     | false    | same row, different col |
| BVA-PE-03 | (3, 4)     | (4, 4)     | false    | different row, same col |
| BVA-PE-04 | (3, 4)     | null       | false    | null comparison |

---

## 3. `Piece` constructor

**Logic**: A piece must have a non-null `PieceType` and a non-null `Color`.

| Test Case | type    | color | Expected           | Reason |
|-----------|---------|-------|--------------------|--------|
| BVA-PC-01 | KING    | WHITE | valid Piece object | normal case |
| BVA-PC-02 | PAWN    | BLACK | valid Piece object | normal case |
| BVA-PC-03 | null    | WHITE | IllegalArgumentException | null type |
| BVA-PC-04 | QUEEN   | null  | IllegalArgumentException | null color |

---

## 4. `Player` constructor

**Logic**: A player must have a non-null, non-empty name and a non-null `Color`.

| Test Case | name        | color | Expected            | Reason |
|-----------|-------------|-------|---------------------|--------|
| BVA-PL-01 | "Alice"     | WHITE | valid Player object | normal case |
| BVA-PL-02 | "Bob"       | BLACK | valid Player object | normal case |
| BVA-PL-03 | ""          | WHITE | IllegalArgumentException | empty name |
| BVA-PL-04 | null        | WHITE | IllegalArgumentException | null name |
| BVA-PL-05 | "Alice"     | null  | IllegalArgumentException | null color |
| BVA-PL-06 | "  "        | WHITE | IllegalArgumentException | blank (whitespace-only) name |
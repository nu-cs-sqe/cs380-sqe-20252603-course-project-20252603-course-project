# BVA Analysis for Board

## Method under test: `Board()`

| ID | Method(s) under test | System under test | Expected output | Implemented?    |
|---|---|---|---|-----------------|
| TC1 | `Board()`, `getSize()` | New board is constructed | Board size is `8` | :white_check_mark: |
| TC2 | `Board()`, `getSquare(int row, int col)` | New board is constructed | All valid squares are empty | :white_check_mark:                |

## Method under test: `getSize()`

| ID | Method(s) under test | System under test | Expected output | Implemented? |
|---|---|---|---|--------------|
| TC3 | `getSize()` | New board is constructed | Returns `8` | :white_check_mark: Done in TC1 |

## Method under test: `getSquare(int row, int col)`

| ID | Method(s) under test | System under test | Expected output | Implemented? |
|---|---|---|---|--|
| TC4 | `getSquare(int, int)` | Position `(0, 0)` | Does not throw; returns square contents | :white_check_mark: |
| TC5 | `getSquare(int, int)` | Position `(7, 7)` | Does not throw; returns square contents | :white_check_mark: |
| TC6 | `getSquare(int, int)` | Position `(-1, 0)` | Throws `IndexOutOfBoundsException` | :white_check_mark: |
| TC7 | `getSquare(int, int)` | Position `(8, 0)` | Throws `IndexOutOfBoundsException` | :white_check_mark: |
| TC8 | `getSquare(int, int)` | Position `(0, -1)` | Throws `IndexOutOfBoundsException` | :white_check_mark: |
| TC9 | `getSquare(int, int)` | Position `(0, 8)` | Throws `IndexOutOfBoundsException` | :white_check_mark: |


## Method under test: `setupInitialPosition()`

| ID | Method(s) under test | System under test | Expected output | Implemented? |
|---|---|---|---|---|
| TC10 | `setupInitialPosition()`, `getSquare(int, int)` | Empty board, after setup | Board contains exactly `32` non-null pieces | :x: |
| TC11 | `setupInitialPosition()`, `getSquare(int, int)` | Empty board, after setup | White back row pieces are in standard starting positions | :x: |
| TC12 | `setupInitialPosition()`, `getSquare(int, int)` | Empty board, after setup | Black back row pieces are in standard starting positions | :x: |
| TC13 | `setupInitialPosition()`, `getSquare(int, int)` | Empty board, after setup | White pawns occupy all squares in the white pawn row | :x: |
| TC14 | `setupInitialPosition()`, `getSquare(int, int)` | Empty board, after setup | Black pawns occupy all squares in the black pawn row | :x: |
| TC15 | `setupInitialPosition()`, `getSquare(int, int)` | Empty board, after setup | Middle rows contain only empty squares | :x: |
| TC16 | `setupInitialPosition()`, `getSquare(int, int)` | Board already contains pieces, after setup | Board is reset/overwritten into the standard starting position | :x: |



## Method under test: `isEmpty(int row, int col)`

| ID | Method(s) under test | System under test | Expected output | Implemented? |
|---|---|---|---|---|
| TC17 | `isEmpty(int, int)` | New board, position `(0, 0)` | Returns `true` | :x: |
| TC18 | `isEmpty(int, int)` | Board after `setupInitialPosition()`, occupied starting square | Returns `false` | :x: |
| TC19 | `isEmpty(int, int)` | Board after `setupInitialPosition()`, empty middle square | Returns `true` | :x: |
| TC20 | `isEmpty(int, int)` | Position `(-1, 0)` | Throws `IndexOutOfBoundsException` | :x: |
| TC21 | `isEmpty(int, int)` | Position `(8, 0)` | Throws `IndexOutOfBoundsException` | :x: |
| TC22 | `isEmpty(int, int)` | Position `(0, -1)` | Throws `IndexOutOfBoundsException` | :x: |
| TC23 | `isEmpty(int, int)` | Position `(0, 8)` | Throws `IndexOutOfBoundsException` | :x: |
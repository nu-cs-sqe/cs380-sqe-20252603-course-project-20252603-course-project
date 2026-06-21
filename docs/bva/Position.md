# BVA: Position

### Method under test: `Position(int row, int col)`
|     | State of the System          | Expected output                 | Implemented?       |
|-----|------------------------------|---------------------------------|--------------------|
| TC1 | row=1, col=1 (both at LOW)   | Position created successfully   | :white_check_mark: |
| TC2 | row=8, col=8 (both at HIGH)  | Position created successfully   | :white_check_mark: |
| TC3 | row=0, col=1 (row at LOW-1)  | IllegalArgumentException thrown | :white_check_mark: |
| TC4 | row=9, col=1 (row at HIGH+1) | IllegalArgumentException thrown | :white_check_mark: |
| TC5 | row=1, col=0 (col at LOW-1)  | IllegalArgumentException thrown | :white_check_mark: |
| TC6 | row=1, col=9 (col at HIGH+1) | IllegalArgumentException thrown | :white_check_mark: |

### Method under test: `getRow()`
|     | State of the System         | Expected output | Implemented?       |
|-----|-----------------------------|-----------------|--------------------|
| TC7 | Position created with row=1 | Returns 1       | :white_check_mark: |
| TC8 | Position created with row=8 | Returns 8       | :white_check_mark: |

### Method under test: `getCol()`
|      | State of the System         | Expected output | Implemented?       |
|------|-----------------------------|-----------------|--------------------|
| TC9  | Position created with col=1 | Returns 1       | :white_check_mark: |
| TC10 | Position created with col=8 | Returns 8       | :white_check_mark: |

### Method under test: `equals(Object o)`
|      | State of the System                                    | Expected output | Implemented?       |
|------|--------------------------------------------------------|-----------------|--------------------|
| TC11 | Comparing a Position to itself (same object reference) | true            | :white_check_mark: |
| TC12 | Two different Position objects with same row and col   | true            | :white_check_mark: |
| TC13 | Two Position objects with different row, same col      | false           | :white_check_mark: |
| TC14 | Two Position objects with same row, different col      | false           | :white_check_mark: |
| TC15 | Comparing Position to null                             | false           | :white_check_mark: |

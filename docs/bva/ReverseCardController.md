# BVA Analysis for ReverseCardController class

### Method under test: `executeCardAction()`
- **TC1: Two Players (Minimum Valid Boundary)** ( :white_check_mark: )
    - **State of the system**: Game has exactly 2 alive players. Current index is 0.
    - **Expected output**: Turn order is reversed. Current index is updated to 1. Next index is updated to 0.

- **TC2: Three Players (Minimum + 1 Boundary)** ( :white_check_mark: )
    - **State of the system**: Game has exactly 3 alive players. Current index is 1.
    - **Expected output**: Turn order is reversed. Current index is updated to 1. Next index is updated to 2.

- **TC3: Five Players (Maximum Valid Boundary)** ( :white_check_mark: )
    - **State of the system**: Game has 5 alive players. Current index is 2.
    - **Expected output**: Turn order is reversed. Current index is updated to 2. Next index is updated to 3.

- **TC4: Five Players, Current Player at Max Index (Upper Boundary)** ( :white_check_mark: )
  - **State of the system**: Game has 5 alive players. Current index is 4 (the last player in the original array).
  - **Expected output**: The player order is completely reversed. Current index is correctly mapped to 0. The `nextPlayerIndex` increments and wraps to 1.

- **TC5: Three Players, Current Player at Min Index (Lower Boundary)** ( :white_check_mark: )
  - **State of the system**: Game has 3 alive players: [P0, P1, P2]. Current index is 0 (the first player in the original array).
  - **Expected output**: The player object order is reversed to exactly [P2, P1, P0]. Current index is mapped to 2. The `nextPlayerIndex` math successfully wraps around the array length and becomes 0.
# Dice Service BVA

### Method under test: rollDice
|                    | State of the System                                                         | Expected output                                                                    | Implemented?         |
|--------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------------------|----------------------|
| T1_expectReturnMax | Standard state, dice is rolled and is stubbed to return 6.                  | A value of 6 is returned                                                           | :white_check_mark:   |
| T2_expectReturnMin | Standard state, dice is rolled and is stubbed to return 1.                  | A value of 1 is returned                                                           | :white_check_mark:   |
| T3_multiRoll2Rolls | multiRollDice is called with a roll count of 3.                             | An integer array of length 6 is returned with expected values.                     | :white_check_mark:   |
| T4_multiRollLessThan1 | multiRollDice is called with a roll count of less than 1 (in this case, 0). | An execption is thrown. It is not premitted to roll a non postive number of times. | :white_check_mark:   |

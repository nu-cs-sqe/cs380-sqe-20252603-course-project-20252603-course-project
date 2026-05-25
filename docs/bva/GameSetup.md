# Game Setup BVA

### Method under test: validatePlayerCount
|                                  | State of the System    | Expected output             | Implemented?             |
|----------------------------------|------------------------|-----------------------------|--------------------------|
| TC1_ValidateMinimumSizeGame      | playerCount is 2       | The playerCount is accepted | :white_check_mark:        |
| TC2_ValidateBelowMinimumSizeGame | playerCount is 1       | The playerCount is rejected | :white_check_mark:        |
| TC3_ValidateMaximumSizeGame      | playerCount is 6       | The playerCount is accepted | :white_check_mark:        |
| TC4_ValidateAboveMaximumSizeGame | playerCount is 7       | The playerCount is rejected | :white_check_mark:        |
| TC5_ValidateINT_MAX              | playerCount is INT_MAX | The playerCount is rejected | :white_check_mark:        |
| TC6_ValidateINT_MIN              | playerCount is INT_MIN | The playerCount is rejected | :white_check_mark: |
| TC7_ValidateInRangeOfValidGame   | playerCount is 4       | The playerCount is accepted | :white_check_mark: |

### Method under test: createPlayer
|                                                 | State of the System                                                                                 | Expected output                                                                                           | Implemented? |
|-------------------------------------------------|-----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|-------------|
| TC1_CreatePlayersBasic                          | Standard list of names and colors of matching length are used.                                      | createPlayer should initilize the private field Players successfully with a list of Player instances.     | :white_check_mark:          |
| TC2_CreatePlayersUnequalListLengthSmallerNames  | Names list is shorter than the color list (names has fewer elements, index mismatch).               | createPlayer should throw an error. It is not possible to create valid Players list.                      | :white_check_mark:          |
| TC3_CreatePlayersUnequalListLengthSmallerColors | Colors list is shorter than the names list (colors has fewer elements, index mismatch).             | createPlayer should throw an error. It is not possible to create valid Players list.                      | :white_check_mark:          |
| TC4_CreatePlayersBothListsEmpty                 | Both lists, colors and names, are empty.                                                            | createPlayer should throw an error. This should propogate up from the validatePlayerCount method.         | :white_check_mark:          |
| TC5_CreatePlayersBothListsOversized             | Both lists, colors and names, have an equal number of elements greater than the maximum allowed, 6. | createPlayer should throw an error. This should propogate up from the validatePlayerCount method.         | :white_check_mark:          |
| TC6_CreatePlayersBasicRepeatNames               | Standard list of names and colors of matching length are used, but some names are repeated.         | createPlayer should initilize the private field Players successfully with a list of Player instances.     | :white_check_mark:          |
| TC7_CreatePlayersBasicRepeatColors              | Standard list of names and colors of matching length are used, but some colors are repeated.        | createPlayer should throw an error. It is not possible to create valid Players list with repeated colors. | :white_check_mark:          |
| TC8_CreatePlayersBasicMaxLen                    | Standard list of names and colors of matching length are used. Maximum number of players.           | createPlayer should initilize the private field Players successfully with a list of Player instances.     | :white_check_mark:          |
| TC9_CreatePlayersBasicMinLen                    | Standard list of names and colors of matching length are used. Minimum number of players.           | createPlayer should initilize the private field Players successfully with a list of Player instances.     | :white_check_mark:          |

### Method under test: validateUniqueColors
|                                   | State of the System                                                                           | Expected output                                                            | Implemented? |
|-----------------------------------|-----------------------------------------------------------------------------------------------|----------------------------------------------------------------------------|-------------|
| TC1_ValidateUniqueColorsNoRepeats | List colors, of type PlayerColors, contains a list of non-repeating, valid, colors.           | This is unique, so no error should be thrown. List accepted.               | :white_check_mark:          |
| TC2_ValidateUniqueColorsRepeats   | List colors, of type PlayerColors, contains a list of repeating, but otherwise valid, colors. | This is a non-unique list, so an error should be thrown. List rejected.    | :white_check_mark:          |
| TC3_ValidateUniqueColorsEmpty | List colors, of type PlayerColors, contains an empty list.                                    | This is unique, albeit empty, so no error should be thrown. List accepted. | :white_check_mark:          |
| TC4_ValidateUniqueColorsManyRepeats   | List colors, of type PlayerColors, contains a list of repeating, but otherwise valid, colors. | This is a non-unique list, so an error should be thrown. List rejected.    | :white_check_mark:          |

### Method under test: CreateNewGame
|                                       | State of the System                           | Expected output                                                          | Implemented?       |
|---------------------------------------|-----------------------------------------------|--------------------------------------------------------------------------|--------------------|
| TC1_Create_Game_Function_Requirements | List colors, List of PlayerColors, all valid, | Should return created GameState with valid players and territories added | :white_check_mark: |


### Method under test: orchestra
|                                                         | State of the System                                                           | Expected output                                                                                                                             | Implemented? |
|---------------------------------------------------------|-------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| TC1_shouldInitializeTurnOrderAfterSetup                 | Lists of unique and valid PlayerColors and names, allows for valid creation.  | GameState is properly orchestrated, TurnOrder is not null.                                                                                  | :white_check_mark:          |
| TC2_shouldSetCurrentPlayerToFirstPlayerInTurnOrder      | Lists of unique and valid PlayerColors and names, allows for valid creation.  | GameState is properly orchestrated, First Player is not null, and is a player object.                                                       | :white_check_mark:          |
| TC3_shouldSetGamePhaseToReinforcementWhenSetupCompletes | Lists of unique and valid PlayerColors and names, allows for valid creation.  | GameState is properly orchestrated. GamePhase is correctly set to GamePhase.Reinforcement                                                   | :white_check_mark:          |
| TC4_shouldReturnFullyInitializedGameState               | Lists of unique and valid PlayerColors and names, allows for valid creation.  | GameState is properly orchestrated, Territories and Player numbers are correct, as well as other requirements for game State initialization | :white_check_mark:          |

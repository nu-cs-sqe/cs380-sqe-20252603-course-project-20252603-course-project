# Game Setup BVA

### Method under test: canFortify
|                                                         | State of the System                                           | Expected output       | Implemented?         |
|---------------------------------------------------------|---------------------------------------------------------------|-----------------------|----------------------|
| T1_shouldAllowFortifyBetweenConnectedOwnedTerritories   | One Player, two adjacent territories, valid movement          | Valid Movement        | :white_check_mark:   |
| T2_shouldRejectFortifyWhenSourceIsNotOwnedByPlayer      | Player 1 owns dest, player 2 owns src, both adjacent          | Movement is rejected  | :white_check_mark:   |
| T3_shouldRejectFortifyWhenDestinationIsNotOwnedByPlayer | Player 2 owns dest, player 1 owns src, both adjacent          | Movement is rejected  | :white_check_mark:   |
| T5_shouldRejectFortifyWhenSourceHasOnlyOneArmy          | Player 1 owns dest & src, src has 1 army.                     | Movement is rejected  | :white_check_mark:   |
| T6_shouldRejectFortifyWhenMoveWouldLeaveSourceEmpty     | Player 1 owns dest & src, src has 5 armies, armiestoMove is 5 | Movement i s rejected | :white_check_mark:   |

### Method under test: areConnectedThroughOwnedTerritories
|                                                         | State of the System                                  | Expected output              | Implemented?         |
|---------------------------------------------------------|------------------------------------------------------|------------------------------|----------------------|
| T1_shouldAllowFortifyBetweenConnectedOwnedTerritories   | One Player, two adjacent territories, valid movement | Valid Movement               | :white_check_mark:   |
| T2_shouldRejectFortifyWhenSourceIsNotOwnedByPlayer      | Player 1 owns dest, player 2 owns src, both adjacent | Valid Movement               | :white_check_mark:   |
| T3_shouldRejectFortifyWhenDestinationIsNotOwnedByPlayer | Player 2 owns dest, player 1 owns src, both adjacent | Valid Movement               | :white_check_mark:   |
| T4_shouldRejectFortifyWhenTerritoriesAreNotConnected    | SRC and DEST owned by Player 1, not adjacent         | Movement is rejected         | :white_check_mark:   |
# Game Setup BVA

### Method under test: canAttack
|                                                                        | State of the System                                                                                             | Expected output       | Implemented?         |
|------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|-----------------------|----------------------|
| T1_shouldAllowAttackIfTerritoriesAreAdjacentAndOwnedByDifferentPlayers | Two adjacent territories owned by different players where the attacker has more than 1 army on their territory. | Valid Attack (True)   | :white_check_mark:   |
| T2_shouldRejectAttackIfAttackingTerritoryIsNotOwnedByCurrentPlayer     | Attacking territory is owned by a player other than the attacker. Otherwise normal state.                       | Failed Attack (False) | :white_check_mark:   |
| T3_shouldRejectAttackIfDefendingTerritoryIsOwnedByCurrentPlayer      | Defending territory is owned by the attacker. Otherwise normal state.                                           | Failed Attack (False) | :white_check_mark:   |
| T4_shouldRejectAttackIfTerritoriesAreNotAdjacent                     | Attacking and defending territories are not adjacent. Otherwise normal state.                                   | Failed Attack (False) | :white_check_mark:   |
| T5_shouldRejectAttackIfAttackingTerritoryHasOnlyOneArmy              | Attacking player has only one army available on the territory they are attacking from. Otherwise normal state.  | Failed Attack (False) | :white_check_mark:   |

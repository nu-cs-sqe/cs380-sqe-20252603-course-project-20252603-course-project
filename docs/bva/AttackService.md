# Game Setup BVA

### Method under test: canAttack
|                                                                          | State of the System                                                                                             | Expected output       | Implemented?         |
|--------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|-----------------------|----------------------|
| T1_shouldAllowAttackWhenTerritoriesAreAdjacentAndOwnedByDifferentPlayers | Two adjacent territories owned by different players where the attacker has more than 1 army on their territory. | Valid Attack (True)   | :white_check_mark:   |
| T2_shouldRejectAttackWhenAttackingTerritoryIsNotOwnedByCurrentPlayer     | Attacking territory is owned by a player other than the attacker. Otherwise normal state.                       | Failed Attack (False) | :white_check_mark:   |

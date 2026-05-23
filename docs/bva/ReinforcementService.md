### Method under test: placeReinforcements(Player player, Territory territory, int armies, GameState gameState)
|                                                                 | State of the System                                                                  | Expected output                        | Implemented?           |
|-----------------------------------------------------------------|--------------------------------------------------------------------------------------|----------------------------------------|------------------------|
| TC1: Allow Player to place armies without error                 | Player controls 2 territories, placement is valid                                    | void, no error call                    | :white_check_mark:     |
| TC2: Stop Player from placing army on another players territory | Player 1 controls 2 territories, Player 1 controls 1 territory, placement is invalid | IllegalArgumentError                   | :white_check_mark:     |
| TC3: top Player from placing more armies than they possess      | Player 1 controls 2 territories, placement is valid, but army size is too big        | IllegalArgumentError                   | :white_check_mark:     |
| TC4: Remaining_Armies decreases after placement                 | Player controls 2 territories, placement is valid                                    | void (remaining army assertion passes) | :white_check_mark:     |
| TC5: Territory Army increases after placement                   | Player controls 2 territories, placement is valid                                    | void (territory army assertion passes) | :white_check_mark:     |

### Method under test: calculateContinentBonus(Player player, GameState gameState)
|                                              | State of the System                                       | Expected output | Implemented?       |
|----------------------------------------------|-----------------------------------------------------------|-----------------|--------------------|
| TC1:Player_Doesn't_Control_Full_Continent    | 1 Player: controls 5/6 territories in Africa, 1 in Asia   | 0 Bonus armies  | :white_check_mark: |
| TC2:Control_Of_Africa                        | Player controls all 6 territories of Africa               | 3 bonus armies  | :white_check_mark: |
| TC3:Control_Of_Australia                     | Player controls all 4 territories of Australia            | 2 bonus armies  | :white_check_mark: |
| TC4:Control_Of_South_America                 | Player controls all 4 territories of South America        | 2 bonus armies  | :white_check_mark: |
| TC5:Control_Of_North_America                 | Player controls all 9 territories of North America        | 5 bonus armies  | :white_check_mark: |
| TC6:Control_Of_Europe                        | Player controls all 7 territories of Europe               | 5 bonus armies  | :white_check_mark: |
| TC7:Control_Of_Asia                          | Player controls all 12 territories of Asia                | 7 bonus armies  | :white_check_mark: |
| TC8:Control_Of_Africa_Australia_Double_bonus | Player controls all 10 territories of Africa & Australia  | 5 bonus armies  | :white_check_mark: |
| TC9: No bonus for majority continent control | Player 1 controls 3/4 of Australia, Player 2 controls 1/4 | 0 bonus armies  | :white_check_mark: |

### Method under test: calculateBaseReinforcements(Player player, GameState gameState)
|                                         | State of the System             | Expected output | Implemented?           |
|-----------------------------------------|---------------------------------|-----------------|------------------------|
| TC1: Minimum return for low territories | Player controls 2 territories   | 3 armies        | :white_check_mark:     |
| TC2: 9_territories_3_armies             | player controls 9 territories   | 3 armies        | :white_check_mark:     |
| TC3:Lowest_Boundary                     | player controls 1 territories   | 3 armies        | :white_check_mark:     |
| TC4: 9/3                                | player controls 9 territories   | 3 armies        | :white_check_mark:     |
| TC5: Below_Boundary 2                   | player controls 11 territories  | 3 armies        | :white_check_mark:     |
| TC6: Above_Boundary 2                   | player controls 12 territories  | 4 armies        | :white_check_mark:     |
| TC7: Below_Boundary 3                   | player controls 14 territories  | 4 armies        | :white_check_mark:     |
| TC8: Above_Boundary 3                   | player controls 15 territories  | 5 armies        | :white_check_mark:     |
| TC9: Below_Boundary 4                   | player controls 17 territories  | 5 armies        | :white_check_mark:     |
| TC10: Above_Boundary 4                  | player controls 18 territories  | 6 armies        | :white_check_mark:     |
| TC11: Below_Boundary 5                  | player controls 20 territories  | 6 armies        | :white_check_mark:     |
| TC12: Above_Boundary 5                  | player controls 21 territories  | 7 armies        | :white_check_mark:     |
| TC13: Below_Boundary 6                  | player controls 23 territories  | 7 armies        | :white_check_mark:     |
| TC14: Above_Boundary 6                  | player controls 24 territories  | 8 armies        | :white_check_mark:     |
| TC15: Below_Boundary 7                  | player controls 26 territories  | 8 armies        | :white_check_mark:     |
| TC16: Above_Boundary 7                  | player controls 27 territories  | 9 armies        | :white_check_mark:     |
| TC17: Below_Boundary 8                  | player controls 29 territories  | 9 armies        | :white_check_mark:     |
| TC18: Above_Boundary 8                  | player controls 30 territories  | 10 armies       | :white_check_mark:     |
| TC19:Below_Boundary 9                   | player controls 32 territories  | 10 armies       | :white_check_mark:     |
| TC20: Above_Boundary 9                  | player controls 33 territories  | 11 armies       | :white_check_mark:     |
| TC21: Below_Boundary 10                 | player controls 35 territories  | 11 armies       | :white_check_mark:     |
| TC22: Above_Boundary 10                 | player controls 36 territories  | 12 armies       | :white_check_mark:     |
| TC23: Below_Boundary 11                 | player controls 38 territories  | 12 armies       | :white_check_mark:     |
| TC24: Above_Boundary 11                 | player controls 39 territories  | 13 armies       | :white_check_mark:     |
| TC25: Below_Boundary 12                 | player controls 41 territories  | 13 armies       | :white_check_mark:     |
| TC26: Above_Boundary 12                 | player controls 42 territories  | 14 armies       | :white_check_mark:     |

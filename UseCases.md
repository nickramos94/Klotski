![UseCase.png](img/UseCase.png)

| |USE CASE 1 |
| - | - |
|**Name** |Main menu |
|**Actors** |User |
|**Brief Description** |The user opens the application. |
|**Preconditions:** |none |
|**Basic flow** |The home screen is displayed and the user can select a level or read some info about the game before playing it. |
|**Alternate flows** |none |
|**Postconditions** |Home screen is displayed |


| |USE CASE 1 |
| - | - |
|**Name** |Select Level |
|**Actors** |User |
|**Brief Description** |The user chooses one of the 5+1 configurations. |
|**Preconditions:** |none |
|**Basic flow** |User selects one of the 5+1 levels and clicks on “play”.  |
|**Alternate flows** |User doesn’t select a level. Level 1 is selected by default |
|**Postconditions** |The level selected is displayed. |
|**Note** |<p>The level can also be selected during a game (after use case #4) clicking the “level” button and choosing one of the 5 options.It equals to starting a new game (use case #4)|


| |USE CASE 1 |
| - | - |
|**Name** |Info |
|**Actors** |User |
|**Brief Description** |Information about the game are shown |
|**Preconditions:** |none |
|**Basic flow** |User clicks on “info” and information about the game and rules are shown |
|**Alternate flows** |none |
|**Postconditions** |none |


| |USE CASE 1 |
| - | - |
|**Name** |Play game |
|**Actors** |User |
|**Brief Description** |The user clicks on the “play” button |
|**Preconditions:** |none |
|**Basic flow** |A new game starts and the board is displayed |
|**Alternate flows** |none |
|**Postconditions** |The game board with the selcted configuration  is displayed and the user can move the pieces |


| |USE CASE 1 |
| - | - |
|**Name** |Move |
|**Actors** |User |
|**Brief Description** |The user can move the pieces according to the game rules |
|**Preconditions:** |A piece has to be selected |
|**Basic flow** |The user drags the pieces to the free places |
|**Alternate flows** |The user tries to drag the blocks outside the board, to move them diagonally or to overlapping them and the piece won’t move |
|**Postconditions** |The moves counter increments and now the user has the opportunity to undo or save |


| |USE CASE 1 |
| - | - |
|**Name** |Undo |
|**Actors** |User |
|**Brief Description** |User decides to undo a move. |
|**Preconditions:** |The moves counter can’t be 0, at least one move must have been performed |
|**Basic flow** |User clicks on “undo”  and the board returns to the previous configuration |
|**Alternate flows** |Moves counter is 0 and can’t decrement. The board configuration doesn’t change. |
|**Postconditions** |The moves counter decrements by one and the board configuration returns to the previous condition |


| |USE CASE 1 |
| - | - |
|**Name** |Settings |
|**Actors** |User |
|**Brief Description** |The user clicks on the “setting” button |
|**Preconditions:** |A game must have started, can’t do it from the main menu |
|**Basic flow** |User clicks on “settings” and 3 different options are displayed. He can save the game, load a saved one or return to the main menu |
|**Alternate flows** |User doesn’t select any of the 3 options |
|**Postconditions** |Use case #8, #9 or #10 |


| |USE CASE 1 |
| - | - |
|**Name** |Save |
|**Actors** |User |
|**Brief Description** |The current board configuration gets saved |
|**Preconditions:** |Use case #7 |
|**Basic flow** |After making some moves, the user saves the configuration |
|**Alternate flows** |The user saves before making any move. That equals saving the selected configuration |
|**Postconditions** |The configuration is saved and can be loaded (use case #9) |


| |USE CASE 1 |
| - | - |
|**Name** |Load |
|**Actors** |User |
|**Brief Description** |The saved configuration gets loaded |
|**Preconditions:** |Use case #7 and #8 |
|**Basic flow** |User saves a game, makes sono moves and then loads the saving. The saved configuration is displayed and the moves counter changes too |
|**Alternate flows** |User tries to load a configuration without saving one before. The display doesn’t change because nothing gets loaded |
|**Postconditions** |The configuration and the moves counter change |


| |USE CASE 1 |
| - | - |
|**Name** |Return to main menu |
|**Actors** |User |
|**Brief Description** |The user return to the home screen using the settings. The situation will be the same one as in the use case #1 |
|**Preconditions:** |Use case #7 |
|**Basic flow** |The main menu is displayed |
|**Alternate flows** |none |
|**Postconditions** |Use case #1 |


| |USE CASE 1 |
| - | - |
|**Name** |Reset |
|**Actors** |User |
|**Brief Description** |The user return to the home screen using the settings. The situation will be the same one as in the use case #1 |
|**Preconditions:** |Use case #7 |
|**Basic flow** |After making some moves the user decides to reset |
|**Alternate flows** |The user resets the game without making any move, the configuration doesn’t change |
|**Postconditions** |New game with the same level starts |


| |USE CASE 1 |
| - | - |
|**Name** |Next best move |
|**Actors** |Solver |
|**Brief Description** |The best possible move is performed automatically |
|**Preconditions:** |User must be connected to interntet |
|**Basic flow** |The solver performes the best move |
|**Alternate flows** |none |
|**Postconditions** |The configuration chages and moved counter increments by one |


| |USE CASE 1 |
| - | - |
|**Name** |Solve all |
|**Actors** |Solver |
|**Brief Description** |The game is solved automatically |
|**Preconditions:** |User must be connected to interntet |
|**Basic flow** |The pieces move automatically until the 2x2 square teaches the winning position |
|**Alternate flows** |Stop (use case #14) |
|**Postconditions** |Win (use case #15) |


| |USE CASE 1 |
| - | - |
|**Name** |Stop |
|**Actors** |User and Solver |
|**Brief Description** |User stops the automatic solving |
|**Preconditions:** |Use case #13 |
|**Basic flow** |After letting the solver move some pieces the user decides to stop him |
|**Alternate flows** |none |
|**Postconditions** |The solver stops solving |


| |USE CASE 1 |
| - | - |
|**Name** |Win |
|**Actors** |User and/or solver |
|**Brief Description** |User (or solver) wins the game |
|**Preconditions:** |The 2x2 square is moved to the winning position |
|**Basic flow** |User (or solver) moves the pieces until the 2x2 reaches the winning position |
|**Alternate flows** |User isn’t able to make the 2x2 square go to the winning position |
|**Postconditions** |<p>Restart (same as use case #11) or </p><p>go back to the main menu  (use case #1) </p>|

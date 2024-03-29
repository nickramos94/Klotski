[Home](../README.md)

# Sequence Diagrams
A sequence diagram is a visual representation of the interactions between objects or components in a system, showcasing the flow of messages exchanged over time to achieve a particular behavior or scenario.

- [Main Menu](#main-menu)
    - [System Sequence Diagram](#system-sequence-diagram)
    - [Internal Sequence Diagrams](#internal-sequence-diagrams)
        - [Info](#--info)
        - [Level Selection](#--level-selection)
- [Play the Game](#play-the-game)
    - [System Sequence Diagram](#system-sequence-diagram-1)
    - [Internal Sequence Diagrams](#internal-sequence-diagrams-1)
        - [Move a Piece](#--move-a-piece)
        - [Settings](#--settings)
        - [Levels](#--levels)
        - [Reset](#--reset)
        - [Auto Solve](#--auto-solve)
        - [Undo](#--undo)
        - [Next Best Move](#--next-best-move)
- [Others Internal Sequence Diagrams](#others-internal-sequence-diagrams)
    - [Check Win](#--check-win)
    - [Solve](#--solve)


## Main Menu
### System Sequence Diagram
User-System interaction when the the User launch the program.

![Models Design](../img/Sequence_Diagrams/SD-main_menu-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-main_menu-dark_theme.png#gh-dark-mode-only)

### Internal Sequence Diagrams

#### - Info
Internal behavior of the system when the user press the Info Button.

![Models Design](../img/Sequence_Diagrams/SD-info-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-info-dark_theme.png#gh-dark-mode-only)

#### - Level Selection
Internal behavior of the system when the user press the Play Button after selecting a level.

![Models Design](../img/Sequence_Diagrams/SD-level_selection-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-level_selection-dark_theme.png#gh-dark-mode-only)


## Play the Game
### System Sequence Diagram
User-System interaction when the User plays the game after selecting a level.

![Models Design](../img/Sequence_Diagrams/SD-play_game-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-play_game-dark_theme.png#gh-dark-mode-only)

### Internal Sequence Diagrams

#### - Move a Piece
Internal behaviour of the system when the user click with the mouse inside the game board.

![Models Design](../img/Sequence_Diagrams/SD-move_piece-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-move_piece-dark_theme.png#gh-dark-mode-only)

#### - Settings
Internal behaviour of the system when the user click on the settings button and choose an action among the three that are displayed: Save, Load and Main Menu.

![Models Design](../img/Sequence_Diagrams/SD-settings-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-settings-dark_theme.png#gh-dark-mode-only)

#### - Levels
Internal behaviour of the system when the user click on the Levels button and choose a level among the 6 that are displayed.

![Models Design](../img/Sequence_Diagrams/SD-levels-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-levels-dark_theme.png#gh-dark-mode-only)

#### - Reset
Internal behaviour of the system when the user click on the Reset button.

![Models Design](../img/Sequence_Diagrams/SD-reset-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-reset-dark_theme.png#gh-dark-mode-only)

#### - Auto Solve
Internal behaviour of the system when the user click on the Solve all button.

![Models Design](../img/Sequence_Diagrams/SD-solve_all-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-solve_all-dark_theme.png#gh-dark-mode-only)

#### - Undo
Internal behaviour of the system when the user click on the Undo button.

![Models Design](../img/Sequence_Diagrams/SD-undo-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-undo-dark_theme.png#gh-dark-mode-only)

#### - Next Best Move
Internal behaviour of the system when the user click on the Best Move button.

![Models Design](../img/Sequence_Diagrams/SD-next_best_move-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-next_best_move-dark_theme.png#gh-dark-mode-only)


### Others Internal Sequence Diagrams

#### - Check Win
Internal behaviour of the system when a Check Win is requested by the System.
Normally after a Move or a Best Move.

![Models Design](../img/Sequence_Diagrams/SD-check_win-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-check_win-dark_theme.png#gh-dark-mode-only)

#### - Solve
Internal behaviour of the system when Solve is requested by the System.
Normally when the Random Level is selected or when a Best Move is made.

![Models Design](../img/Sequence_Diagrams/SD-solve-light_theme.png#gh-light-mode-only)
![Models Design](../img/Sequence_Diagrams/SD-solve-dark_theme.png#gh-dark-mode-only)
# ALL IMPORTANT NOTES
## LAST DEADLINE
### Supporting Different Users by making login and signup, for BONUS.
This is done through SignIn page 
- Currently, `play` signifies `login`, and `quit` signifies `signup`. 
- current_player should be an attribute for almost every page except MainGame, LoadingScreen and SignIn.
- Create logout method(or option). There should be an option to logout on the HomePage, which redirects to the SignIn page.
#### Improvements, wrt SignIN page:
- Change background image, and image buttons(play -> login, quit -> signup)
- Change UI part of text fields
- See if prompt can be centered
#### Things to keep in mind:
- After login/signup, I have the Player object of the player(which includes all player details including username, password, levels cleared, etc). I think we have to pass this player object to the HomePage and all the other pages. Then we would be able to update the state of the player.




## Completed HomePage. Things to keep in mind:

## Completed LevelScreen.Things to keep in mind:
- This screen renders icons which when clicked sends us to that particular levels individual screen 
- It also has a back button which sends us to HomePage.

## Completed Level_1 Screen. Things to keep in mind:
- This screen renders the 1st level, currently it is just static.
- It also renders Pause button that forwards us to PausePage.

## Completed LoadingScreen. Things to keep in mind:
- This Screen renders a loading screen for 5 sec, after which it then renders the LevelScreen.

## Completed Lost_Screen. Things to keep in mind:
- This Screen is rendered when space bar us pressed in Level_1 currently, signifying that the user has lost.
- This screen also renders 2 buttons (restart button or exit button which sends the user to LevelScreen).

## Completed Victory_Screen. Things to keep in mind:
- This Screen is rendered when enter ket is pressed in Level_1 currently, signifying that the user has won the level.
- This screen also renders 3 buttons (restart button or exit button which sends the user to LevelScreen and a nextLevel button that sends the user to next level).

## Completed MainGane. Things to keep in mind:
- This page initiates the call of rendering the GUI and renders the loading page.

## Completed PausePage. Things to keep in mind:
- This page is rendered when the pause button is pressed in any level it pauses the game and offers option like resuming the game, restarting the game, exiting the game or even returning to homepage with various button on the left side.

## Utilities Folder:
- Not required in code, just contains some UI components which may be useful for creating other components

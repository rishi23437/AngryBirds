# AngryBirds
This project is an Angry Birds clone in java, which we made as a part of our Advanced Programming course. 

## Description
The game has all the basic features of any Angry Birds game. It has 3 main levels, along with an infinite level generator. The application is object-oriented, and uses Serialization to save and restore game states from a .json file. Multiple players are supported, and each player can save 1 game state of theirs. This project also contains a few JUnit tests.

## Set up and Run
### IntelliJ Idea
- To run the project using IntelliJ Idea, download the project folder and place it in the folder where you run your other IntelliJ projects(could be called IdeaProjects for some people). Then, you can run it normally on IntelliJ!
- To run the JUnit tests, after setting up the project as mentioned above, open the core/src/test/java/io/github/angry_birds directory. There are 3 JUnit tests, each of which can be run normally on IntelliJ!

## Platforms
A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).
- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle
This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.

## Bonus
### Random Level Generator
- This project includes a **random level generator**, that dynamically generates new levels each time. 
- This allows players to enjoy **infinite gameplay**. 

### User Profiles
- The game supports and saves the data of **multiple players**. 
- Players can register using their unique username and password, and are allowed to play a certain level only if they beat the previous one. 
- Additionally as mentioned above, they can save the game state of a particular level.
- Sample game states are saved in the saved_game_states.json file of the data_files directory.

## Credits
The following websites/tutorials have helped us tremendously in understanding libGDX and the project, and guided us towards making the game:
- [libGDX Documentation](https://libgdx.com/wiki/)
- [libGDX Simple Game](https://libgdx.com/wiki/start/a-simple-game)
- [Box2D Documentation](https://box2d.org/documentation/)
- [libGDX-Box2D Documentation](https://libgdx.com/wiki/extensions/physics/box2d)
- [Angry Birds Images(sprites, backgrounds)](https://angrybirds.fandom.com/wiki/Angry_Birds_(game)/Gallery/)

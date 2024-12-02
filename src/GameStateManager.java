package io.github.angry_birds;

import com.badlogic.gdx.physics.box2d.World;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GameStateManager {
    private static final String SAVE_FILE = "data_files/saved_game_states.json";

    public static void saveGameState(String username, Level level, Bird bird, World sample_world) {
        try {
            // Load existing game states
            Map<String, GameState> gameStates = loadAllGameStates();

            // DEBUGGING
            System.out.println(level.getClass().getSimpleName());
            System.out.println("Number of birds: " + level.birds.size());
            System.out.println("Number of blocks: " + level.blocks.size());
            System.out.println("Number of pigs: " + level.pigs.size());


            // Create the current game state
            GameState gameState = new GameState(
                username,
                level.getClass().getSimpleName(),
                level.getBirdStates(bird, sample_world),
                level.getBlockStates(),
                level.getPigStates()
            );

            // DEBUGGING
            System.out.println(gameState);


            // DO NOT ALLOW STORING OF MULTIPLE GAME STATES PER PLAYER!!!!!!!!!
            gameStates.put(username, gameState);
            System.out.println("Gamestate saved to map.");

            // update SAVE_FILE
            Gson gson = new Gson();
            try (FileWriter writer = new FileWriter(SAVE_FILE)) {
                gson.toJson(gameStates, writer);
            }
            System.out.println("All gamestates saved to file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, GameState> loadAllGameStates() {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, GameState>>() {}.getType();

            try (FileReader reader = new FileReader(SAVE_FILE)) {
                return gson.fromJson(reader, type);
            }
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static Level restoreGameState(MainGame game, Player player, World world) {
        try {
            Map<String, GameState> gameStates = loadAllGameStates();

            GameState gameState = gameStates.get(player.getUsername());
            if (gameState == null) {
                System.out.println("No saved game state for user: " + player.getUsername());
                return null;
            }

            // instantiating the required level class
            Level restoredLevel;
            switch (gameState.getLevelType()) {
                case "Level1":
                    restoredLevel = new Level1(game, player, new Slingshot("Level_1images/slingshot.png"), world, true);
                    break;
                case "Level2":
                    restoredLevel = new Level2(game, player, new Slingshot("Level_1images/slingshot.png"), world, true);
                    break;
                case "Level3":
                    restoredLevel = new Level3(game, player, new Slingshot("Level_1images/slingshot.png"), world, true);
                    break;
                default:
                    throw new IllegalArgumentException("Level type is invalid(not 1, 2 or 3). Level number: " + gameState.getLevelType());
            }

            restoredLevel.setWorld(world);
            restoredLevel.pigs.clear();
            restoredLevel.birds.clear();
            restoredLevel.blocks.clear();

            // Restore birds
            restoredLevel.birds = new LinkedList<>();
            for (BirdState birdState : gameState.getBirdStates()) {
                // CREATE BIRDS ALSO, ADD NOT NULL CONDITION FOR BIRDBODY
                //if(birds.getName.eq)
                restoredLevel.birds.add(birdState.toBird());
            }
            System.out.println("Birds restored.");

            // Restore blocks
            restoredLevel.blocks = new ArrayList<>();
            for (BlockState blockState : gameState.getBlockStates()) {
                if (blockState.health > 0) {
                    System.out.println("Block health > 0");
                    Block block = blockState.toBlock();
                    block.create(world, blockState.x * Level.PPM, blockState.y * Level.PPM, blockState.width, blockState.height);
                    System.out.println("BlockState Coords: " + blockState.x + " " + blockState.y);
                    if (block.getBlockBody() != null) {
                        restoredLevel.blocks.add(block);
                        // System.out.println("Block Coords: " + block.x + " " + blockState.y);
                        continue;
                    }
                    System.out.println("Block body is null");
                }
            }
            System.out.println("Final Number of blocks after restoring: " + restoredLevel.blocks.size());
            System.out.println("Blocks restored.");

            // restoring the pigs
            restoredLevel.pigs = new ArrayList<>();
            for (PigState pigState : gameState.getPigStates()) {
                if (pigState.health > 0) {
                    System.out.println("Pig health > 0");
                    Pig pig = pigState.toPig();
                    pig.create(world, (int) (pigState.x * Level.PPM), (int) (pigState.y * Level.PPM));
                    if (pig.getPigBody() != null) {
                        restoredLevel.pigs.add(pig);
                        continue;
                    }
                    System.out.println("Pig body is null");
                }
            }
            System.out.println("Final Number of pigs after restoring: " + restoredLevel.pigs.size());
            System.out.println("Pigs restored.");

            return restoredLevel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}


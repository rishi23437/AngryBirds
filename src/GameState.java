package io.github.angry_birds;

import java.util.List;

public class GameState {
    private String username;
    private String levelType; // e.g., "Level1", "Level2"
    private List<BirdState> birdStates;
    private List<BlockState> blockStates;
    private List<PigState> pigStates;

    // Constructor
    public GameState(String username, String levelType, List<BirdState> birdStates, List<BlockState> blockStates, List<PigState> pigStates) {
        this.username = username;
        this.levelType = levelType;
        this.birdStates = birdStates;
        this.blockStates = blockStates;
        this.pigStates = pigStates;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getLevelType() {
        return levelType;
    }

    public List<BirdState> getBirdStates() {
        return birdStates;
    }

    public List<BlockState> getBlockStates() {
        return blockStates;
    }

    public List<PigState> getPigStates() {
        return pigStates;
    }
}

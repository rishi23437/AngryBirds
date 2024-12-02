package io.github.angry_birds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class Level implements Screen {
    Slingshot slingshot;
    Queue<Bird> birds;

    public ArrayList<Block> blocks;
    public ArrayList<Pig> pigs;

    Texture save_button_image;
    ImageButton save_button;

    World world;
    boolean restored = false;

    public static final float PPM = 32.0f;

    public List<BirdState> getBirdStates(Bird curr_bird, World sample_world) {
        List<BirdState> birdStates = new ArrayList<>();

        birdStates.add(new BirdState(curr_bird.getClass().getSimpleName(), curr_bird.getBirdBody().getPosition().x,
            curr_bird.getBirdBody().getPosition().y, curr_bird.getBird_Health()));

        for (Bird bird : birds) {
            System.out.println("Queeeeeeeeeeeeeee");
            System.out.println("number of birds: " + birds.size());
            System.out.println(bird.getClass().getSimpleName());

            bird.create(sample_world);

            birdStates.add(new BirdState(bird.getClass().getSimpleName(), bird.getBirdBody().getPosition().x,
                bird.getBirdBody().getPosition().y, bird.getBird_Health()));
            System.out.println("Bird State added successfully!");
            System.out.println(birdStates.size());
        }
        System.out.println("Final size of birdStates: " + birdStates.size());
        return birdStates;
    }

    public List<BlockState> getBlockStates() {
        List<BlockState> blockStates = new ArrayList<>();
        for (Block block : blocks) {
            if (block.getBlockBody() != null) {
                blockStates.add(new BlockState(block.getBlockBody().getPosition().x, block.getBlockBody().getPosition().y,
                    block.getwidth(), block.getheight(), block.getBlockHealth(), block.getBlock_image_path()));
            }
        }
        return blockStates;
    }

    public List<PigState> getPigStates() {
        List<PigState> pigStates = new ArrayList<>();
        for (Pig pig : pigs) {
            if (pig.getPigBody() != null) {
                pigStates.add(new PigState(pig.getPigBody().getPosition().x, pig.getPigBody().getPosition().y,
                    pig.getPigHealth(), pig.getPig_image_path()));
            }
        }
        return pigStates;
    }

    public void setWorld(World w) {
        this.world = w;
    }

    abstract void resumeGame();
}

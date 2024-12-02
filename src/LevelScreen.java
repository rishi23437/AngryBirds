package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelScreen implements Screen{
    private final MainGame game;

    private SpriteBatch batch;                                  // drawing
    private Stage stage;                                        // managing buttons
    private FitViewport viewport;                               // viewable window size

    private Texture background;
    private Texture map_text;

    private Texture back_image;
    private ImageButton back_button;

    private Texture level1_image;
    private Texture level2_image;
    private Texture level3_image;
    private Texture infinite_level_image;
    private Texture lock_image;
    private ImageButton level1_button;
    private ImageButton level2_button;
    private ImageButton level3_button;
    private ImageButton infinite_level_button;

    private Texture restore_button_image;
    private ImageButton restore_button;

    private Player current_player;

    private World world;
    public static Box2DDebugRenderer debugRenderer;

    private Slingshot slingshot;

    public LevelScreen(MainGame game, Player player) {
        this.game = game;
        this.current_player = player;
        this.slingshot = new Slingshot("Level_1images/slingshot.png");
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        /*
        World() takes 2 params:
        1. A vector for Gravity. It has x and y coords(therefore vector2). Here, y = -10 because gravity is
        10m/s^2 in negative y-direction. x = 0.
        2. doSleep(boolean): if true, bodies are allowed to sleep, computations do not occur every second
        */
        world = new World(new Vector2(0, -10), true);
        world.setVelocityThreshold(0.1f);
//        world.setContactListener(new CollisionHandler());

        debugRenderer = new Box2DDebugRenderer();

        background = new Texture("level_screen.png");
        map_text = new Texture("map_brown.png");
        lock_image = new Texture("level_lock.png");

        restore_button_image = new Texture("load_game_databutton.png");                 // CHANGE LATER
        restore_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(restore_button_image)));
        restore_button.setPosition(605, 345);
        restore_button.setSize(30, 30);
        restore_button.addListener(new ClickListener());
        stage.addActor(restore_button);

        back_image = new Texture("back_button.png");
        back_button = new ImageButton(new TextureRegionDrawable(back_image));
        back_button.setPosition(2, 356);
        back_button.setSize(35, 23);
        back_button.addListener(new ClickListener());
        stage.addActor(back_button);

        level1_image = new Texture("level_1.png");
        level1_button = new ImageButton(new TextureRegionDrawable(level1_image));
        level1_button.setPosition(130, 170);
        level1_button.setSize(60, 60);
        level1_button.addListener(new ClickListener());
        stage.addActor(level1_button);

        if (current_player.getLevels_cleared() >= 1) { // Ensure `level1_button` is initialized only if `a == 0`
            level2_image = new Texture("level_2.png");
            level2_button = new ImageButton(new TextureRegionDrawable(level2_image));
            level2_button.setPosition(290, 170);
            level2_button.setSize(60, 60);
            level2_button.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Logic can also be added here if needed
                    super.clicked(event, x, y);
                }
            });
            stage.addActor(level2_button);
        }

        if (current_player.getLevels_cleared() >= 2) {
            level3_image = new Texture("level_3.png");
            level3_button = new ImageButton(new TextureRegionDrawable(level3_image));
            level3_button.setPosition(450, 170);
            level3_button.setSize(60, 60);
            level3_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Logic can also be added here if needed
                    super.clicked(event, x, y);
                }
            });
            stage.addActor(level3_button);
        }

        if (current_player.getLevels_cleared() >= 3) {
            infinite_level_image = new Texture("infinite_level.png");
            infinite_level_button = new ImageButton(new TextureRegionDrawable(infinite_level_image));
            infinite_level_button.setPosition(290, 50);
            infinite_level_button.setSize(60, 60);
            infinite_level_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Logic can also be added here if needed
                    super.clicked(event, x, y);
                }
            });
            stage.addActor(infinite_level_button);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        float world_width  = viewport.getWorldWidth();
        float world_height = viewport.getWorldHeight();

        batch.begin();
        batch.draw(background, 0, 0, world_width, world_height);
        batch.draw(map_text, 280, 300, 80, 40);
//        for(int i =current_player.getLevels_cleared(); i<3; ++i){
//            if(current_player.getLevels_cleared() >= 1) {
//                batch.draw(lock_image, (130 + (i*160)), 170, 60, 60);
//            }
//        }
        if((current_player.getLevels_cleared() <1 )){
            batch.draw(lock_image, 290, 170, 60, 60);
        }
        if(current_player.getLevels_cleared()<2){
            batch.draw(lock_image, 450, 170, 60, 60);
        }
        if(current_player.getLevels_cleared()<3){
            batch.draw(lock_image, 290, 50, 60, 60);
        }
        batch.end();

        stage.act(delta);
        stage.draw();                         // drawing all buttons added to stage object
//
//        for(int i=0; i<=current_player.getLevels_cleared(); ++i){
//
//        }
        // Dealing with button presses
        // Back button is pressed

        if (back_button.isPressed()) {
            // switching back to the previous screen(HomePage)
            game.setScreen((Screen) new HomePage(game, current_player));
        }

        if (level1_button.isPressed()) {
            // switching to Level 1
            game.setScreen((Screen) new Level1(game, current_player, slingshot));
        }

        if (current_player.getLevels_cleared() >= 1 && back_button != null && level2_button.isPressed()) {
            // switching to Level 2
            game.setScreen((Screen) new Level2(game, current_player, slingshot));
        }

        if (current_player.getLevels_cleared() >= 2 && back_button != null && level3_button.isPressed()) {
            // switching to Level 3
            game.setScreen((Screen) new Level3(game, current_player, slingshot));
        }

        if (current_player.getLevels_cleared() >= 3 && back_button != null && infinite_level_button.isPressed()) {
            //switching to infinite level
            game.setScreen((Screen) new RandomLevel(game, current_player, slingshot));
        }

        if (restore_button.isPressed()) {
            Level restored_level = GameStateManager.restoreGameState(game, current_player, world);
            if (restored_level == null) {
                System.out.println("No saved game instance for the current player exists.");
            }
            else {
                game.setScreen(restored_level);
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
    }

}

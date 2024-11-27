package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.LinkedList;

public class RandomLevel extends Level implements Screen {
    private SpriteBatch batch;

    private Texture backgroundTexture;
    private Texture redbirdTexture;
    private Texture kingpigTexture;
    private Texture woodblockTexture;

    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private Texture pausebuttonTexture;
    private ImageButton pausebutton;

    private final MainGame game;
    private Player current_player;

    private World world;
//    public static Box2DDebugRenderer debugRenderer;

    // FOR TESTING, CREATING A BIRD OBJECT
    private Bird bird;
    private boolean paused = false;

    private boolean dragging = false;         // Whether the bird is being dragged
    private Vector2 dragStartPosition;        // Start position of the drag
    private Vector2 currentMousePosition;     // Current position of the mouse during dragging
    public ArrayList<Block> blocks;
    Block temp_block;

    public RandomLevel(MainGame game, Player player, Slingshot slingshot) {
        this.game = game;                           // Store the reference to MainGame
        this.current_player = player;
        this.slingshot = slingshot;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("Level_1images/Background1.png");

        world = new World(new Vector2(0, -10), true);

        camera = new OrthographicCamera();
        viewport = new FitViewport(640, 380, camera);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        birds = new LinkedList<>();
        this.setBirds();
        slingshot.setBird_list(birds);
        bird = slingshot.getCurrent_bird();
        create_ground(world, camera);
        bird.create(world);


        pausebuttonTexture = new Texture("Level_1images/pausebutton.png");
        pausebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pausebuttonTexture)));
        pausebutton.setPosition(600, 340);
        pausebutton.setSize(30, 30);

        stage.addActor(pausebutton);
        pausebutton.addListener(new ClickListener());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        if (!paused) {
            world.step(1 / 60f, 6, 2);
        }

        batch.begin();
        float world_width = viewport.getWorldWidth();
        float world_height = viewport.getWorldHeight();
        batch.draw(backgroundTexture, 0, 0, world_width, world_height);

        // Define the slingshot position and size
        float slingshotX = Slingshot.anchor_pointX;
        float slingshotY = Slingshot.anchor_pointY;
        float slingshotHeight = Slingshot.height;
        float slingshotWidth = slingshot.getSlingshot_texture().getWidth() * (slingshotHeight / slingshot.getSlingshot_texture().getHeight()); // Scale width based on height

        // Draw the slingshot at (125, 55) with a height of 75 and scaled width
        batch.draw(slingshot.getSlingshot_texture(), slingshotX, slingshotY, slingshotWidth, slingshotHeight);

        Vector2 bird_position = bird.getBirdBody().getPosition();
        batch.draw(bird.getBirdImage(),
            (bird_position.x*PPM) - bird.get_radius()*PPM,
            (bird_position.y*PPM) - bird.get_radius()*PPM,
            2*bird.get_radius()*PPM, 2*bird.get_radius()*PPM);

        batch.end();

        LevelScreen.debugRenderer.render(world, camera.combined);

//        float delta;
        stage.act(delta);                                               // for buttons
        stage.draw();

        if (pausebutton.isPressed()) {
//            paused = true; // Pause the game
            game.setScreen(new PausePage(game, current_player, this, slingshot));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Set the screen to VictoryScreen, increment levels_cleared using victory()
            current_player.victory();
            game.setScreen(new VictoryScreen(game, current_player, slingshot));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // set the screen to LostScreen
            game.setScreen(new LostScreen(game, current_player, slingshot));
        }
    }

    public void setBirds() {
        // CHANGE THE TYPE OF BIRDS LATER
        this.birds.add(new RedBird());
    }

    private void handleInput() {
        try {
            Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (!dragging) {
                    Vector2 birdPosition = bird.getBirdBody().getPosition().scl(Level.PPM);
                    float birdRadius = bird.get_radius() * Level.PPM;
                    if (mousePosition.dst(birdPosition) <= birdRadius) {
                        dragging = true;
                        dragStartPosition = mousePosition;
                    }
                } else {
                    currentMousePosition = mousePosition;
                }
            } else if (dragging) {
                // Release the bird
                dragging = false;

                //Calculate release force
                Vector2 releaseForce;
                if(currentMousePosition.dst(dragStartPosition) > 96f) {
                    releaseForce = (dragStartPosition.sub(currentMousePosition)).nor().scl(8f);
                }
                else{
                    releaseForce = dragStartPosition.sub(currentMousePosition).scl(0.1f);
                }
                //System.out.println(dragStartPosition.sub(currentMousePosition)+"  "+(currentMousePosition.dst(dragStartPosition));
//                Vector2 releaseForce = dragStartPosition.sub(currentMousePosition).scl(0.1f); // Adjust force scaling
                bird.getBirdBody().applyLinearImpulse(releaseForce, bird.getBirdBody().getWorldCenter(), true);
            }
        } catch (Exception e) {
            Gdx.app.log("Input Error", "Error during input handling: " + e.getMessage());
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
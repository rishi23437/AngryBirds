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

import java.util.LinkedList;

public class Level1 extends Level implements Screen {
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

    // FOR TESTING, CREATING A BIRD OBJECT
    private Bird bird;


    public Level1(MainGame game, Player player, World world, Slingshot slingshot) {
        this.game = game;                           // Store the reference to MainGame
        this.current_player = player;
        this.world = world;
        this.slingshot = slingshot;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("Level_1images/Background1.png");
        redbirdTexture = new Texture("Level_1images/redBird.png");
        woodblockTexture = new Texture("Level_1images/woodblock.png");
        kingpigTexture = new Texture("Level_1images/kingpig.png");

        camera = new OrthographicCamera();
        viewport = new FitViewport(640, 380, camera);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);



        // TESTING
        birds = new LinkedList<Bird>();
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

        world.step(1/60f, 6, 2);

        // Check for input and print cursor location
//        if (Gdx.input.isTouched()) {
//            // Get screen coordinates (in pixels)
//            float screenX = Gdx.input.getX();
//            float screenY = Gdx.input.getY();
//
//            // Convert screen coordinates to world coordinates
//            Vector2 worldCoordinates = new Vector2(screenX, screenY);
//            viewport.unproject(worldCoordinates);
//
//            // Print world coordinates to the console
//            System.out.println("Cursor Location - X: " + worldCoordinates.x + ", Y: " + worldCoordinates.y);
//        }

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

        batch.draw(redbirdTexture, 100, 55, 25, 25);
        batch.draw(redbirdTexture, 70, 55, 25, 25);

        batch.draw(woodblockTexture, 500, 55, 35, 35);
        batch.draw(woodblockTexture, 535, 55, 35, 35);
        batch.draw(woodblockTexture, 570, 55, 35, 35);
        batch.draw(woodblockTexture, 500, 90, 35, 35);
        batch.draw(woodblockTexture, 570, 90, 35, 35);

        batch.draw(kingpigTexture, 502, 125, 30, 30);
        batch.draw(kingpigTexture, 572, 125, 30, 30);

        // DRAWING THE BIRD
        Vector2 bird_position = bird.getBirdBody().getPosition();
        batch.draw(bird.getBirdImage(),
            (bird_position.x*PPM) - bird.get_radius()*PPM, (bird_position.y*PPM) - bird.get_radius()*PPM,
            2*bird.get_radius()*PPM, 2*bird.get_radius()*PPM);

        batch.end();

        LevelScreen.debugRenderer.render(world, camera.combined);

        stage.act(delta);                                               // for buttons
        stage.draw();


        if (pausebutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new PausePage(game, current_player, this, world, slingshot));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Set the screen to VictoryScreen, increment levels_cleared using victory()
            current_player.victory();
            game.setScreen(new VictoryScreen(game, current_player, world, slingshot));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // set the screen to LostScreen
            game.setScreen(new LostScreen(game, current_player, world, slingshot));
        }
    }

    public void setBirds() {
        // CHANGE THE TYPE OF BIRDS LATER
        this.birds.add(new RedBird());
        this.birds.add(new RedBird());
        this.birds.add(new RedBird());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        redbirdTexture.dispose();
        kingpigTexture.dispose();
        woodblockTexture.dispose();
        pausebuttonTexture.dispose();
        stage.dispose();
        if (bird.getCircle() != null) {
            bird.getCircle().dispose();
        }
    }
}

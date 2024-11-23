package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level1 implements Screen {
    private SpriteBatch batch;

    private Texture backgroundTexture;
    private Texture slingshotTexture;
    private Texture redbirdTexture;
    private Texture kingpigTexture;
    private Texture woodblockTexture;

    private Stage stage;
    private Texture pausebuttonTexture;
    private ImageButton pausebutton;

    private Viewport viewport;
    private final MainGame game;

    private Player current_player;

    public Level1(MainGame game, Player player) {
        this.game = game;                           // Store the reference to MainGame
        this.current_player = player;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("Level_1images/Background1.png");
        slingshotTexture = new Texture("Level_1images/slingshot.png");
        redbirdTexture = new Texture("Level_1images/redBird.png");
        woodblockTexture = new Texture("Level_1images/woodblock.png");
        kingpigTexture = new Texture("Level_1images/kingpig.png");
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

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
        float slingshotX = 125;
        float slingshotY = 55;
        float slingshotHeight = 75;
        float slingshotWidth = slingshotTexture.getWidth() * (slingshotHeight / slingshotTexture.getHeight()); // Scale width based on height

        // Draw the slingshot at (125, 55) with a height of 75 and scaled width
        batch.draw(slingshotTexture, slingshotX, slingshotY, slingshotWidth, slingshotHeight);

        batch.draw(redbirdTexture, 100, 55, 25, 25);
        batch.draw(redbirdTexture, 70, 55, 25, 25);

        batch.draw(woodblockTexture, 500, 55, 35, 35);
        batch.draw(woodblockTexture, 535, 55, 35, 35);
        batch.draw(woodblockTexture, 570, 55, 35, 35);
        batch.draw(woodblockTexture, 500, 90, 35, 35);
        batch.draw(woodblockTexture, 570, 90, 35, 35);

        batch.draw(kingpigTexture, 502, 125, 30, 30);
        batch.draw(kingpigTexture, 572, 125, 30, 30);
        batch.end();

        stage.act(delta);                                               // for buttons
        stage.draw();

        if (pausebutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new PausePage(game, current_player));
        }

        /*
        For STATIC GUI:
        1. ENTER: WIN
        2. SPACE: LOSS
        */
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Set the screen to VictoryScreen
            game.setScreen(new VictoryScreen(game, current_player));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // Set the screen to VictoryScreen
            game.setScreen(new LostScreen(game, current_player));
        }
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
        slingshotTexture.dispose();
        redbirdTexture.dispose();
        kingpigTexture.dispose();
        woodblockTexture.dispose();
        pausebuttonTexture.dispose();
        stage.dispose();
    }
}

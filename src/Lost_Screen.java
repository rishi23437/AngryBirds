package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Lost_Screen implements Screen {
    private final MainGame game;
    private SpriteBatch batch;
    private Viewport viewport;

    private Texture backgroundTexture;
    private Texture loserbannerTexture;
    private Texture memepigTexture;

    private Stage stage;

    private Texture restartbuttonTexture;
    private ImageButton restartbutton;
    private Texture exitbuttonTexture;
    private ImageButton exitbutton;

    public Lost_Screen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("LevelOver.jpg");
        loserbannerTexture = new Texture("levelfailed.png");
        memepigTexture = new Texture("memepig.png");
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        restartbuttonTexture = new Texture("PausePageimages/restartbutton.png");
        restartbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(restartbuttonTexture)));
        restartbutton.setPosition(275, 60);
        restartbutton.setSize(45, 45);

        exitbuttonTexture = new Texture("PausePageimages/exitbutton.png");
        exitbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitbuttonTexture)));
        exitbutton.setPosition(325, 60);
        exitbutton.setSize(45, 45);

        stage.addActor(restartbutton);
        stage.addActor(exitbutton);
        restartbutton.addListener(new ClickListener());
        exitbutton.addListener(new ClickListener());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

//        Check for input and print cursor location
        if (Gdx.input.isTouched()) {
            // Get screen coordinates (in pixels)
            float screenX = Gdx.input.getX();
            float screenY = Gdx.input.getY();

            // Convert screen coordinates to world coordinates
            Vector2 worldCoordinates = new Vector2(screenX, screenY);
            viewport.unproject(worldCoordinates);

            // Print world coordinates to the console
            System.out.println("Cursor Location - X: " + worldCoordinates.x + ", Y: " + worldCoordinates.y);
        }

        batch.begin();
        float world_width = viewport.getWorldWidth();
        float world_height = viewport.getWorldHeight();
        batch.draw(backgroundTexture, 0, 0, world_width, world_height);
        batch.draw(loserbannerTexture, 230, 330, 180, 45);
        batch.draw(memepigTexture, 235, 165, 150,150);
        batch.end();

        stage.act(delta);                                               // for buttons
        stage.draw();

        if (exitbutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new LevelScreen(game));
        }

        if (restartbutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new Level_1(game));
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
        batch.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        loserbannerTexture.dispose();
        memepigTexture.dispose();
        restartbuttonTexture.dispose();
        exitbuttonTexture.dispose();
    }
}

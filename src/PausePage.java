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

public class PausePage implements Screen {
    private final MainGame game;
    private SpriteBatch batch;
    private Viewport viewport;

    private Texture backgroundTexture;

    private Stage stage;
    private Texture resumebuttonTexture;
    private ImageButton resumebutton;
    private Texture restartbuttonTexture;
    private ImageButton restartbutton;
    private Texture exitbuttonTexture;
    private ImageButton exitbutton;
    private Texture returntohomebuttonTexture;
    private ImageButton returntohomebutton;


    public PausePage(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("PausePageimages/birds_pause.jpg");
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        resumebuttonTexture = new Texture("PausePageimages/resumebutton.png");
        resumebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(resumebuttonTexture)));
        resumebutton.setPosition(10, 340);
        resumebutton.setSize(30, 30);

        restartbuttonTexture = new Texture("PausePageimages/restartbutton.png");
        restartbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(restartbuttonTexture)));
        restartbutton.setPosition(10, 300);
        restartbutton.setSize(30, 30);

        exitbuttonTexture = new Texture("PausePageimages/exitbutton.png");
        exitbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitbuttonTexture)));
        exitbutton.setPosition(10, 260);
        exitbutton.setSize(30, 30);

        returntohomebuttonTexture = new Texture("PausePageimages/returntohomebutton.png");
        returntohomebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(returntohomebuttonTexture)));
        returntohomebutton.setPosition(10, 220);
        returntohomebutton.setSize(30, 30);

        stage.addActor(resumebutton);
        stage.addActor(restartbutton);
        stage.addActor(exitbutton);
        stage.addActor(returntohomebutton);
        resumebutton.addListener(new ClickListener());
        restartbutton.addListener(new ClickListener());
        exitbutton.addListener(new ClickListener());
        returntohomebutton.addListener(new ClickListener());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        //Check for input and print cursor location
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
        batch.end();

        stage.act(delta);                                               // for buttons
        stage.draw();

        if (resumebutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new Level_1(game));
        }

        if (restartbutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new Level_1(game));
        }

        if (exitbutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new LevelPage(game));
        }

        if (returntohomebutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new HomePage(game));
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
        backgroundTexture.dispose();
        resumebuttonTexture.dispose();
        restartbuttonTexture.dispose();
        exitbuttonTexture.dispose();
        returntohomebuttonTexture.dispose();
        stage.dispose();
    }
}

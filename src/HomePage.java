package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HomePage implements Screen {
    private final MainGame game;
    private SpriteBatch batch;                  // draws textures/sprites at specific places
    private FitViewport viewport;               // size of window
    private Stage stage;                        // to manage buttons and input

    private Texture background;                // texture: keeps images in video ram

    private Texture play_image;
    private ImageButton play_button;

    private Texture quit_image;
    private ImageButton quit_button;

    public HomePage(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {                      // show is create() for screens(analogy)
        // show just loads into memory, render displays it
        batch = new SpriteBatch();
        background = new Texture("home_page.png");
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Play button
        play_image = new Texture("play_button.png");
        TextureRegionDrawable region1 = new TextureRegionDrawable(new TextureRegion(play_image));
        play_button = new ImageButton(region1);
        play_button.setPosition(290, 265);
        play_button.setSize(60, 40);

        // Quit button
        quit_image = new Texture("libgdx.png");        // CHANGE TO QUIT_BUTTON.PNG LATER
        TextureRegionDrawable region2 = new TextureRegionDrawable(new TextureRegion(quit_image));
        quit_button = new ImageButton(region2);
        quit_button.setPosition(290, 235);
        quit_button.setSize(60, 40);

        stage.addActor(play_button);
        stage.addActor(quit_button);
        play_button.addListener(new ClickListener());                           // initialize listener: deals with button presses
        quit_button.addListener(new ClickListener());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);        // clear the screen

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        float world_width = viewport.getWorldWidth();
        float world_height = viewport.getWorldHeight();

        batch.begin();
        batch.draw(background, 0, 0, world_width, world_height);
        // batch.draw(play_image, 290, 265, 60, 40);         // these should be the COORDS OF CENTER OF BUTTON
        batch.end();

        stage.act(delta);                                               // for buttons
        stage.draw();

        if (play_button.isPressed()) {
            // Switch to LevelPage class
            game.setScreen((Screen) new LevelPage(game));
        }
        if (quit_button.isPressed()) {
            // Quit Application
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

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

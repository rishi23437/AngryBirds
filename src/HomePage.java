package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Texture header;
    private Texture red_bird_image;

    private Texture play_image;
    private ImageButton play_button;

    private Texture quit_image;
    private ImageButton quit_button;

    private Player current_player;


    public HomePage(MainGame game, Player player) {
        this.game = game;
        current_player = player;
    }

    @Override
    public void show() {                      // show is create() for screens(analogy)
        // show just loads into memory, render displays it
        batch = new SpriteBatch();
        background = new Texture("home_page.png");
        header = new Texture("angry_birds_text.png");
        red_bird_image = new Texture("red_bird.png");
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Play button
        play_image = new Texture("play_button.png");
        play_button = new ImageButton(new TextureRegionDrawable(play_image));
        play_button.setPosition(270, 215);                           // around 320, 245
        play_button.setSize(100, 60);

        // Quit button
        quit_image = new Texture("quit_button.png");        // CHANGE TO QUIT_BUTTON.PNG LATER
        quit_button = new ImageButton(new TextureRegionDrawable(quit_image));
        quit_button.setPosition(270, 165);                              // around y: 190(center)
        quit_button.setSize(100, 60);

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
        batch.draw(header, 220, 300, 200, 60);                   // around 320, 330
        batch.draw(red_bird_image, 230, 28, 160, 100);
        batch.end();

        stage.act(delta);                                               // for buttons
        stage.draw();

        // play button function
        if (play_button.isPressed()) {
            // Switch to LevelScreen class
            game.setScreen((Screen) new LevelScreen(game, current_player));
        }
        // quit button function
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

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
    private ImageButton level1_button;
    private ImageButton level2_button;
    private ImageButton level3_button;

    private Player current_player;

    public LevelScreen(MainGame game, Player player) {
        this.game = game;
        this.current_player = player;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        background = new Texture("level_screen.png");
        map_text = new Texture("map_brown.png");

        // Back button
        back_image = new Texture("back_button.png");
        back_button = new ImageButton(new TextureRegionDrawable(back_image));
        back_button.setPosition(2, 356);
        back_button.setSize(35, 23);

        // Levels
        level1_image = new Texture("level_1.png");
        level2_image = new Texture("level_2.png");
        level3_image = new Texture("level_3.png");

        level1_button = new ImageButton(new TextureRegionDrawable(level1_image));
        level2_button = new ImageButton(new TextureRegionDrawable(level2_image));
        level3_button = new ImageButton(new TextureRegionDrawable(level3_image));

        level1_button.setPosition(130, 170);
        level2_button.setPosition(290, 170);
        level3_button.setPosition(450, 170);

        level1_button.setSize(60, 60);
        level2_button.setSize(60, 60);
        level3_button.setSize(60, 60);

        stage.addActor(back_button);
        stage.addActor(level1_button);
        stage.addActor(level2_button);
        stage.addActor(level3_button);

        back_button.addListener(new ClickListener());
        level1_button.addListener(new ClickListener());
        level2_button.addListener(new ClickListener());
        level3_button.addListener(new ClickListener());
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
        batch.draw(map_text, 280, 300, 80, 40);                    // around 320, 320
        batch.end();

        stage.act(delta);
        stage.draw();                         // drawing all buttons added to stage object

        // Dealing with button presses
        // Back button is pressed
        if (back_button.isPressed()) {
            // switching back to the previous screen(HomePage)
            game.setScreen((Screen) new HomePage(game, current_player));
        }
        // Level 1 is pressed
        else if (level1_button.isPressed()) {
            // switching to Level 1
            game.setScreen((Screen) new Level_1(game, current_player));
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

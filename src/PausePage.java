package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
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
//    private Texture resumebuttonTexture;
//    private ImageButton resumebutton;
    private Texture restartbuttonTexture;
    private ImageButton restartbutton;
    private Texture exitbuttonTexture;
    private ImageButton exitbutton;
    private Texture returntohomebuttonTexture;
    private ImageButton returntohomebutton;

    private Player current_player;

    private Level existing_level;
    private Slingshot slingshot;
    private World world;


    public PausePage(MainGame game, Player player, Level level, Slingshot slingshot) {
        this.game = game;
        this.current_player = player;
        this.existing_level = level;
//        this.world = world;
        this.slingshot = slingshot;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("PausePageimages/birds_pause.jpg");
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

//        resumebuttonTexture = new Texture("PausePageimages/resumebutton.png");
//        resumebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(resumebuttonTexture)));
//        resumebutton.setPosition(10, 330);
//        resumebutton.setSize(40, 40);

        restartbuttonTexture = new Texture("PausePageimages/restartbutton.png");
        restartbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(restartbuttonTexture)));
        restartbutton.setPosition(10, 285);
        restartbutton.setSize(40, 40);

        exitbuttonTexture = new Texture("PausePageimages/exitbutton.png");
        exitbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitbuttonTexture)));
        exitbutton.setPosition(10, 240);
        exitbutton.setSize(40, 40);

        returntohomebuttonTexture = new Texture("PausePageimages/returntohomebutton.png");
        returntohomebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(returntohomebuttonTexture)));
        returntohomebutton.setPosition(10, 195);
        returntohomebutton.setSize(40, 40);

//        stage.addActor(resumebutton);
        stage.addActor(restartbutton);
        stage.addActor(exitbutton);
        stage.addActor(returntohomebutton);
//        resumebutton.addListener(new ClickListener());
        restartbutton.addListener(new ClickListener());
        exitbutton.addListener(new ClickListener());
        returntohomebutton.addListener(new ClickListener());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        float world_width = viewport.getWorldWidth();
        float world_height = viewport.getWorldHeight();
        batch.draw(backgroundTexture, 0, 0, world_width, world_height);
        batch.end();

        stage.act(delta);                                               // for buttons
        stage.draw();

//        if (resumebutton.isPressed()) {
//            // switch to the level which called PausePage, using existing_level
//            // the screen should automatically switch to the specific level
//            existing_level.resumeGame();
//            game.setScreen(existing_level);
//        }
//        if (resumebutton.isPressed()) {
//            if (existing_level instanceof Level1) {
//                ((Level1) existing_level).setPaused(false); // Unpause the level
//            }
//            game.setScreen(existing_level);
//        }


        if (restartbutton.isPressed()) {
            // switch to the level which called PausePage, by creating a new instance of it
            // RESET SLINGSHOT BIRD LIST
            if (existing_level instanceof Level1) {
                existing_level.dispose();
                game.setScreen((Screen) new Level1(game, current_player, slingshot));
            }
            else if(existing_level instanceof Level2) {
                existing_level.dispose();
                game.setScreen((Screen) new Level2(game, current_player, slingshot));
            }
            else if(existing_level instanceof Level3) {
                existing_level.dispose();
                game.setScreen((Screen) new Level3(game, current_player, slingshot));
            }
            else if(existing_level instanceof RandomLevel) {
                existing_level.dispose();
                game.setScreen((Screen) new RandomLevel(game, current_player, slingshot));
            }
        }

        if (exitbutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new LevelScreen(game, current_player));
        }

        if (returntohomebutton.isPressed()) {
            // Switch to PauseButton class
            game.setScreen(new HomePage(game, current_player));
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
//        resumebuttonTexture.dispose();
        restartbuttonTexture.dispose();
        exitbuttonTexture.dispose();
        returntohomebuttonTexture.dispose();
        stage.dispose();
    }
}

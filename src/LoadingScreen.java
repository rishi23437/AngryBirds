package io.github.angry_birds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadingScreen implements Screen {
    private final MainGame game;

    private SpriteBatch batch;
    private Texture background;
    private FitViewport viewport;

    private float time_elapsed = 0.0f;

    public LoadingScreen(MainGame game) {
        this.game = game;
    }

    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(640, 380);

        background = new Texture("loading_screen.png");
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        float world_width = viewport.getWorldWidth();
        float world_height = viewport.getWorldHeight();
        batch.draw(background, 0, 0, world_width, world_height);
        batch.end();

        time_elapsed += delta;

        if (time_elapsed >= 3) {
            // Switching to HomePage after 3 seconds
            // NOTE: later switch to LOGIN/SIGNUP, then go to home page.
            game.setScreen((Screen) new HomePage(game));
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    public void pause() {}
    public void resume() {}
    public void hide() {}
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}

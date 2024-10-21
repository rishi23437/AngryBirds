package io.github.angry_birds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

// use f suffix for float numbers(eg: 22.5f)
public class MainGame extends Game {
    // sound: audio clip less than 10 secs, otherwise: music
    // create(): load/initialize assets

    public void create() {
        setScreen((Screen) new HomePage(this)); // setting initial screen to home page rn, later switch to LOGIN/SIGNUP
    }
    public void render() {
        super.render();
    }

    public void dispose() {}
}

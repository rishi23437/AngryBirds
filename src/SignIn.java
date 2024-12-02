package io.github.angry_birds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SignIn implements Screen {
    private final MainGame game;
    private SpriteBatch batch;
    private FitViewport viewport;

    private Texture background;

    private Stage stage;
    private Skin skin;

    private Texture login_image;
    private Texture signUp_image;
    private ImageButton login_button;
    private ImageButton signUp_button;

    private TextField username_field;
    private TextField password_field;

    private Label prompt;

    // Determining login or sign up using choice
    // 0: sign up, 1: login
    private int choice = -1;
    private boolean player_load_flag = true;

    public SignIn(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(640, 380);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        background = new Texture("signin_page.jpg");     // CHANGE TO REAL PIC LATER

        // Login button
        login_image = new Texture("login_button.png");                       // CHANGE LATER
        login_button = new ImageButton(new TextureRegionDrawable(login_image));
        login_button.setPosition(270, 215);
        login_button.setSize(100, 60);

        // Sign Up button
        signUp_image = new Texture("signup_button.png");                          // CHANGE LATER
        signUp_button = new ImageButton(new TextureRegionDrawable(signUp_image));
        signUp_button.setPosition(270, 160);
        signUp_button.setSize(100, 60);

        // Login text field
        username_field = new TextField("", skin);
        username_field.setSize(100, 60);
        username_field.setPosition(270, 215);
        username_field.setVisible(false);                    // Initially hidden
        username_field.setText("Username");

        // Sign Up text field
        password_field = new TextField("", skin);
        password_field.setSize(100, 60);
        password_field.setPosition(270, 160);
        password_field.setVisible(false);
        password_field.setText("Password");

        // Prompt label
        prompt = new Label("Enter your username and password", skin);
        prompt.setPosition(100, 305);
        prompt.setSize(80, 50);
        prompt.setVisible(false);

        stage.addActor(login_button);
        stage.addActor(signUp_button);
        stage.addActor(username_field);
        stage.addActor(password_field);
        stage.addActor(prompt);

        // Adding click listeners to login and signup buttons
        login_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choice = 1;
                // making the buttons invisible
                login_button.setVisible(false);
                signUp_button.setVisible(false);

                // logic for entering username in login
                prompt.setVisible(true);
                username_field.setVisible(true);
                username_field.setFocusTraversal(true);
                password_field.setVisible(true);
                password_field.setFocusTraversal(true);
            }
        });

        signUp_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choice = 0;
                // making buttons invisible
                login_button.setVisible(false);
                signUp_button.setVisible(false);

                // logic for entering username in sign up
                prompt.setVisible(true);
                username_field.setVisible(true);
                username_field.setFocusTraversal(true);
                password_field.setVisible(true);
                password_field.setFocusTraversal(true);
            }
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        float world_width = viewport.getWorldWidth();
        float world_height = viewport.getWorldHeight();
        batch.draw(background, 0, 0, world_width, world_height);
        batch.end();

        stage.act(delta);
        stage.draw();

        // LOADING PLAYERS FROM FILE TO LIST
        if (player_load_flag) {
            Player.load_players();
            player_load_flag = false;
        }

        // LOGIN
        if (username_field.isVisible() && !username_field.getText().equals("Username")
            && password_field.isVisible() && !password_field.getText().equals("Password") &&
            Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER) && choice == 1) {

            String username = username_field.getText();
            String password = password_field.getText();
            username_field.setVisible(false);
            password_field.setVisible(false);

            // NOTE: ON SUCCESSFUL LOGIN, when we go to home page, we have to TRANSFER the PLAYER OBJECT
            Player current_player = Player.login(username, password);

            if (current_player == null) {
                // SOME PROBLEM IN LOGGING IN, FILL THIS
                prompt.setText("Either user does not exist, or the password is incorrect. PLease try again!");
                prompt.setVisible(true);

                username_field.setVisible(false);
                password_field.setVisible(false);

                login_button.setVisible(true);
                signUp_button.setVisible(true);
            }
            else {
                prompt.setText("You have successfully logged in!");
                game.setScreen((Screen) new HomePage(game, current_player));        // switching to homepage
            }
        }
        // SIGN-UP
        else if (username_field.isVisible() && !username_field.getText().equals("Username")
            && password_field.isVisible() && !password_field.getText().equals("Password") &&
            Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER) && choice == 0) {

            String username = username_field.getText();
            String password = password_field.getText();
            username_field.setVisible(false);
            password_field.setVisible(false);

            Player current_player = Player.signup(username, password);

            if (current_player == null) {
                // Problem has occured in signing up
                // FILL THIS
                prompt.setText("Player with given username already exists. Please try again!");
                prompt.setVisible(true);

                username_field.setVisible(false);
                password_field.setVisible(false);

                login_button.setVisible(true);
                signUp_button.setVisible(true);
            }
            else {
                prompt.setText("You have successfully signed up!");
                game.setScreen((Screen) new HomePage(game, current_player));
            }
        }
    }

    public void resize(int width, int height) {}
    public void pause() {}
    public void resume() {}
    public void hide() {}
    public void dispose() {
        batch.dispose();
        skin.dispose();
        stage.dispose();
    }
}

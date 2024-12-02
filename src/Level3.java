package io.github.angry_birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.LinkedList;

public class Level3 extends Level implements Screen {
    private SpriteBatch batch;

    private Texture backgroundTexture;
    private Texture redbirdTexture;
    private Texture kingpigTexture;
    private Texture woodblockTexture;

    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private Texture pausebuttonTexture;
    private ImageButton pausebutton;
    private Texture InMenuTexture;
    private ImageButton InMenuButton;
    private Texture resumebuttonTexture;
    private ImageButton resumebutton;

    private final MainGame game;
    private Player current_player;
    private World world;
    private Bird bird;
    private boolean isPaused = false;

    private boolean dragging = false;         // Whether the bird is being dragged
    private Vector2 dragStartPosition;        // Start position of the drag
    private Vector2 currentMousePosition;     // Current position of the mouse during dragging
    public ArrayList<Integer> Array_birds = new ArrayList<>();
    private Block temp_block;
    private Pig temp_pig;
    private Ground ground = new Ground();

    private int num_pigs_remaining;

    public Level3(MainGame game, Player player, Slingshot slingshot) {
        this.game = game;                           // Store the reference to MainGame
        this.current_player = player;
        this.slingshot = slingshot;

        this.birds = new LinkedList<>();
        this.blocks = new ArrayList<>();
        this.pigs = new ArrayList<>();
    }

    public Level3(MainGame game, Player player, Slingshot slingshot, World w, boolean restored) {
        this.game = game;
        this.current_player = player;
        this.slingshot = slingshot;
        this.restored = restored;
        this.world = w;

        blocks = new ArrayList<>();
        pigs = new ArrayList<>();
        birds = new LinkedList<>();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("Level_1images/Background1.png");
        redbirdTexture = new Texture("Level_1images/redBird.png");
        woodblockTexture = new Texture("Level_1images/woodblock.png");
        kingpigTexture = new Texture("Level_1images/kingpig.png");

        if (!restored) {
            world = new World(new Vector2(0, -10), true);
        }
        world.setContactListener(new GameContactListener());

        camera = new OrthographicCamera();
        viewport = new FitViewport(640, 380, camera);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // TESTING
        if (!restored) {
            this.setBirds();
        }

        slingshot.setBird_list(birds);
        bird = slingshot.getCurrent_bird();
        ground.create(world, camera);
        if (bird != null) {
            bird.create(world);
            slingshot.create(world);
        }
        else {
            System.out.println("Current Bird is null. No birds remain. Level failed.");
            game.setScreen(new LostScreen(game, current_player, slingshot, 3));
            return;
        }

        // blocks = new ArrayList<>();
        if (!restored) {
            this.setBlocks();
        }

        // pigs = new ArrayList<>();
        if (!restored) {
            this.setPigs();
        }
        num_pigs_remaining = this.pigs.size();


        InMenuTexture = new Texture("InMenuButton.png");
        InMenuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(InMenuTexture)));
        InMenuButton.setPosition(15, 330);
        InMenuButton.setSize(40, 40);
        stage.addActor(InMenuButton);
        InMenuButton.addListener(new ClickListener());

        save_button_image = new Texture("save_game_databutton.png");                 // CHANGE LATER
        save_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(save_button_image)));
        save_button.setPosition(15, 280);
        save_button.setSize(40, 40);
        stage.addActor(save_button);
        save_button.addListener(new ClickListener());

        pause_resume();
    }

    public void pause_resume(){
        if (!isPaused) {
            pausebuttonTexture = new Texture("Level_1images/pausebutton.png");
            pausebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pausebuttonTexture)));
            pausebutton.setPosition(580, 330);
            pausebutton.setSize(40, 40);
            pausebutton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Logic can also be added here if needed
                    super.clicked(event, x, y);
                }
            });
            stage.addActor(pausebutton);
        }

        if (isPaused) {
            resumebuttonTexture = new Texture("resumebutton.png");
            resumebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(resumebuttonTexture)));
            resumebutton.setPosition(580, 330);
            resumebutton.setSize(40, 40);
            resumebutton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Logic can also be added here if needed
                    super.clicked(event, x, y);
                }
            });
            stage.addActor(resumebutton);
        }
    }

    @Override
    public void render(float delta) {
        if (!isPaused) {
            world.step(1 / 60f, 6, 2);
        }

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        float world_width = viewport.getWorldWidth();
        float world_height = viewport.getWorldHeight();
        batch.draw(backgroundTexture, 0, 0, world_width, world_height);

        // Define the slingshot position and size
        float slingshotX = Slingshot.anchor_pointX;
        float slingshotY = Slingshot.anchor_pointY;
        float slingshotHeight = Slingshot.height;
        float slingshotWidth = slingshot.getSlingshot_texture().getWidth() * (slingshotHeight / slingshot.getSlingshot_texture().getHeight()); // Scale width based on height

        // Draw the slingshot at (125, 55) with a height of 75 and scaled width
        batch.draw(slingshot.getSlingshot_texture(), slingshotX, slingshotY, slingshotWidth, slingshotHeight);


        Vector2 bird_position = bird.getBirdBody().getPosition();
        batch.draw(bird.getBirdImage(),
            (bird_position.x*PPM) - bird.get_radius()*PPM,
            (bird_position.y*PPM) - bird.get_radius()*PPM,
            2*bird.get_radius()*PPM, 2*bird.get_radius()*PPM);


        // Drawing pigs
        for (Pig pig : pigs) {
            temp_pig = pig;
            if (temp_pig.isHasBeenDisposed()) {
                continue;
            }
            Vector2 pig_position = temp_pig.getPigBody().getPosition();
            batch.draw(temp_pig.getPigImage(),
                (pig_position.x * PPM) - temp_pig.get_radius() * PPM,
                (pig_position.y * PPM) - temp_pig.get_radius() * PPM,
                2 * temp_pig.get_radius() * PPM, 2 * temp_pig.get_radius() * PPM);
        }


        for (Block block : blocks) {
            temp_block = block;
            if (temp_block.isHasBeenDisposed()) {
                continue;
            }
            Vector2 block_position = temp_block.getBlockBody().getPosition();
            batch.draw(
                temp_block.getBlockImage(),
                (block_position.x * Level.PPM) - (temp_block.getwidth() / 2),
                (block_position.y * Level.PPM) - (temp_block.getheight() / 2),
                temp_block.getwidth(),
                temp_block.getheight()
            );
        }

        batch.end();

        LevelScreen.debugRenderer.render(world, camera.combined);

        stage.act(delta);                                               // for buttons
        stage.draw();


        if (!isPaused && pausebutton != null && pausebutton.isPressed()) {
            isPaused = true; // Pause the game
            pauseGame();
        }

        if (isPaused && resumebutton != null && resumebutton.isPressed()) {
            isPaused = false; // Pause the game
            resumeGame();
        }

        if (InMenuButton.isPressed()) {
            game.setScreen(new PausePage(game, current_player, this, slingshot));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Set the screen to VictoryScreen, increment levels_cleared using victory()
            if(current_player.getLevels_cleared()<3){
                current_player.setLevels_cleared(3);
            }
            game.setScreen(new VictoryScreen(game, current_player, slingshot, 3));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // set the screen to LostScreen
            game.setScreen(new LostScreen(game, current_player, slingshot, 3));
        }

        if (save_button.isPressed()) {
            GameStateManager.saveGameState(current_player.getUsername(), this, bird, world);
            game.setScreen(new LevelScreen(game, current_player));
            System.out.println("This should NOT be printed. Screen should be set to Level screen");
        }

        handleInput();

        // destroying body of current bird when the conditions are met
        // CONDITIONS: bird out of viewport range(no condition for negative-y), bird health = 0, bird movement 0
        // IMPLEMENT THE NEXT 2 AFTERWARDS
        Vector2 curr_bird_position = bird.getBirdBody().getPosition();
        if (curr_bird_position.x > 640/Level.PPM || curr_bird_position.x < 0/Level.PPM || curr_bird_position.y > 780/Level.PPM || bird.getBird_Health() <= 0 || bird.getStoppedmoving()) {
            dispose_bird(bird);

            // bird should refer to the next bird now
            if(!Array_birds.isEmpty()){
                Array_birds.removeFirst();
            }

            bird = slingshot.set_next_bird();                                       // does poll
            if (bird == null) {
                // if all birds are finished, then game is lost
                game.setScreen(new LostScreen(game, current_player, slingshot, 3));
                return;
            }
            bird.create(world);
        }

        // Disposing Pigs
        for (Pig curr_pig : pigs) {
            Vector2 curr_pig_position = curr_pig.getPigBody().getPosition();

            // for pigs, movement condition is NOT required. CHECK CONTACT WITH GROUND(MAYBE)
            if (!curr_pig.isHasBeenDisposed()) {
                if (curr_pig_position.x > 640 / Level.PPM || curr_pig_position.x < 0 / Level.PPM || curr_pig_position.y > 380 / Level.PPM
                    || curr_pig.getPigHealth() <= 0) {
                    // INSTEAD OF REMOVING THE PIG, setting hasBeenDisposed to true. It cannot be disposed again
                    dispose_pig(curr_pig);
                    num_pigs_remaining--;
                }
            }

            // checking for negative also in case there is any error
            if (num_pigs_remaining <= 0) {
                // INCREMENT NUMBER OF LEVELS PASSED FOR PLAYER IF REQUIRED
                if(current_player.getLevels_cleared()<3){
                    current_player.setLevels_cleared(3);
                }
                game.setScreen(new VictoryScreen(game, current_player, slingshot, 3));
                return;
            }
        }

        // Dispose Blocks - no ground condition
        for (Block curr_block : blocks) {
            Vector2 curr_block_position = curr_block.getBlockBody().getPosition();

            if (!curr_block.isHasBeenDisposed()) {
                if (curr_block_position.x > 640 / Level.PPM || curr_block_position.x < 0 / Level.PPM || curr_block_position.y > 380 / Level.PPM
                    || curr_block.getBlockHealth() <= 0) {
                    dispose_block(curr_block);
                }
            }
        }
    }

    public void setBirds() {
        this.birds.clear();
        this.Array_birds.clear();
        this.birds.add(new RedBird());
        this.birds.add(new YellowBird());
        this.Array_birds.add(2);
        this.birds.add(new BlueBird());
        this.Array_birds.add(3);
    }

    public void setPigs() {
        this.pigs.clear();

            this.pigs.add(new Pig("helmetpig.png", 18));
            temp_pig=pigs.getLast(); temp_pig.create(world, (440+(40)*1), 255);

        num_pigs_remaining = pigs.size();
    }

    private void setBlocks() {
        for(int i=0 ; i<2; ++i ){
            int randomNumber = 4;
            for(int j =0; j<randomNumber; ++j){
                switch(i){
                    case 1:this.blocks.add(new Block("iceblock.png",4));break;
                    case 0:this.blocks.add(new Block("steelblock.png",5));break;
                    default:break;
                }
                temp_block=blocks.getLast(); temp_block.create(world, (440+(80)*i), (55+(40)*j), 35, 35);
            }
        }
    }

    public void dispose_bird(Bird bird) {
        // YOU CAN USE THIS FOR JUNIT TESTING
        if (bird.getBirdBody() != null) {
            world.destroyBody(bird.getBirdBody());
            System.out.println("Bird body disposed.");
        }
    }

    public void dispose_pig(Pig pig) {
        if (pig.getPigBody() != null) {
            world.destroyBody(pig.getPigBody());
            pig.setHasBeenDisposed(true);                           // a pig body can be disposed only once
            System.out.println("Pig body disposed.");
        }
    }

    public void dispose_block(Block block) {
        if (block.getBlockBody() != null) {
            world.destroyBody(block.getBlockBody());
            block.setHasBeenDisposed(true);                         // a block body can be disposed only once
            System.out.println("Block body disposed.");
        }
    }

    private void handleInput() {
        try {
            Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (!dragging) {
                    Vector2 birdPosition = bird.getBirdBody().getPosition().scl(Level.PPM);
                    float birdRadius = bird.get_radius() * Level.PPM;
                    if (mousePosition.dst(birdPosition) <= birdRadius && !bird.get_HasBeenTouched()) {
                        dragging = true;
                        bird.set_HasBeenTouched(true);
                        dragStartPosition = mousePosition;
                    }
                } else {
                    currentMousePosition = mousePosition;
                }
            } else if (dragging) {
                // Release the bird
                dragging = false;

                //Calculate release force
                Vector2 releaseForce;
                if(currentMousePosition.dst(dragStartPosition) > 96f) {
                    releaseForce = (dragStartPosition.sub(currentMousePosition)).nor().scl(8f);
                }
                else{
                    releaseForce = dragStartPosition.sub(currentMousePosition).scl(0.1f);
                }
                bird.getBirdBody().applyLinearImpulse(releaseForce, bird.getBirdBody().getWorldCenter(), true);
            }
        } catch (Exception e) {
            Gdx.app.log("Input Error", "Error during input handling: " + e.getMessage());
        }
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void pauseGame() {
        isPaused = true;
        pausebuttonTexture.dispose();
        pause_resume();
    }

    public void resumeGame() {
        isPaused = false;
        resumebuttonTexture.dispose();
        pause_resume();
    }

    @Override
    public void pause() { pauseGame();}

    @Override
    public void resume() { resumeGame();}

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        redbirdTexture.dispose();
        kingpigTexture.dispose();
        woodblockTexture.dispose();
        pausebuttonTexture.dispose();
        stage.dispose();
        if (bird.getCircle() != null) {
            bird.getCircle().dispose();
        }
    }
}

package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Slingshot {
    private Queue<Bird> bird_list = new LinkedList<Bird>();
    private Bird current_bird;
    private BodyDef slingshotBodyDef;
    private Body slingshotBody;
    private PolygonShape slingshotShape;
    private FixtureDef slingshotFixtureDef;
    private Fixture slingshotFixture;

    private Texture slingshot_texture;

    // release point should be EXACTLY ABOVE anchor point
    public static final int height = 75;
    public static final int anchor_pointX = 125, anchor_pointY = 55;
    private static final Vector2 release_point = new Vector2(125, 130);     // decide Y-COORD


    public void create(World world) {
        slingshotBodyDef = new BodyDef();
        slingshotBodyDef.type = BodyDef.BodyType.StaticBody;

        slingshotBodyDef.position.set(130/Level.PPM, 50/Level.PPM);

        slingshotBody = world.createBody(slingshotBodyDef);

        slingshotShape = new PolygonShape();
        slingshotShape.setAsBox(16/Level.PPM, height/Level.PPM);

        slingshotFixtureDef = new FixtureDef();
        slingshotFixtureDef.shape = slingshotShape;
        slingshotFixtureDef.density = 0.8f;
        slingshotFixtureDef.friction = 1f;
        slingshotFixtureDef.restitution = 0f;              // for bounce

        slingshotFixture = slingshotBody.createFixture(slingshotFixtureDef);
    }

    public Slingshot(String path) {
        this.slingshot_texture = new Texture(path);
    }

    public Slingshot() {
        // for JUNIT TESTS ONLY
    }

    public Texture getSlingshot_texture() {
        return slingshot_texture;
    }

    public void setBird_list(Queue<Bird> bird_list) {
        // ALL CHANGES TO BIRD_LIST MUST BE DONE THROUGH slingshot.bird_list ONLY. NOT birds of class LEVE
        this.bird_list = bird_list;
        this.current_bird = bird_list.poll();
    }

    public Bird getCurrent_bird() {
        return current_bird;
    }

    public Bird set_next_bird() {
        this.current_bird = bird_list.poll();
        return this.current_bird;
    }
//    public Bird set_next_bird1(ArrayList<Bird> Bird_array) {
//        Bird_array.removeFirst();
//        this.current_bird = bird_list.poll();
//        return this.current_bird;
//    }

}

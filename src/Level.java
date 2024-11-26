package io.github.angry_birds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Queue;

public abstract class Level implements Screen {
    Slingshot slingshot;
    Queue<Bird> birds;


    public static final float PPM = 32.0f;


    public void create_ground(World world, Camera camera) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        // x-coord: corresponding to center of ground, y-coord: bottom
        groundBodyDef.position.set(new Vector2(320/PPM, 0));

        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth/PPM, 50f/PPM);

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundBox;
        groundFixtureDef.friction = 0.9f; // High friction value for the ground
        groundFixtureDef.restitution = 0.1f;

        groundBody.createFixture(groundFixtureDef);
        groundBox.dispose();
    }
}

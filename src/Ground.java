package io.github.angry_birds;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Ground {
    private BodyDef groundBodyDef;
    private Body groundBody;
    private PolygonShape groundBox;
    private FixtureDef groundFixtureDef;
    private Fixture blockFixture;

    public Ground() {
    }

    public void create(World world, Camera camera) {
        groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        // x-coord: corresponding to center of ground, y-coord: bottom
        groundBodyDef.position.set(new Vector2(320/Level.PPM, 0));

        groundBody = world.createBody(groundBodyDef);
        groundBody.setUserData(this);

        groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth/Level.PPM, 50f/Level.PPM);

        groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundBox;
        groundFixtureDef.friction = 0.9f; // High friction value for the ground
        groundFixtureDef.restitution = 0.1f;

        groundBody.createFixture(groundFixtureDef);
        groundBox.dispose();
    }

    public PolygonShape getCircle() { return groundBox; }

    public Body getBlockBody() {
        return groundBody;
    }
}

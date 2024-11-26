package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    private BodyDef pigBodyDef;
    private Body pigBody;
    private CircleShape circle;
    private FixtureDef pigFixtureDef;
    private Fixture pigFixture;
    Texture pig_image;

    public Pig(String path) {
        this.pig_image = new Texture(path);
    }

    public void create(World world) {
        pigBodyDef = new BodyDef();
        pigBodyDef.type = BodyDef.BodyType.DynamicBody;

        pigBodyDef.position.set(130/Level.PPM, 130/Level.PPM);

        pigBody = world.createBody(pigBodyDef);

        circle = new CircleShape();
        circle.setRadius((float)16/Level.PPM);

        pigFixtureDef = new FixtureDef();
        pigFixtureDef.shape = circle;
        pigFixtureDef.density = 0.6f;
        pigFixtureDef.friction = 0.4f;
        pigFixtureDef.restitution = 0.3f;              // for bounce

        pigFixture = pigBody.createFixture(pigFixtureDef);
    }

    public CircleShape getCircle() { return circle; }

    public Body getPigBody() {
        return pigBody;
    }

    public float get_radius() {
        return circle.getRadius();
    }
}

package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class RedBird extends Bird {
    private BodyDef birdBodyDef;

    private CircleShape circle;
    private FixtureDef birdFixtureDef;
    private Fixture birdFixture;
//    public Vector2 initialBirdPosition;

    public RedBird() {
        this.bird_image = new Texture("red_bird.png");
        this.Bird_Health = 15;
    }

    public RedBird (String s) {
        // for JUNIT TESTS ONLY
    }

    public void create(World world) {
        birdBodyDef = new BodyDef();
        birdBodyDef.type = BodyDef.BodyType.DynamicBody;

        birdBodyDef.position.set(140/Level.PPM, 200/Level.PPM);

        birdBody = world.createBody(birdBodyDef);
        birdBody.setUserData(this);

        circle = new CircleShape();
        circle.setRadius((float)16/Level.PPM);

        birdFixtureDef = new FixtureDef();
        birdFixtureDef.shape = circle;
        birdFixtureDef.density = 0.6f;
        birdFixtureDef.friction = 0.4f;
        birdFixtureDef.restitution = 0.3f;              // for bounce

        birdFixture = birdBody.createFixture(birdFixtureDef);
        initialBirdPosition = new Vector2(birdBody.getPosition());
    }

    public CircleShape getCircle() { return circle; }

    public Body getBirdBody() {
        return birdBody;
    }

    public float get_radius() {
        return circle.getRadius();
    }
}

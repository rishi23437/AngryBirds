package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BlueBird extends Bird {
    private BodyDef birdBodyDef;

    private CircleShape circle;
    private FixtureDef birdFixtureDef;
    private Fixture birdFixture;
//    public Vector2 initialBirdPosition;

    public BlueBird() {
        this.bird_image = new Texture("Level_1images/BlueBird.png");
        this.Bird_Health = 24;
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
        birdFixtureDef.density = 0.9f;
        birdFixtureDef.friction = 0.5f;
        birdFixtureDef.restitution = 0.6f;              // for bounce

        birdFixture = birdBody.createFixture(birdFixtureDef);
//        birdBody.setLinearDamping(2f); // Adjust damping for gradual slowdown
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

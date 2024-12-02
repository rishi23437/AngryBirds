package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    private BodyDef pigBodyDef;
    private Body pigBody;
    private CircleShape circle;
    private FixtureDef pigFixtureDef;
    private Fixture pigFixture;
    private int pigtype;

    private String pig_image_path;
    private Texture pig_image;
    private boolean hasBeenDisposed = false;

    public int PigHealth;
    private int x;
    private int y;

    public Pig(String path,  int PigHealth) {
        this.pig_image_path = path;
        this.pig_image = new Texture(path);
        this.PigHealth = PigHealth;
        switch(PigHealth){
            case(15): pigtype=1; break;
            case(12): pigtype=2; break;
            case(18): pigtype=3; break;
            default: break;
        }
    }

    public void create(World world, int x, int y) {
        pigBodyDef = new BodyDef();
        pigBodyDef.type = BodyDef.BodyType.DynamicBody;

        this.x=x;
        this.y=y;
        pigBodyDef.position.set(x/Level.PPM, y/Level.PPM);

        pigBody = world.createBody(pigBodyDef);
        pigBody.setUserData(this);

        circle = new CircleShape();
        circle.setRadius((float)16/Level.PPM);

        pigFixtureDef = new FixtureDef();
        pigFixtureDef.shape = circle;
        pigFixtureDef.density = 0.6f;
        pigFixtureDef.friction = 0.4f;
        pigFixtureDef.restitution = 0.1f;              // for bounce
        pigBody.setLinearDamping(2f);
        pigFixture = pigBody.createFixture(pigFixtureDef);
    }

    public CircleShape getCircle() { return circle; }

    public Body getPigBody() {
        return pigBody;
    }

    public void setPosition(float x, float y) {
        this.x = (int)x;
        this.y = (int)y;
        pigBody.setTransform(x / Level.PPM, y / Level.PPM, 0);
    }

    public float get_radius() {
        return circle.getRadius();
    }

    public Texture getPigImage() {
        return pig_image;
    }

    public void setPigHealth(int ph) {
        PigHealth = ph;
    }

    public int getPigHealth() {
        return this.PigHealth;
    }

    public boolean isHasBeenDisposed() {
        return hasBeenDisposed;
    }

    public void setHasBeenDisposed(boolean hbd) {
        this.hasBeenDisposed = hbd;
    }

    public void setPig_image(Texture pig_image) {
        this.pig_image = pig_image;
    }
    public int getPigtype(){
        return this.pigtype;
    }

    public String getPig_image_path() {
        return pig_image_path;
    }

    public void setPig_image_path(String pig_image_path) {
        this.pig_image_path = pig_image_path;
    }
}

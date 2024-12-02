package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Bird {
    public Vector2 initialBirdPosition;
    private boolean stoppedmoving = false;
    private boolean hasBeenTouched = false;
    int Bird_Health;
    Texture bird_image;
    Body birdBody;

    public abstract void create(World world);
    public abstract CircleShape getCircle();
    public abstract Body getBirdBody();
    public abstract float get_radius();

    public Texture getBirdImage() {
        return bird_image;
    }
    public void setPosition(float x, float y) {
        initialBirdPosition = new Vector2(x, y);
        birdBody.setTransform(x / Level.PPM, y / Level.PPM, 0);
    }

    public boolean get_HasBeenTouched() {
        return hasBeenTouched;
    }

    public void set_HasBeenTouched(boolean hasBeenTouched) {
        this.hasBeenTouched = hasBeenTouched;
    }

    public int getBird_Health() {
        return Bird_Health;
    }
    public void setBird_Health(int Bird_Health) {
        this.Bird_Health = Bird_Health;
    }

    public void dispose() {
        birdBody.getWorld().destroyBody(birdBody);
        bird_image.dispose();
    }

    public boolean getStoppedmoving() {
        return stoppedmoving;
    }
    public void setStoppedmoving(boolean stoppedmoving) {
        this.stoppedmoving = stoppedmoving;
    }
}

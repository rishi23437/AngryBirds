package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Bird {
    public Vector2 initialBirdPosition;
    private boolean hasBeenTouched = false;
    // ADD attribute representing MASS - maybe int mass

    Texture bird_image;
    public abstract void create(World world);

    public abstract CircleShape getCircle();
    public abstract Body getBirdBody();
    public abstract float get_radius();

    public Texture getBirdImage() {
        return bird_image;
    }

    public boolean get_HasBeenTouched() {
        return hasBeenTouched;
    }

    public void set_HasBeenTouched(boolean hasBeenTouched) {
        this.hasBeenTouched = hasBeenTouched;
    }
}

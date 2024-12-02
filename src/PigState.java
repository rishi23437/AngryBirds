package io.github.angry_birds;

public class PigState {
    public float x, y;
    public int health;
    public String texturePath;

    public PigState(float x, float y, int health, String texturePath) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.texturePath = texturePath;
    }

    public Pig toPig() {
        return new Pig(this.texturePath, this.health);
    }
}


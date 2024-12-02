package io.github.angry_birds;

public class BlockState {
    public float x, y, width, height;
    public int health;
    public String texturePath;

    public BlockState(float x, float y, float width, float height, int health, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.texturePath = texturePath;
    }

    public Block toBlock() {
        return new Block(this.texturePath, this.health);
    }
}

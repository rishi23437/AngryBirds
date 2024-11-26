package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Block {
    private BodyDef blockBodyDef;
    private Body blockBody;
    private PolygonShape groundBox;
    private FixtureDef blockFixtureDef;
    private Fixture blockFixture;
    Texture blockTexture;
    public int BlockHealth;
    private float height;
    private float width;

    public Block(String path, int BlockHealth) {
        this.blockTexture = new Texture(path);
        this.BlockHealth = BlockHealth;
    }

    public void create(World world, float x, float y, float width, float height) {
        blockBodyDef = new BodyDef();
        blockBodyDef.type = BodyDef.BodyType.DynamicBody;

        blockBodyDef.position.set(x/Level.PPM, y/Level.PPM);
        blockBody = world.createBody(blockBodyDef);

        this.height = height;
        this.width = width;
        groundBox = new PolygonShape();
        groundBox.setAsBox(width / (2 * Level.PPM), height / (2 * Level.PPM));

        blockFixtureDef = new FixtureDef();
        blockFixtureDef.shape = groundBox;
        blockFixtureDef.density = 0.6f;
        blockFixtureDef.friction = 0.4f;
        blockFixtureDef.restitution = 0.3f;              // for bounce

        blockFixture = blockBody.createFixture(blockFixtureDef);
    }

    public PolygonShape getCircle() { return groundBox; }

    public Body getBlockBody() {
        return blockBody;
    }

    public float getwidth(){
        return width;
    }
    public float getheight(){
        return height;
    }

    public Texture getBlockImage() {
        return blockTexture;
    }

}

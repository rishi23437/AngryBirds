package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Block {
    private BodyDef blockBodyDef;
    private Body blockBody;
    private PolygonShape groundBox;
    private FixtureDef blockFixtureDef;
    private Fixture blockFixture;

    private String block_image_path;
    Texture blockTexture;

    private int BlockType;
    private int BlockHealth;
    private boolean hasBeenDisposed = false;

    private float height;
    private float width;

    public Block(String path, int BlockHealth) {
        this.blockTexture = new Texture(path);
        this.block_image_path = path;
        this.BlockHealth = BlockHealth;
        switch(BlockHealth){
            case(3): BlockType=1; break;
            case(4): BlockType=2; break;
            case(5): BlockType=3; break;
            default: break;
        }
    }

    public void create(World world, float x, float y, float width, float height) {
        blockBodyDef = new BodyDef();
        blockBodyDef.type = BodyDef.BodyType.DynamicBody;

        blockBodyDef.position.set(x/Level.PPM, y/Level.PPM);
        blockBody = world.createBody(blockBodyDef);

        blockBody = world.createBody(blockBodyDef);
        blockBody.setUserData(this);

        this.height = height;
        this.width = width;
        groundBox = new PolygonShape();
        groundBox.setAsBox(width / (2 * Level.PPM), height / (2 * Level.PPM));

        blockFixtureDef = new FixtureDef();
        blockFixtureDef.shape = groundBox;
        blockFixtureDef.density = 0.1f;
        blockFixtureDef.friction = 0.1f;
        blockFixtureDef.restitution = 0.4f;              // for bounce

        blockFixture = blockBody.createFixture(blockFixtureDef);
    }

    public void setPosition(float x, float y) {
        blockBody.setTransform(x / Level.PPM, y / Level.PPM, 0);
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
    public void setBlockImage(Texture blockImage) {
        blockTexture = blockImage;
    }

    public void dispose() {
        blockBody.getWorld().destroyBody(blockBody);
        blockTexture.dispose();
    }
    public int getBlockHealth() {
        return BlockHealth;
    }

    public void setBlockHealth(int health) {
        BlockHealth = health;
    }

    public boolean isHasBeenDisposed() {
        return hasBeenDisposed;
    }

    public void setHasBeenDisposed(boolean hbd) {
        this.hasBeenDisposed = hbd;
    }

    public int getBlockType(){
        return BlockType;
    }

    public void setBlock_image_path(String image_path){
        this.block_image_path = image_path;
    }

    public String getBlock_image_path() {
        return block_image_path;
    }

}

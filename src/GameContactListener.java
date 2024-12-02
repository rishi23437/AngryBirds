package io.github.angry_birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        if (userDataA instanceof Bird && userDataB instanceof Pig) {
            System.out.println("Bird, pig contact");
            handleBirdPigCollision((Bird) userDataA, (Pig) userDataB);
        } else if (userDataA instanceof Pig && userDataB instanceof Bird) {
            System.out.println("Pig, bird contact");
            handleBirdPigCollision((Bird) userDataB, (Pig) userDataA);
        } else if (userDataA instanceof Bird && userDataB instanceof Block) {
            System.out.println("Bird, block contact");
            handleBirdBlockCollision((Bird) userDataA, (Block) userDataB);
        } else if (userDataA instanceof Block && userDataB instanceof Bird) {
            System.out.println("Block, bird contact");
            handleBirdBlockCollision((Bird) userDataB, (Block) userDataA);
        } else if (userDataA instanceof Ground && userDataB instanceof Bird) {
            System.out.println("Ground, bird contact");
            handleBirdGroundCollision((Ground) userDataA, (Bird) userDataB);
        } else if (userDataA instanceof Bird && userDataB instanceof Ground) {
            System.out.println("Ground, bird contact");
            handleBirdGroundCollision((Ground) userDataB, (Bird) userDataA);
        }
    }
    private void handleBirdGroundCollision(Ground ground, Bird bird) {
        bird.getBirdBody().setLinearDamping(1f);
        if(bird.get_HasBeenTouched()){
            Vector2 linearVelocity = bird.getBirdBody().getLinearVelocity();
            System.out.println("Linear velocity: " + linearVelocity);
            if(linearVelocity.x < 1){
                bird.setStoppedmoving(true);
            };
        }
    }

    private void handleBirdPigCollision(Bird bird, Pig pig) {
        int birdHealth = bird.getBird_Health();
        int pigHealth = pig.getPigHealth();
        switch(pig.getPigtype()){
            case 1:pig.setPig_image(new Texture("kingpigdamaged.png"));
                pig.setPig_image_path("kingpigdamaged.png");break;
            case 2:pig.setPig_image(new Texture("taupigdamaged.png"));
                pig.setPig_image_path("taupigdamaged.png");break;
            case 3:pig.setPig_image(new Texture("helmetpigdamaged.png"));
                pig.setPig_image_path("helmetpigdamaged.png");break;
            default: break;
        }


        bird.setBird_Health(birdHealth - pigHealth);
        pig.setPigHealth(pigHealth - birdHealth);
        System.out.println("Bird health: " + bird.getBird_Health());
        System.out.println("Pig health: " + pig.getPigHealth());
    }

    private void handleBirdBlockCollision(Bird bird, Block block) {
        int birdHealth = bird.getBird_Health();
        int blockHealth = block.getBlockHealth();
        switch(block.getBlockType()){
            case 1:block.setBlockImage(new Texture("woodblockdamaged.png"));
                block.setBlock_image_path("woodblockdamaged.png");break;
            case 2:block.setBlockImage(new Texture("iceblockdamaged.png"));
                block.setBlock_image_path("iceblockdamaged.png");break;
            case 3:block.setBlockImage(new Texture("steelblockdamaged.png"));
                block.setBlock_image_path("steelblockdamaged.png");break;
            default: break;
        }

        bird.setBird_Health(birdHealth - blockHealth);
        block.setBlockHealth(blockHealth - birdHealth);
        System.out.println("Bird health: " + bird.getBird_Health());
        System.out.println("Block health: " + block.getBlockHealth());
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}

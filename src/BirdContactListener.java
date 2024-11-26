package io.github.angry_birds;

import com.badlogic.gdx.physics.box2d.*;

public class BirdContactListener implements ContactListener {

    private Body birdBody;

    public BirdContactListener(Body birdBody) {
        this.birdBody = birdBody;
    }

    @Override
    public void beginContact(Contact contact) {
        // Check if the bird is touching a ground or block
        if (isBirdContactingSurface(contact)) {
            birdBody.setLinearDamping(2f); // Apply damping when in contact with a surface
        }
    }

    @Override
    public void endContact(Contact contact) {
        // When the bird stops touching a surface, remove damping
        if (isBirdContactingSurface(contact)) {
            birdBody.setLinearDamping(0f); // Stop damping when no longer in contact with surface
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // No need to implement this for our case
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // No need to implement this for our case
    }

    private boolean isBirdContactingSurface(Contact contact) {
        // If the bird is in contact with the ground or block, apply damping
        // Check if either fixture is the bird
        if (contact.getFixtureA().getBody() == birdBody || contact.getFixtureB().getBody() == birdBody) {
            // You can check the type of the other body (ground, block, etc.)
            // For simplicity, we're assuming contact with any surface applies damping
            return true;
        }
        return false;
    }
}

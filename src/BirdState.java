package io.github.angry_birds;

public class BirdState {
    public String type; // "RedBird", "YellowBird", etc.
    public float x, y;
    public int health;

    public BirdState(String type, float x, float y, int health) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.health = health;
    }

    public Bird toBird() {
        Bird bird;
        switch (type) {
            case "RedBird":
                bird = new RedBird();
                break;
            case "YellowBird":
                bird = new YellowBird();
                break;
            case "BlueBird":
                bird = new BlueBird();
                break;
            default:
                throw new IllegalArgumentException("Unknown bird type: " + type);
        }
        bird.setBird_Health(this.health);
        // bird.setPosition(this.x, this.y);
        return bird;
    }
}


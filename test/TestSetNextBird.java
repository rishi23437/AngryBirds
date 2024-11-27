package io.github.angry_birds;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.jupiter.api.BeforeAll;

public class TestSetNextBird {
    // Tests the set_next_bird() method of the Slingshot class

//    @BeforeAll
//    static void setup() {
//        // Initialize the headless backend
//        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
//        new HeadlessApplication(new ApplicationListener() {
//            @Override
//            public void create() {}
//            @Override
//            public void resize(int width, int height) {}
//            @Override
//            public void render() {}
//            @Override
//            public void pause() {}
//            @Override
//            public void resume() {}
//            @Override
//            public void dispose() {}
//        }, config);
//    }


    @Test
    void testSetNextBird() {
        Slingshot test_slingshot = new Slingshot();
        Bird sample_bird = new RedBird("SampleBird");
        Bird b1 = new RedBird("Bird1");
        Bird b2 = new RedBird("Bird2");
        Bird b3 = new RedBird("Bird3");

        Queue<Bird> test_birds = new LinkedList<>();
        test_birds.add(sample_bird);
        test_birds.add(b1);
        test_birds.add(b2);
        test_birds.add(b3);

        test_slingshot.setBird_list(test_birds);

        Bird result1 = test_slingshot.set_next_bird();
        Bird result2 = test_slingshot.set_next_bird();
        Bird result3 = test_slingshot.set_next_bird();

        assertEquals(b1, result1);
        assertEquals(b2, result2);
        assertEquals(b3, result3);
    }
}

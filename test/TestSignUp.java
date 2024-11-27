package io.github.angry_birds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestSignUp {
    @Test
    public void testSignUp() {
        Player result1 = Player.signup("rishi", "pass");
        Player result2 = Player.signup("sahas", "555555");
        Player result3 = Player.signup("mendhak", "frog");
        Player result4 = Player.signup("saandvi", "saand");

        Player result5 = Player.signup("saandvi", "saand");          // user already exists
        Player result6 = Player.signup("mendhak", "a");              // user already exists

        // Player object is returned, therefore not null
        assertNotEquals(null, result1);
        assertNotEquals(null, result2);
        assertNotEquals(null, result3);
        assertNotEquals(null, result4);

        // Since players already exist, the new players are not signed up, and null is returned
        assertNull(result5);
        assertNull(result6);

        Player.getPlayers().clear();                            // resetting the players
    }
}

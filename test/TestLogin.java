package io.github.angry_birds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestLogin {
    @Test
    public void testLogin() {
        Player rishi = new Player("rishi", "pass");
        Player sahas = new Player("sahas", "555555");
        Player mayank = new Player("mendhak", "frog");

        Player.getPlayers().add(rishi);
        Player.getPlayers().add(sahas);
        Player.getPlayers().add(mayank);

        Player result1 = Player.login("rishi", "pass");
        Player result2 = Player.login("sahas", "444444");      // pass is incorrect
        Player result3 = Player.login("mayank", "frog");       // name is incorrect(agreed)

        assertEquals(rishi, result1);
        assertNull(result2);
        assertNull(result3);
    }
}

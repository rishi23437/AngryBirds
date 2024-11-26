package io.github.angry_birds;

import java.util.ArrayList;

public class Player {
    // ATTRIBUTES WHICH DEFINE A PLAYER
    private String username;
    private String password;

    // update this everytime the player CLEARS a level(just before victory screen is displayed)
    private int levels_cleared;

    // list of all players. Sign up new players here, search new players while logging in from here
    private static ArrayList<Player> players = new ArrayList<Player>();

    public Player(String username, String password) {
        // MODIFY OTHER FIELDS(like levels cleared, among others) LATER(when you declare them) in the constructor
        this.username = username;
        this.password = password;
        this.levels_cleared = 0;
    }

    public static Player login(String username, String password) {
        for (Player p : players) {
            if (p.username.equals(username) && p.password.equals(password)) {
                return p;
            }
        }
        return null;
    }

    public static Player signup(String username, String password) {
        // Check if username already exists
        for (Player p : players) {
            if (p.username.equals(username)) {
                // Player with given username already exists
                return null;
            }
        }
        // username and password are okay, make and register new player
        Player new_player = new Player(username, password);
        players.add(new_player);
        return new_player;
    }

    public void victory() {
        levels_cleared++;
    }
}

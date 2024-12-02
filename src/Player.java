package io.github.angry_birds;

import java.io.*;
import java.util.ArrayList;

public class Player {
    // ATTRIBUTES WHICH DEFINE A PLAYER
    private String username;
    private String password;

    // update this everytime the player CLEARS a level(just before victory screen is displayed)
    private int levels_cleared;

    private Level saved_game_state;

    // list of all players. Sign up new players here, search new players while logging in from here
    private static ArrayList<Player> players = new ArrayList<Player>();

    public Player(String username, String password) {
        // MODIFY OTHER FIELDS(like levels cleared, among others) LATER(when you declare them) in the constructor
        this.username = username;
        this.password = password;
        this.levels_cleared = 0;
    }

    public Player(String username, String password, int levels_cleared) {
        this.username = username;
        this.password = password;
        this.levels_cleared = levels_cleared;
    }

    public Player(String username, String password, int levels_cleared, Level saved_game_state) {
        this.username = username;
        this.password = password;
        this.levels_cleared = levels_cleared;
        this.saved_game_state = saved_game_state;
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

    // Store player on sign up
    public static void store_players_to_file() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_files/player_details.txt"))) {
            for (Player player : players) {
                writer.newLine();
                writer.write(player.getUsername() + "," + player.getPassword() + "," + player.getLevels_cleared());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load_players() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data_files/player_details.txt"))) {
            players.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    int levels_cleared = Integer.parseInt(parts[2]);
                    players.add(new Player(username, password, levels_cleared));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void victory() {
        levels_cleared++;
    }

    public int getLevels_cleared() {
        return levels_cleared;
    }

    public void setLevels_cleared(int levels_cleared) {
        this.levels_cleared = levels_cleared;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}

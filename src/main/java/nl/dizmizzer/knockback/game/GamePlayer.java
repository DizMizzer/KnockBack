package nl.dizmizzer.knockback.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by DizMizzer.
 * Users don't have permission to release
 * the code unless stated by the Developer.
 * You are allowed to copy the source code
 * and edit it in any way, but not distribute
 * it. If you want to distribute addons,
 * please use the API. If you can't access
 * a certain thing in the API, please contact
 * the developer in contact.txt.
 */
public class GamePlayer {

    static HashMap<UUID, GamePlayer> gameplayers = new HashMap<>();
    private Player player;
    private Game game;
    private UUID id;

    public GamePlayer(Player player) {
        this.player = player;
    }

    public static GamePlayer getGamePlayer(Player player) {
        if (gameplayers.containsKey(player.getUniqueId())) {
            return gameplayers.get(player.getUniqueId());
        }
        gameplayers.put(player.getUniqueId(), new GamePlayer(player));
        return gameplayers.get(player.getUniqueId());
    }

    public static void removePlayer(UUID id) {
        if (gameplayers.containsKey(id)) gameplayers.remove(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public void teleport(Location location) {
        player.teleport(location);
    }

    public void sendMessage(String string) {
        getPlayer().sendMessage(string);
    }
}

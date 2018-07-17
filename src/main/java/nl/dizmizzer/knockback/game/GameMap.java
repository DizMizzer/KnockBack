package nl.dizmizzer.knockback.game;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.manager.DataManager;
import org.bukkit.Location;

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
public class GameMap {

    private Location spawn;
    private int maxHeight;
    private String mapName;
    private Game game;

    public GameMap(String mapName, Location spawn, int maxheight) {
        this.spawn = spawn;
        this.maxHeight = maxheight;
        this.mapName = mapName;
    }

    public GameMap(String string, Location spawn) {
        this.spawn = spawn;
        this.mapName = string;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public boolean isUsed() {
        return game != null;
    }

    public String getMapName() {
        return mapName;
    }

    public static GameMap findMap(String s) {
        for (GameMap gameMap : Knockback.getInstance().getGameMaps()) {
            if (gameMap.getMapName().equalsIgnoreCase(s)) return gameMap;
        }

        return null;
    }
}

package nl.dizmizzer.knockback;

import nl.dizmizzer.knockback.Scheduler.GameAutoStartScheduler;
import nl.dizmizzer.knockback.commands.CommandHandler;
import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GameMap;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.listener.*;
import nl.dizmizzer.knockback.manager.CacheManager;
import nl.dizmizzer.knockback.manager.ConfigManager;
import nl.dizmizzer.knockback.manager.DataManager;
import nl.dizmizzer.knockback.manager.StringManager;
import nl.dizmizzer.knockback.utils.LocationSerializer;
import nl.dizmizzer.knockback.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class Knockback extends JavaPlugin {

    private static Knockback instance;

    private HashMap<String, GameMap> times = new HashMap<>();
    private List<Game> games = new ArrayList<>();
    private BukkitRunnable br;
    private List<GameMap> gameMaps = new ArrayList<>();
    private Map<GamePlayer, GameMap> gameEditors = new HashMap<>();

    public static Knockback getInstance() {
        return instance;
    }

    public void onEnable() {
        // Plugin startup logic
        instance = this;

        ConfigManager.getInstance().setup();
        DataManager.getInstance().setup();
        CacheManager.getInstance().setup();
        List<String> temp = ConfigManager.getInstance().getStringList("games.times");
        temp = MathUtil.sortDates(temp);
        loadMaps();
        if (getGameMaps().size() < 1) {
            getServer().getConsoleSender().sendMessage(StringManager.prefix + ChatColor.RED + " No maps have been installed yet!");
        } else {
            for (String s : temp) {
                if (s.split(" ").length > 1)
                    times.put(s.split(" ")[0], GameMap.findMap(s.split(" ")[1]));
                else
                    times.put(s.split(" ")[0], null);
            }

            new GameAutoStartScheduler().runTaskLater(this, MathUtil.closestTime(times.keySet()) * 20 + 40);

            getServer().broadcastMessage(StringManager.prefix + ChatColor.AQUA + " Game starts in " + ChatColor.YELLOW + +((MathUtil.closestTime(times.keySet())) / 60) + ChatColor.AQUA + " minutes!");
        }

        registerEvents();
        this.getCommand("kb").setExecutor(new CommandHandler());
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerHitListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
    }

    public void addRunningGame(Game game) {
        games.add(game);
    }

    public HashMap<String, GameMap> getTimes() {
        return times;
    }

    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : getServer().getOnlinePlayers()) {
            if (GamePlayer.getGamePlayer(player).getGame() != null)
            CacheManager.getInstance().cachePlayer(player);
        }
        DataManager.getInstance().saveConfiguration();
        CacheManager.getInstance().saveFile();
    }

    public void addGameEditor(GamePlayer gameEditor, String gameMap) {
        for (GameMap gameMaps : gameMaps) {
            if (gameMaps.getMapName() == gameMap) {
                gameEditor.getPlayer().sendMessage(ChatColor.RED + "Map with name " + ChatColor.YELLOW + gameMap + ChatColor.RED + " already exists!");
            }
        }
        if (!gameEditors.containsKey(gameEditor)) {
            gameEditors.put(gameEditor, new GameMap(gameMap, gameEditor.getLocation().clone()));
        }
    }

    public void removeEditor(GamePlayer player) {
        if (gameEditors.containsKey(player)) {
            gameEditors.remove(player);
        }
    }

    public void addMap(GameMap map) {
        if (!gameMaps.contains(map)) gameMaps.add(map);
    }

    public static void debug(Debug c, Object s) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + c.getClassName() + " " + s);
    }

    public GameMap getGameMap(GamePlayer gamePlayer) {
        if (gameEditors.containsKey(gamePlayer))
            return gameEditors.get(gamePlayer);

        return null;
    }

    public List<GameMap> getGameMaps() {
        return gameMaps;
    }

    private void loadMaps() {
        FileConfiguration config = DataManager.getInstance().getConfiguration();
        if (!config.contains("maps")) return;
        Set<String> mapNames = config.getConfigurationSection("maps").getKeys(false);

        for (String s : mapNames) {
            Location loc = LocationSerializer.toLocation(new Object[]{
                    DataManager.getInstance().getConfiguration().getList("maps." + s + ".location").get(0),
                    DataManager.getInstance().getConfiguration().getList("maps." + s + ".location").get(1),
                    DataManager.getInstance().getConfiguration().getList("maps." + s + ".location").get(2),
                    DataManager.getInstance().getConfiguration().getList("maps." + s + ".location").get(3)
            });
            GameMap gameMap = new GameMap(s, loc);
            gameMap.setMaxHeight(config.getInt("maps." + s + ".height"));
            gameMap.setSpawn(loc);
            gameMaps.add(gameMap);
        }
    }

    public List<Game> getGames() {
        return games;
    }

    public boolean hasEditor(GamePlayer gamePlayer) {
        return gameEditors.containsKey(gamePlayer);
    }

    public void deleteRunningGame(Game game) {

        if (games.contains(game)) games.remove(game);
    }
}

//TODO Stop Command
//TODO Change Map Height
//TODO Change Map Name ??????????????
//TODO Make Help Command Nicer
//TODO Give different name on Custom game creation
/*TODO Add and fix permissions
  Classes Done:
  - LootCommand
 */

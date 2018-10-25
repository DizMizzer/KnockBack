package nl.dizmizzer.knockback.manager;

import nl.dizmizzer.knockback.Knockback;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
public class ConfigManager {

    static ConfigManager instance = new ConfigManager();

    private File file;
    private FileConfiguration configuration;

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    public void setup() {

        if (!Knockback.getInstance().getDataFolder().exists()) Knockback.getInstance().getDataFolder().mkdir();

        file = new File(Knockback.getInstance().getDataFolder(), "config.yml");
        try {
            if (file.createNewFile()) {
                configuration = YamlConfiguration.loadConfiguration(file);
                configuration.set("spawn", new Object[]{"gamemap", 100, 70, 100});
                configuration.options().header("Please configure all the things in here.");
                configuration.set("games.times", Arrays.asList("18:00:00", "20:25:00"));
                configuration.set("games.loot", new String[]{"DIAMOND, DIAMOND_BLOCK, DIAMOND_ORE"});
                configuration.save(file);
            }
            configuration = YamlConfiguration.loadConfiguration(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration = YamlConfiguration.loadConfiguration(file);


    }

    public List<String> getStringList(String s) {
        return configuration.getStringList(s);
    }

    public Location getSpawn() {
        List<Object> spawn = (List<Object>) configuration.getList("spawn");
        return new Location(Bukkit.getWorld(spawn.get(0).toString()), (Integer) spawn.get(1),(Integer)  spawn.get(2),(Integer)  spawn.get(3));
    }
}

package nl.dizmizzer.knockback.manager;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.utils.InventoryStringDeSerializer;
import nl.dizmizzer.knockback.utils.LocationSerializer;
import nl.dizmizzer.knockback.game.GameMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

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
public class DataManager {
    static DataManager instance = new DataManager();

    private File file;
    private FileConfiguration configuration;

    private DataManager() {
    }

    public static DataManager getInstance() {
        return instance;
    }

    public void setup() {
        if (!Knockback.getInstance().getDataFolder().exists()) {
            Knockback.getInstance().getDataFolder().mkdir();
        }

        file = new File(Knockback.getInstance().getDataFolder(), "data.yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuration = YamlConfiguration.loadConfiguration(file);

    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public void addMap(GameMap gameMap) {
        getConfiguration().set("maps." + gameMap.getMapName() + ".location", LocationSerializer.serialize(gameMap.getSpawn()));
        getConfiguration().set("maps." + gameMap.getMapName() + ".height", gameMap.getMaxHeight());
    }

    public void saveConfiguration() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveLoot(ItemStack[] items) {
        getConfiguration().set("items", InventoryStringDeSerializer.toBase64(items));
    }

    public ItemStack[] getLoot() {
        try {
            return InventoryStringDeSerializer.stacksFromBase64(getConfiguration().getString("items"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ItemStack[0];
    }
}

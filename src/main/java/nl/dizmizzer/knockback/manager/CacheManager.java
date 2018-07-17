package nl.dizmizzer.knockback.manager;

import com.google.gson.internal.LazilyParsedNumber;
import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.utils.InventoryStringDeSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
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

public class CacheManager {

    static CacheManager instance = new CacheManager();

    private File file;
    private FileConfiguration configuration;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        return instance;
    }

    public void setup() {
        if (!Knockback.getInstance().getDataFolder().exists()) {
            Knockback.getInstance().getDataFolder().mkdir();
        }

        file = new File(Knockback.getInstance().getDataFolder(), "cache.yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuration = YamlConfiguration.loadConfiguration(file);

    }

    public void saveFile() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cachePlayer(Player player) {
        configuration.set(player.getUniqueId().toString() + ".inventory", InventoryStringDeSerializer.toBase64(player.getInventory().getContents()));
        configuration.set(player.getUniqueId().toString() + ".armor", InventoryStringDeSerializer.toBase64(player.getInventory().getArmorContents()));
    }

    public boolean isCached(UUID id) {
        Knockback.getInstance().getLogger().info(String.valueOf(configuration.contains(id.toString())));
        return configuration.contains(id.toString());
    }

    public String getInventory(UUID id) {
        return configuration.getString(id.toString() + ".inventory");
    }

    public String getArmor(UUID id) {
        return configuration.getString(id.toString() + ".armor");
    }

    public void deletePlayerCache(UUID uniqueId) {
        configuration.set(uniqueId.toString() + ".inventory", null);
        configuration.set(uniqueId.toString() + ".armor", null);
        configuration.set(uniqueId.toString(), null);
    }
}

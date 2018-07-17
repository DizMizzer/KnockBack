package nl.dizmizzer.knockback.listener;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.manager.CacheManager;
import nl.dizmizzer.knockback.manager.ConfigManager;
import nl.dizmizzer.knockback.utils.InventoryStringDeSerializer;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
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
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (CacheManager.getInstance().isCached(e.getPlayer().getUniqueId())) {
            e.getPlayer().teleport(ConfigManager.getInstance().getSpawn());
            try {
                e.getPlayer().getInventory().setContents(InventoryStringDeSerializer.inventoryFromBase64(CacheManager.getInstance().getInventory(e.getPlayer().getUniqueId())).getContents());
                ItemStack[] itemStacks = InventoryStringDeSerializer.inventoryFromBase64(CacheManager.getInstance().getArmor(e.getPlayer().getUniqueId())).getContents();

                List<ItemStack> armor = new ArrayList<>();
                for (ItemStack im : itemStacks) {
                    if (im != null)
                        armor.add(im);
                }

                e.getPlayer().getInventory().setArmorContents(armor.toArray(new ItemStack[armor.size()]));
                CacheManager.getInstance().deletePlayerCache(e.getPlayer().getUniqueId());
                e.getPlayer().updateInventory();
                e.getPlayer().setGameMode(GameMode.SURVIVAL);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}

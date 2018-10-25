package nl.dizmizzer.knockback.listener;

import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.inventory.GUIHolder;
import nl.dizmizzer.knockback.inventory.Icon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory() == null) return;
        if (e.getInventory().getHolder() == null) return;
        if (!(e.getInventory().getHolder() instanceof GUIHolder)) return;
        if (e.getRawSlot() != e.getSlot()) {
            if (e.getView().getTopInventory().getItem(53) == null) {
                e.setCancelled(true);
                return;
            }
            if (e.getView().getTopInventory().getItem(53).getType() != Material.BOOK) {
                e.setCancelled(true);
            }
            return;
        }
        GUIHolder holder = (GUIHolder) e.getInventory().getHolder();


        if (e.getRawSlot() == e.getSlot()) {
            if (e.getInventory().getItem(53) == null) {
                e.setCancelled(true);
                return;
            }
            if (e.getInventory().getItem(53).getType() != Material.BOOK) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) return;
                e.setCurrentItem(e.getCursor());
                e.setCancelled(true);
            } else {
                if (holder.getIcon(e.getRawSlot()) != null) {
                    e.setCancelled(true);
                    holder.getIcon(e.getRawSlot()).execute((Player) e.getWhoClicked(), e.getInventory());
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        GamePlayer gamePlayer = GamePlayer.getGamePlayer(e.getPlayer());

        if (gamePlayer.getGame() == null) {
            return;
        }

        e.setCancelled(true);
    }

}

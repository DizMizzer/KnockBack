package nl.dizmizzer.knockback.listener;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.manager.CacheManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

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
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        GamePlayer gamePlayer = GamePlayer.getGamePlayer(e.getPlayer());
        Knockback.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Colored messages :D");
        if (gamePlayer.getGame() != null)
        CacheManager.getInstance().cachePlayer(e.getPlayer());
    }
}

package nl.dizmizzer.knockback.listener;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.manager.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

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
public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (!Knockback.getInstance().hasEditor(GamePlayer.getGamePlayer(e.getPlayer()))) return;
        try {
            int height = Integer.parseInt(e.getMessage());

            Knockback.getInstance().getGameMap(GamePlayer.getGamePlayer(e.getPlayer())).setMaxHeight(height);
            DataManager.getInstance().addMap(Knockback.getInstance().getGameMap(GamePlayer.getGamePlayer(e.getPlayer())));
            Knockback.getInstance().addMap(Knockback.getInstance().getGameMap(GamePlayer.getGamePlayer(e.getPlayer())));


            e.getPlayer().sendMessage(ChatColor.GREEN + "Map " + ChatColor.YELLOW + Knockback.getInstance().getGameMap(GamePlayer.getGamePlayer(e.getPlayer())).getMapName() + ChatColor.GREEN + " has been added succesfully!");
            Knockback.getInstance().removeEditor(GamePlayer.getGamePlayer(e.getPlayer()));
            e.setCancelled(true);
        } catch (NumberFormatException ex) {
            e.getPlayer().sendMessage(ChatColor.RED + "Couldn't parse message to number! Map will be deleted!");
            Knockback.getInstance().removeEditor(GamePlayer.getGamePlayer(e.getPlayer()));
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (GamePlayer.getGamePlayer(e.getPlayer()).getGame() != null) {
            if (e.getMessage().toLowerCase().replace("/","").startsWith("kb")) {
                return;
            }

            e.setCancelled(true);
        }
    }
}

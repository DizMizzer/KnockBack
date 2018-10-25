package nl.dizmizzer.knockback.listener;

import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.game.GameState;
import nl.dizmizzer.knockback.manager.CacheManager;
import nl.dizmizzer.knockback.manager.ConfigManager;
import nl.dizmizzer.knockback.manager.StringManager;
import nl.dizmizzer.knockback.utils.InventoryStringDeSerializer;
import org.bukkit.GameMode;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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
public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (GamePlayer.getGamePlayer(e.getPlayer()).getGame() == null) return;

        GamePlayer gamePlayer = GamePlayer.getGamePlayer(e.getPlayer());
        Game game = gamePlayer.getGame();

        if (game.getMaxheight() > gamePlayer.getLocation().getY()) {
            if (game.getGameState() == GameState.LOBBY || game.getGameState() == GameState.PREGAME) {
                gamePlayer.teleport(game.getSpawn());
            }
            if (game.getGameState() == GameState.INGAME) {
                gamePlayer.teleport(ConfigManager.getInstance().getSpawn());

                gamePlayer.sendMessage(StringManager.elimination);
                gamePlayer.setGame(null);
                game.removePlayer(gamePlayer);
                e.getPlayer().setGameMode(GameMode.SURVIVAL);

                if (game.getGamePlayers().size() == 1) {
                    game.finishGame(false);
                    return;
                }

                if (game.getGamePlayers().size() < 1) {
                    game.finishGame(false);
                }
            }
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (GamePlayer.getGamePlayer(e.getPlayer()).getGame() == null) return;
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) return;
        GamePlayer gamePlayer = GamePlayer.getGamePlayer(e.getPlayer());
        if (e.getTo().getX() == gamePlayer.getGame().getSpawn().getX() &&
                e.getTo().getY() == gamePlayer.getGame().getSpawn().getY() &&
                e.getTo().getZ() == gamePlayer.getGame().getSpawn().getZ() &&
                e.getTo().getWorld() == gamePlayer.getGame().getSpawn().getWorld()) {
            return;
        }

        return;
    }
}

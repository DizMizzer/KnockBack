package nl.dizmizzer.knockback.listener;

import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.game.GameState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
public class PlayerHitListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;

        GamePlayer hitted = GamePlayer.getGamePlayer((Player) e.getEntity());
        GamePlayer attacker = GamePlayer.getGamePlayer((Player) e.getDamager());

        if (hitted.getGame() == null && attacker.getGame() == null) return;
        if (hitted.getGame() != null && attacker.getGame() == null) {
            e.setCancelled(true);
            return;
        }
        if (hitted.getGame() == null && attacker.getGame() != null) {
            e.setCancelled(true);
            return;
        }
        if (hitted.getGame().getGameid() != attacker.getGame().getGameid()) {
            e.setCancelled(true);
            return;
        }

        if (hitted.getGame().getGameState() != GameState.INGAME) {
            e.setCancelled(true);
            return;
        }
        e.setDamage(0);

    }
}

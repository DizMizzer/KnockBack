package nl.dizmizzer.knockback.Scheduler;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GameMap;
import nl.dizmizzer.knockback.manager.StringManager;
import nl.dizmizzer.knockback.utils.MathUtil;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
public class GameAutoStartScheduler extends BukkitRunnable {

    @Override
    public void run() {
        GameMap gameMap = MathUtil.getClosestTimeMap(Knockback.getInstance().getTimes());
        if (gameMap == null) {
            Knockback.getInstance().getGameMaps().get(ThreadLocalRandom.current().nextInt(0, Knockback.getInstance().getGameMaps().size()));
        }
        Game game = new Game(UUID.randomUUID());
        if (game.createGame()) {
            Knockback.getInstance().getServer().broadcastMessage(StringManager.newgame);
        }
        new GameAutoStartScheduler().runTaskLater(Knockback.getInstance(), MathUtil.closestTime(Knockback.getInstance().getTimes().keySet()) * 20 + 20);
    }
}

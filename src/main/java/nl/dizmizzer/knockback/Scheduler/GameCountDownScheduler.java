package nl.dizmizzer.knockback.Scheduler;

import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.game.GameState;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
public class GameCountDownScheduler extends BukkitRunnable implements GameSchedule {

    private Game game;
    private int countdown;

    //GameState PREGAME -> GameState INGAME
    public GameCountDownScheduler(Game game, int time) {
        this.countdown = time;
        this.game = game;
        for (GamePlayer gamePlayer : game.getGamePlayers()) {
            gamePlayer.teleport(game.getSpawn());
            gamePlayer.sendMessage(StringManager.prefix + ChatColor.AQUA + " Prepare to fight!");
        }
        game.setGameState(GameState.PREGAME);
    }

    @Override
    public void run() {

        if (countdown == 30 || countdown == 15 || countdown == 10 || countdown <= 5) {
        }
        countdown--;

        if (countdown == 0) {
            game.setGameState(GameState.INGAME);
            for (GamePlayer gamePlayer : game.getGamePlayers()) {
                gamePlayer.sendMessage(StringManager.gamestart);
                gamePlayer.getPlayer().getInventory().addItem(getCookie());
            }
            cancel();
        }

    }

    public static ItemStack getCookie() {
        ItemStack cookie = new ItemStack(Material.COOKIE);
        ItemMeta im = cookie.getItemMeta();
        im.setDisplayName(ChatColor.AQUA + "Cookie");
        im.addEnchant(Enchantment.KNOCKBACK, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        cookie.setItemMeta(im);

        return cookie;
    }

    public int getCountdown() {
        return countdown;
    }

    @Override
    public void stopCountdown() {
        if (countdown > 0) {
            for (GamePlayer gamePlayer : game.getGamePlayers()) {
                gamePlayer.sendMessage(ChatColor.AQUA + "Countdown has been stopped!");
            }
            cancel();
        }
    }
}

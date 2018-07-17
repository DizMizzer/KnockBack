package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.Debug;
import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.Scheduler.GameCountDownScheduler;
import nl.dizmizzer.knockback.Scheduler.GameLoadScheduler;
import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.exceptions.WrongStateException;
import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.game.GameState;
import nl.dizmizzer.knockback.manager.CacheManager;
import nl.dizmizzer.knockback.manager.ConfigManager;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
public class JoinCommand implements SubCommand, Debug {

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {

            return;
        }

        Player player = (Player) sender;
        Game game = null;
        for (Game g: Knockback.getInstance().getGames()) {
            if (g.getGameState() != GameState.LOBBY || g.getGamePlayers().size() >= g.getMaxPlayers()) {
                continue;
            }
            game = g;
            break;
        }

        if (game == null) {
            player.sendMessage(StringManager.prefix + ChatColor.RED + " No games available!");
            return;
        }

        GamePlayer gamePlayer = GamePlayer.getGamePlayer(player);
        Knockback.debug(this, game.getGamePlayers().size()); //Amount is 0
        gamePlayer.setGame(game);
        game.addPlayer(gamePlayer);
        Knockback.debug(this, game.getGamePlayers().size()); //Amount is 1
        gamePlayer.teleport(ConfigManager.getInstance().getSpawn());
        Knockback.debug(this, game.getGamePlayers().size()); //Amount is 0 again
        gamePlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
        Knockback.debug(this, game.getGamePlayers().size()); // 0
        CacheManager.getInstance().cachePlayer(gamePlayer.getPlayer());
        Knockback.debug(this, game.getGamePlayers().size()); //0
        gamePlayer.getPlayer().getInventory().clear();
        gamePlayer.sendMessage(StringManager.prefix + " " + ChatColor.AQUA + " You have succesfully joined!");
        Knockback.debug(this, game.getGamePlayers().size()); //0
        for (GamePlayer pla : game.getGamePlayers()) {
            pla.sendMessage(StringManager.prefix + " " + ChatColor.YELLOW + gamePlayer.getPlayer().getDisplayName()+ ChatColor.AQUA + " has joined the game!" + ChatColor.YELLOW + "" + game.getGamePlayers().size() + "/" + game.getMaxPlayers());
        }
        if (game.getGamePlayers().size() >= game.getMinPlayers()) {
            try {
                new GameLoadScheduler(game, 15).runTaskTimer(Knockback.getInstance(), 0, 20);
            } catch (WrongStateException e) {
                e.printStackTrace();
            }
        }


    }

    public String getClassName() {
        return getClass().getName();
    }
}

package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.game.GameState;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
public class StopCommand implements SubCommand {

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("knockback.stop")) return;
        if (args.length <= 1) {
            if (!(sender instanceof Player)) return;
            GamePlayer gamePlayer = GamePlayer.getGamePlayer((Player) sender);
            if (gamePlayer.getGame() == null) {
                gamePlayer.sendMessage(StringManager.prefix + ChatColor.RED + " You aren't in a game!");
                return;
            }
            gamePlayer.getGame().finishGame(true);

            return;
        }

        UUID id = UUID.fromString(args[1]);

        Game g = null;
        for (Game game : Knockback.getInstance().getGames()) {
            if (game.getGameid().toString().equalsIgnoreCase(id.toString())) {
                g = game;
                break;
            }
        }

        if (g == null) {
            sender.sendMessage(StringManager.prefix + ChatColor.RED + " Couldn't find a game!");
            return;
        }
        sender.sendMessage(StringManager.prefix + ChatColor.RED + " Stopped the selected game!");
        g.finishGame(true);
    }
}

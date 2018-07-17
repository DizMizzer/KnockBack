package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.game.GameState;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.ChatColor;
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
public class ForceStartCommand implements SubCommand {

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if (!sender.hasPermission("knockback.force")) return;

        GamePlayer gamePlayer = GamePlayer.getGamePlayer((Player) sender);

        if (gamePlayer.getGame() == null) {
            gamePlayer.sendMessage(StringManager.prefix + ChatColor.RED + " You aren't in a game!");
            return;
        }

        Game game = gamePlayer.getGame();
        if (game.getGameState() != GameState.LOBBY) {
            gamePlayer.sendMessage(StringManager.prefix+ ChatColor.RED + " Your game has already started!");
            return;
        }

        game.startGame(true);
    }
}

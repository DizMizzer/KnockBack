package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
public class CreateCommand implements SubCommand {

    /*
     * Create a new map.
     */
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("knockback.create")) return;
        if (args.length < 2) {
            sender.sendMessage(StringManager.prefix + ChatColor.RED + " Didn't specify a map name!");
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringManager.prefix + ChatColor.RED + " Only players can create maps!");
            return;
        }

        Player player = (Player) sender;
        GamePlayer gamePlayer = GamePlayer.getGamePlayer(player);
        sender.sendMessage(StringManager.prefix + ChatColor.GREEN + " Please type the minimum height in Integers!");

        Knockback.getInstance().addGameEditor(gamePlayer, args[1]);
    }
}

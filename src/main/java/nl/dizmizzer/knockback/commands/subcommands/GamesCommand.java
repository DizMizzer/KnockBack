package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
public class GamesCommand implements SubCommand {

    public void execute(CommandSender sender, String[] args) {
        for (Game game : Knockback.getInstance().getGames()) {
            sender.sendMessage(ChatColor.AQUA + game.getGameid().toString().split("-")[0] +
            " " + ChatColor.YELLOW + game.getGamePlayers().size() + "/" + game.getMaxPlayers() +
            " "+ ChatColor.GOLD + game.getGameState().toString().toLowerCase() +
            " " + game.getGameMap().getMapName());
        }
    }
}

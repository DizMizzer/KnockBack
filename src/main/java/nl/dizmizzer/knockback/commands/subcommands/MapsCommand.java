package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.game.GameMap;
import nl.dizmizzer.knockback.manager.StringManager;
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
public class MapsCommand implements SubCommand {

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("knockback.maps"))
        sender.sendMessage(ChatColor.GOLD +"Mapname: \tIs in Use:");
        for (GameMap gameMap : Knockback.getInstance().getGameMaps()) {
            sender.sendMessage(ChatColor.GOLD + gameMap.getMapName() + ":\t" + gameMap.isUsed());
        }
    }
}
